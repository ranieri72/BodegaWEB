package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.ProdutosContract;
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

    public ProdutosDAO(Context mContext) {
        this.mContext = mContext;
    }

    public Produtos inserir(Produtos produto){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromProdutos(produto);
        long id = db.insert(ProdutosContract.TABLE_NAME, null, values);
        produto.setId(id);

        db.close();
        return produto;
    }

    public int inserir(List<Produtos> lista){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (Produtos produto : lista){
            ContentValues values = valuesFromProdutos(produto);
            db.insert(ProdutosContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int atualizar(Produtos produto){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromProdutos(produto);
        int rowsAffected = db.update(ProdutosContract.TABLE_NAME, values, ProdutosContract._ID + " = ?", new String[]{String.valueOf(produto.getId())});

        db.close();
        return rowsAffected;
    }

    public int excluir(Produtos produto){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(ProdutosContract.TABLE_NAME, ProdutosContract._ID + " = ?", new String[]{String.valueOf(produto.getId())});

        db.close();
        return rowsAffected;
    }

    public int excluir(){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(ProdutosContract.TABLE_NAME, null, null);

        db.close();
        return rowsAffected;
    }

    public Produtos selecionar(Produtos produto){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT p.*, c._id AS cID, c.nome AS cNome, s._id AS sID, s.nome AS sNome" +
                " FROM produtos AS p, categorias AS c, subCategorias AS s" +
                " WHERE p.idCategoria = c._id AND c.idSubCategoria = s._id AND p._id = ?", new String[]{String.valueOf(produto.getId())});

        if (cursor.moveToNext()){
            produto = valuesFromCursor(cursor);

            produto.getCategoria().setId(cursor.getLong(cursor.getColumnIndex("cID")));
            produto.getCategoria().setNome(cursor.getString(cursor.getColumnIndex("cNome")));
            produto.getCategoria().getSubCategoria().setId(cursor.getLong(cursor.getColumnIndex("sID")));
            produto.getCategoria().getSubCategoria().setNome(cursor.getString(cursor.getColumnIndex("sNome")));
        }
        cursor.close();
        db.close();
        return produto;
    }

    public List<Produtos> listar(){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ProdutosContract.TABLE_NAME, null);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;

        while (cursor.moveToNext()){
            produto = valuesFromCursor(cursor);
            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Produtos> listar(SubCategorias subCategoria){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT p.*, c._id AS cID, c.nome AS cNome FROM " +
                ProdutosContract.TABLE_NAME + " AS p, " +
                CategoriasContract.TABLE_NAME + " AS c " +
                "WHERE p.idCategoria = c._id AND c.idSubCategoria = ? ORDER BY c.ordem ASC, p.nome ASC", new String[]{String.valueOf(subCategoria.getId())});

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;

        while (cursor.moveToNext()){
            produto = valuesFromCursor(cursor);
            produto.getCategoria().setId(cursor.getLong(cursor.getColumnIndex("cID")));
            produto.getCategoria().setNome(cursor.getString(cursor.getColumnIndex("cNome")));

            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Produtos> listar(boolean alterado){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] estado = alterado ? new String[]{"1"} : new String[]{"0"};

        Cursor cursor = db.rawQuery("SELECT p.*, c._id AS cID FROM produtos AS p, categorias AS c" +
                " WHERE p.idCategoria = c._id AND p.alterado = ?", estado);

        List<Produtos> lista = new ArrayList<>();
        Produtos produto;

        while (cursor.moveToNext()){
            produto = valuesFromCursor(cursor);
            produto.getCategoria().setId(cursor.getLong(cursor.getColumnIndex("cID")));
            lista.add(produto);
        }
        cursor.close();
        db.close();
        return lista;
    }

    private ContentValues valuesFromProdutos(Produtos produto) {
        ContentValues values = new ContentValues();
        values.put(ProdutosContract._ID, produto.getId());
        values.put(ProdutosContract.NOME, produto.getNome());
        values.put(ProdutosContract.ESTOQUE, produto.getEstoque());
        values.put(ProdutosContract.NOVOESTOQUE, produto.getNovoEstoque());
        values.put(ProdutosContract.ALTERADO, produto.isAlterado());
        values.put(ProdutosContract.PRECO, produto.getPrecoSugerido());
        values.put(ProdutosContract.CATEGORIA, produto.getCategoria().getId());

        return values;
    }

    private Produtos valuesFromCursor(Cursor cursor) {
        Produtos p = new Produtos();
        p.setId(cursor.getLong(cursor.getColumnIndex(ProdutosContract._ID)));
        p.setNome(cursor.getString(cursor.getColumnIndex(ProdutosContract.NOME)));
        p.setEstoque(cursor.getInt(cursor.getColumnIndex(ProdutosContract.ESTOQUE)));
        p.setNovoEstoque(cursor.getInt(cursor.getColumnIndex(ProdutosContract.NOVOESTOQUE)));
        p.setAlterado((cursor.getInt(cursor.getColumnIndex(ProdutosContract.ALTERADO))) == 1);
        p.setPrecoSugerido(cursor.getDouble(cursor.getColumnIndex(ProdutosContract.PRECO)));

        return p;
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
