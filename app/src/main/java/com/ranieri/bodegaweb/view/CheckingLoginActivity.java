package com.ranieri.bodegaweb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ranieri.bodegaweb.asyncTask.LoginTask;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.dao.PermissionsDAO;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.User;

import static com.ranieri.bodegaweb.util.Util.updateUser;

public class CheckingLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDAO dao = new UserDAO(this);
        AppSession.user = dao.selectAutoLogin();
        Intent it = null;

        if (AppSession.user == null) {
            it = new Intent(this, LoginActivity.class);
            startActivity(it);
            finish();
        } else {
            //verifica internet
            try {
                AppSession.user.setStatusCode(User.loginOk);
                new LoginTask().execute(User.loginAccount).get();
                if (AppSession.user == null || AppSession.user.getStatusCode() != User.loginOk) {
                    it = new Intent(this, LoginActivity.class);
                } else {
                    updateUser(this, true);
                    it = new Intent(this, MainActivity.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
                it = new Intent(this, MainActivity.class);
            } finally {
                startActivity(it);
                finish();
            }
        }
    }
}
