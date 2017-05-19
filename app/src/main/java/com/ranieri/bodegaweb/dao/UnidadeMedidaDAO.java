package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.UnidadeMedidaContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.UnidadeMedida;

import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class UnidadeMedidaDAO {

    private Context mContext;

    public UnidadeMedidaDAO(Context context) {
        mContext = context;
    }

    public int inserir(List<UnidadeMedida> lista){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (UnidadeMedida unidadeMedida : lista){
            ContentValues values = valuesFromUnidadeMedida(unidadeMedida);
            db.insert(UnidadeMedidaContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(UnidadeMedidaContract.TABLE_NAME, null, null);

        db.close();
        return rowsAffected;
    }

    private ContentValues valuesFromUnidadeMedida(UnidadeMedida unidadeMedida) {
        ContentValues values = new ContentValues();
        values.put(UnidadeMedidaContract._ID, unidadeMedida.getId());
        values.put(UnidadeMedidaContract.NOME, unidadeMedida.getNome());
        values.put(UnidadeMedidaContract.ORDEM, unidadeMedida.getOrdem());
        values.put(UnidadeMedidaContract.MULTIPLICADOR, unidadeMedida.getMultiplicador());

        return values;
    }

    public int refreshOrders(ListJson listJson) {
        excluir();
        return inserir(listJson.getListaUnidadeMedida());
    }
}
