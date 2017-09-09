package com.ranieri.bodegaweb.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ranieri.bodegaweb.asyncTask.LoginTask;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.User;
import com.ranieri.bodegaweb.util.Util;

public class CheckingLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDAO dao = new UserDAO(this);
        AppSession.user = dao.selectAutoLogin();
        SharedPreferences sharedPreferences = getSharedPreferences(Util.tabletViewPreference, Context.MODE_PRIVATE);
        Util.isTablet = sharedPreferences.getBoolean(Util.tabletViewPreference, true);

        Intent it;
        if (AppSession.user == null) {
            it = new Intent(this, LoginActivity.class);
            startActivity(it);
            finish();
        } else {
            //TODO verifica internet
            try {
                new LoginTask().execute(User.loginAccount).get();
                if (AppSession.user != null && (AppSession.user.getStatusCode() == User.loginOk || AppSession.user.getStatusCode() == User.serverNotFound)) {
                    if (AppSession.user.getStatusCode() != User.serverNotFound) {
                        Util.updateUser(this, true);
                    }
                    Util.intentToMainActivity(this);
                } else {
                    it = new Intent(this, LoginActivity.class);
                    startActivity(it);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Util.intentToMainActivity(this);
            } finally {
                finish();
            }
        }
    }
}
