/**
 * Class yang berisi activity dari Payment
 */
package com.MuhammadNajihAflahJSleepKM;

import static com.MuhammadNajihAflahJSleepKM.DetailRoomActivity.selectedRoom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.Payment;
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    public static Payment payment;
    /**
     * Method saat tombol Book diklik
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        TextView namePayment = findViewById(R.id.namePayment);
        TextView pricePayment = findViewById(R.id.pricePayment);
        TextView addressPayment = findViewById(R.id.addressPayment);
        EditText from = findViewById(R.id.from);
        EditText to = findViewById(R.id.to);

        Button pay = findViewById(R.id.payButtonPayment);
        Button back = findViewById(R.id.backbuttonPayment);

        namePayment.setText(selectedRoom.name);
        pricePayment.setText(String.valueOf(selectedRoom.price.price));
        addressPayment.setText(selectedRoom.address);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(PaymentActivity.this, DetailRoomActivity.class );
                startActivity(move);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBooking(MainActivity.accountLogin.id, selectedRoom.id, selectedRoom.id, from.getText().toString(), to.getText().toString());
            }
        });
    }

    protected void requestBooking(int buyerId, int renterId, int roomId, String dateFrom, String dateTo){
        mApiService.createPayment(buyerId, renterId, roomId, dateFrom, dateTo).enqueue(new Callback<Payment>() {

            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful()) {
                    payment = response.body();
                    acceptBooking(payment.id);
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Failed to Pay", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(mContext, "Failed to Pay", Toast.LENGTH_SHORT).show();
            }
        });

    }
    protected void acceptBooking(int id) {
        mApiService.accept(id).enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                MainActivity.accountLogin.balance = MainActivity.accountLogin.balance - selectedRoom.price.price;

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

}