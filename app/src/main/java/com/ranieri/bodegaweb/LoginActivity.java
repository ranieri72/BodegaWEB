package com.ranieri.bodegaweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ranieri.bodegaweb.asyncTask.LoginTask;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.User;

import org.parceler.Parcels;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

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
        User user;
        UserDAO dao;
        Bundle bundle;
        boolean error = false;

        if (view.getId() == R.id.btnSignIn) {
            if (Objects.equals(mEdtLogin.getText().toString(), "")) {
                error = true;
            }
            if (Objects.equals(mEdtPassword.getText().toString(), "")) {
                error = true;
            }
            if (!error) {
                dao = new UserDAO(this);
                user = new User();
                user.setLogin(mEdtLogin.getText().toString());
                user.setPassword(mEdtPassword.getText().toString());
                try {
                    bundle = new Bundle();
                    bundle.putParcelable("user", Parcels.wrap(user));
                    bundle.putInt("cod", User.loginAccount);
                    user = new LoginTask().execute(bundle).get();
                    if (user == null) {
                        messageError(R.string.connectionError);
                    } else {
                        switch (user.getStatusCode()) {
                            case User.loginOk:
                                loginOk(user, dao);
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    messageError(R.string.errorlogin);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    messageError(R.string.errorlogin);
                }
            } else {
                messageError(R.string.invalidinputs);
            }
        }
    }

    private void messageError(int codeMessage) {
        Toast.makeText(this, getResources().getString(codeMessage), Toast.LENGTH_LONG).show();
    }

    @OnCheckedChanged(R.id.chbAutoLogin)
    public void checkboxToggled(boolean isChecked) {
        autoLogin = isChecked;
    }

    private void loginOk(User user, UserDAO dao) {
        Intent it;
        if (dao.count(user) == 0) {
            dao.insert(user, autoLogin);
        } else {
            dao.update(user, autoLogin);
        }
        it = new Intent(this, MainActivity.class);
        it.putExtra("user", Parcels.wrap(user));
        startActivity(it);
        finish();
    }
}
