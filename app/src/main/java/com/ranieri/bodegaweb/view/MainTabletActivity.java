package com.ranieri.bodegaweb.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.StockMovementDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.StockMovement;
import com.ranieri.bodegaweb.model.SubCategorias;
import com.ranieri.bodegaweb.model.UnidadeMedida;
import com.ranieri.bodegaweb.util.Util;
import com.ranieri.bodegaweb.view.fragments.ListCategoryFragment;
import com.ranieri.bodegaweb.view.fragments.ListCategoryFragment.ClickOnCategoryListener;
import com.ranieri.bodegaweb.view.fragments.ListProductsFragment;
import com.ranieri.bodegaweb.view.fragments.ListProductsFragment.ClickOnProductListener;
import com.ranieri.bodegaweb.view.fragments.ListSubCategoryFragment;

import java.util.Date;
import java.util.Objects;

public class MainTabletActivity extends MainGenericActivity implements ClickOnCategoryListener, ClickOnProductListener {

    ListCategoryFragment categoryFragment;
    ListProductsFragment productsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tablet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences(Util.configPreference, Context.MODE_PRIVATE);
        Util.isCountStock = sharedPreferences.getBoolean(Util.countStockPreference, true);

        ListSubCategoryFragment subCategoryFragment = new ListSubCategoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_sub_category, subCategoryFragment, "detalhe").commit();

        SubCategorias subCategoria = new SubCategoriasDAO(this).selecionarPrimeira();

        if (subCategoria != null) {
            categoryFragment = ListCategoryFragment.novaInstancia(subCategoria);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_category, categoryFragment, "detalhe").commit();

            Categorias categoria = new CategoriasDAO(this).selecionarPrimeira(subCategoria);

            if (categoria != null) {
                productsFragment = ListProductsFragment.novaInstancia(categoria);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_products, productsFragment, "detalhe").commit();
            }
        }
    }

    @Override
    public void subCategoryClicked(SubCategorias subCategoria) {
        Log.v("MainActivity", "subCategoryClicked - isTablet");
        categoryFragment.notifyDataSetChanged(subCategoria);

        Categorias categoria = new CategoriasDAO(this).selecionarPrimeira(subCategoria);
        if (categoria != null) {
            productsFragment.notifyDataSetChanged(categoria);
        }
    }

    @Override
    public void categoryClicked(Categorias categoria) {
        productsFragment.notifyDataSetChanged(categoria);
    }

    @Override
    public void productClicked(Produtos produto) {
        if (Util.isCountStock) {
            newStockDialog(produto);
        } else {
            stockMovementDialog(produto);
        }
    }

    private void stockMovementDialog(final Produtos produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setTitle(getResources().getString(R.string.stockMovement, produto.getNome()));
        builder.setMessage(getResources().getString(R.string.qtd));
        builder.setView(input);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    if (!Objects.equals(input.getText().toString(), "")) {
                        StockMovement movement = new StockMovement();
                        movement.setUser(AppSession.user);
                        movement.setQtd(Integer.parseInt(input.getText().toString()));
                        movement.setProduto(produto);
                        movement.setUnidMedida(new UnidadeMedida(1));
                        movement.setPerda(false);
                        movement.setHora(new Date());
                        movement.setData(new Date());
                        new StockMovementDAO(MainTabletActivity.this).inserir(movement);
                        Toast.makeText(MainTabletActivity.this, getResources().getQuantityString(R.plurals.produtosAtualizados, movement.getQtd(), movement.getQtd()), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainTabletActivity.this, getResources().getString(R.string.invalidinputs), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainTabletActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void newStockDialog(final Produtos produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setTitle(getResources().getString(R.string.editstock, produto.getNome()));
        builder.setMessage(getResources().getString(R.string.qtd));
        builder.setView(input);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    if (!Objects.equals(input.getText().toString(), "")) {
                        produto.setNovoEstoque(Integer.parseInt(input.getText().toString()));
                        new ProdutosDAO(MainTabletActivity.this).atualizar(produto);
                        Toast.makeText(MainTabletActivity.this, getResources().getString(R.string.estoqueAtual, produto.getNovoEstoque()), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainTabletActivity.this, getResources().getString(R.string.invalidinputs), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainTabletActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }
}