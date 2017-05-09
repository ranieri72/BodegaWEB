package com.ranieri.bodegaweb.database;

import android.content.Context;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private OutputStreamWriter outputStreamWriter;

    public void writeRawTextFile(List<Produtos> lista, Context ctx) {
        context = ctx;
        List<Produtos> listaMovimentacoes = new ArrayList<>();
        List<Produtos> listaItensPedidos = new ArrayList<>();

        for (Produtos p : lista) {
            if (p.getEstoque() > p.getNovoEstoque()) {
                listaMovimentacoes.add(p);
                continue;
            }
            if (p.getEstoque() < p.getNovoEstoque()) {
                listaItensPedidos.add(p);
            }
        }
        if (!listaMovimentacoes.isEmpty()) {
            writeMovimentacoes(listaMovimentacoes);
        }
        if (!listaItensPedidos.isEmpty()) {
            writeItensPedidos(listaItensPedidos);
        }
    }

    private void writeMovimentacoes(List<Produtos> lista) {
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput("movimentacoes_estoque.txt", Context.MODE_PRIVATE));
            String sqlInsert = "INSERT INTO negocio.itentradaprateleira (idproduto, qtd, hora, data, idunidmedida, perda) VALUES (";
            String sqlValues;
            for (Produtos p : lista) {
                sqlValues = String.valueOf(p.getId()) + ", ";
                sqlValues += (p.getEstoque() - p.getNovoEstoque()) + ", ";
                sqlValues += "CURRENT_TIME, ";
                sqlValues += "CURRENT_DATE, ";
                sqlValues += "1, ";
                sqlValues += "true);";
                outputStreamWriter.write(sqlInsert + sqlValues);
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeItensPedidos(List<Produtos> lista) {
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput("itens_pedido.txt", Context.MODE_PRIVATE));
            String sqlInsert = "INSERT INTO negocio.itenspedido (precounit, qtd, idproduto, idpedido, idunidmedida) VALUES (";
            String sqlValues;
            for (Produtos p : lista) {
                sqlValues = "0, ";
                sqlValues += (p.getNovoEstoque() - p.getEstoque()) + ", ";
                sqlValues += String.valueOf(p.getId()) + ", ";
                sqlValues += ", ";
                sqlValues += "1);";
                outputStreamWriter.write(sqlInsert + sqlValues);
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        List<Produtos> lista = new ArrayList<>();
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
