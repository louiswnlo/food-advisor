package com.louis.foodadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    String data1[], data2[], data3[];
    int images[];
    Context context;

    public RecyclerViewAdapter(Context ct, String s1[], String s2[], String s3[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;
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
        holder.mytext1.setText(data1[position]);
        holder.mytext2.setText(data2[position]);
        holder.mytext3.setText(data3[position]);
        holder.myImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mytext1, mytext2, mytext3;
        ImageView myImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mytext1 = itemView.findViewById(R.id.txt_shop_name);
            mytext2 = itemView.findViewById(R.id.txt_shop_address);
            mytext3 = itemView.findViewById(R.id.txt_shop_category);
            myImage = itemView.findViewById(R.id.img_shop);
        }
    }

}
