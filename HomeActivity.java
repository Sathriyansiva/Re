package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import APIInterface.CategoryAPI;
import Model.ChangePassword.Changepassresult;
import Model.FullUnitNumber.Fullunitnolist;
import Model.Partnolist.Partnolist;
import Model.Partnosearch.Partnosearch;
import Model.Partnosearch.partnolist;
import RetroClient.RetroClient;
import Shared.Config;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<String> Partlist;
    List<String> Fullunitlist;
    List<partnolist> PartDetails;
    String Partno;
    String OLDPASS, NEWPASS, CONFIRMPASS;
    String User_Name, Password;
    NetworkConnection net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        net = new NetworkConnection(HomeActivity.this);
        Partlist = new ArrayList<>();
        Fullunitlist = new ArrayList<>();
        PartDetails = new ArrayList<>();
        sharedPreferences = this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        User_Name = sharedPreferences.getString("KEY_log_User_Name", " ");
        Password = sharedPreferences.getString("KEY_log_Password", " ");
    }

    public void application(View view) {
        Intent i = new Intent(HomeActivity.this, Segments.class);
        startActivity(i);
    }

    public void product(View view) {
        Intent i = new Intent(HomeActivity.this, Product.class);
        startActivity(i);
    }

    public void part(View view) {
        try {
            if (net.CheckInternet()) {


            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                    HomeActivity.this).create();

            LayoutInflater inflater = (HomeActivity.this).getLayoutInflater();
            View dialog = inflater.inflate(R.layout.partalert, null);
            alertDialog.setView(dialog);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);

            Button submit = (Button) dialog.findViewById(R.id.submit);
            final AutoCompleteTextView at_part = (AutoCompleteTextView) dialog.findViewById(R.id.part_no);
            CategoryAPI service = RetroClient.getApiService();

            Call<Partnolist> call = service.PartNumberlist();

            call.enqueue(new Callback<Partnolist>() {
                @Override
                public void onResponse(Call<Partnolist> call, Response<Partnolist> response) {
                    //Dismiss Dialog
                    if (response.isSuccessful()) {
                        Partlist = response.body().getData();
                        Collections.sort(Partlist);
                        ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1, Partlist);
                        at_part.setAdapter(adapter);
                        Partno = at_part.getText().toString();

                    } else {
                        Toast.makeText(HomeActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Partnolist> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Partno = at_part.getText().toString();
                        if (!Partno.equals("")) {
                            Intent i = new Intent(HomeActivity.this, Partdetails.class);
                            i.putExtra("Partnumber", Partno);
                            startActivity(i);
                        } else {
                            at_part.setError("Please Enter Part Number");

//                            Toast.makeText(HomeActivity.this, "Please Enter Part Number", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            } else {
                Toast.makeText(HomeActivity.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
            }
        }
       catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void network(View view) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                HomeActivity.this).create();
        LayoutInflater inflater = (HomeActivity.this).getLayoutInflater();
        View dialog = inflater.inflate(R.layout.networkalert, null);
        alertDialog.setView(dialog);
        LinearLayout deler = (LinearLayout) dialog.findViewById(R.id.servicedeler);
        LinearLayout distibuter = (LinearLayout) dialog.findViewById(R.id.didtibuter);

        deler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(HomeActivity.this, Dealer.class);
                    startActivity(i);
                    alertDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }


            }
        });
        distibuter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(HomeActivity.this, Distributer.class);
                    startActivity(i);
                    alertDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }


            }
        });
        alertDialog.show();

    }

    public void serviceparts(View view) {
        if (net.CheckInternet()) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                HomeActivity.this).create();
        LayoutInflater inflater = (HomeActivity.this).getLayoutInflater();
        View dialog = inflater.inflate(R.layout.servicepartalert, null);
        alertDialog.setView(dialog);
        Button submit = (Button) dialog.findViewById(R.id.submit);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final AutoCompleteTextView at_part = (AutoCompleteTextView) dialog.findViewById(R.id.ed_fullunit);
        CategoryAPI service = RetroClient.getApiService();

        Call<Fullunitnolist> call = service.fullunitlist();

        call.enqueue(new Callback<Fullunitnolist>() {
            @Override
            public void onResponse(Call<Fullunitnolist> call, Response<Fullunitnolist> response) {
                //Dismiss Dialog
                if (response.isSuccessful()) {
                    Fullunitlist = response.body().getData();
                    Collections.sort(Fullunitlist);
                    ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1, Fullunitlist);
                    at_part.setAdapter(adapter);
                    Partno = at_part.getText().toString();

                } else {
                    Toast.makeText(HomeActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Fullunitnolist> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Poor Network..", Toast.LENGTH_SHORT).show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Partno = at_part.getText().toString();
                    if (!Partno.equals("")) {
                        Intent i = new Intent(HomeActivity.this, ServiceParts.class);
                        i.putExtra("Partnumber", Partno);
                        startActivity(i);
                        alertDialog.dismiss();
                    } else {
                        at_part.setError("Please Enter Part Number");
//                            Toast.makeText(HomeActivity.this, "Please Enter Part Number", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    } else {
        Toast.makeText(HomeActivity.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
    }
    }

    public void sendenquiry(View view) {
        Intent i = new Intent(HomeActivity.this, SendEnquiry.class);
        startActivity(i);
    }

    public void exit(View view) {
        try {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
            alertDialog.setTitle("");
            alertDialog.setMessage("Are you sure want to Exit?");
            alertDialog.setIcon(R.drawable.close);
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(com.ltvscatalogue.HomeActivity.this);
//        alertDialog.setTitle("Hi");
        alertDialog.setMessage("Are you sure want to Logout?");
//        alertDialog.setIcon(R.drawable.close);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putBoolean("KEY_isLoggedin", false);

                            editor.apply();
                            startActivity(new Intent(com.ltvscatalogue.HomeActivity.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(com.ltvscatalogue.HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public void Changepswd(View view) {
        try {
            if (net.CheckInternet()) {
            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                    HomeActivity.this).create();

            LayoutInflater inflater = (HomeActivity.this).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.changepassword, null);
            alertDialog.setView(dialogView);

            Button Cancel = (Button) dialogView.findViewById(R.id.ch_cancel);
            Button save = (Button) dialogView.findViewById(R.id.ch_save);

            final EditText old_passwd = (EditText) dialogView.findViewById(R.id.ed_oldPass);
            final EditText New_Password = (EditText) dialogView.findViewById(R.id.ed_newPass);
            final EditText Confirm_pass = (EditText) dialogView.findViewById(R.id.ed_confirmPass);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OLDPASS = old_passwd.getText().toString().trim();
                    NEWPASS = New_Password.getText().toString().trim();
                    CONFIRMPASS = Confirm_pass.getText().toString().trim();

                    if (OLDPASS.equals("")) {
                        old_passwd.setError("Please Enter Old Password");
                    } else if (NEWPASS.equals("")) {
                        New_Password.setError("Please Enter New Password");
                    } else if (CONFIRMPASS.equals("")) {
                        Confirm_pass.setError("Please Enter Confirm New Password");
                    } else if (!NEWPASS.equals(CONFIRMPASS)) {
                        Confirm_pass.setError("Please Check Confirm New Password");
                        Toast.makeText(HomeActivity.this, "Please Check New Password", Toast.LENGTH_SHORT).show();
                    } else {
                        CHPSW();
                        alertDialog.dismiss();
                    }
                }
            });

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        } else {
            Toast.makeText(HomeActivity.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void CHPSW() {
        try {
            if (net.CheckInternet()) {
            final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Changepassresult> call = service.changepassword(User_Name, OLDPASS, NEWPASS);
            call.enqueue(new Callback<Changepassresult>() {
                @Override
                public void onResponse(Call<Changepassresult> call, Response<Changepassresult> response) {
                    if (response.body().getResult().equals("success")) {
                        progressDialog.dismiss();
                        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                HomeActivity.this).create();

                        LayoutInflater inflater = (HomeActivity.this).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.alert, null);
                        alertDialog.setView(dialogView);


                        Button Ok = (Button) dialogView.findViewById(R.id.ok);


                        final TextView Message = (TextView) dialogView.findViewById(R.id.msg);
                        Message.setText("Password has Changed Successfully");
                        Ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(HomeActivity.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                                alertDialog.dismiss();
                            }
                        });


                        alertDialog.show();
//                        Toast.makeText(Home.this, "Your Password Changed", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(HomeActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Changepassresult> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
        Toast.makeText(HomeActivity.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
        }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

}
