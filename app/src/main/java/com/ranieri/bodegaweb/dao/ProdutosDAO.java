package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.dao.contract.CategoriasContract;
import com.ranieri.bodegaweb.dao.contract.OrderContract;
import com.ranieri.bodegaweb.dao.contract.OrderItemsContract;
import com.ranieri.bodegaweb.dao.contract.ProdutosContract;
import com.ranieri.bodegaweb.dao.contract.ProviderContract;
import com.ranieri.bodegaweb.dao.contract.SubCategoriasContract;
import com.ranieri.bodegaweb.dao.database.BodegaHelper;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 27 de abr.
 */

public class ProdutosDAO extends GenericDAO<Produtos> {

    private int indexId;
    private int indexName;
    private int indexStock;
    private int indexNewStock;
    private int indexAltered;
    private int indexDeleted;
    private int indexSuggestedPrice;

    public ProdutosDAO(Context mContext) {
        super(mContext);
        tableName = ProdutosContract.TABLE_NAME;
    }

    public int atualizar(Produtos produto) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        produto.setAlterado(true);

        ContentValues values = valuesFromObject(produto);
        int rowsAffected = db.update(tableName, values, ProdutosContract.COLUMN_ID + " = ?", new String[]{String.valueOf(produto.getId())});

        db.close();
        return rowsAffected;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(tableName, null, null);

