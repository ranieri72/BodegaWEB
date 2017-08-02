package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;

import com.ranieri.bodegaweb.dao.contract.UnidadeMedidaContract;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.UnidadeMedida;

/**
 * Created by ranie on 16 de mai.
 */

public class UnidadeMedidaDAO extends GenericDAO<UnidadeMedida> {

    public UnidadeMedidaDAO(Context context) {
        super(context);
        tableName = UnidadeMedidaContract.TABLE_NAME;
    }

    @Override
    protected ContentValues valuesFromObject(UnidadeMedida unidadeMedida) {
        ContentValues values = new ContentValues();
        values.put(UnidadeMedidaContract.COLUMN_ID, unidadeMedida.getId());
        values.put(UnidadeMedidaContract.COLUMN_NAME, unidadeMedida.getNome());
        values.put(UnidadeMedidaContract.COLUMN_ORDER, unidadeMedida.getOrdem());
        values.put(UnidadeMedidaContract.COLUMN_MULTIPLIER, unidadeMedida.getMultiplicador());
        return values;
    }

    public int refreshOrders(ListJson listJson) {
        excluir();
        return inserir(listJson.getListaUnidadeMedida());
    }
}