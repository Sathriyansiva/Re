package com.ltvscatalogue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import APIInterface.CategoryAPI;
import Model.Forgot.Forgotresult;
import Model.Login.Loginmodel;
import Model.Register.Registerresult;
import RetroClient.RetroClient;
import Shared.Config;
import network.NetworkConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    String emailPattern ="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    String email,Password;
    EditText ed_email, ed_Password;
    Boolean paswdset=false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    NetworkConnection net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_Password = (EditText) findViewById(R.id.ed_password);
        net = new NetworkConnection(Login.this);
    }
    public void login(View view){
        email=ed_email.getText().toString().trim();
        Password=ed_Password.getText().toString().trim();
        if (email.equals("")|| !email.matches(emailPattern)) {
            ed_email.setError("Please Enter Username");
        } else if (Password.equals("")) {
            ed_Password.setError("Please Enter Password");
        }else {
            if (net.CheckInternet()) {
                getlogin();

            } else {
                Toast.makeText(this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void forgot(View view) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                Login.this).create();

        LayoutInflater inflater = (Login.this).getLayoutInflater();
        View dialog = inflater.inflate(R.layout.forgot, null);
        alertDialog.setView(dialog);

        TextView tologin = (TextView) dialog.findViewById(R.id.tologin);
        Button submit = (Button) dialog.findViewById(R.id.submit);
        final EditText ed_email = (EditText) dialog.findViewById(R.id.email);
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = ed_email.getText().toString().trim();
                if (email.length() == 0 || !email.matches(emailPattern)) {
                    ed_email.setError("Invalid email address");
                } else {
                    ed_email.setText("");
                    if (net.CheckInternet()) {
                        getforgotpassword();
                    } else {
                        Toast.makeText(Login.this, "Please check your network connection and try again!", Toast.LENGTH_SHORT).show();
                    }

                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
    public void getlogin() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Loginmodel> call = service.logint( email,Password);
            call.enqueue(new Callback<Loginmodel>() {
                @Override
                public void onResponse(Call<Loginmodel> call, Response<Loginmodel> response) {
                    if (response.body().getResult().equals("success")) {
                        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("KEY_log_User_Name", email);
                        editor.putString("KEY_log_Password", Password);
                        editor.putBoolean("KEY_isLoggedin",true);
                        editor.apply();
                        ed_email.getText().clear();
                        ed_Password.getText().clear();
                        startActivity(new Intent(Login.this, HomeActivity.class));
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Loginmodel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();

        }
    }
    public void getforgotpassword() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                    R.style.Progress);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);

            progressDialog.show();
            CategoryAPI service = RetroClient.getApiService();

            Call<Forgotresult> call = service.forgotpassword(email);
            call.enqueue(new Callback<Forgotresult>() {
                @Override
                public void onResponse(Call<Forgotresult> call, Response<Forgotresult> response) {
                    if (response.body().getResult().equals("success")) {
                        Toast.makeText(getApplicationContext(), "Please Check Your Email.Your Username Password sent to your registered EmailId", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Your EmailId Not Matched", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Forgotresult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Your EmailId Not Matched", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Poor Network..", Toast.LENGTH_LONG).show();

        }
    }
    public void loginreg(View view) {
        startActivity(new Intent(this, Registration.class));
    }
}
