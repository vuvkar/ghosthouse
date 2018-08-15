package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import okhttp3.*;

import java.io.IOException;

import static javax.swing.UIManager.getString;

public class SaveDataLoader {

    // variables for prefs
    OkHttpClient client = new OkHttpClient();

    private int hashcode;
    private int newHashcode;
    private String hashStr;
    private int room = 86574;

    public  SaveDataLoader(){
        Preferences prefs = Gdx.app.getPreferences("prefs");
        prefs.putString(String.valueOf(hashcode), "No hash code");
        System.out.println(prefs);
        String user = prefs.getString("hashcode", "newHashcode");

        if (getString(prefs) == null) {
            newHashcode = (int)(Math.random() * 1000 + 1);
            prefs.putString(String.valueOf(newHashcode), "new hash code");
            prefs.flush();
        } else {
            prefs.getString(String.valueOf(hashcode), "old hash code");
        }
    }

    public void makeRequest() {
        String url = "http://localhost:8080/api/foo";
        get(url);
    }

    private void get(String uri) {
        final Request request = new Request.Builder()
                .url(uri)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //eshutyun
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }

    // FIXME: ADD FUNCTIONALITY
    private void post(RequestBody formBody, String uri) {
        final Request request = new Request.Builder()
                .url(uri)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void save(int room) {
        //qaq ker meri
        System.out.println("AXPEEEEEEEEEEERSSSSSSSSSSSSSSSSS");
    }

    public int load() {
        //zibil ker meri
        System.out.println("MERNEM QEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        // room from db
        return room;
    }
}
