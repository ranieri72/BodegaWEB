package com.ranieri.bodegaweb.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.adapter.SubCategoriasAdapter;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.database.ProdutosTxt;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.List;


public class ListCategoryFragment extends Fragment {

    SubCategoriasDAO mDAO;
    ProdutosDAO produtosDAO;
    Produtos produto;
    ProdutosTxt produtosTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_list_category, container, false);

        mDAO = new SubCategoriasDAO(getActivity());
        ListView mListView = (ListView)layout.findViewById(R.id.listSubCategorias);
        List<SubCategorias> mCategorias = mDAO.listar();

//        if (mCategorias.isEmpty()){
//            preencherBanco();
//            mCategorias = mDAO.listar();
//        }

        SubCategoriasAdapter adapter = new SubCategoriasAdapter(getActivity(), mCategorias);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(tratadorDeCliques);


        return layout;
    }

    AdapterView.OnItemClickListener tratadorDeCliques = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

}
