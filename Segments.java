package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import APIInterface.CategoryAPI;
import Adapter.Applicationadpt;
import Adapter.Marutiadpt;
import Adapter.Segmentadpt;
import Model.Dealerstate.Dealerstatelist;
import Model.Segments.Segment;
import Model.Segments.Segmentlist;
import RetroClient.RetroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Segments extends AppCompatActivity {
    GridView grid_view;

    //    Integer[] imageId = {R.drawable.passangercar, R.drawable.home, R.drawable.home,
//            R.drawable.home, R.drawable.home, R.drawable.home, R.drawable.home,
//            R.drawable.home};
//    List<Segmentlist> segmentlist;
    List<String> segmentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segments);
        grid_view = (GridView) findViewById(R.id.gridView);
        segmentlist = new ArrayList<>();
        segmentlist.add("Two Wheeler");
        segmentlist.add("Three Wheeler");
        segmentlist.add("CAR");
        segmentlist.add("UV");
        segmentlist.add("LCV");
        segmentlist.add("HCV");
        segmentlist.add("Tractor");
        segmentlist.add("Engine");
        segmentlist.add("Equipment");
        segmentlist.add("Marine");
        segmentlist.add("Defense");
        segmentlist.add("Locomotive");

//        final ProgressDialog progressDialog = new ProgressDialog(Segments.this,
//                R.style.Progress);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        CategoryAPI service = RetroClient.getApiService();
//        Call<Segment> call = service.segmentlist();
//        call.enqueue(new Callback<Segment>() {
//            @Override
//            public void onResponse(Call<Segment> call, Response<Segment> response) {
//                //Dismiss Dialog
//                if (response.body().getResult().equals("success")) {
//                    segmentlist = response.body().getData();
//
//                    progressDialog.dismiss();
//                } else {
//                    Toast.makeText(Segments.this, "Error", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }
//            }
//            @Override
//            public void onFailure(Call<Segment> call, Throwable t) {
//                Toast.makeText(Segments.this, "Server Problem", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//        });
        grid_view.setAdapter(new Segmentadpt(Segments.this, segmentlist));
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a = new Intent(Segments.this, ApplicationActivity.class);
//                a.putExtra("Segment", segmentlist.get(i).getSegmentName());
                a.putExtra("Segment", segmentlist.get(i));
                startActivity(a);
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
