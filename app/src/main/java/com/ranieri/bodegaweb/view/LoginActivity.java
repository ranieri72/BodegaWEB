package com.ranieri.bodegaweb.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

    @BindView(R.id.progress_login)
    ProgressBar progBar;

    private boolean autoLogin = false;
    private ConnectivityManager connManager;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.login));
        ButterKnife.bind(this);
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
                        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                        if (mWifi.isConnected()) {
                            showProgressBar();
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
                                    case User.serverNotFound:
                                        messageError(R.string.serverNotFound);
                                        break;
                                }
                            }
                        } else {
                            messageError(R.string.wifiOff);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        messageError(R.string.errorlogin);
                    } finally {
                        progBar.setVisibility(View.GONE);
                    }
                } else {
                    messageError(R.string.invalidinputs);
                }
                break;
        }
    }

    protected void showProgressBar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progBar = new ProgressBar(LoginActivity.this);
                        progBar.setIndeterminate(true);
                        progBar.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
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
        if (dao.count(AppSession.user) == 0) {
            createUser(this, autoLogin);
        } else {
            updateUser(this, autoLogin);
        }
        Util.intentToMainActivity(this);
        finish();
    }
}
