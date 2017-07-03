package com.ranieri.bodegaweb.asyncTask;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.model.User;

import org.parceler.Parcels;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class LoginTask extends AsyncTask<Bundle, Void, User> {

    @Override
    protected User doInBackground(Bundle... params) {
        OkHttpClient client = new OkHttpClient();
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        Request request;

        String ipv4 = "http://192.168.0.2";
        final String urlTest = ipv4 + ":8080/bodegaWEB/rest/test";

        try {
            request = new Request.Builder().url(urlTest).build();
            client.newCall(request).execute();
        } catch (IOException e) {
            ipv4 = "http://192.168.15.12";
        }

        ipv4 += ":8080/bodegaWEB/rest/login/";

        User user = Parcels.unwrap(params[0].getParcelable("user"));
        int cod = (Integer) params[0].get("cod");

        switch (cod) {
            case User.createAccount:
                ipv4 += "createuser";
                break;
            case User.updateAccount:
                ipv4 += "updateuser";
                break;
            case User.deleteAccount:
                ipv4 += "deleteuser";
                break;
        }
        try {
            String jsonString = gson.toJson(user, User.class);

            RequestBody body = RequestBody.create(json, jsonString);
            request = new Request.Builder().url(ipv4).post(body).build();
            Response response = client.newCall(request).execute();

            jsonString = response.body().string();
            user = gson.fromJson(jsonString, User.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return user;
        }
    }
}