/**
 * Class yang berisi activity dari About Me
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
import androidx.cardview.widget.CardView;

import com.MuhammadNajihAflahJSleepKM.model.Account;
import com.MuhammadNajihAflahJSleepKM.model.Renter;
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    static Account accountLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        Account accountLogin = MainActivity.accountLogin;
        TextView name = findViewById(R.id.nameText);
        TextView email = findViewById(R.id.emailText);
        TextView balance = findViewById(R.id.balanceText);
        EditText amount = findViewById(R.id.amount);
        Button topUpButton = findViewById(R.id.TopUp);

        name.setText(accountLogin.name);
        email.setText(accountLogin.email);
        balance.setText(Double.toString(accountLogin.balance));
        Button registerRenterButton = findViewById(R.id.registerRenterButton);

        EditText name2 = findViewById(R.id.NameAboutme2);
        EditText email2 = findViewById(R.id.addressAboutMe2);
        EditText phone = findViewById(R.id.phoneAboutMe2);
        Button registerButton = findViewById(R.id.register);
        Button cancelButton = findViewById(R.id.cancel);

        TextView name3 = findViewById(R.id.nameText2);
        TextView email3 = findViewById(R.id.addressText);
        TextView phone2 = findViewById(R.id.phoneText);

        CardView card1 = findViewById(R.id.Cardview1);
        CardView card2 = findViewById(R.id.Cardview2);

        registerRenterButton.setVisibility(View.INVISIBLE);
        card1.setVisibility(View.INVISIBLE);
        card2.setVisibility(View.INVISIBLE);

        if (MainActivity.accountLogin.renter == null) {
            registerRenterButton.setVisibility(View.VISIBLE);
        } else {
            name3.setText(accountLogin.renter.username);
            email3.setText(accountLogin.renter.address);
            phone2.setText(accountLogin.renter.phoneNumber);
            registerRenterButton.setVisibility(View.INVISIBLE);
            card2.setVisibility(View.VISIBLE);
        }
        registerRenterButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Menampilkan cardview untuk register renter
             * @param view
             */
            @Override
            public void onClick(View view) {
                registerRenterButton.setVisibility(View.INVISIBLE);
                card1.setVisibility(View.VISIBLE);

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Membuat renter baru
             * @param view
             */
            @Override
            public void onClick(View view) {
                Renter renter = requestRenter(accountLogin.id, name2.getText().toString(), email2.getText().toString(), phone.getText().toString());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Mengembalikan ke halaman sebelumnya
             * @param view
             */
            @Override
            public void onClick(View view) {
                registerRenterButton.setVisibility(View.VISIBLE);
                card1.setVisibility(View.INVISIBLE);
            }
        });
        topUpButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Melakukan topup balance
             * @param view
             */
            @Override
            public void onClick(View view) {
                requestTopUp(accountLogin.id, Double.parseDouble(amount.getText().toString()));
            }
        });
    }

    protected Renter requestRenter(int id, String username, String address, String phoneNumber){
        mApiService.registerRenter (id, username, address, phoneNumber).enqueue(new Callback<Renter>() {
            /**
             * Menampilkan pesan bila renter berhasil dibuat
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if (response.isSuccessful()) {
                    Renter renter;
                    renter = response.body();
                    System.out.println(renter.toString());
                    AboutMeActivity.accountLogin.renter = renter;
                    Toast.makeText(mContext, "Register Renter Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            /**
             * Menampilkan pesan bila renter gagal dibuat
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected void requestTopUp(int id, double balance){
        mApiService.topUp(id, balance).enqueue(new Callback<Boolean>() {
            /**
             * Menampilkan pesan bila topup berhasil
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                MainActivity.accountLogin.balance = MainActivity.accountLogin.balance + balance;
                Toast.makeText(mContext, "Top Up Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AboutMeActivity.this, MainActivity.class);
                startActivity(intent);
            }

            /**
             * Menampilkan pesan bila topup gagal
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Top Up Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

