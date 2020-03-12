package com.louis.foodadvisor;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double shopLat;
    private double shopLong;
    private String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        shopLat = intent.getDoubleExtra("SHOP_ADDRESS_LAT", 0.0);
        shopLong = intent.getDoubleExtra("SHOP_ADDRESS_LONG", 0.0);
        shopName = intent.getStringExtra("SHOP_NAME");

        TextView txtShopName;
        ImageButton backBtn;
        backBtn = findViewById(R.id.back_btn);

        txtShopName = findViewById(R.id.txt_shop_name);
        txtShopName.setText(shopName);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng shop = new LatLng(shopLat, shopLong);
        mMap.addMarker(new MarkerOptions().position(shop).title(shopName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop, 18.0f));
    }
}
