package com.ranieri.bodegaweb.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.ConnectionRequester;
import com.ranieri.bodegaweb.model.User;

import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class LoginTask extends AsyncTask<Integer, Void, Void> {

    @Override
    protected Void doInBackground(Integer... params) {
        Gson gson = new Gson();

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

            Response response = new ConnectionRequester(url).postRequester(jsonString);

            jsonString = response.body().string();
            AppSession.user = gson.fromJson(jsonString, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            AppSession.user.setStatusCode(User.serverNotFound);
        }
        return null;
    }
}