package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import APIInterface.CategoryAPI;
import Model.Companyrep.Companyrep;
import Model.Register.Registerresult;
import Model.Servicedealer.Servicedealer;
import RetroClient.RetroClient;
import Shared.Config;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    LinearLayout customer, servicedealer, companybranchers;
    EditText ed_name, ed_mobile, ed_email, ed_companname, ed_address, ed_city, ed_country,
            ed_pincode, ed_serviceemail, ed_companyrep_email;
    MaterialSpinner usertype, servicecenters;
    ArrayList<String> Usertypelist, servicecenterlist;
    String Usertype, Servicedealer;
    String name, email, phone, address, city, country, company, pincode;
    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    NetworkConnection net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        net = new NetworkConnection(Registration.this);
        customer = (LinearLayout) findViewById(R.id.customer);
        servicedealer = (LinearLayout) findViewById(R.id.service);
        companybranchers = (LinearLayout) findViewById(R.id.Companybranchers);
        usertype = (MaterialSpinner) findViewById(R.id.usertype);
        servicecenters = (MaterialSpinner) findViewById(R.id.servicecenters);
        ed_serviceemail = (EditText) findViewById(R.id.serv_emailid);
        ed_companyrep_email = (EditText) findViewById(R.id.companyrep_email);
        ed_name = (EditText) findViewById(R.id.name);
        ed_mobile = (EditText) findViewById(R.id.phone);
        ed_email = (EditText) findViewById(R.id.email);
        ed_companname = (EditText) findViewById(R.id.company_name);
        ed_address = (EditText) findViewById(R.id.address);
        ed_city = (EditText) findViewById(R.id.city);
        ed_country = (EditText) findViewById(R.id.country);
        ed_pincode = (EditText) findViewById(R.id.pincode);
        Usertypelist = new ArrayList<String>();
        servicecenterlist = new ArrayList<String>();
        usertype.setBackground(getResources().getDrawable(R.drawable.autotextback));
        servicecenters.setBackground(getResources().getDrawable(R.drawable.autotextback));
        Usertypelist.add("--Select User Type--");
        Usertypelist.add("Customers (Product user , Vehicle Owner)");
        Usertypelist.add("Institutional Customers (Fleet Operators , Organisation,etc.,)");
        Usertypelist.add("Service Centres (Auto-Electrical Workshops)");
        Usertypelist.add("Retailers (Parts Re-Seller)");
        Usertypelist.add("Distributors (Parts Whole-Sellers)");
        Usertypelist.add("Company Branches (LIS & TVS Groups)");
        Usertypelist.add("Company Representatives (Lucas TVS)");
        Usertypelist.add("Others (Not Listed above)");
        usertype.setItems(Usertypelist);
        usertype.setPadding(30, 0, 0, 0);
        servicecenterlist.add("--Select Service Centers--");
        servicecenterlist.add("Lucas TVS Authorised Dealers");
        servicecenterlist.add("Other Auto Electrical Garages");
        servicecenters.setItems(servicecenterlist);
        servicecenters.setPadding(30, 0, 0, 0);
        customer.setVisibility(View.GONE);
        servicedealer.setVisibility(View.GONE);
        companybranchers.setVisibility(View.GONE);
        usertype.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Usertype = usertype.getText().toString().trim();
                if (Usertype.equals("Customers (Product user , Vehicle Owner)")) {
                    customer.setVisibility(View.VISIBLE);
                    ed_companname.setVisibility(View.GONE);
                    servicedealer.setVisibility(View.GONE);
                    companybranchers.setVisibility(View.GONE);

                } else if (Usertype.equals("Service Centres (Auto-Electrical Workshops)")) {
                    servicedealer.setVisibility(View.VISIBLE);
                    customer.setVisibility(View.GONE);
                    companybranchers.setVisibility(View.GONE);
                    ed_serviceemail.setVisibility(View.GONE);
                    Servicedealer = servicecenters.getText().toString().trim();
                    servicecenters.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                            Servicedealer = servicecenters.getText().toString().trim();
                            if (Servicedealer.equals("Other Auto Electrical Garages")) {
                                customer.setVisibility(View.VISIBLE);
                                ed_companname.setVisibility(View.VISIBLE);
                                ed_serviceemail.setVisibility(View.GONE);
                            } else if (Servicedealer.equals("Lucas TVS Authorised Dealers")) {
                                customer.setVisibility(View.GONE);
                                ed_companname.setVisibility(View.GONE);
                                companybranchers.setVisibility(View.GONE);
                                ed_serviceemail.setVisibility(View.VISIBLE);

                            } else if (Servicedealer.equals("--Select Service Centers--")) {
                                customer.setVisibility(View.GONE);
                                ed_companname.setVisibility(View.GONE);
                                companybranchers.setVisibility(View.GONE);
                                ed_serviceemail.setVisibility(View.GONE);
                            }
                        }
                    });
                } else if (Usertype.equals("Company Branches (LIS & TVS Groups)") || Usertype.equals("Company Representatives (Lucas TVS)")) {
                    companybranchers.setVisibility(View.VISIBLE);
                    customer.setVisibility(View.GONE);
                    ed_companname.setVisibility(View.VISIBLE);
                    servicedealer.setVisibility(View.GONE);
                } else if (Usertype.equals("--Select User Type--")) {
                    customer.setVisibility(View.GONE);
                    servicedealer.setVisibility(View.GONE);
                    companybranchers.setVisibility(View.GONE);
                } else {
                    customer.setVisibility(View.VISIBLE);
                    ed_companname.setVisibility(View.VISIBLE);
                    servicedealer.setVisibility(View.GONE);
                    companybranchers.setVisibility(View.GONE);
                }

            }
        });

    }

    public void register(View view) {
        try {

        Usertype = usertype.getText().toString().trim();
        if (Usertype.equals("Customers (Product user , Vehicle Owner)")) {
            Usertype = usertype.getText().toString().trim();
            name = ed_name.getText().toString().trim();
            email = ed_email.getText().toString().trim();
            phone = ed_mobile.getText().toString().trim();
            address = ed_address.getText().toString().trim();
            city = ed_city.getText().toString().trim();
            country = ed_country.getText().toString().trim();
            company = "";
            pincode = ed_pincode.getText().toString().trim();
            if (name.equals("")) {
                ed_name.setError("Please Enter Name");
            } else if (email.equals("") || !email.matches(emailPattern)) {
                ed_email.setError("Please Enter Email Id");
            } else if (phone.equals("")) {
                ed_mobile.setError("Please Enter Mobile Number");
            } else if (address.equals("")) {
                ed_address.setError("Please Enter Address");
            } else if (city.equals("")) {
                ed_city.setError("Please Enter Email City");
            } else if (country.equals("")) {
                ed_country.setError("Please Enter Country");
            } else {
//                getregister();
                checkInternet();
            }
        } else if (Usertype.equals("Service Centres (Auto-Electrical Workshops)")) {

            Servicedealer = servicecenters.getText().toString().trim();
            if (Servicedealer.equals("Other Auto Electrical Garages")) {
                Usertype = servicecenters.getText().toString().trim();
                name = ed_name.getText().toString().trim();
                email = ed_email.getText().toString().trim();
                phone = ed_mobile.getText().toString().trim();
                address = ed_address.getText().toString().trim();
                city = ed_city.getText().toString().trim();
                country = ed_country.getText().toString().trim();
                company = ed_companname.getText().toString().trim();
                pincode = ed_pincode.getText().toString().trim();
                if (name.equals("")) {
                    ed_name.setError("Please Enter Name");
                } else if (email.equals("") || !email.matches(emailPattern)) {
                    ed_email.setError("Please Enter Email Id");
                } else if (phone.equals("")) {
                    ed_mobile.setError("Please Enter Mobile Number");
                } else if (address.equals("")) {
                    ed_address.setError("Please Enter Address");
                } else if (city.equals("")) {
                    ed_city.setError("Please Enter Email City");
                } else if (country.equals("")) {
                    ed_country.setError("Please Enter Country");
                } else if (company.equals("")) {
                    ed_companname.setError("Please Enter Company NAme");
                } else {
//                    getregister();
                    checkInternet();
                }
            } else if (Servicedealer.equals("Lucas TVS Authorised Dealers")) {
                Usertype = servicecenters.getText().toString().trim();
                email = ed_serviceemail.getText().toString().trim();
                if (!email.equals("") || email.matches(emailPattern)) {
                    CategoryAPI service = RetroClient.getApiService();
                    Call<Model.Servicedealer.Servicedealer> call = service.servicedealer(email);
                    call.enqueue(new Callback<Servicedealer>() {
                        @Override
                        public void onResponse(Call<Servicedealer> call, Response<Servicedealer> response) {
                            if (response.body().getResult().equals("success")) {
                                name = response.body().getData().getContactperson();
                                email = response.body().getData().getEmailId();
                                address = response.body().getData().getAddress() + response.body().getData().getAddress1();
                                phone = response.body().getData().getMobileno();
                                city = response.body().getData().getCity();
                                country = ed_country.getText().toString().trim();
                                company = response.body().getData().getDealername();
                                pincode = "";
//                                getregister();
                                checkInternet();
                            } else {
                                Toast.makeText(getApplicationContext(), "Data not Fetched", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Servicedealer> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Data not Fetched", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    ed_serviceemail.setError("Please Enter Email Id");
                }
            } else if (Servicedealer.equals("") || Servicedealer.equals("--Select Service Centers--")) {
                Toast.makeText(Registration.this, "Please Select Service Centers", Toast.LENGTH_SHORT).show();
            }

        } else if (Usertype.equals("Company Representatives (Lucas TVS)")) {
            email = ed_companyrep_email.getText().toString().trim();
            Usertype = usertype.getText().toString().trim();
            String newstring = email;
            String[] separated = newstring.split("@");
            if (email.equals("")|| !email.matches(emailPattern)) {
                ed_companyrep_email.setError("Please Enter Email Id");

            }
           else if (!separated[1].equals("lucastvs.co.in")) {
                ed_companyrep_email.setError("Enter valid email id");
            }
            else {
//                getservice();
                if (net.CheckInternet()) {
                    getservice();
                } else {
                    Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (Usertype.equals("Company Branches (LIS & TVS Groups)")) {
            email = ed_companyrep_email.getText().toString().trim();
            Usertype = usertype.getText().toString().trim();
            String newstring = email;
            String[] separated = newstring.split("@");
            if (email.equals("")|| !email.matches(emailPattern)) {
                ed_companyrep_email.setError("Please Enter Email Id");

            }
            else if (!separated[1].equals("lismail.in") || !separated[1].equals("mastvs.com") || !separated[1].equals("sundarammotors.com")
                    || !separated[1].equals("tvs.in") || !separated[1].equals("tvsoesl.com") || !separated[1].equals("impal.net")) {
                ed_companyrep_email.setError("Enter valid email id");
            }
            else {
//                getservice();
                if (net.CheckInternet()) {
                    getservice();
                } else {
                    Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (Usertype.equals("--Select User Type--") || Usertype.equals("")) {

            Toast.makeText(Registration.this, "Please Select User Type", Toast.LENGTH_SHORT).show();
        } else {

            Usertype = usertype.getText().toString().trim();
            name = ed_name.getText().toString().trim();
            email = ed_email.getText().toString().trim();
            phone = ed_mobile.getText().toString().trim();
            address = ed_address.getText().toString().trim();
            city = ed_city.getText().toString().trim();
            country = ed_country.getText().toString().trim();
            company = ed_companname.getText().toString().trim();
            pincode = ed_pincode.getText().toString().trim();
            if (name.equals("")) {
                ed_name.setError("Please Enter Name");
            } else if (email.equals("") || !email.matches(emailPattern)) {
                ed_email.setError("Please Enter Email Id");
            } else if (phone.equals("")) {
                ed_mobile.setError("Please Enter Mobile Number");
            } else if (address.equals("")) {
                ed_address.setError("Please Enter Address");
            } else if (city.equals("")) {
                ed_city.setError("Please Enter Email City");
            } else if (country.equals("")) {
                ed_country.setError("Please Enter Country");
            } else if (company.equals("")) {
                ed_companname.setError("Please Enter Company NAme");
            } else {
//                getregister();
                checkInternet();
            }
        }
    }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getservice() {
        CategoryAPI service = RetroClient.getApiService();

        Call<Companyrep> call = service.companyrep(email);
        call.enqueue(new Callback<Companyrep>() {
            @Override
            public void onResponse(Call<Companyrep> call, Response<Companyrep> response) {
                if (response.body().getResult().equals("success")) {
                    name = response.body().getData().get(0).getName();
                    email = response.body().getData().get(0).getEmailid();
                    address = response.body().getData().get(0).getAddress();
                    phone = response.body().getData().get(0).getPhoneno();
                    city = response.body().getData().get(0).getCity();
                    country = ed_country.getText().toString().trim();
                    company = response.body().getData().get(0).getCompanyName();
                    pincode = "";
                    checkInternet();
                } else {
                    Toast.makeText(getApplicationContext(), "Data not Fetched", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Companyrep> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Data not Fetched", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getregister() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Registerresult> call = service.register(name, email, phone, company, city, address, country, pincode, Usertype);
            call.enqueue(new Callback<Registerresult>() {
                @Override
                public void onResponse(Call<Registerresult> call, Response<Registerresult> response) {
                    if (response.body().getResult().equals("success")) {
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                Registration.this).create();

                        LayoutInflater inflater = (Registration.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert, null);
                        alertDialog.setView(dialogView);
                        Button Ok = (Button) dialogView.findViewById(R.id.ok);
                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        Message.setText("Thank you for your Registration. Your Username Password sent to your registerd EmailId");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Registration.this, Login.class));
                                alertDialog.dismiss();
//                                sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//                                editor = sharedPreferences.edit();
//                                editor.putBoolean("KEY_paswdset",false);
//                                editor.apply();
                            }
                        });
                        alertDialog.show();
                        progressDialog.dismiss();
                    } else if (response.body().getResult().equals("already exists")) {
                        Toast.makeText(getApplicationContext(), "Email Id Already exists", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Registerresult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Poor Network..", Toast.LENGTH_LONG).show();

        }
    }
    private void checkInternet() {
        if (net.CheckInternet()) {
            getregister();
        } else {
            Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
    }
    public void login(View view) {
        startActivity(new Intent(Registration.this, Login.class));
    }
}
