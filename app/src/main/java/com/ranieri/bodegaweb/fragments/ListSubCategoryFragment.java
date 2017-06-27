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
import com.ranieri.bodegaweb.adapter.SubCategoriasAdapter;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class ListSubCategoryFragment extends Fragment {

    @BindView(R.id.listSubCategorias)
    ListView mListView;

    Unbinder unbinder;
    SubCategoriasAdapter mAdapter;
    List<SubCategorias> mSubCategorias;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListSubCategoryFragment", "onCreate");
        SubCategoriasDAO mDAO = new SubCategoriasDAO(getActivity());
        mSubCategorias = mDAO.listar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListSubCategoryFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_sub_category, container, false);
        unbinder = ButterKnife.bind(this, layout);
        mAdapter = new SubCategoriasAdapter(getActivity(), mSubCategorias);
        mListView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listSubCategorias)
    void onItemClicked(int position) {
        Log.v("ListSubCategoryFragment", "onItemClicked");

        SubCategorias subCategoria = mAdapter.getItem(position);
        if (getActivity() instanceof ClickOnSubCategoryListener) {
            ClickOnSubCategoryListener listener = (ClickOnSubCategoryListener) getActivity();
            listener.subCategoryClicked(subCategoria);
        }
    }

    public interface ClickOnSubCategoryListener {
        void subCategoryClicked(SubCategorias subCategoria);
    }
}
