/**
 * Class yang berisi activity dari Detail Room
 */

package com.MuhammadNajihAflahJSleepKM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.Facility;
import com.MuhammadNajihAflahJSleepKM.model.Room;
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

public class DetailRoomActivity extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    public static Room selectedRoom;
    /**
     * Method saat tombol Detail Room diklik
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        CheckBox AC = findViewById(R.id.AcCheck2);
        CheckBox Refrigerator = findViewById(R.id.refrigeratorCheck2);
        CheckBox WiFi = findViewById(R.id.wifiCheck2);
        CheckBox Bathtub = findViewById(R.id.BathtubCheck2);
        CheckBox Balcony = findViewById(R.id.balconyCheck2);
        CheckBox Restaurant = findViewById(R.id.restaurantCheck2);
        CheckBox SwimmingPool = findViewById(R.id.PoolCheck2);
        CheckBox FitnessCenter = findViewById(R.id.fitnessCheck2);

        Button book = findViewById(R.id.bookButton);
        Button cancel = findViewById(R.id.cancelbutton);

        TextView NameDetail = findViewById(R.id.nameDetail);
        TextView BedTypeDetail = findViewById(R.id.typeDetail);
        TextView SizeDetail = findViewById(R.id.sizeDetail);
        TextView PriceDetail = findViewById(R.id.priceDetail);
        TextView AddressDetail = findViewById(R.id.addressDetail);

        NameDetail.setText(selectedRoom.name);
        BedTypeDetail.setText(String.valueOf(selectedRoom.size));
        SizeDetail.setText(String.valueOf(selectedRoom.bedType));
        AddressDetail.setText(selectedRoom.address);
        PriceDetail.setText(String.valueOf(selectedRoom.price.price));

        AC.setClickable(false);
        Refrigerator.setClickable(false);
        WiFi.setClickable(false);
        Bathtub.setClickable(false);
        Balcony.setClickable(false);
        Restaurant.setClickable(false);
        SwimmingPool.setClickable(false);
        FitnessCenter.setClickable(false);

        for (int i = 0; i < selectedRoom.facility.length; i++){
            if(selectedRoom.facility[i] == Facility.AC){
                AC.setChecked(true);
            }
            else if(selectedRoom.facility[i] == Facility.Refrigerator){
                Refrigerator.setChecked(true);
            }
            else if(selectedRoom.facility[i] == Facility.WiFi){
                WiFi.setChecked(true);
            }
            else if(selectedRoom.facility[i] == Facility.Bathtub){
                Bathtub.setChecked(true);
            }
            else if(selectedRoom.facility[i] == Facility.Balcony){
                Balcony.setChecked(true);
            }
            else if(selectedRoom.facility[i] == Facility.Restaurant){
                Restaurant.setChecked(true);
            }
            else if(selectedRoom.facility[i] == Facility.SwimmingPool){
                SwimmingPool.setChecked(true);
            }
            else if(selectedRoom.facility[i] == Facility.FitnessCenter){
                FitnessCenter.setChecked(true);
            }
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            /**
             * Method saat tombol cancel diklik
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            /**
             * Method saat tombol book diklik
             * @param view
             */
            @Override
            public void onClick(View view) {
                //PaymentActivity.CurrentPayment = (Payment)
                Intent move = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                startActivity(move);
            }
        });

    }
}