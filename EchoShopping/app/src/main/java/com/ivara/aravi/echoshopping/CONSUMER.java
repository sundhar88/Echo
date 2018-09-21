package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class CONSUMER extends AppCompatActivity {

    private Button NearMe, FindProduct;
    private Firebase fb;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> SellerList = new ArrayList<>();
    private ArrayList<String> shopData = new ArrayList<>();
    private ArrayList<String> GrabbedData = new ArrayList<>();
    private String EMAIL, PHONE_NO, SELLER;

    private String url = "https://abbsmeet.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);
        Firebase.setAndroidContext(this);

        initVal();

        NearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetSellersList();
                Toast.makeText(getApplicationContext(),"Processing , Plz wait . . .",Toast.LENGTH_LONG).show();
//                intentForShopList();
            }
        });

        FindProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetSellersList();
                Toast.makeText(getApplicationContext(),"Sorry for Inconvinence !, This is not Availabe right now !",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Processing , Plz wait . . .",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void fileFolder() {

        Firebase data = fb.child("ECHOSHOPPER").child("SELLER").child(PHONE_NO);

        data.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String LAT = dataSnapshot.child("LATITUDE").getValue(String.class);
                String LONGI = dataSnapshot.child("LONGITUDE").getValue(String.class) + " ";
                String Phone = dataSnapshot.child("phone_NO").getValue(String.class) + " ";
                String selected_SELLER_TYPE = dataSnapshot.child("selected_SELLER_TYPE").getValue(String.class) + " ";
                String selected_SHOP_TYPE = dataSnapshot.child("selected_SHOP_TYPE").getValue(String.class) + " ";
                String seller_DP = dataSnapshot.child("seller_DP").getValue(String.class) + " ";
                String seller_NAME = dataSnapshot.child("seller_NAME").getValue(String.class) + " ";
                String shop_NAME = dataSnapshot.child("shop_NAME").getValue(String.class) + " ";
                String user_EMAIL = dataSnapshot.child("user_EMAIL").getValue(String.class) + " ";
                String user_TYPE = dataSnapshot.child("user_TYPE").getValue(String.class) + " ";
                EchoShopAdapter echoShopAdapter = new EchoShopAdapter(user_EMAIL, Phone, user_TYPE, selected_SHOP_TYPE,selected_SELLER_TYPE,seller_NAME,seller_DP,shop_NAME);

                if (LAT!=null)
                {
                    try {
                        File file = new File("/sdcard/download", "fire.txt");
                        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter fos = new BufferedWriter(fileWriter);
                        fos.write(echoShopAdapter.getUSER_EMAIL());
                        fos.write("+++");
                        fos.write(echoShopAdapter.getPHONE_NO());
                        fos.write("+++");
                        fos.write(echoShopAdapter.getUSER_TYPE());
                        fos.write("+++");
                        fos.write(echoShopAdapter.getSELECTED_SHOP_TYPE());
                        fos.write("+++");
                        fos.write(echoShopAdapter.getSELECTED_SELLER_TYPE());
                        fos.write("+++");
                        fos.write(echoShopAdapter.getSELLER_NAME());
                        fos.write("+++");
                        fos.write(echoShopAdapter.getSELLER_DP());
                        fos.write("+++");
                        fos.write(echoShopAdapter.getSHOP_NAME());
                        fos.write("+++");
                        fos.write(LAT);
                        fos.write("+++");
                        fos.write(LONGI);
                        fos.write("+++");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    fileFolder();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

    }

    private void GetSellersList() {

        fb = new Firebase(url);

        final Firebase phone = fb.child("ECHOSHOPPER").child("LASTSELLER");

        phone.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                PHONE_NO = dataSnapshot.getValue(String.class);
                proceed();
                fileFolder();

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        intentForShopList();
                    }
                };

                timer.schedule(timerTask,1000*3);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        DatabaseReference getList = databaseReference.child("ECHOSHOPPER").child(getBACK(EMAIL)).child("PHONE");

//        getList.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                PHONE_NO = String.valueOf(dataSnapshot.child("SELLERS").getValue());
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



//        GrabSellersPhone();

    }

    private void proceed (){

        Firebase dataSellerGetData = fb.child("ECHOSHOPPER").child("SELLER").child(PHONE_NO).child("shop_NAME");

        dataSellerGetData.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                SELLER = dataSnapshot.getValue(String.class);
                Log.d("Aravi",""+SELLER+PHONE_NO);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private String getBACK(String email) {

        StringTokenizer stringTokenizer = new StringTokenizer(email,"@");
        return stringTokenizer.nextToken();

    }

    private void GrabSellersPhone() {

        for (int i = 1; i <= SellerList.size(); ++i )
        {

                GrabbedData.add(SellerList.get(i));

        }

    }

    private void intentForShopList() {

        Intent intent = new Intent(getApplicationContext(),ShopsList.class);
        intent.putExtra("MAIL",EMAIL);
        intent.putExtra("GRABBED_LIST",PHONE_NO);
        intent.putExtra("SHOP_NAME",SELLER);
        intent.putExtra("PHONE",PHONE_NO);
        startActivity(intent);

    }

    private void initVal() {

        NearMe = (Button)findViewById(R.id.NEAR_ME);
        FindProduct = (Button)findViewById(R.id.find_product);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        SellerList = new ArrayList<String>();

        EMAIL = getIntent().getStringExtra("MAIL");

//        GetSellersList();

    }
}
