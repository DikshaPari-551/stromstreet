package com.stormstreet.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.stormstreet.myapplication.R;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Checksocketconnection extends AppCompatActivity {
  private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksocketconnection);
        Dataconnection();
    }

    private void Dataconnection() {
        IO.Options opts = new IO.Options();
        try {
            mSocket = IO.socket("https://node-stromestreet.mobiloitte.com", opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args)
            {
                Log.d("TAG", "call: ");
                // Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // Toast.makeText(getApplicationContext(), "disconnected", Toast.LENGTH_SHORT).show();
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });
        mSocket.connect();

        JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject.put("room_id", orderId);
            jsonObject.put("userId", getIntent().getStringExtra("userId"));
            jsonObject.put("vendorId", getIntent().getStringExtra("vendorId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSocket.emit("join_room", jsonObject);
        mSocket.on("join_room", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[0];
            }
        });

        try {
            mSocket.on("get_location", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            JSONObject obj = (JSONObject) args[0];
                            try {
//                                socketLat = obj.getString("lat");
//                                socketLng = obj.getString("long");
//                                userLocation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}