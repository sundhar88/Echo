package com.ivara.aravi.echoshopping;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class Details_Of_Shop extends AppCompatActivity {

    private TextView SHOPNAME;

    private Button priceList, NAVIGATION, DELIVERY, CONTACTSHOP, GO;
    private ImageButton status;
    private String LAT, LONGI, DUMMY, SELLER_PHONE_NO, SELLER_TYPE, SHOP_TYPE, SELLER_DP, SHOP_OWNER, SHOP_NAME, SELLER_MAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__of__shop);

        initViews();

        priceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),documentListFetcher.class));
            }
        });

        NAVIGATION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", Double.parseDouble(LAT), Double.parseDouble(LONGI), "Where the Shop is at");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

//                Intent intent = new Intent(getApplicationContext(),NaviToShop.class);
//                intent.putExtra("LAT",LAT);
//                intent.putExtra("LONGI",LONGI);
//                startActivity(intent);
            }
        });

        GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        DELIVERY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),requestDeliveryToSeller.class);
                intent.putExtra("SELLERPHONE",SELLER_PHONE_NO);
                startActivity(intent);
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StatusChecker.class);
                intent.putExtra("PHONE",SELLER_PHONE_NO);
                startActivity(intent);
            }
        });

        CONTACTSHOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ContactSeller.class);
                intent.putExtra("SELLERPHONE",SELLER_PHONE_NO);
                intent.putExtra("SELLERDP",SELLER_DP);
                intent.putExtra("SELLERNAME",SHOP_OWNER);
                intent.putExtra("SELLEREMAIL",SELLER_MAIL);
                intent.putExtra("SHOPNAME",SHOP_NAME);
                startActivity(intent);

            }
        });

    }

    private void initViews() {

        priceList = (Button)findViewById(R.id.pricelist);
        NAVIGATION = (Button)findViewById(R.id.navi);
        DELIVERY = (Button)findViewById(R.id.request);
        CONTACTSHOP = (Button)findViewById(R.id.contact);
        status = (ImageButton)findViewById(R.id.request_status);
        GO = (Button)findViewById(R.id.back);

        SHOPNAME = (TextView)findViewById(R.id.shop_name_echo_saber);

        LAT = getIntent().getStringExtra("LAT");
        LONGI =  getIntent().getStringExtra("LONGI");
        SELLER_PHONE_NO = getIntent().getStringExtra("SELLER_PHONE");
        SELLER_TYPE = getIntent().getStringExtra("SELLERCAT");
        SHOP_TYPE = getIntent().getStringExtra("SHOPCAT");
        SELLER_DP = getIntent().getStringExtra("SELLERDP");
        SHOP_OWNER = getIntent().getStringExtra("SELLERNAME");
        SHOP_NAME = getIntent().getStringExtra("SHOPNAME");

        SHOPNAME.setText(SHOP_NAME);

        SELLER_MAIL = getIntent().getStringExtra("SELLERMAIL");

    }
}
