package com.ranieri.bodegaweb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.asyncTask.LoginTask;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.User;
import com.ranieri.bodegaweb.util.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.ranieri.bodegaweb.util.Util.createUser;
import static com.ranieri.bodegaweb.util.Util.updateUser;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtLogin)
    EditText mEdtLogin;

    @BindView(R.id.edtPassword)
    EditText mEdtPassword;

    boolean autoLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.login));
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignIn)
    void signInButton(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                boolean error = false;
                String login = mEdtLogin.getText().toString();
                String password = mEdtPassword.getText().toString();
                if (Objects.equals(login, "")) {
                    error = true;
                }
                if (Objects.equals(password, "")) {
                    error = true;
                }
                if (!error) {
                    AppSession.user = new User();
                    AppSession.user.setLogin(login);
                    AppSession.user.setPassword(password);
                    try {
                        new LoginTask().execute(User.loginAccount).get();
                        if (AppSession.user == null) {
                            messageError(R.string.connectionError);
                        } else {
                            switch (AppSession.user.getStatusCode()) {
                                case User.loginOk:
                                    loginOk();
                                    break;
                                case User.userAndPasswordDoesntMatch:
                                    messageError(R.string.userAndPasswordDoesntMatch);
                                    break;
                                case User.userDoesntExist:
                                    messageError(R.string.userDoesntExist);
                                    break;
                                case User.serverError:
                                    messageError(R.string.serverError);
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        messageError(R.string.errorlogin);
                    }
                } else {
                    messageError(R.string.invalidinputs);
                }
                break;
        }
    }

    private void messageError(int codeMessage) {
        Toast.makeText(this, getResources().getString(codeMessage), Toast.LENGTH_LONG).show();
    }

    @OnCheckedChanged(R.id.chbAutoLogin)
    public void checkboxToggled(boolean isChecked) {
        autoLogin = isChecked;
    }

    private void loginOk() {
        UserDAO dao = new UserDAO(this);
        Intent it;
        if (dao.count(AppSession.user) == 0) {
            createUser(this, autoLogin);
        } else {
            updateUser(this, autoLogin);
        }
        Util.intentToMainActivity(this);
        finish();
    }
}
