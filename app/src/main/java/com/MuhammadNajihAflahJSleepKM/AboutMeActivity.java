package com.MuhammadNajihAflahJSleepKM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Button registerRenterButton = findViewById(R.id.registerRenterButton);

        registerRenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(AboutMeActivity.this, AboutMeActivity2.class);
                startActivity(move);
            }
        });

    }
}