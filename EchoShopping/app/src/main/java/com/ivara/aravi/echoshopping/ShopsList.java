package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopsList extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String SHOP_NAME, SELLER_EMAIL,PHONE_SELLER;
    private RecyclerView recyclerViewShopList;
    private ArrayList<String> shopsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_list);

        FIREdata();

        initValuesOrObjects();

        Log.d("Aravi",""+PHONE_SELLER + " "+ PHONE_SELLER +" "+SHOP_NAME);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ShopCardAdapter shopCardAdapter = new ShopCardAdapter(SHOP_NAME, getApplicationContext(), shopsList.get(0));
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewShopList.setAdapter(shopCardAdapter);
        recyclerViewShopList.setLayoutManager(layoutManager);


    }

    private void FIREdata() {
        PHONE_SELLER = getIntent().getStringExtra("PHONE");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        DatabaseReference dataSellerGetData = databaseReference.child("ECHO_SHOPPER").child("SELLER").child(PHONE_SELLER).child("shop_NAME");

        dataSellerGetData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SHOP_NAME = String.valueOf(dataSnapshot.getValue());
                Log.d("Aravi",""+SHOP_NAME);
                Log.d("Aravi",""+PHONE_SELLER + " "+ PHONE_SELLER +" "+SHOP_NAME);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                SHOP_NAME = String.valueOf(dataSnapshot.getValue());
                Log.d("Aravi",""+SHOP_NAME);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initValuesOrObjects() {


        Log.d("Aravi",""+PHONE_SELLER + " "+ PHONE_SELLER);

        shopsList.add(PHONE_SELLER);

        recyclerViewShopList = (RecyclerView)findViewById(R.id.recycler_view_shop_list);

        SHOP_NAME = getIntent().getStringExtra("SHOP_NAME");

    }
}
