package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.ProdutosContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 27 de abr.
 */

public class ProdutosDAO {

    private Context mContext;
    private Produtos produto;

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

    public Produtos selecionar(Produtos produto){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT p._id, p.nome, p.estoque, p.preco, c._id AS cID, c.nome AS cNome, s._id AS sID, s.nome AS sNome" +
                " FROM produtos AS p, categorias AS c, subCategorias AS s" +
                " WHERE p.idCategoria = c._id AND c.idSubCategoria = s._id AND p._id = ?", new String[]{String.valueOf(produto.getId())});

        if (cursor.moveToNext()){

            produto.setId(cursor.getLong(cursor.getColumnIndex(ProdutosContract._ID)));
            produto.setNome(cursor.getString(cursor.getColumnIndex(ProdutosContract.NOME)));
            produto.setEstoque(cursor.getInt(cursor.getColumnIndex(ProdutosContract.ESTOQUE)));
            produto.setPreço(cursor.getDouble(cursor.getColumnIndex(ProdutosContract.PRECO)));

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

        Cursor cursor = db.rawQuery("SELECT p._id, p.nome, p.estoque, p.preco, c._id AS cID, c.nome AS cNome, s._id AS sID, s.nome AS sNome" +
                " FROM produtos AS p, categorias AS c, subCategorias AS s" +
                " WHERE p.idCategoria = c._id AND c.idSubCategoria = s._id", null);

        List<Produtos> lista = new ArrayList<>();

        while (cursor.moveToNext()){
            produto = new Produtos();

            produto.setId(cursor.getLong(cursor.getColumnIndex(ProdutosContract._ID)));
            produto.setNome(cursor.getString(cursor.getColumnIndex(ProdutosContract.NOME)));
            produto.setEstoque(cursor.getInt(cursor.getColumnIndex(ProdutosContract.ESTOQUE)));
            produto.setPreço(cursor.getDouble(cursor.getColumnIndex(ProdutosContract.PRECO)));

            produto.getCategoria().setId(cursor.getLong(cursor.getColumnIndex("cID")));
            produto.getCategoria().setNome(cursor.getString(cursor.getColumnIndex("cNome")));
            produto.getCategoria().getSubCategoria().setId(cursor.getLong(cursor.getColumnIndex("sID")));
            produto.getCategoria().getSubCategoria().setNome(cursor.getString(cursor.getColumnIndex("sNome")));

            lista.add(produto);
        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Produtos> listarPorSubCategoria(SubCategorias subCategoria){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT p._id, p.nome, p.estoque, p.preco, c._id AS cID, c.nome AS cNome FROM produtos AS p, categorias AS c" +
                " WHERE p.idCategoria = c._id AND c.idSubCategoria = ? ORDER BY c.ordem ASC, p.nome ASC", new String[]{String.valueOf(subCategoria.getId())});

        List<Produtos> lista = new ArrayList<>();

        while (cursor.moveToNext()){
            produto = new Produtos();

            produto.setId(cursor.getLong(cursor.getColumnIndex(ProdutosContract._ID)));
            produto.setNome(cursor.getString(cursor.getColumnIndex(ProdutosContract.NOME)));
            produto.setEstoque(cursor.getInt(cursor.getColumnIndex(ProdutosContract.ESTOQUE)));
            produto.setPreço(cursor.getDouble(cursor.getColumnIndex(ProdutosContract.PRECO)));

            produto.getCategoria().setId(cursor.getLong(cursor.getColumnIndex("cID")));
            produto.getCategoria().setNome(cursor.getString(cursor.getColumnIndex("cNome")));

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
        values.put(ProdutosContract.PRECO, produto.getPreço());
        values.put(ProdutosContract.CATEGORIA, produto.getCategoria().getId());

        return values;
    }
}
