package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.Applicationadpt;
import Adapter.Dealeradapter;
import Model.AppPartlist.AppPart;
import Model.Dealetstatefilter.Dealerstatefilter;
import Model.OEcustomers.OEcustomerlist;
import Model.OEcustomers.OEcustomers;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationActivity extends AppCompatActivity {
    GridView grid_view;
    ArrayList<String> catagoryList;
    ArrayList<String> imageList;
    Applicationadpt adapter;
    List<OEcustomerlist> oecustlist;
    String segment;
    TextView root_segment;
    NetworkConnection net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        net = new NetworkConnection(ApplicationActivity.this);
        grid_view = (GridView) findViewById(R.id.gridView);
        root_segment = (TextView) findViewById(R.id.root_segment);
        oecustlist = new ArrayList<>();

        Intent i = getIntent();
        segment = i.getStringExtra("Segment");
        root_segment.setText(segment);
        getmake();
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a = new Intent(ApplicationActivity.this, Maruti.class);
                a.putExtra("Make", oecustlist.get(i).getOemCustomer());
                a.putExtra("segment", segment);
                startActivity(a);
            }
        });
    }

    public void getmake() {
        try {
            if (net.CheckInternet()) {
                final ProgressDialog progressDialog = new ProgressDialog(ApplicationActivity.this,
                        R.style.Progress);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                CategoryAPI service = RetroClient.getApiService();

                Call<OEcustomers> call = service.oecustomerlist(segment);
                call.enqueue(new Callback<OEcustomers>() {
                    @Override
                    public void onResponse(Call<OEcustomers> call, Response<OEcustomers> response) {
                        if (response.body().getResult().equals("success")) {
                            oecustlist = response.body().getData();
                            grid_view.setAdapter(new Applicationadpt(ApplicationActivity.this, oecustlist));

                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<OEcustomers> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(ApplicationActivity.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
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
