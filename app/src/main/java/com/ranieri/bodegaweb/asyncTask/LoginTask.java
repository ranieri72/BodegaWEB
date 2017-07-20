package com.ranieri.bodegaweb.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.PostRequester;
import com.ranieri.bodegaweb.model.User;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class LoginTask extends AsyncTask<Integer, Void, Void> {

    @Override
    protected Void doInBackground(Integer... params) {
        OkHttpClient client = new OkHttpClient();
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        Request request;

        String url = "";
        switch (params[0]) {
            case User.loginAccount:
                url = ConnectionConstants.urlLogin;
                break;
            case User.createAccount:
                url = ConnectionConstants.urlCreateUser;
                break;
            case User.updateAccount:
                url = ConnectionConstants.urlUpdateUser;
                break;
            case User.deleteAccount:
                url = ConnectionConstants.urlDeleteUser;
                break;
        }
        try {
            String jsonString = gson.toJson(AppSession.user, User.class);

            Response response = new PostRequester(client, json, url, jsonString).invoke();

            jsonString = response.body().string();
            AppSession.user = gson.fromJson(jsonString, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}