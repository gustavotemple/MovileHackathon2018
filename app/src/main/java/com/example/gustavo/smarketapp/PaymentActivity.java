package com.example.gustavo.smarketapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    @BindView(R.id.finish)
    public Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        ButterKnife.bind(this);

        finish.setOnClickListener(view -> {
            Toast.makeText(this, "Compra finalizada", Toast.LENGTH_LONG).show();
        });
    }
}
