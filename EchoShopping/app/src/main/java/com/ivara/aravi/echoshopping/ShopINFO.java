package com.ivara.aravi.echoshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopINFO extends AppCompatActivity {

    private EditText SHOP_NAME;
    private Spinner SHOP_TYPE, SELLER_TYPE;
    private Button PROCEED_FURTHER;
    private String USER_EMAIL, PHONE_NO, USER_TYPE, SELECTED_SHOP_TYPE, SELECTED_SELLER_TYPE, SELLER_NAME, SELLER_DP;
    private String[] shopp_types, seller_types;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);

        initVIEWS();

        spinnerInit();

        SHOP_TYPE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SELECTED_SHOP_TYPE = shopp_types[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                SELECTED_SHOP_TYPE = null;
            }
        });

        SELLER_TYPE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SELECTED_SELLER_TYPE = seller_types[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                SELECTED_SELLER_TYPE = null;
            }
        });

        PROCEED_FURTHER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shop_name = SHOP_NAME.getText().toString().trim();

                if (!shop_name.equals(null))
                {
                    if (!SELECTED_SHOP_TYPE.equals(null)){
                        if (!SELECTED_SELLER_TYPE.equals(null))
                        {
                            EchoShopAdapter echoShopAdapter = new EchoShopAdapter(USER_EMAIL, PHONE_NO, USER_TYPE, SELECTED_SHOP_TYPE, SELECTED_SELLER_TYPE, SELLER_NAME, SELLER_DP,shop_name);
                            DatabaseReference databaseReference = firebaseDatabase.getReference();
                            databaseReference.child("ECHOSHOPPER").child(USER_TYPE).child(PHONE_NO).setValue(echoShopAdapter);
                            Toast.makeText(getApplicationContext(),"Still one step ahead !",Toast.LENGTH_SHORT).show();
                            databaseReference.child("ECHOSHOPPER").child("LASTSELLER").setValue(PHONE_NO);

                            Intent intent = new Intent(getApplicationContext(),ShopLocation.class);
                            intent.putExtra("USER_EMAIL",USER_EMAIL);
                            intent.putExtra("PHONE",PHONE_NO);
                            intent.putExtra("USER_TYPE",USER_TYPE);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"SELLER TYPE IS INEVITABLE !",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"SHOP TYPE IS ESSENTIAL !",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"SHOP NAME IS NECESSARY !",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void spinnerInit() {

        shopp_types = new String[] {"Computer and Mobiles","Paints and Hardwares", "Grocery", "Clothings", "Foods", "Electronics Parts", "Show Rooms", "Bikes and Cars", "Heavy Vehicles", "Books", "Home Decors and Furnitures", "Jewellery store", "Pharmacy", "Gift shop", "Cake Shop", "Snacks Shop"};
        seller_types = new String[] {"Wholesaler","Reseller"};

        ArrayAdapter shop_typesAA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, shopp_types);
        shop_typesAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SHOP_TYPE.setAdapter(shop_typesAA);

        ArrayAdapter seller_typesAA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, seller_types);
        seller_typesAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SELLER_TYPE.setAdapter(seller_typesAA);

    }

    private void initVIEWS() {

        SHOP_NAME = (EditText)findViewById(R.id.shop_name_echo);
        SHOP_TYPE = (Spinner)findViewById(R.id.shop_type);
        SELLER_TYPE = (Spinner)findViewById(R.id.shop_seller_type);

        PROCEED_FURTHER = (Button)findViewById(R.id.proceed_further);

        USER_EMAIL = getIntent().getStringExtra("EMAIL");
        PHONE_NO = getIntent().getStringExtra("PHONE");
        USER_TYPE = getIntent().getStringExtra("USER_TYPE");
        SELLER_NAME = getIntent().getStringExtra("SELLER_NAME");
        SELLER_DP = getIntent().getStringExtra("IMAGE_URL");

        firebaseDatabase = FirebaseDatabase.getInstance();

    }
}
