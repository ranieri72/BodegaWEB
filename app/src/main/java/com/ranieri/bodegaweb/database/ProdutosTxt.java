package com.ranieri.bodegaweb.database;

import android.content.Context;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 28 de abr.
 */

public class ProdutosTxt {

    private InputStream inputStream;
    private InputStreamReader inputreader;
    private BufferedReader buffreader;
    private Context context;

    public List<Produtos> readRawTextFile(Context ctx){
        context = ctx;

        List<Produtos> lista = readProdutoId();

        lista = readProdutoNome(lista);
        lista = readProdutoEstoque(lista);
        lista = readProdutoPreco(lista);
        lista = readProdutoCategoria(lista);

        return lista;
    }

    private  List<Produtos> readProdutoId(){
        inputStream = context.getResources().openRawResource(R.raw.produtos_id);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;

        List<Produtos> lista = new ArrayList<Produtos>();
        Produtos produto;

        try {
            while (( line = buffreader.readLine()) != null) {
                produto = new Produtos();
                produto.setId(Long.parseLong(line));
                lista.add(produto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private  List<Produtos> readProdutoNome(List<Produtos> lista){
        inputStream = context.getResources().openRawResource(R.raw.produtos_nome);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;
        int contador = 0;

        try {
            while (( line = buffreader.readLine()) != null) {
                lista.get(contador).setNome(line);
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private  List<Produtos> readProdutoEstoque(List<Produtos> lista){
        inputStream = context.getResources().openRawResource(R.raw.produtos_estoque);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;
        int contador = 0;

        try {
            while (( line = buffreader.readLine()) != null) {
                lista.get(contador).setEstoque(Integer.parseInt(line));
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private  List<Produtos> readProdutoPreco(List<Produtos> lista){
        inputStream = context.getResources().openRawResource(R.raw.produtos_preco);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;
        int contador = 0;

        try {
            while (( line = buffreader.readLine()) != null) {
                lista.get(contador).setPreço(Double.parseDouble(line));
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private  List<Produtos> readProdutoCategoria(List<Produtos> lista){
        inputStream = context.getResources().openRawResource(R.raw.produtos_categoria);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;
        int contador = 0;
        Categorias categoria;

        try {
            while (( line = buffreader.readLine()) != null) {
                categoria = new Categorias();
                categoria.setId(Long.parseLong(line));
                lista.get(contador).setCategoria(categoria);
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
