package com.ranieri.bodegaweb.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.model.Estoque;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class ProdutosTask extends AsyncTask<Void, Void, Estoque> {

    @Override
    protected Estoque doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.25.8:8080/bodegaWEB/rest/produtos/";

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Gson gson = new Gson();
            return gson.fromJson(jsonString, Estoque.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}