package com.example.appbanhang.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LoaispAdapter;
import com.example.appbanhang.adapter.SanphamAdapter;
import com.example.appbanhang.model.Loaisp;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="";
    String hinhanhloaisp="";
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActionBar();
            }
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSanPhamMoiNhat();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"bạn hãy kiểm tra lại kết nối");
            finish();
        }


    }

    private void GetDuLieuSanPhamMoiNhat() {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    int ID=0;
                    String Tensanpham="";
                    Integer Giasanphamm=0;
                    String Hinhanhsanpham="";
                    String Motasanpham="";
                    int IDSanpham=0;

                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tensanpham=jsonObject.getString("tensp");
                            Giasanphamm=jsonObject.getInt("giasp");
                            Hinhanhsanpham=jsonObject.getString("hinhanhsp");
                            Motasanpham=jsonObject.getString("motasp");
                            IDSanpham=jsonObject.getInt("idsanpham");

                            mangsanpham.add(new Sanpham(id,Tensanpham,Giasanphamm,Hinhanhsanpham,Motasanpham,IDSanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloaisp");
                            hinhanhloaisp=jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new Loaisp(0,"Liên hệ","https://img.icons8.com/officel/2x/user-menu-male.png"));
                    mangloaisp.add(4,new Loaisp(0,"Thông tin","https://img.icons8.com/plasticine/2x/information.png"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/2019/11/banner/thang-oppo-800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/11/banner/thu-cu-note10-800-300-800x300-(2).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/11/banner/800-300-800x300-(15).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/11/banner/800-300-800x300-(19).png");
        for (int i=0;i<mangquangcao.size();i++)
        {
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void Anhxa() {
        toolbar= (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh=(RecyclerView) findViewById(R.id.recyclerview);
        navigationView=(NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh=(ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp=new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chính","https://img.icons8.com/cute-clipart/2x/home.png"));
        loaispAdapter=new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        mangsanpham=new ArrayList<>();
        sanphamAdapter=new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
    }
}
