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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.louis.foodadvisor.Model.ShopInfo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    Context context = this;
    private FusedLocationProviderClient mFusedLocationClient;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Find the user location first
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

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

    private void fetchLocation() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to access this feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();


            } else {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

            }
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Logic to handle location object

                               double latitude = location.getLatitude();
                               double longtitude = location.getLongitude();

                                SharedPreferences pref = getSharedPreferences("userLocation", MODE_PRIVATE);
                                pref.edit()
                                        .putString("latitude", String.valueOf(latitude))
                                        .putString("longtitude", String.valueOf(longtitude))
                                        .apply();

                            } else{
                                System.out.println("No");
                            }
                        }
                    });

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else{

            }
        }
    }

    public List<ShopInfo> arrangeShops(List<ShopInfo> shops){

        List<ShopInfo> shoplist = new ArrayList<>();

        for (int i = 0; i < shops.size(); i++) {

            ShopInfo shop = shops.get(i);

            SharedPreferences pref;
            pref = getSharedPreferences("userLocation", MODE_PRIVATE);
            String userLocationLat = pref.getString("latitude", "");
            String userLocationLong = pref.getString("longtitude", "");

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

            shoplist.add(shop);
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
