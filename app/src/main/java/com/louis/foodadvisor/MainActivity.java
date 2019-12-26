package com.louis.foodadvisor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String testShopName[], testShopAddress[], testShopCategory[];
    int images[] = {R.drawable.img_shop_1, R.drawable.img_shop_2,
                    R.drawable.img_shop_1, R.drawable.img_shop_2,
                    R.drawable.img_shop_1, R.drawable.img_shop_2,
                    R.drawable.img_shop_1, R.drawable.img_shop_2,
                    R.drawable.img_shop_1, R.drawable.img_shop_2 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        testShopName = getResources().getStringArray(R.array.test_shop_name);
        testShopAddress = getResources().getStringArray(R.array.test_shop_address);
        testShopCategory = getResources().getStringArray(R.array.test_shop_category);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, testShopName, testShopAddress, testShopCategory, images);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
