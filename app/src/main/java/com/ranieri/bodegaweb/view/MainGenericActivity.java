package com.ranieri.bodegaweb.view;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.view.fragments.ListSubCategoryFragment.ClickOnSubCategoryListener;

import java.util.concurrent.ExecutionException;

public abstract class MainGenericActivity extends AppCompatActivity implements ClickOnSubCategoryListener {

    private static final int BARCODE_REQUEST = 1;

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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                dialogUpdate();
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

    private void dialogUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.atualizarDados));
        builder.setMessage(getResources().getString(R.string.areYouSure));
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
            Toast.makeText(this, getResources().getString(R.string.produtosAtualizados) + qtd, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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