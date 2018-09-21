package com.ivara.aravi.echoshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class StatusChecker extends AppCompatActivity {

    private String PHONE_NO, Address, PAYMENT, PRODUCT, Statr;
    private Firebase fb;
    private String URL = "https://abbsmeet.firebaseio.com/";
    private TextView prod, adm, stat, paymentM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_checker);
        Firebase.setAndroidContext(this);

        prod = (TextView)findViewById(R.id.prod_nam);
        adm = (TextView)findViewById(R.id.addrr);
        stat = (TextView)findViewById(R.id.PendingStatus);
        paymentM = (TextView)findViewById(R.id.pays);

        PHONE_NO = getIntent().getStringExtra("PHONE");

        fb = new Firebase(URL);
        Firebase data = fb.child("ECHOSHOPPING").child("REQUESTS").child(PHONE_NO);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Address = dataSnapshot.child("Address").getValue(String.class);
                PAYMENT = dataSnapshot.child("PAYMENT").getValue(String.class);
                PRODUCT = dataSnapshot.child("PRODUCT").getValue(String.class);
                Statr = dataSnapshot.child("STATUS").getValue(String.class);

                setDataToViews();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void setDataToViews() {

        stat.setText(Statr);
        paymentM.setText(PAYMENT);
        prod.setText(PRODUCT);
        adm.setText(Address);

    }
}
