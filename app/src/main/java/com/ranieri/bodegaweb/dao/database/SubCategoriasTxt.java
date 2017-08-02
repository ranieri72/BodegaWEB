package com.ranieri.bodegaweb.dao.database;

import android.content.Context;

import com.ranieri.bodegaweb.R;
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

public class SubCategoriasTxt {

    private InputStream inputStream;
    private InputStreamReader inputreader;
    private BufferedReader buffreader;
    private Context context;

    public List<SubCategorias> readRawTextFile(Context ctx){
        context = ctx;

        List<SubCategorias> lista = readSubCategoriaId();
        lista = readSubCategoriaNome(lista);

        return lista;
    }

    private  List<SubCategorias> readSubCategoriaId(){
        inputStream = context.getResources().openRawResource(R.raw.subcategorias_id);

        inputreader = new InputStreamReader(inputStream);
        buffreader = new BufferedReader(inputreader);
        String line;

        List<SubCategorias> lista = new ArrayList<SubCategorias>();
        SubCategorias subCategoria;

        try {
            while (( line = buffreader.readLine()) != null) {
                subCategoria = new SubCategorias();
                subCategoria.setId(Long.parseLong(line));
                lista.add(subCategoria);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private  List<SubCategorias> readSubCategoriaNome(List<SubCategorias> lista){
        inputStream = context.getResources().openRawResource(R.raw.subcategorias_nome);

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
}
