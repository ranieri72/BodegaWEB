package com.ranieri.bodegaweb.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.IonRequester;
import com.ranieri.bodegaweb.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class ConfiguracoesActivity extends AppCompatActivity {

//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;

    @BindView(R.id.switchCountStock)
    Switch mSwitchCountStock;

    @BindView(R.id.switchTabletView)
    Switch mSwitchIsTablet;

    @BindView(R.id.ipv4_spinner)
    Spinner mIpv4Spinner;

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
        sharedPreferences = getSharedPreferences(Util.configPreference, Context.MODE_PRIVATE);

        onCreateSwitchTablet();
        onCreateSwitchStock();
        onCreateSpinner();
    }

    private void onCreateSwitchTablet() {
        isTablet = Util.isTablet;
        mSwitchIsTablet.setChecked(sharedPreferences.getBoolean(Util.tabletViewPreference, true));

        if (mSwitchIsTablet.isChecked() == isTablet) {
            mTxtAlert.setVisibility(View.INVISIBLE);
        } else {
            mTxtAlert.setVisibility(View.VISIBLE);
        }
    }

    private void onCreateSwitchStock() {
        mSwitchCountStock.setChecked(sharedPreferences.getBoolean(Util.countStockPreference, false));
    }

    private void onCreateSpinner() {
        Map<String, String> desenvolvimentoMap = new HashMap<>();
        desenvolvimentoMap.put("Desenv", ConnectionConstants.desenvolvimento);

        Map<String, String> testeMap = new HashMap<>();
        testeMap.put("Teste", ConnectionConstants.teste);

        Map<String, String> producaoMap = new HashMap<>();
        producaoMap.put("Producao", ConnectionConstants.producao);

        ArrayList<Map<String, String>> ipList = new ArrayList<>();
        ipList.add(desenvolvimentoMap);
        ipList.add(testeMap);
        ipList.add(producaoMap);

        ArrayAdapter<Map<String, String>> adapter = new ArrayAdapter<>(ConfiguracoesActivity.this,
                android.R.layout.simple_spinner_item, ipList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIpv4Spinner.setAdapter(adapter);
        mIpv4Spinner.setSelection(0);
    }

    @OnItemSelected(R.id.ipv4_spinner)
    public void onItemSelected(Spinner spinner, int pos) {
        //Toast.makeText(this, spinner.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.btnSave, R.id.btnIonTest})
    void onItemClicked(View v) {
        //progressBar.setIndeterminate(true);
        switch (v.getId()) {
            case R.id.btnSave:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Util.tabletViewPreference, mSwitchIsTablet.isChecked());
                editor.putBoolean(Util.countStockPreference, mSwitchCountStock.isChecked());
                editor.apply();
                Util.isCountStock = sharedPreferences.getBoolean(Util.countStockPreference, false);

                if (mSwitchIsTablet.isChecked() == isTablet) {
                    mTxtAlert.setVisibility(View.INVISIBLE);
                } else {
                    mTxtAlert.setVisibility(View.VISIBLE);
                }
                Toast.makeText(this, getResources().getString(R.string.configuracoesSalvas), Toast.LENGTH_LONG).show();
                break;
            case R.id.btnIonTest:
                String errorMsg = "";
                try {
                    new IonRequester(this, ConnectionConstants.urlProducts).getJson();
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