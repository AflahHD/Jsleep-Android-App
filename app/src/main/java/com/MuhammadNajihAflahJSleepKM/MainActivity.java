/**
 * Class yang berisi activity dari Main
 */
package com.MuhammadNajihAflahJSleepKM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MuhammadNajihAflahJSleepKM.model.Account;
import com.MuhammadNajihAflahJSleepKM.model.Room;
import com.MuhammadNajihAflahJSleepKM.request.BaseApiService;
import com.MuhammadNajihAflahJSleepKM.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Account accountLogin;
    BaseApiService mApiService;
    Context mContext;
    final int PAGE_SIZE = 8;

    /**
     * Method saat tombol Login diklik
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        Button prev = findViewById(R.id.prevButton);
        Button next = findViewById(R.id.nextButton);
        Button go = findViewById(R.id.goButton);

        EditText search = findViewById(R.id.searchMain);

        search.setText("0");
        List<Room> room = requestRoom(0, PAGE_SIZE);

        prev.setOnClickListener(new View.OnClickListener() {
            /**
             * Mengembalikan halaman pada ListView ke halaman sebelumnya
             * @param view
             */
            @Override
            public void onClick(View view) {
                ArrayList<Room> roomList = (ArrayList<Room>) requestRoom(Integer.parseInt(search.getText().toString()) - 1, PAGE_SIZE);
                if (Integer.parseInt(search.getText().toString()) > 0) {
                    search.setText(String.valueOf(Integer.parseInt(search.getText().toString()) - 1));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            /**
             * Mengubah halaman pada ListView ke halaman berikutnya
             * @param view
             */
            @Override
            public void onClick(View view) {
                ArrayList<Room> roomList = (ArrayList<Room>) requestRoom(Integer.parseInt(search.getText().toString()) + 1, PAGE_SIZE);
                search.setText(String.valueOf(Integer.parseInt(search.getText().toString()) + 1));
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            /**
             * Mengubah halaman pada ListView ke halaman yang diinginkan
             * @param view
             */
            @Override
            public void onClick(View view) {
                ArrayList<Room> roomList = (ArrayList<Room>) requestRoom(Integer.parseInt(search.getText().toString()), PAGE_SIZE);
            }
        });
    }

    public class RoomsAdapter extends ArrayAdapter<Room> {
        public RoomsAdapter(Context context, ArrayList<Room> rooms) {
            super(context, 0, rooms);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Room room = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_room, parent, false);
            }
            TextView name = (TextView) convertView.findViewById(R.id.roomName);
            name.setText(room.name);
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.add_button);

        if (accountLogin.renter != null) {
            menuItem.setEnabled(true);
            menuItem.setVisible(true);
        } else {
            // disabled
            menuItem.setEnabled(false);
            menuItem.setVisible(false);
        }
        return true;
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

    /**
     * Menampilkan ListView dari room yang dibuat
     *
     * @param page
     * @param pageSize
     * @return
     */
    protected List<Room> requestRoom(int page, int pageSize) {
        if (page < 0) {
            Toast.makeText(mContext, "There is no page", Toast.LENGTH_SHORT).show();
            return null;
        }
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            /**
             * Method untuk request Room
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    List<Room> roomList;
                    roomList = response.body();
                    RoomsAdapter roomsAdapter = new RoomsAdapter(MainActivity.this, (ArrayList<Room>) roomList);
                    ListView roomView = (ListView) findViewById(R.id.ListView);
                    roomView.setAdapter(roomsAdapter);


                    roomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        /**
                         *
                         * @param parent
                         * @param view
                         * @param position
                         * @param id
                         */
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DetailRoomActivity.selectedRoom = (Room) roomView.getAdapter().getItem(position);
                            Intent move = new Intent(MainActivity.this, DetailRoomActivity.class);
                            startActivity(move);
                        }
                    });
                }
            }

            /**
             * Method untuk menampilkan error
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Page Unreachable!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}


