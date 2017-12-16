package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.Applicationadpt;
import Adapter.Marutiadpt;
import Model.Modellist.AppmodelList;
import Model.Modellist.Model;
import Model.OEcustomers.OEcustomers;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Maruti extends AppCompatActivity {
    GridView grid_view;

    String Segment,Make;
    List<AppmodelList>Modelist;
    TextView root_segment,root_make;
    private Button btn_prev;
    private Button btn_next;
    private int pageCount;
    public int NUM_ITEMS_PAGE = 12;
    public int TOTAL_LIST_ITEMS = 0;
    private int increment = 0;
    public int val=0;
    LinearLayout Pagination;
    NetworkConnection net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maruti);
        net = new NetworkConnection(Maruti.this);
        grid_view = (GridView) findViewById(R.id.gridView);
        root_segment=(TextView)findViewById(R.id.root_segment);
        root_make=(TextView)findViewById(R.id.root_make);

        Modelist= new ArrayList<>();
        Intent i=getIntent();
        Segment=i.getStringExtra("segment");
        Make=i.getStringExtra("Make");
        root_segment.setText(Segment);
        root_make.setText(Make);
        getmodel();
        Pagination=(LinearLayout)findViewById(R.id.Pagination) ;

        btn_prev = (Button) findViewById(R.id.prev);
        btn_next = (Button) findViewById(R.id.next);
        btn_prev.setVisibility(View.GONE);

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment++;
                CheckEnable();
                getmodel();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment--;
                CheckEnable();
                getmodel();
            }
        });
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a=new Intent(Maruti.this,PartList.class);
                a.putExtra("segment",Segment);
                a.putExtra("Make",Make);
                a.putExtra("Model",Modelist.get(i).getModel());
                startActivity(a);
            }
        });
    }
    private void CheckEnable() {
        try {
            TOTAL_LIST_ITEMS = Modelist.size();
            val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
            val = val == 0 ? 0 : 1;
            pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;
            if (increment >= pageCount) {
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
    public void getmodel() {
        try {
            if (net.CheckInternet()) {
            final ProgressDialog progressDialog = new ProgressDialog(Maruti.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            CategoryAPI service = RetroClient.getApiService();

            Call<Model> call = service.modellist(Segment,Make);
            call.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    if (response.body().getResult().equals("success")) {
                        Modelist = response.body().getData();
                        Modelist = response.body().getData();
                        TOTAL_LIST_ITEMS=Modelist.size();
                        if(TOTAL_LIST_ITEMS<13){
                            Pagination.setVisibility(View.GONE);
                        }else {
                            Pagination.setVisibility(View.VISIBLE);
                        }
                        List< AppmodelList> sort = new ArrayList<>();
                        int start = increment * NUM_ITEMS_PAGE;
                        for ( int i = start; i < (start)+NUM_ITEMS_PAGE; i++) {
                            if(i<TOTAL_LIST_ITEMS)
                            {
                                sort.add(Modelist.get(i));
                            }
                        }
                        grid_view.setAdapter(new Marutiadpt(Maruti.this, sort));

                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(Maruti.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
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
