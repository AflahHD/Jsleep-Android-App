package com.MuhammadNajihAflahJSleepKM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.Account;
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText name,email,password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        Button RegisterButton = findViewById(R.id.RegisterButton);
        name = findViewById(R.id.nameRegister);
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passRegister);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestRegister(name.getText().toString(), email.getText().toString(),
                        password.getText().toString());
            }
        });
    }

    protected Account requestRegister(String name, String email, String password) {
        mApiService.register(name,email,password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Account already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}