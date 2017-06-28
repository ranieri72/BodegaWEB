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
import com.ranieri.bodegaweb.adapter.OrderItemsAdapter;
import com.ranieri.bodegaweb.dao.OrderItemsDAO;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.OrderItems;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class ListOrderItemsFragment extends Fragment {

    @BindView(R.id.listOrderItems)
    ListView mListView;

    List<OrderItems> mLista;
    OrderItemsAdapter adapter;
    Unbinder unbinder;

    public static ListOrderItemsFragment novaInstancia(Order order) {
        Log.v("ListOrderItemsFragment", "novaInstancia");
        ListOrderItemsFragment fragment = new ListOrderItemsFragment();
        Bundle args = new Bundle();
        Parcelable parcelable = Parcels.wrap(order);
        args.putParcelable("order", parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListOrderItemsFragment", "onCreate");
        mLista = new ArrayList<>();
        Order order;

        if (getArguments() != null) {
            Parcelable parcelable = getArguments().getParcelable("order");
            order = Parcels.unwrap(parcelable);
            OrderItemsDAO mDAO = new OrderItemsDAO(getActivity());
            mLista = mDAO.listar(order);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListOrderItemsFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_order_items, container, false);
        unbinder = ButterKnife.bind(this, layout);
        adapter = new OrderItemsAdapter(getActivity(), mLista);
        mListView.setAdapter(adapter);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listOrderItems)
    void onItemClicked(int position) {
        Log.v("ListOrderItemsFragment", "onItemClicked");

        OrderItems orderItems = adapter.getItem(position);
        if (getActivity() instanceof ListOrderItemsFragment.ClickOnOrderItemsListener) {
            ListOrderItemsFragment.ClickOnOrderItemsListener listener = (ListOrderItemsFragment.ClickOnOrderItemsListener) getActivity();
            listener.orderItemsClicked(orderItems);
        }
    }

    public interface ClickOnOrderItemsListener {
        void orderItemsClicked(OrderItems orderItems);
    }

}
