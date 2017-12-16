package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import APIInterface.CategoryAPI;
import Adapter.Dealeradapter;
import Adapter.Partadpt;
import Adapter.ProductgridAdapter;
import Model.Dealercity.Dealercitylist;
import Model.Dealercityfilter.Dealercityfilter;
import Model.Dealerstate.Dealerstatelist;
import Model.Dealetstatefilter.Dealerstatedetails;
import Model.Dealetstatefilter.Dealerstatefilter;
import Model.Productsearch.Productsearch;
import Model.Rating.Ratinglist;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dealer extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<String> statelist;
    List<String> citylist;
    Dealeradapter adapter;
    List<Dealerstatedetails> statefilter;
    List<Dealercityfilter> cityfilter;
    String State, City;
    TextView d_state, d_city;
    private Button btn_prev;
    private Button btn_next;
    private int pageCount;
    public int NUM_ITEMS_PAGE = 2;
    public int TOTAL_LIST_ITEMS = 0;
    private int increment = 0;
    public int val = 0;
    LinearLayout Pagination;
    NetworkConnection net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        net = new NetworkConnection(Dealer.this);
        d_state = (TextView) findViewById(R.id.d_state);
        d_city = (TextView) findViewById(R.id.d_city);
        Pagination = (LinearLayout) findViewById(R.id.Pagination);
        statefilter = new ArrayList<>();
        cityfilter = new ArrayList<>();

        statelist = new ArrayList<String>();
        citylist = new ArrayList<String>();
        statelist.add("SELECT STATE");
        citylist.add("SELECT CITY");
        btn_prev = (Button) findViewById(R.id.prev);
        btn_next = (Button) findViewById(R.id.next);
        btn_prev.setVisibility(View.GONE);

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment++;
                CheckEnable();
                getcityfilter();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment--;
                CheckEnable();
                getcityfilter();
            }
        });
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                Dealer.this).create();

        LayoutInflater inflater = (Dealer.this).getLayoutInflater();
        final View dialog = inflater.inflate(R.layout.dealeralert, null);
        alertDialog.setView(dialog);
        final MaterialSpinner statespin = (MaterialSpinner) dialog.findViewById(R.id.statespin);
        final MaterialSpinner cityspin = (MaterialSpinner) dialog.findViewById(R.id.cityspin);
        statespin.setBackground(getResources().getDrawable(R.drawable.autotextback));
        cityspin.setBackground(getResources().getDrawable(R.drawable.autotextback));

        statespin.setItems(statelist);
        statespin.setPadding(30, 0, 0, 0);
        cityspin.setItems(citylist);
        cityspin.setPadding(30, 0, 0, 0);
        final CategoryAPI service = RetroClient.getApiService();
        statespin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (net.CheckInternet()) {

                Call<Dealerstatelist> call = service.dealerstate();
                call.enqueue(new Callback<Dealerstatelist>() {
                    @Override
                    public void onResponse(Call<Dealerstatelist> call, Response<Dealerstatelist> response) {
                        //Dismiss Dialog
                        if (response.body().getResult().equals("success")) {
                            statelist = response.body().getData();
                            Set<String> hs = new HashSet<>();
                            hs.addAll(statelist);
                            statelist.clear();
                            statelist.addAll(hs);

                            Collections.sort(statelist);
                            statelist.add("SELECT STATE");
                            statespin.setItems(statelist);
                            statespin.setPadding(30, 0, 0, 0);
                            State = statespin.getText().toString().trim();
                        } else {
                            Toast.makeText(Dealer.this, "No Record Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Dealerstatelist> call, Throwable t) {
                        Toast.makeText(Dealer.this, "Poor Network..", Toast.LENGTH_SHORT).show();
                    }
                });
                } else {
                    Toast.makeText(Dealer.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cityspin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (net.CheckInternet()) {
                    State = statespin.getText().toString().trim();
                    Call<Dealercitylist> call1 = service.dealercity(State);
                    call1.enqueue(new Callback<Dealercitylist>() {
                        @Override
                        public void onResponse(Call<Dealercitylist> call, Response<Dealercitylist> response) {
                            if (response.body().getResult().equals("success")) {
                                citylist = response.body().getData();
                                Set<String> hs = new HashSet<>();
                                hs.addAll(citylist);
                                citylist.clear();
                                citylist.addAll(hs);
                                Collections.sort(citylist);
                                citylist.add("SELECT CITY");
                                cityspin.setItems(citylist);
                                cityspin.setPadding(30, 0, 0, 0);
                            } else {
                                Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Dealercitylist> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Poor Network..", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(Dealer.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Button search = (Button) dialog.findViewById(R.id.search);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State = statespin.getText().toString().trim();
                City = cityspin.getText().toString().trim();
                d_state.setText(State);
                d_city.setText(City);
                getcityfilter();
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
    }

    private void CheckEnable() {
        try {
            TOTAL_LIST_ITEMS = statefilter.size();
            val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
            val = val == 0 ? 0 : 1;
            pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
            if (increment == pageCount) {
                btn_next.setVisibility(View.GONE);
            } else if (increment == 0) {
                btn_prev.setVisibility(View.GONE);
            } else {
                btn_prev.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void getcityfilter() {
        try {
            if (net.CheckInternet()) {
            final ProgressDialog progressDialog = new ProgressDialog(Dealer.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Dealerstatefilter> call = service.dealercityfilter(State, City);
            call.enqueue(new Callback<Dealerstatefilter>() {
                @Override
                public void onResponse(Call<Dealerstatefilter> call, Response<Dealerstatefilter> response) {
                    if (response.body().getResult().equals("success")) {
                        statefilter = response.body().getData();
                        TOTAL_LIST_ITEMS = statefilter.size();
                        if (TOTAL_LIST_ITEMS < 3) {
                            Pagination.setVisibility(View.GONE);
                        } else {
                            Pagination.setVisibility(View.VISIBLE);
                        }
                        List<Dealerstatedetails> sort = new ArrayList<>();
                        int start = increment * NUM_ITEMS_PAGE;
                        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
                            if (i < TOTAL_LIST_ITEMS) {
                                sort.add(statefilter.get(i));
                            }
                        }
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new Dealeradapter(getApplicationContext(), sort);
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Dealerstatefilter> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } else {
                Toast.makeText(Dealer.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
            }
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
