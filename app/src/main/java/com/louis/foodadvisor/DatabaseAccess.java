package com.louis.foodadvisor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.louis.foodadvisor.Model.ShopInfo;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteAssetHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if (db != null){
            this.db.close();
        }
    }

    public List<ShopInfo> getShopInfo(){

        Cursor c = db.rawQuery("SELECT * FROM SHOPS", null);

        List<ShopInfo> list = new ArrayList<>();

        while (c.moveToNext()){
                Integer shopID = c.getInt(0);
                String shopName = c.getString(1);
                String shopPhoto = c.getString(2);
                String shopAddress = c.getString(3);
                Double shopAddressLat = c.getDouble(4);
                Double shopAddressLong = c.getDouble(5);
                String shopCategory = c.getString(6);
                String shopStartTime = c.getString(7);
                String shopEndTime = c.getString(8);
                String shopPrice = c.getString(9);

                ShopInfo shop = new ShopInfo(shopID, shopName, shopPhoto, shopAddress, shopAddressLat, shopAddressLong, shopCategory, shopStartTime, shopEndTime, shopPrice);

                list.add(shop);
        }


        c.close();

        return list;
    }

}
