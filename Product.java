package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.Productadapter;
import Model.Productlist.Productlist;
import RetroClient.RetroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {
    GridView grid_view;
    List<String> catagoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        grid_view = (GridView) findViewById(R.id.gridView);
        catagoryList = new ArrayList<>();
        catagoryList.add("STARTER MOTOR");
        catagoryList.add("ALTERNATOR");
        catagoryList.add("WIPER MOTOR");

        catagoryList.add("DISTRIBUTOR");
        catagoryList.add("CAM SENSOR");///typ
        catagoryList.add("IGNITION COIL");

        catagoryList.add("BLOWER MOTOR");
        catagoryList.add("HALOGEN BULB");
        catagoryList.add("HORN");

        catagoryList.add("FAN MOTOR");
        catagoryList.add("HEAD LAMP");//typ
        catagoryList.add("FILTER");

        grid_view.setAdapter(new Productadapter(Product.this, catagoryList));
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                try {
                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            Product.this).create();

                    LayoutInflater inflater = (Product.this).getLayoutInflater();
                    View dialog = inflater.inflate(R.layout.productalert, null);
                    alertDialog.setView(dialog);

                    LinearLayout ratinglayout = (LinearLayout) dialog.findViewById(R.id.ratingli);
                    LinearLayout typelayout = (LinearLayout) dialog.findViewById(R.id.type);
                    if (i == 3 || i==4 ||i==5||i == 9 || i==10 ||i==11 ) {
                        ratinglayout.setVisibility(View.INVISIBLE);
                    } else {
                        ratinglayout.setVisibility(View.VISIBLE);

                    }

                    ratinglayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i1 = new Intent(Product.this, ProductGrid.class);
                            i1.putExtra("Productchoice", "Rating");
                            i1.putExtra("Product", catagoryList.get(i));
                            startActivity(i1);
                        }
                    });
                    typelayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i1 = new Intent(Product.this, ProductGrid.class);
                            i1.putExtra("Productchoice", "Type");
                            i1.putExtra("Product", catagoryList.get(i));
                            startActivity(i1);

                        }
                    });
                    alertDialog.show();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }


    public void Home(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
