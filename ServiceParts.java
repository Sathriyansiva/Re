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

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.ServicePartadapter;
import Adapter.Serviceadaptertwo;
import Model.FullUnitsearch.Fullunitsearch;
import Model.FullUnitsearch.Fullunitsearchlist;
import Model.FullUnitsearch.SerFullunitsearch;
import Model.FullUnitsearch.Serfullunitsearchlist;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceParts extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ServicePartadapter adapter;
    Serviceadaptertwo serviceadapter2;
    String Partnumber;
    List<Fullunitsearchlist> Fullunitsearchlist;
    List<Serfullunitsearchlist> SerFullunitsearchlist;
    private ArrayList<String> sino;
    int count = 1;
    private Button btn_prev;
    private Button btn_next;
    private int pageCount;
    public int NUM_ITEMS_PAGE = 8;
    public int TOTAL_LIST_ITEMS = 0;
    private int increment = 0;
    public int val = 0;
    LinearLayout Pagination;
    TextView Root;
    NetworkConnection net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_parts);
        net = new NetworkConnection(ServiceParts.this);
        Root = (TextView) findViewById(R.id.root1);
        sino = new ArrayList<>();
        Fullunitsearchlist = new ArrayList<>();
        SerFullunitsearchlist = new ArrayList<>();
        Intent i = getIntent();
        Partnumber = i.getStringExtra("Partnumber");
        Root.setText(Partnumber);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        checkInternet();
        Pagination = (LinearLayout) findViewById(R.id.Pagination);
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
    }

    private void CheckEnable() {
        try {
            TOTAL_LIST_ITEMS = Fullunitsearchlist.size();
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

    private void checkInternet() {
        if (net.CheckInternet()) {
            getpartdetails();
        } else {
            Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getpartdetails() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(ServiceParts.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            final CategoryAPI service = RetroClient.getApiService();

            Call<Fullunitsearch> call = service.fullsearchlist(Partnumber);
            call.enqueue(new Callback<Fullunitsearch>() {
                @Override
                public void onResponse(Call<Fullunitsearch> call, Response<Fullunitsearch> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("success")) {
                        Fullunitsearchlist = response.body().getData();
                        TOTAL_LIST_ITEMS = Fullunitsearchlist.size();
                        if (TOTAL_LIST_ITEMS < 9) {
                            Pagination.setVisibility(View.GONE);
                        } else {
                            Pagination.setVisibility(View.VISIBLE);
                        }
                        if (Fullunitsearchlist.size() > 0) {
                            for (int i1 = 1; i1 <= Fullunitsearchlist.size(); i1++) {
                                String sio = String.valueOf(count);
                                sino.add(sio);
                                count++;
                            }
                        }
                        ArrayList<String> sino1 = new ArrayList<>();
                        List<Fullunitsearchlist> sort = new ArrayList<>();
                        int start = increment * NUM_ITEMS_PAGE;
                        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
                            if (i < TOTAL_LIST_ITEMS) {
                                sort.add(Fullunitsearchlist.get(i));
                                sino1.add(sino.get(i));
                            }
                        }
                        layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ServicePartadapter(getApplicationContext(), sort, sino1);
                        recyclerView.setAdapter(adapter);

                    } else if (response.body().getResult().equals("ServiceSuccess")) {
                        getservicedetails();
                    } else {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                ServiceParts.this).create();

                        LayoutInflater inflater = (ServiceParts.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.emptypartalert, null);
                        alertDialog.setView(dialogView);
                        Button Ok = (Button) dialogView.findViewById(R.id.ok);
                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        final TextView send = (TextView) dialogView.findViewById(R.id.toenquiry);
                        Message.setText("No Exploded View Parts Available !");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ServiceParts.this, HomeActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ServiceParts.this, SendEnquiry.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        progressDialog.dismiss();
                        Toast.makeText(ServiceParts.this, "No Record Found", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Fullunitsearch> call, Throwable t) {
                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            ServiceParts.this).create();

                    LayoutInflater inflater = (ServiceParts.this).getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.emptypartalert, null);
                    alertDialog.setView(dialogView);
                    Button Ok = (Button) dialogView.findViewById(R.id.ok);
                    final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                    final TextView send = (TextView) dialogView.findViewById(R.id.toenquiry);
                    Message.setText("No Exploded View Parts Available !");
                    Ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ServiceParts.this, HomeActivity.class));
                            alertDialog.dismiss();
                        }
                    });
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ServiceParts.this, SendEnquiry.class));
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    progressDialog.dismiss();
                    Toast.makeText(ServiceParts.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Poor Network..", Toast.LENGTH_LONG).show();
        }
    }

    public void getservicedetails() {
        CategoryAPI service = RetroClient.getApiService();
        Call<SerFullunitsearch> call = service.serfullsearchlist(Partnumber);
        call.enqueue(new Callback<SerFullunitsearch>() {
            @Override
            public void onResponse(Call<SerFullunitsearch> call, Response<SerFullunitsearch> response) {
                if (response.body().getResult().equals("ServiceSuccess")) {

                    SerFullunitsearchlist = response.body().getData();
                    TOTAL_LIST_ITEMS = SerFullunitsearchlist.size();
                    if (TOTAL_LIST_ITEMS < 9) {
                        Pagination.setVisibility(View.GONE);
                    } else {
                        Pagination.setVisibility(View.VISIBLE);
                    }
                    if (SerFullunitsearchlist.size() > 0) {
                        for (int i1 = 1; i1 <= SerFullunitsearchlist.size(); i1++) {
                            String sio = String.valueOf(count);
                            sino.add(sio);
                            count++;
                        }
                    }
                    ArrayList<String> sino1 = new ArrayList<>();
                    List<Serfullunitsearchlist> sort1 = new ArrayList<>();
                    int start = increment * NUM_ITEMS_PAGE;
                    for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
                        if (i < TOTAL_LIST_ITEMS) {
                            sort1.add(SerFullunitsearchlist.get(i));
                            sino1.add(sino.get(i));
                        }
                    }
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    serviceadapter2 = new Serviceadaptertwo(getApplicationContext(), sort1);
                    recyclerView.setAdapter(serviceadapter2);
                }

            }

            @Override
            public void onFailure(Call<SerFullunitsearch> call, Throwable t) {
                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                        ServiceParts.this).create();

                LayoutInflater inflater = (ServiceParts.this).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.emptypartalert, null);
                alertDialog.setView(dialogView);
                Button Ok = (Button) dialogView.findViewById(R.id.ok);
                final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                final TextView send = (TextView) dialogView.findViewById(R.id.toenquiry);
                Message.setText("No Exploded View Parts Available !");
                Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ServiceParts.this, HomeActivity.class));
                        alertDialog.dismiss();
                    }
                });
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ServiceParts.this, SendEnquiry.class));
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
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