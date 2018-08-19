package com.example.gustavo.smarketapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.cart)
    public RecyclerView recyclerView;

    @BindView(R.id.pay)
    public Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle(R.string.cart);

        ButterKnife.bind(this);

        pay.setOnClickListener(view -> {
            Intent intent = new Intent(this, AboutDeliveryActivity.class);
            startActivity(intent);
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final CartListAdapter cartListAdapter = new CartListAdapter();
        recyclerView.setAdapter(cartListAdapter);

        ArrayList<Product> result = getIntent().getParcelableArrayListExtra("list");

        cartListAdapter.init(result);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
