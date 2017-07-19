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
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;
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
    ProdutosAdapter mAdapter;
    Unbinder unbinder;

    public static ListProductsFragment novaInstancia(Categorias categoria) {
        Log.v("ListProductsFragment", "novaInstancia Categorias");
        ListProductsFragment fragment = new ListProductsFragment();
        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(categoria);
        args.putParcelable("categoria", parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    public static ListProductsFragment novaInstancia(SubCategorias subCategoria) {
        Log.v("ListProductsFragment", "novaInstancia SubCategorias");
        ListProductsFragment fragment = new ListProductsFragment();
        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(subCategoria);
        args.putParcelable("subCategoria", parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    public static ListProductsFragment novaInstancia(Provider provider) {
        Log.v("ListProductsFragment", "novaInstancia Provider");
        ListProductsFragment fragment = new ListProductsFragment();
        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(provider);
        args.putParcelable("provider", parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLista = new ArrayList<>();

        if (getArguments() != null && getArguments().getParcelable("categoria") != null) {
            Log.v("ListProductsFragment", "onCreate - categoria");
            Parcelable parcelable = getArguments().getParcelable("categoria");
            Categorias categoria = Parcels.unwrap(parcelable);
            ProdutosDAO mDAO = new ProdutosDAO(getActivity());
            mLista = mDAO.listar(categoria);
        }
        if (getArguments() != null && getArguments().getParcelable("subCategoria") != null) {
            Log.v("ListProductsFragment", "onCreate - subCategoria");
            Parcelable parcelable = getArguments().getParcelable("subCategoria");
            SubCategorias subCategoria = Parcels.unwrap(parcelable);
            ProdutosDAO mDAO = new ProdutosDAO(getActivity());
            mLista = mDAO.listar(subCategoria);
        }
        if (getArguments() != null && getArguments().getParcelable("provider") != null) {
            Log.v("ListProductsFragment", "onCreate - provider");
            Parcelable parcelable = getArguments().getParcelable("provider");
            Provider provider = Parcels.unwrap(parcelable);
            ProdutosDAO mDAO = new ProdutosDAO(getActivity());
            mLista = mDAO.listar(provider);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListProductsFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_products, container, false);
        unbinder = ButterKnife.bind(this, layout);
        mAdapter = new ProdutosAdapter(getActivity(), mLista);
        mListView.setAdapter(mAdapter);
        return layout;
    }

    public void notifyDataSetChanged(Categorias categorias) {
        ProdutosDAO mDAO = new ProdutosDAO(getActivity());
        mLista = mDAO.listar(categorias);
        mAdapter = new ProdutosAdapter(getActivity(), mLista);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listProdutos)
    void onItemClicked(int position) {
        Log.v("ListProductsFragment", "onItemClicked");

        Produtos produto = (Produtos) mAdapter.getItem(position);
        if (getActivity() instanceof ListProductsFragment.ClickOnProductListener) {
            ListProductsFragment.ClickOnProductListener listener = (ListProductsFragment.ClickOnProductListener) getActivity();
            listener.productClicked(produto);
        }
    }

    public interface ClickOnProductListener {
        void productClicked(Produtos produto);
    }

}
