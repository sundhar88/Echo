package com.ivara.aravi.echoshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Requestings extends AppCompatActivity {

    private String PHONE_NO, Address, PAYMENT, PRODUCT, USER;
    private Firebase fb;
    private String URL = "https://abbsmeet.firebaseio.com/";
    private String[] iterat = new String[] {"Address", "PAYMENT", "PRODUCT", "USER"};
    private TextView prod, adm, user, paymentM;
    private ImageButton acc, rej;
    Boolean hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestings);
        Firebase.setAndroidContext(this);

        PHONE_NO = getIntent().getStringExtra("PHONE");

        prod = (TextView)findViewById(R.id.prod_nam);
        adm = (TextView)findViewById(R.id.addrr);
        user = (TextView)findViewById(R.id.PendingStatus);
        paymentM = (TextView)findViewById(R.id.pays);
        acc = (ImageButton)findViewById(R.id.accept_tick);
        rej = (ImageButton)findViewById(R.id.reject_x);

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hello = true;
                SetterCalled();

                Toast.makeText(getApplicationContext(),"Request Accepted",Toast.LENGTH_LONG).show();

            }
        });

        rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hello = false;
                SetterCalled();
                Toast.makeText(getApplicationContext(),"Request rejected !",Toast.LENGTH_LONG).show();
            }
        });

        fb = new Firebase(URL);
        Firebase data = fb.child("ECHOSHOPPING").child("REQUESTS").child(PHONE_NO);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Address = dataSnapshot.child("Address").getValue(String.class);
                PAYMENT = dataSnapshot.child("PAYMENT").getValue(String.class);
                PRODUCT = dataSnapshot.child("PRODUCT").getValue(String.class);
                USER = dataSnapshot.child("USER").getValue(String.class);

                setDataToViews();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }

    private void SetterCalled() {

        if (hello)
        {
            fb.child("ECHOSHOPPING").child("REQUESTS").child(PHONE_NO).child("STATUS").setValue("ACCEPTED");
        }
        else {
            fb.child("ECHOSHOPPING").child("REQUESTS").child(PHONE_NO).child("STATUS").setValue("REJECTED");
        }

    }

    private void setDataToViews() {

        user.setText(USER);
        paymentM.setText(PAYMENT);
        prod.setText(PRODUCT);
        adm.setText(Address);

    }
}
