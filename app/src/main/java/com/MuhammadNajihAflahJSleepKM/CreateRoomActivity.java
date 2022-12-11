/**
 * Class yang berisi activity dari Create Room
 */
package com.MuhammadNajihAflahJSleepKM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.BedType;
import com.MuhammadNajihAflahJSleepKM.model.City;
import com.MuhammadNajihAflahJSleepKM.model.Facility;
import com.MuhammadNajihAflahJSleepKM.model.Room;
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    ArrayList<Facility> facilities = new ArrayList<>();
    /**
     * Method saat tombol Create Room diklik
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        EditText name = findViewById(R.id.nameCreateRoom);
        EditText price = findViewById(R.id.priceCreateRoom);
        EditText address = findViewById(R.id.addressCreateRoom);
        EditText size = findViewById(R.id.sizeCreateRoom);

        CheckBox AC = findViewById(R.id.AcCheck2);
        CheckBox refrigerator = findViewById(R.id.refrigeratorCheck2);
        CheckBox wifi = findViewById(R.id.wifiCheck2);
        CheckBox bathtub = findViewById(R.id.BathtubCheck2);
        CheckBox balcony = findViewById(R.id.balconyCheck2);
        CheckBox restaurant = findViewById(R.id.restaurantCheck2);
        CheckBox pool = findViewById(R.id.PoolCheck2);
        CheckBox fitness = findViewById(R.id.fitnessCheck2);

        Spinner citySpin = findViewById(R.id.citySpinner);
        Spinner bedSpin = findViewById(R.id.bedSpinner);
        citySpin.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));
        bedSpin.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));

        Button cancel = findViewById(R.id.cancelButton);
        Button create = findViewById(R.id.createButton);

        final City[] citySelect = new City[1];

        citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Method saat memilih item pada spinner city
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                citySelect[0] = (City) citySpin.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

        final BedType[] bedTypeSelect = new BedType[1];
        bedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Method saat memilih item pada spinner BedType
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bedTypeSelect[0] = (BedType) bedSpin.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            /**
             * Method saat mengklik tombol Create
             * @param view
             */
            @Override
            public void onClick(View view) {

                if (AC.isChecked()) {
                    facilities.add(Facility.AC);
                } else {
                    facilities.remove(Facility.AC);
                }
                if (wifi.isChecked()) {
                    facilities.add(Facility.WiFi);
                } else {
                    facilities.remove(Facility.WiFi);
                }
                if (refrigerator.isChecked()) {
                    facilities.add(Facility.Refrigerator);
                } else {
                    facilities.remove(Facility.Refrigerator);
                }
                if (bathtub.isChecked()) {
                    facilities.add(Facility.Bathtub);
                } else {
                    facilities.remove(Facility.Bathtub);
                }
                if (balcony.isChecked()) {
                    facilities.add(Facility.Balcony);
                } else {
                    facilities.remove(Facility.Balcony);
                }
                if (restaurant.isChecked()) {
                    facilities.add(Facility.Restaurant);
                } else {
                    facilities.remove(Facility.Restaurant);
                }
                if (pool.isChecked()) {
                    facilities.add(Facility.SwimmingPool);
                } else {
                    facilities.remove(Facility.SwimmingPool);
                }
                if (fitness.isChecked()) {
                    facilities.add(Facility.FitnessCenter);
                } else {
                    facilities.remove(Facility.FitnessCenter);
                }
                Facility[] facilitySelect = new Facility[facilities.size()];
                facilities.toArray(facilitySelect);

                Room room = requestRoom(MainActivity.accountLogin.id,
                        name.getText().toString(),
                        Integer.parseInt(size.getText().toString()),
                        Double.parseDouble(price.getText().toString()),
                        facilitySelect,
                        citySelect[0],
                        address.getText().toString(),
                        bedTypeSelect[0]);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });
    }

    /**
     * Method untuk request room
     *
     * @param id
     * @param name
     * @param size
     * @param price
     * @param facility
     * @param city
     * @param address
     * @param bedType
     * @return
     */
    protected Room requestRoom(int id, String name, int size, double price, Facility[] facility, City city, String address, BedType bedType) {
        mApiService.createRoom(id, name, size, price, facility, city, address, bedType).enqueue(new Callback<Room>() {
            /**
             * Method bila request room berhasil
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                Room newRoom;
                newRoom = response.body();
                Toast.makeText(mContext, "Create Room Success", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }

            /**
             * Method bila request room gagal
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Create Room Failed", Toast.LENGTH_SHORT).show();
                System.out.println(t);
            }
        });
        return null;
    }
}
