package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import okhttp3.*;
import okio.BufferedSink;

import java.io.IOException;

import static javax.swing.UIManager.getString;

public class SaveDataLoader {
    OkHttpClient client = new OkHttpClient();

    // variables for prefs
    private int hashcode;
    private int newHashcode;
    private String hashStr;
    private int room = 86574;

    public SaveDataLoader() {
        Preferences prefs = Gdx.app.getPreferences("prefs");
        prefs.putString(String.valueOf(hashcode), "No hash code");
        String user = prefs.getString("hashcode", "newHashcode");

        if (getString(prefs) == null) {
            newHashcode = (int) (Math.random() * 1000 + 1);
            prefs.putString(String.valueOf(newHashcode), "new hash code");
            System.out.println("APER NO PREFS????!??!?!??!?!!!!!");
            System.out.println("hash: " + newHashcode);

            // saving hash code with flush
            prefs.flush();
        } else {
            prefs.getString(String.valueOf(hashcode), "old hash code");
            System.out.println("ba stex mtnum es ap?!?!");
        }
    }

    public void makeRequest() {
        RequestBody fromBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        };
        String url = "http://localhost:8080/api/foo";
        get(url);
        post(fromBody, url);
    }

    // GET method for getting user data from db
    private void get(String uri) {
        final Request request = new Request.Builder()
                .url(uri)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //eshutyun[0]
                System.out.println("Nothing to show");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }

    // FIXME: ADD FUNCTIONALITY FOR POST METHOD
    private void post(RequestBody formBody, String uri) {
        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");
        final Request request = new Request.Builder()
                .url(uri)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //eshutyun[1]
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
        //qaq ker meri
        System.out.println("AXPEEEEEEEEEEERSSSSSSSSSSSSSSSSS SAVE-UM AAAAAAAA");
    }

    public int load() {
        //zibil ker meri
        System.out.println("MERNEM QEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE LOAD-UM ESSSSSSSSSSSSS");
        // room from db
        return room;
    }
}
