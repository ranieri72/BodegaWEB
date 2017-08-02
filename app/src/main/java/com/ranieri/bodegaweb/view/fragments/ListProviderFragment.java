package com.ranieri.bodegaweb.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.view.adapter.ProviderAdapter;
import com.ranieri.bodegaweb.dao.ProviderDAO;
import com.ranieri.bodegaweb.model.Provider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class ListProviderFragment extends Fragment {

    @BindView(R.id.listProvider)
    ListView mListView;

    Unbinder unbinder;
    ProviderAdapter mAdapter;
    List<Provider> mProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListProviderFragment", "onCreate");
        ProviderDAO mDAO = new ProviderDAO(getActivity());
        mProvider = mDAO.listar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListProviderFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_provider, container, false);
        unbinder = ButterKnife.bind(this, layout);
        mAdapter = new ProviderAdapter(getActivity(), mProvider);
        mListView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listProvider)
    void onItemClicked(int position) {
        Log.v("ListProviderFragment", "onItemClicked");

        Provider provider = mAdapter.getItem(position);
        if (getActivity() instanceof ClickOnProviderListener) {
            ClickOnProviderListener listener = (ClickOnProviderListener) getActivity();
            listener.providerClicked(provider);
        }
    }

    public interface ClickOnProviderListener {
        void providerClicked(Provider provider);
    }
}
