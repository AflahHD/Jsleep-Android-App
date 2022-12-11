/**
 * Class yang berisi activity dari Login
 */
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
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText email,password;
    Context mContext;

    /**
     * Method saat aplikasi diklik
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        Button LoginButton = findViewById(R.id.LoginButton);
        TextView register = findViewById(R.id.RegisterNowButton);
        email = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        TextView mainActivity = findViewById(R.id.LoginButton);

        register.setOnClickListener(new View.OnClickListener() {
            /**
             * Melakukan intent ke RegisterActivity
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        mainActivity.setOnClickListener(new View.OnClickListener() {
            /**
             * Melakukan intent ke MainActivity
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(move);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Melakukan request account bila tombol login diklik
             * @param view
             */
            @Override
            public void onClick(View view) {
                Account account = requestAccount();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestLogin(email.getText().toString(), password.getText().toString());
            }
        });


    }

    protected Account requestAccount() {
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            /**
             * Method untuk request account
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id=0", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected Account requestLogin(String email, String password) {
        mApiService.login(email,password).enqueue(new Callback<Account>() {
            /**
             * Method untuk request login
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    MainActivity.accountLogin = account;
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "login error", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}