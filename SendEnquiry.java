package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import APIInterface.CategoryAPI;
import Adapter.Distributeradapter;
import Model.Distributerstatefilter.Distributerstatedetails;
import Model.Distributerstatefilter.Distributerstatefilter;
import Model.SendEnquiry.Enquiry;
import RetroClient.RetroClient;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class SendEnquiry extends AppCompatActivity {
    private MaterialSpinner contentspin;
    ArrayList<String> contentlist;
    EditText ed_name, ed_email, ed_phone, ed_company, ed_city, ed_partno, ed_msg;
    String name, emailid, mobileno, Partno, message, city, company, contacttype;
    NetworkConnection net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_enquiry);
        net = new NetworkConnection(SendEnquiry.this);
        contentspin = (MaterialSpinner) findViewById(R.id.contentspin);
        contentspin.setBackground(getResources().getDrawable(R.drawable.autotextback));
        ed_name = (EditText) findViewById(R.id.en_name);
        ed_email = (EditText) findViewById(R.id.en_email);
        ed_phone = (EditText) findViewById(R.id.en_phone);
        ed_company = (EditText) findViewById(R.id.en_company);
        ed_city = (EditText) findViewById(R.id.en_city);
        ed_partno = (EditText) findViewById(R.id.en_partno);
        ed_msg = (EditText) findViewById(R.id.en_mesg);

        contentlist = new ArrayList<String>();
        contentlist.add("--Select Contact Type--");
        contentlist.add("Service Support");
        contentlist.add("Warranty Support");
        contentlist.add("Technical Enquiry");
        contentlist.add("Training Request");
        contentlist.add("Product Enquiry");
        contentlist.add("Service Parts");
        contentlist.add("Dealership Enquiry");
        contentlist.add("Test Equipments");
        contentlist.add("Newsletter");
        contentlist.add("Others");
        contentspin.setItems(contentlist);
        contentspin.setPadding(30, 0, 0, 0);
    }

    public void submit(View view) {
        name = ed_name.getText().toString().trim();
        emailid = ed_email.getText().toString().trim();
        mobileno = ed_phone.getText().toString().trim();
        Partno = ed_partno.getText().toString().trim();
        message = ed_msg.getText().toString().trim();
        city = ed_city.getText().toString().trim();
        company = ed_company.getText().toString().trim();
        contacttype = contentspin.getText().toString().trim();
        if (name.equals("")) {
            ed_name.setError("Enter Your Name");
        } else if (emailid.equals("")) {
            ed_email.setError("Enter Your Name");
        } else if (mobileno.equals("")) {
            ed_phone.setError("Enter Your Name");
        } else if (Partno.equals("")) {
            ed_partno.setError("Enter Your Name");
        } else if (message.equals("")) {
            ed_msg.setError("Enter Your Name");
        } else if (city.equals("")) {
            ed_city.setError("Enter Your Name");
        } else if (company.equals("")) {
            ed_company.setError("Enter Your Name");
        } else if (contacttype.equals("")) {
            contentspin.setError("Enter Your Name");
        }
        checkInternet();
    }
    private void checkInternet() {
        if (net.CheckInternet()) {
            getenquiryresult();
        } else {
            Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
    }
    public void getenquiryresult() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(SendEnquiry.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Enquiry> call = service.enquiry(name, emailid, mobileno, Partno, message, city, company, contacttype);
            call.enqueue(new Callback<Enquiry>() {
                @Override
                public void onResponse(Call<Enquiry> call, Response<Enquiry> response) {
                    if (response.body().getResult().equals("success")) {
//                         ed_name.getText()
//                         ed_email.getText()
//                         ed_phone.getText()
//                        ed_partno.getText()
//                         ed_msg.getText()
//                         ed_city.getText()
//                         ed_company.getText()
//                         contentspin.getText()
                        Toast.makeText(getApplicationContext(), "Enquiry Successfully Submit", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Enquiry Not Submit", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Enquiry> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Enquiry Not Submit", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Please Wait..", Toast.LENGTH_LONG).show();

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
