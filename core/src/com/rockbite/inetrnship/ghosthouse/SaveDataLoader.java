package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import okhttp3.*;
import okio.BufferedSink;
import org.json.simple.JSONObject;

import java.io.IOException;

public class SaveDataLoader {
    OkHttpClient client = new OkHttpClient();
    Preferences prefs = Gdx.app.getPreferences("data");
    Json json = new Json();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // variables for prefs
    private String hashcode;
    //FIXME: ADD ROOM INDEXES WHEN ROOM CHANGING FUNCTIONALITY IS ADDED
    private int room = 953;

    public SaveDataLoader() {
//        prefs.putString(String.valueOf(hashcode), "No hash code");
//        String user = prefs.getString("hashcode", "newHashcode");

        hashcode = prefs.getString("hashcode", "");
        if (hashcode.equals("")) {
            hashcode = (Math.random() * 1000 + 1) + "";
            prefs.putString("hashcode", hashcode + "");
            prefs.flush();
        }
    }

    public void makeRequest() {
        RequestBody formBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
            }
        };
        String url = "http://localhost:8080/api/foo/" + hashcode;
        get(url);

        post(formBody, "http://localhost:8080/api/foo/");
    }

    // GET method for getting user data from db
    private void get(String uri) {
//        HttpUrl url = new HttpUrl.Builder()
//                .scheme("http")
//                .host("localhost:27017")
//                .addPathSegment("integration_tests")
//                .addQueryParameter("user", prefs + "")
//                .build();
//        System.out.println(url);
        final Request request = new Request.Builder()
                .url(uri)
                //.post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //eshutyun[0]
                System.out.println("Nothing to show");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("APUSH\t" + response.body().string());
            }
        });
    }



    // FIXME: ADD FUNCTIONALITY FOR POST METHOD
    private void post(RequestBody formBody, String uri) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("hashcode", hashcode);
            obj.put("room_id", room);
        } catch (Exception e) {
            System.out.println("GOTOHELL!!! chka qez room talu");
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
//        RequestBody.create(JSON, hashcode);
//
//        requestBody = RequestBody.create(JSON, json.toString());
//
//        final Request request = new Request.Builder()
//                .url(uri)
//                .post(requestBody)
//                .build();

        System.out.println("MAMAAAAAAAAAAAAAAAAAAA" + obj);

        //RequestBody requestBody = RequestBody.create(JSON, json.toString());

        final Request request = new Request.Builder()
                .url(uri)
                .post(requestBody)
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
//        System.out.println("AXPEEEEEEEEEEERSSSSSSSSSSSSSSSSS SAVE-UM AAAAAAAA");
    }

    public int load() {
        //zibil ker meri
//        System.out.println("MERNEM QEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE LOAD-UM ESSSSSSSSSSSSS");
        // room from db
        return room;
    }
}
