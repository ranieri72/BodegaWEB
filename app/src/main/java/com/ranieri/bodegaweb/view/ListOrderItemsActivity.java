package com.ranieri.bodegaweb.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.view.fragments.ListOrderItemsFragment;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;

import org.parceler.Parcels;

import butterknife.ButterKnife;

public class ListOrderItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);
        Log.v("ListOrderItemsActivity", "onCreate");
        ButterKnife.bind(this);

        Provider provider = Parcels.unwrap(getIntent().getParcelableExtra("provider"));
        Produtos produtos = Parcels.unwrap(getIntent().getParcelableExtra("produtos"));

        ListOrderItemsFragment fragment = ListOrderItemsFragment.novaInstancia(provider, produtos);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_order_items, fragment, "detalhe").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ListOrderItemsActivity", "onResume");
    }
}
