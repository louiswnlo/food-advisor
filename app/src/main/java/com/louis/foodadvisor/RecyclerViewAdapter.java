package com.louis.foodadvisor;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.louis.foodadvisor.Model.ShopInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        new DownloadImageTask(holder.imgShop)
                .execute(shops.get(position).getShopPhoto());

        holder.txtShopDistance.setText(shops.get(position).getShopDistance() + "m");
        holder.txtShopName.setText(shops.get(position).getShopName());
        holder.txtShopName.setTag(shops.get(position).getShopID());
        holder.txtShopAddress.setText(shops.get(position).getShopAddress());
        holder.txtShopCategory.setText(shops.get(position).getShopCategory());

        if(checkOpenOrClose(shops.get(position).getShopStartTime(), shops.get(position).getShopEndTime())){
            holder.txtShopOpen.setBackgroundResource(R.color.colorGreenOpen);
            holder.txtShopOpen.setText("營");
        } else {
            holder.txtShopOpen.setBackgroundResource(R.color.colorRedOpen);
            holder.txtShopOpen.setText("休");
        }

        holder.cardShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopInfoActivity.class);
                intent.putExtra("SHOP_ID", shops.get(position).getShopID());
                context.startActivity(intent);
            }
        });

}

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardShop;
        TextView txtShopName, txtShopAddress, txtShopCategory, txtShopDistance, txtShopOpen;
        ImageView imgShop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardShop = itemView.findViewById(R.id.card_shop);
            txtShopName = itemView.findViewById(R.id.txt_shop_name);
            txtShopAddress = itemView.findViewById(R.id.txt_shop_address);
            txtShopCategory = itemView.findViewById(R.id.txt_shop_category);
            txtShopDistance = itemView.findViewById(R.id.txt_shop_distance);
            txtShopOpen = itemView.findViewById(R.id.txt_shop_open);
            imgShop = itemView.findViewById(R.id.img_shop);
        }
    }

    public boolean checkOpenOrClose(String openTime, String closeTime){

            try {
                String string1 = openTime;
                Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(time1);
                calendar1.add(Calendar.DATE, 1);


                String string2 = closeTime;
                Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(time2);
                calendar2.add(Calendar.DATE, 1);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                Date d = new SimpleDateFormat("HH:mm").parse(currentTime);
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(d);
                calendar3.add(Calendar.DATE, 1);

                Date x = calendar3.getTime();
                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                    return true;
                }

                return false;

            } catch (ParseException e){

            }

            return false;

    }


}
