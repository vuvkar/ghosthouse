package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import okhttp3.*;
import org.json.simple.JSONObject;

import java.io.IOException;

public class SaveDataLoader {
    OkHttpClient client = new OkHttpClient();
    Preferences prefs = Gdx.app.getPreferences("data");

    static String URI = "http://10.10.29.126:8080/api/foo/";

    // variables for prefs
    private String hashcode;

    public SaveDataLoader() {
        hashcode = prefs.getString("hashcode", "");
        if (hashcode.equals("")) {
            hashcode = (Math.random() * 1000 + 1) + "";
            prefs.putString("hashcode", hashcode + "");
            prefs.flush();
        }
    }

    // GET method for getting user data from db
    private void get() {
        System.out.println("get");
        final Request request = new Request.Builder()
                .url(URI)
                .header("hashcode", hashcode)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                GhostHouse.isLoaded = true;
            }
        });
    }

    // FIXME: ADD FUNCTIONALITY FOR POST METHOD
    private void post(int roomID) {
        System.out.println("put");
        JSONObject obj = new JSONObject();
        try {
            obj.put("hashcode", hashcode);
            obj.put("room_id", roomID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        final Request request = new Request.Builder()
                .url(URI)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Nothing to show");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("POST");
                System.out.println(response.body().string());
            }
        });
    }

    public void save(int room) {
        post(room);
    }

    public void load() {
        get();
    }
}