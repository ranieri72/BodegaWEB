package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ranieri.bodegaweb.asyncTask.LoginTask;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.User;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

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
        setTitle(getResources().getString(R.string.login));
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignIn)
    void signInButton(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                boolean error = false;
                if (Objects.equals(mEdtLogin.getText().toString(), "")) {
                    error = true;
                }
                if (Objects.equals(mEdtPassword.getText().toString(), "")) {
                    error = true;
                }
                if (!error) {
                    AppSession.user = new User();
                    AppSession.user.setLogin(mEdtLogin.getText().toString());
                    AppSession.user.setPassword(mEdtPassword.getText().toString());
                    try {
                        new LoginTask().execute(User.loginAccount).get();
                        if (AppSession.user == null) {
                            messageError(R.string.connectionError);
                        } else {
                            switch (AppSession.user.getStatusCode()) {
                                case User.loginOk:
                                    loginOk(AppSession.user);
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

    private void loginOk(User user) {
        UserDAO dao = new UserDAO(this);
        Intent it;
        if (dao.count(user) == 0) {
            dao.insert(user, autoLogin);
        } else {
            dao.update(user, autoLogin);
        }
        it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}
