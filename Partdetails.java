package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Model.Partnosearch.Partnosearch;
import Model.Partnosearch.partnolist;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Partdetails extends AppCompatActivity {
    TextView tv_partno, tv_application, tv_discription, tv_OEpartno, tv_type, tv_status,
            tv_mrp, tv_product, tv_voltage, tv_rating, tv_model, tv_oecustomer, tv_superpartno, tv_tlpn;

    LinearLayout li_partno, li_product, li_voltage, li_rating, li_model, li_oecustomer, li_application,
            li_discription, li_oepartno, li_type, li_status, li_suppressed, li_mrp;
    List<partnolist> PartDetails;
    String Partnumber;
    NetworkConnection net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partdetails);
        net = new NetworkConnection(Partdetails.this);
        PartDetails = new ArrayList<>();
        tv_partno = (TextView) findViewById(R.id.partno);
        tv_application = (TextView) findViewById(R.id.aplctn);
        tv_discription = (TextView) findViewById(R.id.discriptn);
        tv_OEpartno = (TextView) findViewById(R.id.OEpartno);
        tv_type = (TextView) findViewById(R.id.type);
        tv_status = (TextView) findViewById(R.id.status);
        tv_mrp = (TextView) findViewById(R.id.mrp);
        tv_product = (TextView) findViewById(R.id.product);
        tv_voltage = (TextView) findViewById(R.id.voltage);
        tv_rating = (TextView) findViewById(R.id.rating);
        tv_model = (TextView) findViewById(R.id.model);
        tv_oecustomer = (TextView) findViewById(R.id.oecusto);
        tv_superpartno = (TextView) findViewById(R.id.superpartno);

        tv_tlpn = (TextView) findViewById(R.id.ti_partno);

        li_partno = (LinearLayout) findViewById(R.id.li_partno);
        li_product = (LinearLayout) findViewById(R.id.li_product);
        li_voltage = (LinearLayout) findViewById(R.id.li_voltage);
        li_rating = (LinearLayout) findViewById(R.id.li_rating);
        li_model = (LinearLayout) findViewById(R.id.li_model);
        li_oecustomer = (LinearLayout) findViewById(R.id.li_oecustomer);
        li_application = (LinearLayout) findViewById(R.id.li_application);
        li_discription = (LinearLayout) findViewById(R.id.li_discription);
        li_oepartno = (LinearLayout) findViewById(R.id.li_oepartno);
        li_suppressed = (LinearLayout) findViewById(R.id.li_suppressed);
        li_type = (LinearLayout) findViewById(R.id.li_type);
        li_status = (LinearLayout) findViewById(R.id.li_status);
        li_mrp = (LinearLayout) findViewById(R.id.li_mrp);
        Intent i = getIntent();
        Partnumber = i.getStringExtra("Partnumber");
        checkInternet();

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
            final ProgressDialog progressDialog = new ProgressDialog(Partdetails.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Partnosearch> call = service.Partnosearch(Partnumber);
            call.enqueue(new Callback<Partnosearch>() {
                @Override
                public void onResponse(Call<Partnosearch> call, Response<Partnosearch> response) {
                    progressDialog.dismiss();
                    if (response.body().getResult().equals("success")) {
                        tv_superpartno.setText("");
                        PartDetails = response.body().getData();
                        tv_tlpn.setText(Partnumber);
                        tv_partno.setText(PartDetails.get(0).getPartNo());
                        tv_application.setText(PartDetails.get(0).getAppname());
                        tv_discription.setText(PartDetails.get(0).getDescription());
                        tv_OEpartno.setText(PartDetails.get(0).getOemPartno());
                        tv_status.setText(PartDetails.get(0).getProStatus());
                        tv_type.setText(PartDetails.get(0).getProType());
                        tv_mrp.setText(PartDetails.get(0).getMrp());
                        tv_product.setText(PartDetails.get(0).getProductname());
                        tv_voltage.setText(PartDetails.get(0).getPartVolt());
                        tv_rating.setText(PartDetails.get(0).getPartOutputrng());
                        tv_model.setText(PartDetails.get(0).getProModel());
                        tv_superpartno.setText(PartDetails.get(0).getProSupersed());
                        if (tv_status.getText().toString().equals("ACTIVE")) {
                            tv_status.setTextColor(Color.parseColor("#0202ff"));
                        }
                        if (tv_status.getText().toString().equals("INACTIVE")) {
                            tv_status.setTextColor(Color.parseColor("#FFE30311"));
                        }
                        if (tv_status.getText().toString().equals("OBSOLETE")) {
                            tv_status.setTextColor(Color.parseColor("#FFE30311"));
                        }

                        if (!tv_partno.getText().toString().equals("")) {
                            li_partno.setVisibility(View.VISIBLE);
                        } else {
                            li_partno.setVisibility(View.GONE);
                        }
                        if (!tv_application.getText().toString().equals("")) {
                            li_application.setVisibility(View.VISIBLE);
                        } else {
                            li_application.setVisibility(View.GONE);
                        }
                        if (!tv_discription.getText().toString().equals("")) {
                            li_discription.setVisibility(View.VISIBLE);
                        } else {
                            li_discription.setVisibility(View.GONE);
                        }
                        if (!tv_OEpartno.getText().toString().equals("")) {
                            li_oepartno.setVisibility(View.VISIBLE);
                        } else {
                            li_oepartno.setVisibility(View.GONE);
                        }
                        if (!tv_status.getText().toString().equals("")) {
                            li_status.setVisibility(View.VISIBLE);
                        } else {
                            li_status.setVisibility(View.GONE);
                        }
                        if (!tv_type.getText().toString().equals("")) {
                            li_type.setVisibility(View.VISIBLE);
                        } else {
                            li_type.setVisibility(View.GONE);
                        }
                        if (!tv_mrp.getText().toString().equals("")) {
                            li_mrp.setVisibility(View.VISIBLE);
                        } else {
                            li_mrp.setVisibility(View.GONE);
                        }
                        if (!tv_product.getText().toString().equals("")) {
                            li_product.setVisibility(View.VISIBLE);
                        } else {
                            li_product.setVisibility(View.GONE);
                        }
                        if (!tv_voltage.getText().toString().equals("")) {
                            li_voltage.setVisibility(View.VISIBLE);
                        } else {
                            li_voltage.setVisibility(View.GONE);
                        }
                        if (!tv_rating.getText().toString().equals("")) {
                            li_rating.setVisibility(View.VISIBLE);
                        } else {
                            li_rating.setVisibility(View.GONE);
                        }
                        if (!tv_model.getText().toString().equals("")) {
                            li_model.setVisibility(View.VISIBLE);
                        } else {
                            li_model.setVisibility(View.GONE);
                        }
                        if (!tv_oecustomer.getText().toString().equals("")) {
                            li_oecustomer.setVisibility(View.VISIBLE);
                        } else {
                            li_oecustomer.setVisibility(View.GONE);
                        }
                        if (!tv_superpartno.getText().toString().equals("")) {
                            li_suppressed.setVisibility(View.VISIBLE);
                        } else {
                            li_suppressed.setVisibility(View.GONE);
                        }

                        tv_superpartno.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Partnumber = tv_superpartno.getText().toString().trim();
                                checkInternet();
                            }
                        });
                    } else {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Partdetails.this).create();

                        LayoutInflater inflater = (Partdetails.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.emptypartalert, null);
                        alertDialog.setView(dialogView);
                        Button Ok = (Button) dialogView.findViewById(R.id.ok);
                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        final TextView send = (TextView) dialogView.findViewById(R.id.toenquiry);
                        Message.setText("No Exploded View Parts Available !");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Partdetails.this, HomeActivity.class));
                                alertDialog.dismiss();
                            }
                        });
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Partdetails.this, SendEnquiry.class));
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        progressDialog.dismiss();
                        Toast.makeText(Partdetails.this, "No Record Found", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Partnosearch> call, Throwable t) {

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            Partdetails.this).create();

                    LayoutInflater inflater = (Partdetails.this).getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.emptypartalert, null);
                    alertDialog.setView(dialogView);
                    Button Ok = (Button) dialogView.findViewById(R.id.ok);
                    final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                    final TextView send = (TextView) dialogView.findViewById(R.id.toenquiry);
                    Message.setText("No Parts Available !");
                    Ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Partdetails.this, HomeActivity.class));
                            alertDialog.dismiss();
                        }
                    });
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Partdetails.this, SendEnquiry.class));
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();
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