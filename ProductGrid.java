package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.Partadpt;
import Adapter.Productadapter;
import Adapter.ProductgridAdapter;
import Model.Partnosearch.Partnosearch;
import Model.Productlist.Productlist;
import Model.Productsearch.Productfilterlist;
import Model.Productsearch.Productsearch;
import Model.Rating.Ratinglist;
import Model.Type.Typelist;
import Model.Voltage.Voltagelist;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductGrid extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<Productfilterlist> Producfilter;
    ProductgridAdapter adapter;

    private List<String> voltagespinlist, ratingspinlist, typespinlist;
    String select, Product;
    String Rating, Voltage, Type;
    View vw;
    int selectedid;
    TextView root_make;
    private Button btn_prev;
    private Button btn_next;
    private int pageCount;
    public int NUM_ITEMS_PAGE = 8;
    public int TOTAL_LIST_ITEMS = 0;
    private int increment = 0;
    public int val=0;
    LinearLayout Pagination;
    int count = 1;
    NetworkConnection net;
    private ArrayList<String> sino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_grid);
        net = new NetworkConnection(ProductGrid.this);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        root_make = (TextView) findViewById(R.id.root_make);
        Producfilter = new ArrayList<>();
        sino = new ArrayList<>();
        voltagespinlist = new ArrayList<String>();
        ratingspinlist = new ArrayList<String>();
        typespinlist = new ArrayList<String>();
        voltagespinlist.add("Please select voltage");
        ratingspinlist.add("Please select rating");
        typespinlist.add("Please select type");
        vw = (View) findViewById(R.id.vw);


        Intent i = getIntent();
        select = i.getStringExtra("Productchoice");
        Product = i.getStringExtra("Product");
        root_make.setText(Product);
        Pagination=(LinearLayout)findViewById(R.id.Pagination) ;

        btn_prev = (Button) findViewById(R.id.prev);
        btn_next = (Button) findViewById(R.id.next);
        btn_prev.setVisibility(View.GONE);

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment++;
                CheckEnable();
                checkInternet();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment--;
                CheckEnable();
                checkInternet();
            }
        });
        if (select.equals("Rating")) {
            try {
                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                        ProductGrid.this).create();

                LayoutInflater inflater = (ProductGrid.this).getLayoutInflater();
                final View dialog = inflater.inflate(R.layout.productgridalert, null);
                alertDialog.setView(dialog);
                final MaterialSpinner voltagespin = (MaterialSpinner) dialog.findViewById(R.id.voltagespin);
                final MaterialSpinner ratingspin = (MaterialSpinner) dialog.findViewById(R.id.ratingspin);
                final MaterialSpinner typespin = (MaterialSpinner) dialog.findViewById(R.id.typespin);
                Button search = (Button) dialog.findViewById(R.id.search);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                typespin.setBackground(getResources().getDrawable(R.drawable.autotextback));
                voltagespin.setBackground(getResources().getDrawable(R.drawable.autotextback));
                ratingspin.setBackground(getResources().getDrawable(R.drawable.autotextback));
                voltagespin.setVisibility(View.VISIBLE);
                ratingspin.setVisibility(View.VISIBLE);
                typespin.setVisibility(View.GONE);
                voltagespin.setItems(voltagespinlist);
                voltagespin.setPadding(30, 0, 0, 0);
                ratingspin.setItems(ratingspinlist);
                ratingspin.setPadding(30, 0, 0, 0);
                ratingspin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (net.CheckInternet()) {
                            CategoryAPI service = RetroClient.getApiService();
                            Call<Ratinglist> call = service.ratinglist(Product, voltagespin.getText().toString().trim());
                            call.enqueue(new Callback<Ratinglist>() {
                                @Override
                                public void onResponse(Call<Ratinglist> call, Response<Ratinglist> response) {
                                    //Dismiss Dialog
                                    if (response.body().getResult().equals("success")) {
                                        ratingspinlist = new ArrayList<String>();
                                        ratingspinlist = response.body().getData();
                                        ratingspinlist.add("Please select rating");
                                        ratingspin.setItems(ratingspinlist);
                                        ratingspin.setPadding(30, 0, 0, 0);
                                        Rating = ratingspin.getText().toString().trim();

                                    } else {

                                        Toast.makeText(ProductGrid.this, "No Ratings Available", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Ratinglist> call, Throwable t) {
                                    Toast.makeText(ProductGrid.this, "No Ratings Available", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(ProductGrid.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                voltagespin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (net.CheckInternet()) {

                        CategoryAPI service = RetroClient.getApiService();
                        Call<Voltagelist> call1 = service.voltagelist(Product);
                        call1.enqueue(new Callback<Voltagelist>() {
                            @Override
                            public void onResponse(Call<Voltagelist> call, Response<Voltagelist> response) {
                                //Dismiss Dialog
                                if (response.body().getResult().equals("success")) {
                                    voltagespinlist = response.body().getData();
                                    voltagespinlist.add("Please select voltage");
                                    voltagespin.setItems(voltagespinlist);
                                    voltagespin.setPadding(30, 0, 0, 0);
                                    Voltage = voltagespin.getText().toString().trim();
                                } else {
                                    Toast.makeText(ProductGrid.this, "No Voltage Available", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Voltagelist> call, Throwable t) {
                                Toast.makeText(ProductGrid.this, "No Voltage Available", Toast.LENGTH_SHORT).show();
                            }
                        });
                        }  else {
                            Toast.makeText(ProductGrid.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Rating = ratingspin.getText().toString().trim();
                        Voltage = voltagespin.getText().toString().trim();
                        if (Rating.equals("Please select rating")){
                            Rating="";
                        }
                        if (Voltage.equals("Please select voltage")){
                            Voltage="";
                        }
                        Type = "";
                        checkInternet();
                        alertDialog.dismiss();

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                        ProductGrid.this).create();

                LayoutInflater inflater = (ProductGrid.this).getLayoutInflater();
                final View dialog = inflater.inflate(R.layout.productgridalert, null);
                alertDialog.setView(dialog);
                final MaterialSpinner voltagespin = (MaterialSpinner) dialog.findViewById(R.id.voltagespin);
                final MaterialSpinner ratingspin = (MaterialSpinner) dialog.findViewById(R.id.ratingspin);
                final MaterialSpinner typespin = (MaterialSpinner) dialog.findViewById(R.id.typespin);
                Button search = (Button) dialog.findViewById(R.id.search);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);

                typespin.setBackground(getResources().getDrawable(R.drawable.autotextback));
                voltagespin.setBackground(getResources().getDrawable(R.drawable.autotextback));
                ratingspin.setBackground(getResources().getDrawable(R.drawable.autotextback));
                voltagespin.setVisibility(View.GONE);
                ratingspin.setVisibility(View.GONE);
                typespin.setVisibility(View.VISIBLE);

                typespin.setItems(typespinlist);
                typespin.setPadding(30, 0, 0, 0);
                typespin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (net.CheckInternet()) {
                            CategoryAPI service = RetroClient.getApiService();
                            Call<Typelist> call2 = service.typelist(Product);
                            call2.enqueue(new Callback<Typelist>() {
                                @Override
                                public void onResponse(Call<Typelist> call, Response<Typelist> response) {
                                    //Dismiss Dialog
                                    if (response.body().getResult().equals("success")) {
                                        typespinlist = response.body().getData();
                                        typespinlist.add("Please select type");
                                        typespin.setItems(typespinlist);
                                        typespin.setPadding(30, 0, 0, 0);
                                        Type = typespin.getText().toString().trim();
                                    } else {
                                        Toast.makeText(ProductGrid.this, "No Type Available", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Typelist> call, Throwable t) {
                                    Toast.makeText(ProductGrid.this, "No Type Available", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(ProductGrid.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Rating = "";
                        Voltage = "";
                        Type = typespin.getText().toString().trim();
                        if (Type.equals("Please select type")){
                            Type="";
                        }
                        checkInternet();
                        alertDialog.dismiss();

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    private void CheckEnable() {
        try {
            TOTAL_LIST_ITEMS=Producfilter.size();
            val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
            val = val == 0 ? 0 : 1;
            pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
            if (increment  == pageCount) {
                btn_next.setVisibility(View.GONE);
            } else if (increment == 0) {
                btn_prev.setVisibility(View.GONE);
            } else {
                btn_prev.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void checkInternet() {
        if (net.CheckInternet()) {
            getProductdetails();
        } else {
            Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
    }
    public void getProductdetails() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(ProductGrid.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Productsearch> call = service.productsearch(Product, Rating, Type, Voltage);
            call.enqueue(new Callback<Productsearch>() {
                @Override
                public void onResponse(Call<Productsearch> call, Response<Productsearch> response) {
                    if (response.body().getResult().equals("success")) {
                        Producfilter = response.body().getData();

                        TOTAL_LIST_ITEMS=Producfilter.size();
                        if(TOTAL_LIST_ITEMS<9){
                            Pagination.setVisibility(View.GONE);
                        }else {
                            Pagination.setVisibility(View.VISIBLE);
                        }
                        if (Producfilter.size() > 0) {
                            for (int i1 = 1; i1 <= Producfilter.size(); i1++) {
                                String sio = String.valueOf(count);
                                sino.add(sio);
                                count++;
                            }
                        }
                         ArrayList<String> sino1 = new ArrayList<>();
                        List<Productfilterlist> sort = new ArrayList<>();
                        int start = increment * NUM_ITEMS_PAGE;
                        for ( int i = start; i < (start)+NUM_ITEMS_PAGE; i++) {
                            if(i<TOTAL_LIST_ITEMS)
                            {
                                sort.add(Producfilter.get(i));
                                sino1.add(sino.get(i));
                            }
                        }
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ProductgridAdapter(getApplicationContext(), sort,sino1);
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Productsearch> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Poor Network..", Toast.LENGTH_LONG).show();

        }

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