package com.example.gustavo.smarketapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.layout.simple_spinner_item;
import static com.example.gustavo.smarketapp.R.array.address;
import static com.example.gustavo.smarketapp.R.array.markets;

@SuppressLint("SimpleDateFormat")
public class AboutDeliveryActivity extends FragmentActivity {
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");
    private Button dataEntrega;
    private Button next;

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            Toast.makeText(AboutDeliveryActivity.this,
                    mFormatter.format(date), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(AboutDeliveryActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_delivery);

        dataEntrega = (Button) findViewById(R.id.date);
        next = (Button) findViewById(R.id.next);


        final LinearLayout view01 = (LinearLayout) findViewById(R.id.option01View);
        final LinearLayout view02 = (LinearLayout) findViewById(R.id.option02View);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.option01) {
                    view01.setVisibility(View.VISIBLE);
                    view02.setVisibility(View.INVISIBLE);
                }else {
                    view02.setVisibility(View.VISIBLE);
                    view01.setVisibility(View.INVISIBLE);
                }
            }
        });

        dataEntrega.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .build()
                        .show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutDeliveryActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}
