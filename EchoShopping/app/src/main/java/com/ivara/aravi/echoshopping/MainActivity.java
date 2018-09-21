package com.ivara.aravi.echoshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView seller, consumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentBasedOnSelection("SELLER");
            }
        });

        consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentBasedOnSelection("CONSUMER");
            }
        });

    }

    private void intentBasedOnSelection(String seller_echo) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        intent.putExtra("TYPE",seller_echo);
        startActivity(intent);
    }

    private void initViews() {

        seller = (ImageView)findViewById(R.id.echo_seller);
        consumer = (ImageView)findViewById(R.id.echo_customer);

    }


}
