package com.ranieri.bodegaweb.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.connection.AsyncRequest;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfiguracoesActivity extends AppCompatActivity {

//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;

    @BindView(R.id.switchTabletView)
    Switch mSwitchIsTablet;

    @BindView(R.id.txt_nome)
    TextView mTxtAlert;

    private SharedPreferences sharedPreferences;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.configuracoes));
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(Util.tabletViewPreference, Context.MODE_PRIVATE);
        isTablet = Util.isTablet;
        mSwitchIsTablet.setChecked(sharedPreferences.getBoolean(Util.tabletViewPreference, true));

        if (mSwitchIsTablet.isChecked() == isTablet) {
            mTxtAlert.setVisibility(View.INVISIBLE);
        } else {
            mTxtAlert.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btnSave, R.id.btnIonTest})
    void onItemClicked(View v) {
        //progressBar.setIndeterminate(true);
        switch (v.getId()) {
            case R.id.btnSave:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Util.tabletViewPreference, mSwitchIsTablet.isChecked());
                editor.apply();

                if (mSwitchIsTablet.isChecked() == isTablet) {
                    mTxtAlert.setVisibility(View.INVISIBLE);
                } else {
                    mTxtAlert.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btnIonTest:
                String errorMsg = "";
                try {
                    new AsyncRequest(this, ConnectionConstants.urlProducts).getJson();
                    errorMsg = "Ion Teste Ok";
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMsg = "Ion Teste Fail";
                } finally {
                    Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
                }
                break;
        }
        //progressBar.setIndeterminate(false);
    }
}