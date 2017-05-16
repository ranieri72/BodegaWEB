package com.ranieri.bodegaweb.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Provider;

import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class ProviderDAO {

    private Context mContext;

    public ProviderDAO(Context context) {
        mContext = context;
    }

    public void refreshStock(ListJson listJson) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Provider> listaBanco = listar();
        boolean existe;

        for (Provider pJson : listJson.getListaProvider()) {
            existe = false;
            for (Provider pBanco : listaBanco) {
                if (pJson.getId() == pBanco.getId()) {
                    atualizar(pJson);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                inserir(pJson);
            }
        }
    }

    private void inserir(Provider provider) {
    }

    private void atualizar(Provider provider) {
    }

    private List<Provider> listar() {
        return null;
    }
}
