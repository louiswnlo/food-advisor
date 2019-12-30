package com.louis.foodadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.louis.foodadvisor.Model.ShopInfo;

import java.util.List;

public class ShopInfoActivity extends AppCompatActivity implements RatingBottomDialog.BottomSheetListener{

    Dialog confirmDialog;
    private Integer shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);

        Intent intent = getIntent();
        shopId = intent.getIntExtra("SHOP_ID", 0);

        TextView shopName, shopCategory, shopPrice, shopTime, shopAddress, shopReview;
        ImageView shopPhoto;
        ImageButton backBtn;
        RatingBar ratingBar;
        Button ratingBtn;

        shopName = findViewById(R.id.txt_shop_name);
        shopCategory = findViewById(R.id.txt_shop_category);
        shopPrice = findViewById(R.id.txt_shop_price);
        shopTime = findViewById(R.id.txt_time);
        shopAddress = findViewById(R.id.txt_shop_address);
        shopPhoto = findViewById(R.id.img_shop);
        backBtn = findViewById(R.id.back_btn);
        ratingBar = findViewById(R.id.rating_bar);
        shopReview = findViewById(R.id.num_reviews_txt);
        ratingBtn = findViewById(R.id.rate_btn);

        final ShopInfo shop = databaseOpenAndFind(shopId);

        if (shop != null){
            new DownloadImageTask(shopPhoto)
                    .execute(shop.getShopPhoto());
            shopName.setText(shop.getShopName());
            shopCategory.setText(shop.getShopCategory());
            shopPrice.setText(shop.getShopPrice());
            shopTime.setText(shop.getShopStartTime() + " - " + shop.getShopEndTime());
            shopAddress.setText(shop.getShopAddress());
            ratingBar.setRating(shop.getShopAverageRating());

            Integer shopNumRating = shop.getShopNumOfRating();

            if(shopNumRating == 0){
                shopReview.setText("No Review");
            } else if (shopNumRating == 1){
                shopReview.setText("1 Review");
            } else {
                shopReview.setText(shop.getShopNumOfRating() + " Reviews");
            }

            shopAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ShopInfoActivity.this, MapsActivity.class);
                    intent.putExtra("SHOP_ADDRESS_LAT", shop.getShopAddressLat());
                    intent.putExtra("SHOP_ADDRESS_LONG", shop.getShopAddressLong());
                    intent.putExtra("SHOP_NAME", shop.getShopName());
                    startActivity(intent);
                }
            });
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingBottomDialog bottomSheet = new RatingBottomDialog();
                bottomSheet.show(getSupportFragmentManager(), "RatingBottomSheet");
            }
        });

        confirmDialog = new Dialog(this);

    }

    public ShopInfo databaseOpenAndFind(Integer shopId){

        ShopInfo shop = null;

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        shop = databaseAccess.getOneShopInfo(shopId);

        if(shop != null){
            shop = databaseAccess.getOneShopRating(shop);
        }

        databaseAccess.close();

        return shop;
    }

    @Override
    public void onButtonClicked(Float userRating) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        if(databaseAccess.writeUserRating(shopId, userRating)){
            databaseAccess.close();

            confirmDialog.setContentView(R.layout.popup_congrats);
            confirmDialog.show();

            // Hide after some seconds
            final Handler handler  = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (confirmDialog.isShowing()) {
                        confirmDialog.dismiss();
                    }
                }
            };

            confirmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.removeCallbacks(runnable);

                    Intent intent = new Intent(ShopInfoActivity.this, ShopInfoActivity.class);
                    intent.putExtra("SHOP_ID", shopId);
                    startActivity(intent);
                    finish();
                }
            });

            handler.postDelayed(runnable, 2000);
        }


    }
}
