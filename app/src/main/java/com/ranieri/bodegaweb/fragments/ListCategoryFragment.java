package com.ranieri.bodegaweb.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.adapter.CategoriasAdapter;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class ListCategoryFragment extends Fragment {

    @BindView(R.id.listCategory)
    ListView mListView;

    Unbinder unbinder;
    CategoriasAdapter mAdapter;
    List<Categorias> mCategorias;

    public static ListCategoryFragment novaInstancia(SubCategorias subCategoria) {
        Log.v("ListCategoryFragment", "novaInstancia - SubCategorias");
        ListCategoryFragment fragment = new ListCategoryFragment();
        Bundle args = new Bundle();

        args.putParcelable("subCategoria", Parcels.wrap(subCategoria));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListCategoryFragment", "onCreate");
        CategoriasDAO mDAO = new CategoriasDAO(getActivity());
        mCategorias = mDAO.listar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListCategoryFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_category, container, false);
        unbinder = ButterKnife.bind(this, layout);
        mAdapter = new CategoriasAdapter(getActivity(), mCategorias);
        mListView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listCategory)
    void onItemClicked(int position) {
        Log.v("ListCategoryFragment", "onItemClicked");

        Categorias categoria = mAdapter.getItem(position);
        if (getActivity() instanceof ClickOnCategoryListener) {
            ClickOnCategoryListener listener = (ClickOnCategoryListener) getActivity();
            listener.categoryClicked(categoria);
        }
    }

    public interface ClickOnCategoryListener {
        void categoryClicked(Categorias categoria);
    }
}