package com.ranieri.bodegaweb.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.adapter.ProdutosAdapter;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class ListProductsFragment extends Fragment {

    @BindView(R.id.listProdutos)
    ListView mListView;

    List<Produtos> mLista;
    ProdutosAdapter adapter;
    Unbinder unbinder;

    public static ListProductsFragment novaInstancia(SubCategorias subCategoria) {
        Log.v("ListProductsFragment", "novaInstancia");
        ListProductsFragment fragment = new ListProductsFragment();
        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(subCategoria);
        args.putParcelable("subCategoria", parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListProductsFragment", "onCreate");
        mLista = new ArrayList<>();
        SubCategorias subCategoria;

        if (getArguments() != null) {
            Parcelable parcelable = getArguments().getParcelable("subCategoria");
            subCategoria = Parcels.unwrap(parcelable);
            ProdutosDAO mDAO = new ProdutosDAO(getActivity());
            mLista = mDAO.listar(subCategoria);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListProductsFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_products, container, false);
        unbinder = ButterKnife.bind(this, layout);
        adapter = new ProdutosAdapter(getActivity(), mLista);
        mListView.setAdapter(adapter);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listProdutos)
    void onItemClicked(int position) {
        Log.v("ListProductsFragment", "onItemClicked");

        Produtos produto = (Produtos) adapter.getItem(position);
        if (getActivity() instanceof ListProductsFragment.CliqueNoProdutoListener) {
            ListProductsFragment.CliqueNoProdutoListener listener = (ListProductsFragment.CliqueNoProdutoListener) getActivity();
            listener.produtoFoiClicado(produto);
        }
    }

    public interface CliqueNoProdutoListener {
        void produtoFoiClicado(Produtos produto);
    }

}
