package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.Marutiadpt;
import Adapter.Partadpt;
import Model.AppPartlist.AppPArtDetails;
import Model.AppPartlist.AppPart;
import Model.Modellist.Model;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<AppPArtDetails> Partdetails;

    Partadpt adapter;
    String Segment, Make, Model;
    TextView root_segment,root_make,root_model;
    private Button btn_prev;
    private Button btn_next;
    private int pageCount;
    public int NUM_ITEMS_PAGE = 2;
    public int TOTAL_LIST_ITEMS = 0;
    private int increment = 0;
    public int val=0;
    LinearLayout Pagination;
    NetworkConnection net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_list);
        net = new NetworkConnection(PartList.this);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        root_segment=(TextView)findViewById(R.id.root_segment);
        root_make=(TextView)findViewById(R.id.root_make);
        root_model=(TextView)findViewById(R.id.root_model);
        Intent i = getIntent();
        Segment = i.getStringExtra("segment");
        Make = i.getStringExtra("Make");
        Model = i.getStringExtra("Model");

        root_segment.setText(Segment);
        root_make.setText(Make);
        root_model.setText(Model);
        Partdetails=new ArrayList<>();
        checkInternet();
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
    }
    private void CheckEnable() {
        try {
            TOTAL_LIST_ITEMS=Partdetails.size();
            val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
            val = val == 0 ? 0 : 1;
            pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
            if (increment  >= pageCount) {
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
            getpartlist();
        } else {
            Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
    }
    public void getpartlist() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(PartList.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<AppPart> call = service.apppartlist(Segment, Make,Model);
            call.enqueue(new Callback<AppPart>() {
                @Override
                public void onResponse(Call<AppPart> call, Response<AppPart> response) {
                    if (response.body().getResult().equals("success")) {
                        Partdetails = response.body().getData();
                        TOTAL_LIST_ITEMS=Partdetails.size();
                        if(TOTAL_LIST_ITEMS<3){
                            Pagination.setVisibility(View.GONE);
                        }else {
                            Pagination.setVisibility(View.VISIBLE);
                        }
                        List< AppPArtDetails> sort = new ArrayList<>();
                        int start = increment * NUM_ITEMS_PAGE;
                        for ( int i = start; i < (start)+NUM_ITEMS_PAGE; i++) {
                            if(i<TOTAL_LIST_ITEMS)
                            {
                                sort.add(Partdetails.get(i));
                            }
                        }
                        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new Partadpt(getApplicationContext(),sort);
                        recyclerView.setAdapter(adapter);

                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AppPart> call, Throwable t) {
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
