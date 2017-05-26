package com.ranieri.bodegaweb.database;

import android.content.Context;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 28 de abr.
 */

public class CategoriasTxt {

    private InputStream inputStream;
    private InputStreamReader inputreader;
    private BufferedReader buffreader;
    private Context context;

    public List<Categorias> readRawTextFile(Context ctx){
        context = ctx;

        List<Categorias> lista = readCategoriaId();

        lista = readCategoriaNome(lista);
        lista = readCategoriaOrdem(lista);
        lista = readCategoriaSubCategoria(lista);

        return lista;
    }

    private  List<Categorias> readCategoriaId(){
        inputStream = context.getResources().openRawResource(R.raw.categorias_id);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;

        List<Categorias> lista = new ArrayList<Categorias>();
        Categorias categoria;

        try {
            while (( line = buffreader.readLine()) != null) {
                categoria = new Categorias();
                categoria.setId(Long.parseLong(line));
                lista.add(categoria);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private  List<Categorias> readCategoriaNome(List<Categorias> lista){
        inputStream = context.getResources().openRawResource(R.raw.categorias_nome);

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

    private  List<Categorias> readCategoriaOrdem(List<Categorias> lista){
        inputStream = context.getResources().openRawResource(R.raw.categorias_ordem);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;
        int contador = 0;

        try {
            while (( line = buffreader.readLine()) != null) {
                lista.get(contador).setOrdem(Integer.parseInt(line));
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private  List<Categorias> readCategoriaSubCategoria(List<Categorias> lista){
        inputStream = context.getResources().openRawResource(R.raw.categorias_subcategoria);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;
        int contador = 0;
        SubCategorias subCategoria;

        try {
            while (( line = buffreader.readLine()) != null) {
                subCategoria = new SubCategorias();
                subCategoria.setId(Long.parseLong(line));
                lista.get(contador).setSubCategoriaProd(subCategoria);
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
