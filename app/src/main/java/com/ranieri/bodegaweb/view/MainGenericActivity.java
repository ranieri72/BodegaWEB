package com.ranieri.bodegaweb.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.asyncTask.RefreshDataTask;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.IonRequester;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.StockMovementDAO;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.User;
import com.ranieri.bodegaweb.util.Util;
import com.ranieri.bodegaweb.view.fragments.ListSubCategoryFragment.ClickOnSubCategoryListener;

import java.util.concurrent.ExecutionException;

public abstract class MainGenericActivity extends AppCompatActivity implements ClickOnSubCategoryListener {

    private static final int BARCODE_REQUEST = 1;
    private Handler mHandler;
    private ConnectivityManager connManager;
    private Menu mOptionsMenu;
    private NetworkInfo mWifi;
    private int statusIcon;
    private int qtdDataChanged;
    private ProdutosDAO produtosDAO;
    private StockMovementDAO movementDAO;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (AppSession.user.getStatusCode() == User.serverNotFound) {
            Toast.makeText(this, getResources().getString(R.string.serverNotFound), Toast.LENGTH_LONG).show();
            statusIcon = R.drawable.ic_action_off;
        }
        produtosDAO = new ProdutosDAO(this);
        movementDAO = new StockMovementDAO(this);

        mHandler = new Handler();
        startRepeatingTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            checkStatus();
            int mInterval = 5000;
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };

    private void checkStatus() {
        try {
            mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            statusIcon = R.drawable.ic_action_off;
            qtdDataChanged = produtosDAO.contar(true) + movementDAO.contar();
            if (mWifi.isConnected()) {

                new IonRequester(MainGenericActivity.this, ConnectionConstants.urlTest).testConnection();
                if (Util.isConnection) {
                    if (qtdDataChanged > 0) {
                        statusIcon = R.drawable.ic_action_up;
                    } else {
                        statusIcon = R.drawable.ic_action_cached;
                    }
                }
            }
            if (mOptionsMenu != null) {
                mOptionsMenu.findItem(R.id.action_update).setIcon(statusIcon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case BARCODE_REQUEST:
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    if (data != null) {
                        Barcode barcode = data.getParcelableExtra(CameraActivity.BARCODE_RESPONSE);
                        Toast.makeText(this, barcode.displayValue, Toast.LENGTH_LONG).show();
                        Log.w("onActivityResult", "Barcode: " + barcode.displayValue);
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mOptionsMenu.findItem(R.id.action_update).setIcon(statusIcon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                checkUpdate();
                break;
            case R.id.action_barcode:
                barCode();
                break;
            case R.id.action_configuracoes:
                optionConfig();
                break;
            case R.id.action_logout:
                dialogLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkUpdate() {
        checkStatus();
        if (Util.isConnection) {
//            if (qtdDataChanged > 0) {
            dialogUpdate();
//            } else {
//                Toast.makeText(this, "Não a produtos para alterar", Toast.LENGTH_SHORT).show();
//            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.serverNotFound), Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.atualizarDados));
        builder.setMessage(getResources().getQuantityString(R.plurals.areYouSure, qtdDataChanged, qtdDataChanged));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                updateData();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void updateData() {
        try {
            int qtd = new RefreshDataTask().execute(this).get();
            Toast.makeText(this, getResources().getQuantityString(R.plurals.produtosAtualizados, qtd, qtd), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
        }
    }

    private void barCode() {
        Intent it = new Intent(this, CameraActivity.class);
        startActivityForResult(it, BARCODE_REQUEST);
    }

    private void optionConfig() {
        Intent it = new Intent(this, ConfiguracoesActivity.class);
        startActivity(it);
    }

    private void dialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.confirmlogout));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                logout();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void logout() {
        new UserDAO(this).setAutoLoginFalse();
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}