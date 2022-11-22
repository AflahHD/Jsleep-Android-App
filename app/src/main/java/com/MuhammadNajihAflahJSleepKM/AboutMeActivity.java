package com.MuhammadNajihAflahJSleepKM;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.Account;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        Account accountLogin = MainActivity.accountLogin;
        TextView name = findViewById(R.id.nameText);
        TextView email = findViewById(R.id.emailText);
        TextView balance = findViewById(R.id.balanceText);

        name.setText(accountLogin.name);
        email.setText(accountLogin.email);
        balance.setText(Double.toString(accountLogin.balance));
    }
}