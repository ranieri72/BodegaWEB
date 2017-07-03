package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ranieri.bodegaweb.asyncTask.LoginTask;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.User;

import org.parceler.Parcels;

import java.util.concurrent.ExecutionException;

public class CheckingLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDAO dao = new UserDAO(this);
        User user = dao.selectAutoLogin();
        Intent it = null;
        Bundle bundle;

        if (user == null) {
            it = new Intent(this, LoginActivity.class);
            startActivity(it);
            finish();
        } else {
            //verifica internet
            try {
                bundle = new Bundle();
                bundle.putParcelable("user", Parcels.wrap(user));
                bundle.putInt("cod", User.loginAccount);
                user.setStatusCode(User.loginOk);
                user = new LoginTask().execute(bundle).get();
                if (user == null || user.getStatusCode() != User.loginOk) {
                    it = new Intent(this, LoginActivity.class);
                } else {
                    dao.update(user, true);
                    it = new Intent(this, MainActivity.class);
                    it.putExtra("user", Parcels.wrap(user));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                it = new Intent(this, MainActivity.class);
                it.putExtra("user", Parcels.wrap(user));
            } catch (ExecutionException e) {
                e.printStackTrace();
                it = new Intent(this, MainActivity.class);
                it.putExtra("user", Parcels.wrap(user));
            } finally {
                startActivity(it);
                finish();
            }
        }
    }
}
