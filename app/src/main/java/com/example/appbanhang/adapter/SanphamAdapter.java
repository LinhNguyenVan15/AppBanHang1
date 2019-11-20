package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.net.PortUnreachableException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class SanphamAdapter extends RecyclerView.Adapter <SanphamAdapter.ItemHolder>{
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham=arraysanpham.get(position);
        holder.txttensaanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Gía: "+decimalFormat.format(sanpham.getGiasanpham())+"Đ");
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.ic_no_img_black_24dp)
                .error(R.drawable.cancel)
                .into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensaanpham,txtgiasanpham;
        public ItemHolder(View itemView){
            super(itemView);
            imghinhsanpham=(ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham=(TextView) itemView.findViewById(R.id.textviewgiasanpham);
            txttensaanpham=(TextView) itemView.findViewById(R.id.textviewtensanpham);
        }
    }
}
