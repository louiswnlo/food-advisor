package com.louis.foodadvisor;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.louis.foodadvisor.Model.ShopInfo;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<ShopInfo> shops;
    Context context;

    public RecyclerViewAdapter(Context ct, List<ShopInfo> data){
        context = ct;
        shops = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        new DownloadImageTask(holder.imgShop)
                .execute(shops.get(position).getShopPhoto());

        holder.txtShopDistance.setText(shops.get(position).getShopDistance() + "m");

        holder.txtShopName.setText(shops.get(position).getShopName());
        holder.txtShopAddress.setText(shops.get(position).getShopAddress());
        holder.txtShopCategory.setText(shops.get(position).getShopCategory());
}

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtShopName, txtShopAddress, txtShopCategory, txtShopDistance;
        ImageView imgShop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtShopName = itemView.findViewById(R.id.txt_shop_name);
            txtShopAddress = itemView.findViewById(R.id.txt_shop_address);
            txtShopCategory = itemView.findViewById(R.id.txt_shop_category);
            txtShopDistance = itemView.findViewById(R.id.txt_shop_distance);
            imgShop = itemView.findViewById(R.id.img_shop);
        }
    }


}
