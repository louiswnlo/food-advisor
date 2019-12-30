package com.louis.foodadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.louis.foodadvisor.Model.ShopInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Open the database and get shop infomation
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<ShopInfo> shops = databaseAccess.getShopInfo();
        databaseAccess.close();

        // Rearrange the shop list
        shops = arrangeShops(shops);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, shops);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<ShopInfo> arrangeShops(List<ShopInfo> shops){

        List<ShopInfo> shoplist = new ArrayList<>();

        for (int i = 0; i < shops.size(); i++) {

            ShopInfo shop = shops.get(i);

            SharedPreferences pref;
            pref = getSharedPreferences("userLocation", MODE_PRIVATE);
            String userLocationLat = "0.00";
            userLocationLat = pref.getString("latitude", "");
            String userLocationLong = "0.00";
            userLocationLong = pref.getString("longtitude", "");

            Location userLocation = new Location("userLocation");
            userLocation.setLatitude(Double.valueOf(userLocationLat));
            userLocation.setLongitude(Double.valueOf(userLocationLong));

            Location shopLocation =new Location("shopLocation");
            shopLocation.setLatitude(shop.getShopAddressLat());
            shopLocation.setLongitude(shop.getShopAddressLong());

            float locationDistance = userLocation.distanceTo(shopLocation);

            BigDecimal result;
            result = round(locationDistance,0);

            shop.setShopDistance(result.intValue());

            if(shop.getShopDistance() <= 2000){
                shoplist.add(shop);
            }
        }

        Collections.sort(shoplist, new Comparator<ShopInfo>() {
            @Override
            public int compare(ShopInfo o1, ShopInfo o2) {
                return o1.getShopDistance().compareTo(o2.getShopDistance());
            }
        });

        return shoplist;

    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

}
