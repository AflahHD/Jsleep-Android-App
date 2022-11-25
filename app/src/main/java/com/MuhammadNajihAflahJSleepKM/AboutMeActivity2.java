package com.MuhammadNajihAflahJSleepKM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.Account;
import com.MuhammadNajihAflahJSleepKM.model.Renter;
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity2 extends AppCompatActivity {

    BaseApiService mApiService;
    EditText username,address,phoneNumber;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me2);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        username = findViewById(R.id.nameAboutMe2);
        address = findViewById(R.id.addressAboutMe2);
        phoneNumber = findViewById(R.id.phoneAboutMe2);

        Account accountLogin = MainActivity.accountLogin;
        TextView name = findViewById(R.id.nameText);
        TextView email = findViewById(R.id.emailText);
        TextView balance = findViewById(R.id.balanceText);

        name.setText(accountLogin.name);
        email.setText(accountLogin.email);
        balance.setText(Double.toString(accountLogin.balance));

        Button cancel = findViewById(R.id.cancel);
        Button register = findViewById(R.id.register);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(AboutMeActivity2.this, AboutMeActivity.class);
                startActivity(move);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Renter renter = requestRenter(username.getText().toString(),address.getText().toString(), phoneNumber.getText().toString());
            }
        });
    }

    protected Renter requestRenter(String username, String address, String phoneNumber) {
        mApiService.registerRenter(username, address, phoneNumber).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if (response.isSuccessful()) {
                    Renter renter;
                    renter = response.body();
                    System.out.println(renter.toString());
                    Intent move = new Intent(AboutMeActivity2.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "request renter error", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}