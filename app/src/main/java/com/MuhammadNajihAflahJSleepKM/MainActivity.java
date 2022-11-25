package com.MuhammadNajihAflahJSleepKM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.Account;
import com.MuhammadNajihAflahJSleepKM.model.Room;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Account accountLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String jsonString = null;

        try {
            InputStream is = getApplicationContext().getAssets().open("randomRoomList.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type listView = new TypeToken<ArrayList<Room>>() { }.getType();
        ArrayList<Room> rooms = gson.fromJson(jsonString, listView);
        RoomsAdapter roomsAdapter = new RoomsAdapter(this, rooms);
        ListView roomList = (ListView) findViewById(R.id.ListView);
        roomList.setAdapter(roomsAdapter);
    }

    public class RoomsAdapter extends ArrayAdapter<Room> {
        public RoomsAdapter(Context context, ArrayList<Room> rooms) {
            super(context, 0, rooms);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Room room = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_room, parent,false);
            }
            TextView name = (TextView) convertView.findViewById(R.id.roomName);
            name.setText(room.name);
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_button:
                Intent AboutMeActivity = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(AboutMeActivity);
                return true;
            case R.id.add_button:
                Intent CreateRoomActivity = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(CreateRoomActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


