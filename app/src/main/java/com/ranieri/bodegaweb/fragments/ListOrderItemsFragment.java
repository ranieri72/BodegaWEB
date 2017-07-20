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
import com.ranieri.bodegaweb.contract.OrderItemsContract;
import com.ranieri.bodegaweb.dao.OrderItemsDAO;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.OrderItems;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;

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
    private int cod;

    public static ListOrderItemsFragment novaInstancia(Order order) {
        Log.v("ListOrderItemsFragment", "novaInstancia - Order");
        ListOrderItemsFragment fragment = new ListOrderItemsFragment();
        Bundle args = new Bundle();

        args.putParcelable("order", Parcels.wrap(order));
        args.putParcelable("cod", Parcels.wrap(1));

        fragment.setArguments(args);
        return fragment;
    }

    public static ListOrderItemsFragment novaInstancia(Provider provider, Produtos produto) {
        Log.v("ListOrderItemsFragment", "novaInstancia - Provider, Produtos");
        ListOrderItemsFragment fragment = new ListOrderItemsFragment();
        Bundle args = new Bundle();

        args.putParcelable("provider", Parcels.wrap(provider));
        args.putParcelable("produto", Parcels.wrap(produto));
        args.putParcelable("cod", Parcels.wrap(2));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListOrderItemsFragment", "onCreate");
        mLista = new ArrayList<>();
        Parcelable parcelable;
        OrderItemsDAO mDAO;

        if (getArguments() != null && getArguments().getParcelable("order") != null) {
            parcelable = getArguments().getParcelable("cod");
            cod = Parcels.unwrap(parcelable);
            parcelable = getArguments().getParcelable("order");
            Order order = Parcels.unwrap(parcelable);
            mDAO = new OrderItemsDAO(getActivity(), OrderItemsContract.TABLE_NAME);
            mLista = mDAO.listar(order);
        }

        if (getArguments() != null && getArguments().getParcelable("provider") != null && getArguments().getParcelable("produto") != null) {
            parcelable = getArguments().getParcelable("provider");
            Provider provider = Parcels.unwrap(parcelable);

            parcelable = getArguments().getParcelable("produto");
            Produtos produto = Parcels.unwrap(parcelable);

            parcelable = getArguments().getParcelable("cod");
            cod = Parcels.unwrap(parcelable);

            mDAO = new OrderItemsDAO(getActivity(), OrderItemsContract.TABLE_NAME);
            mLista = mDAO.listar(provider, produto);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListOrderItemsFragment", "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_list_order_items, container, false);
        unbinder = ButterKnife.bind(this, layout);
        adapter = new OrderItemsAdapter(getActivity(), mLista, cod);
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
