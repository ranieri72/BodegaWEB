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
import com.ranieri.bodegaweb.view.adapter.OrderAdapter;
import com.ranieri.bodegaweb.dao.OrdersDAO;
import com.ranieri.bodegaweb.model.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class ListOrderFragment extends Fragment {

    @BindView(R.id.listOrder)
    ListView mListView;

    Unbinder unbinder;
    OrderAdapter mAdapter;
    List<Order> mOrder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListOrderFragment", "onCreate");
        OrdersDAO mDAO = new OrdersDAO(getActivity());
        mOrder = mDAO.listar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListOrderFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_order, container, false);
        unbinder = ButterKnife.bind(this, layout);
        mAdapter = new OrderAdapter(getActivity(), mOrder);
        mListView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listOrder)
    void onItemClicked(int position) {
        Log.v("ListOrderFragment", "onItemClicked");

        Order order = mAdapter.getItem(position);
        if (getActivity() instanceof ClickOnOrderListener) {
            ClickOnOrderListener listener = (ClickOnOrderListener) getActivity();
            listener.orderClicked(order);
        }
    }

    public interface ClickOnOrderListener {
        void orderClicked(Order order);
    }
}