        db.close();
        return rowsAffected;
    }

    public Produtos selecionar(Produtos produto) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] produtoId = new String[]{String.valueOf(produto.getId())};

        String sql = "SELECT " +
                ProdutosContract.TABLE_NAME + ".*, " +
                CategoriasContract.NAME + ", " +
                CategoriasContract.SUBCATEGORY + ", " +
                SubCategoriasContract.NAME +
                " FROM " +
                ProdutosContract.TABLE_NAME + ", " +
                CategoriasContract.TABLE_NAME + ", " +
                SubCategoriasContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.CATEGORY +
                " = " +
                CategoriasContract.ID +
                " AND " +
                CategoriasContract.SUBCATEGORY +
                " = " +
                SubCategoriasContract.ID +
                " AND " + ProdutosContract.ID + " = ?;";

        Cursor cursor = db.rawQuery(sql, produtoId);

        if (cursor.moveToNext()) {
            getColumnIndex(cursor);
            produto = valuesFromCursor(cursor);

            produto.getCategoria().setId(cursor.getLong(cursor.getColumnIndex(ProdutosContract.COLUMN_CATEGORY)));
            produto.getCategoria().setNome(cursor.getString(cursor.getColumnIndex(CategoriasContract.COLUMN_NAME)));
            produto.getCategoria().getSubCategoriaProd().setId(cursor.getLong(cursor.getColumnIndex(CategoriasContract.COLUMN_SUBCATEGORY)));
            produto.getCategoria().getSubCategoriaProd().setNome(cursor.getString(cursor.getColumnIndex(SubCategoriasContract.COLUMN_NAME)));
        }
        cursor.close();
        db.close();
        return produto;
    }

    public List<Produtos> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " + tableName;

        Cursor cursor = db.rawQuery(sql, null);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;
        getColumnIndex(cursor);

        while (cursor.moveToNext()) {
            produto = valuesFromCursor(cursor);
            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Produtos> listar(SubCategorias subCategoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] subCategoriaId = new String[]{String.valueOf(subCategoria.getId())};

        String sql = "SELECT " +
                ProdutosContract.TABLE_NAME + ".*, " +
                CategoriasContract.NAME +
                " FROM " +
                ProdutosContract.TABLE_NAME + ", " +
                CategoriasContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.CATEGORY +
                " = " +
                CategoriasContract.ID +
                " AND " +
                CategoriasContract.SUBCATEGORY + " = ? " +
                "ORDER BY " +
                CategoriasContract.ORDER + " ASC, " +
                ProdutosContract.NAME + " ASC";

        Cursor cursor = db.rawQuery(sql, subCategoriaId);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;
        getColumnIndex(cursor);
        int indexCategoryId = cursor.getColumnIndex(ProdutosContract.COLUMN_CATEGORY);
        int indexCategoryName = cursor.getColumnIndex(CategoriasContract.COLUMN_NAME);

        while (cursor.moveToNext()) {
            produto = valuesFromCursor(cursor);
            produto.getCategoria().setId(cursor.getLong(indexCategoryId));
            produto.getCategoria().setNome(cursor.getString(indexCategoryName));

            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Produtos> listar(Categorias categoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] categoriaId = new String[]{String.valueOf(categoria.getId())};

        String sql = "SELECT " +
                ProdutosContract.TABLE_NAME + ".*" +
                " FROM " +
                ProdutosContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.CATEGORY + " = ?" +
                " ORDER BY " +
                ProdutosContract.NAME + " ASC;";

        Cursor cursor = db.rawQuery(sql, categoriaId);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;
        getColumnIndex(cursor);

        while (cursor.moveToNext()) {
            produto = valuesFromCursor(cursor);
            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Produtos> listar(Provider provider) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] idProvider = new String[]{String.valueOf(provider.getId())};

        String sql = "SELECT DISTINCT " +
                ProdutosContract.NAME + ", " +
                ProdutosContract.ID + ", " +
                ProdutosContract.STOCK + ", " +
                ProdutosContract.NEWSTOCK + ", " +
                ProdutosContract.ALTERED + ", " +
                ProdutosContract.DELETED + ", " +
                ProdutosContract.PRICE + ", " +
                ProdutosContract.CATEGORY +
                " FROM " +
                OrderItemsContract.TABLE_NAME + ", " +
                ProdutosContract.TABLE_NAME + ", " +
                OrderContract.TABLE_NAME + ", " +
                ProviderContract.TABLE_NAME +
                " WHERE " +
                OrderItemsContract.ORDER +
                " = " +
                OrderContract.ID +
                " AND " +
                OrderItemsContract.PRODUCT +
                " = " +
                ProdutosContract.ID +
                " AND " +
                OrderContract.PROVIDER +
                " = ?";

        Cursor cursor = db.rawQuery(sql, idProvider);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;
        getColumnIndex(cursor);

        while (cursor.moveToNext()) {
            produto = valuesFromCursor(cursor);
            lista.add(produto);
        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Produtos> listar(boolean alterado) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] estado = alterado ? new String[]{"1"} : new String[]{"0"};

        String sql = "SELECT * FROM " +
                ProdutosContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.ALTERED +
                " = ?";

        Cursor cursor = db.rawQuery(sql, estado);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;
        getColumnIndex(cursor);
        int indexCategoryID = cursor.getColumnIndex(ProdutosContract.COLUMN_CATEGORY);

        while (cursor.moveToNext()) {
            produto = valuesFromCursor(cursor);
            produto.getCategoria().setId(cursor.getLong(indexCategoryID));
            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    public int contar(boolean alterado) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] estado = alterado ? new String[]{"1"} : new String[]{"0"};

        String sql = "SELECT COUNT(" +
                ProdutosContract.ID +
                ") FROM " +
                ProdutosContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.ALTERED +
                " = ?";

        Cursor cursor = db.rawQuery(sql, estado);
        int quantidade = 0;

        if (cursor.moveToNext()) {
            quantidade = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return quantidade;
    }

    private void getColumnIndex(Cursor cursor) {
        indexId = cursor.getColumnIndex(ProdutosContract.COLUMN_ID);
        indexName = cursor.getColumnIndex(ProdutosContract.COLUMN_NAME);
        indexStock = cursor.getColumnIndex(ProdutosContract.COLUMN_STOCK);
        indexNewStock = cursor.getColumnIndex(ProdutosContract.COLUMN_NEWSTOCK);
        indexAltered = cursor.getColumnIndex(ProdutosContract.COLUMN_ALTERED);
        indexDeleted = cursor.getColumnIndex(ProdutosContract.COLUMN_DELETED);
        indexSuggestedPrice = cursor.getColumnIndex(ProdutosContract.COLUMN_PRICE);
    }

    private Produtos valuesFromCursor(Cursor cursor) {
        Produtos p = new Produtos();
        p.setId(cursor.getLong(indexId));
        p.setNome(cursor.getString(indexName));
        p.setEstoque(cursor.getInt(indexStock));
        p.setNovoEstoque(cursor.getInt(indexNewStock));
        p.setAlterado((cursor.getInt(indexAltered)) == 1);
        p.setApagado((cursor.getInt(indexDeleted)) == 1);
        p.setPrecoSugerido(cursor.getDouble(indexSuggestedPrice));
        return p;
    }

    @Override
    protected ContentValues valuesFromObject(Produtos produto) {
        ContentValues values = new ContentValues();
        values.put(ProdutosContract.COLUMN_ID, produto.getId());
        values.put(ProdutosContract.COLUMN_NAME, produto.getNome());
        values.put(ProdutosContract.COLUMN_STOCK, produto.getEstoque());
        values.put(ProdutosContract.COLUMN_NEWSTOCK, produto.getNovoEstoque());
        values.put(ProdutosContract.COLUMN_ALTERED, (produto.isAlterado()) ? 1 : 0);
        values.put(ProdutosContract.COLUMN_DELETED, (produto.isApagado()) ? 1 : 0);
        values.put(ProdutosContract.COLUMN_PRICE, produto.getPrecoSugerido());
        values.put(ProdutosContract.COLUMN_CATEGORY, produto.getCategoria().getId());
        return values;
    }

    public int refreshStock(ListJson lista) {
        excluir();
        return inserir(lista.getListaProdutos());
    }

//    public int refreshStock(ListJson lista){
//        BodegaHelper helper = new BodegaHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        List<Produtos> listaBanco = listar();
//        boolean existe;
//
//        for (Produtos pJson : lista.getListaProdutos()) {
//            existe = false;
//            for (Produtos pBanco : listaBanco) {
//                if (pJson.getId() == pBanco.getId()) {
//                    atualizar(pJson);
//                    existe = true;
//                    break;
//                }
//            }
//            if (!existe) {
//                inserir(pJson);
//            }
//        }
//        return 0;
//    }
}
