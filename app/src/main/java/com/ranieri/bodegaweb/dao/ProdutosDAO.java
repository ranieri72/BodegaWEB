package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.ProdutosContract;
import com.ranieri.bodegaweb.contract.SubCategoriasContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 27 de abr.
 */

public class ProdutosDAO {

    private Context mContext;
    private int indexId;
    private int indexName;
    private int indexStock;
    private int indexNewStock;
    private int indexAltered;
    private int indexDeleted;
    private int indexSuggestedPrice;

    public ProdutosDAO(Context mContext) {
        this.mContext = mContext;
    }

    public Produtos inserir(Produtos produto) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromProdutos(produto);
        long id = db.insert(ProdutosContract.TABLE_NAME, null, values);
        produto.setId(id);

        db.close();
        return produto;
    }

    public int inserir(List<Produtos> lista) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (Produtos produto : lista) {
            ContentValues values = valuesFromProdutos(produto);
            db.insert(ProdutosContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int atualizar(Produtos produto) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromProdutos(produto);
        int rowsAffected = db.update(ProdutosContract.TABLE_NAME, values, ProdutosContract.TABLE_ID + " = ?", new String[]{String.valueOf(produto.getId())});

        db.close();
        return rowsAffected;
    }

    public int excluir(Produtos produto) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(ProdutosContract.TABLE_NAME, ProdutosContract.TABLE_ID + " = ?", new String[]{String.valueOf(produto.getId())});

        db.close();
        return rowsAffected;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(ProdutosContract.TABLE_NAME, null, null);

        db.close();
        return rowsAffected;
    }

    public Produtos selecionar(Produtos produto) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] produtoId = new String[]{String.valueOf(produto.getId())};

        String sql = "SELECT " +
                ProdutosContract.TABLE_NAME + ".*, " +
                CategoriasContract.NOME + ", " +
                CategoriasContract.SUBCATEGORIA + ", " +
                SubCategoriasContract.NOME +
                " FROM " +
                ProdutosContract.TABLE_NAME + ", " +
                CategoriasContract.TABLE_NAME + ", " +
                SubCategoriasContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.CATEGORIA +
                " = " +
                CategoriasContract.ID +
                " AND " +
                CategoriasContract.SUBCATEGORIA +
                " = " +
                SubCategoriasContract.ID +
                " AND " + ProdutosContract.ID + " = ?;";

        Cursor cursor = db.rawQuery(sql, produtoId);

        if (cursor.moveToNext()) {
            getColumnIndex(cursor);
            produto = valuesFromCursor(cursor);

            produto.getCategoria().setId(cursor.getLong(cursor.getColumnIndex(ProdutosContract.TABLE_CATEGORIA)));
            produto.getCategoria().setNome(cursor.getString(cursor.getColumnIndex(CategoriasContract.TABLE_NOME)));
            produto.getCategoria().getSubCategoriaProd().setId(cursor.getLong(cursor.getColumnIndex(CategoriasContract.TABLE_SUBCATEGORIA)));
            produto.getCategoria().getSubCategoriaProd().setNome(cursor.getString(cursor.getColumnIndex(SubCategoriasContract.TABLE_NOME)));
        }
        cursor.close();
        db.close();
        return produto;
    }

    public List<Produtos> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " + ProdutosContract.TABLE_NAME;

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
                CategoriasContract.NOME +
                " FROM " +
                ProdutosContract.TABLE_NAME + ", " +
                CategoriasContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.CATEGORIA +
                " = " +
                CategoriasContract.ID +
                " AND " +
                CategoriasContract.SUBCATEGORIA + " = ? " +
                "ORDER BY " +
                CategoriasContract.ORDEM + " ASC, " +
                ProdutosContract.NOME + " ASC";

        Cursor cursor = db.rawQuery(sql, subCategoriaId);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;
        getColumnIndex(cursor);
        int indexCategoryId = cursor.getColumnIndex(ProdutosContract.TABLE_CATEGORIA);
        int indexCategoryName = cursor.getColumnIndex(CategoriasContract.TABLE_NOME);

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

    public List<Produtos> listar(boolean alterado) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] estado = alterado ? new String[]{"1"} : new String[]{"0"};

        String sql = "SELECT * FROM " +
                ProdutosContract.TABLE_NAME +
                " WHERE " +
                ProdutosContract.ALTERADO +
                " = ?";

        Cursor cursor = db.rawQuery(sql, estado);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;
        getColumnIndex(cursor);
        int indexCategoryID = cursor.getColumnIndex(CategoriasContract.TABLE_ID);

        while (cursor.moveToNext()) {
            produto = valuesFromCursor(cursor);
            produto.getCategoria().setId(cursor.getLong(indexCategoryID));
            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    private void getColumnIndex(Cursor cursor) {
        indexId = cursor.getColumnIndex(ProdutosContract.TABLE_ID);
        indexName = cursor.getColumnIndex(ProdutosContract.TABLE_NOME);
        indexStock = cursor.getColumnIndex(ProdutosContract.TABLE_ESTOQUE);
        indexNewStock = cursor.getColumnIndex(ProdutosContract.TABLE_NOVOESTOQUE);
        indexAltered = cursor.getColumnIndex(ProdutosContract.TABLE_ALTERADO);
        indexDeleted = cursor.getColumnIndex(ProdutosContract.TABLE_APAGADO);
        indexSuggestedPrice = cursor.getColumnIndex(ProdutosContract.TABLE_PRECO);
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

    private ContentValues valuesFromProdutos(Produtos produto) {
        ContentValues values = new ContentValues();
        values.put(ProdutosContract.TABLE_ID, produto.getId());
        values.put(ProdutosContract.TABLE_NOME, produto.getNome());
        values.put(ProdutosContract.TABLE_ESTOQUE, produto.getEstoque());
        values.put(ProdutosContract.TABLE_NOVOESTOQUE, produto.getNovoEstoque());
        values.put(ProdutosContract.TABLE_ALTERADO, produto.isAlterado());
        values.put(ProdutosContract.TABLE_APAGADO, produto.isApagado());
        values.put(ProdutosContract.TABLE_PRECO, produto.getPrecoSugerido());
        values.put(ProdutosContract.TABLE_CATEGORIA, produto.getCategoria().getId());
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
