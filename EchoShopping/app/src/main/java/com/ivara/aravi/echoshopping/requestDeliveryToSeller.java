package com.ivara.aravi.echoshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.net.URL;

public class requestDeliveryToSeller extends AppCompatActivity {

    private String PHONE, SELECTED_PAYMENT;
    private Button submit;
    private EditText product_name, address;
    private Spinner spinner;
    private Firebase fb ;
    private String URL = "https://abbsmeet.firebaseio.com/";
    private String[] pay_methods = new String[] {
            "CASH ON DELIVERY",
            "CARDS ( Credit or Debit )",
            "Android Pay",
            "UPI",
            "Samsung Pay",
            "Online Transfer"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_delivery_to_seller);

        PHONE = getIntent().getStringExtra("SELLERPHONE");

        initViewS();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SELECTED_PAYMENT = pay_methods[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                SELECTED_PAYMENT = "CASH ON DELIVERY";
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ProdName = product_name.getText().toString().trim();
                String ADDR = address.getText().toString().trim();

                if (!ProdName.equals(null))
                {
                    if (!ADDR.equals(null))
                    {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        DatabaseReference data;
                        databaseReference.child("ECHOSHOPPING").child("REQUESTS").child(PHONE).child("PRODUCT").setValue(ProdName);
                        databaseReference.child("ECHOSHOPPING").child("REQUESTS").child(PHONE).child("Address").setValue(ADDR);
                        databaseReference.child("ECHOSHOPPING").child("REQUESTS").child(PHONE).child("PAYMENT").setValue(SELECTED_PAYMENT);
                        databaseReference.child("ECHOSHOPPING").child("REQUESTS").child(PHONE).child("USER").setValue("aravinu19");
                        databaseReference.child("ECHOSHOPPING").child("REQUESTS").child(PHONE).child("STATUS").setValue("PENDING");
                        Toast.makeText(getApplicationContext(),"Request Sent",Toast.LENGTH_LONG).show();

                        product_name.setText("");
                        address.setText("");

                    }
                }

            }
        });

    }

    private void initViewS() {

        fb = new Firebase(URL);

        submit = (Button)findViewById(R.id.submit_request);
        product_name = (EditText)findViewById(R.id.prod_name);
        address = (EditText)findViewById(R.id.address);
        spinner = (Spinner)findViewById(R.id.spinner_payment);

        ArrayAdapter payment = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pay_methods);
        payment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(payment);

    }
}
