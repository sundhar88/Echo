package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class ShopDataFetcher extends AppCompatActivity {

    private String PHONE_NO, PHOTO_URL;
    private String LAT, LONGI, DUMMY, SELLER_PHONE_NO, SELLER_TYPE, SHOP_TYPE, SELLER_DP, SHOP_OWNER, SHOP_NAME, SELLER_MAIL;
    private Firebase fb ;
    private String URL = "https://abbsmeet.firebaseio.com/";
    private ArrayList<String> shopData = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_data_fetcher);
        Firebase.setAndroidContext(this);

        fb = new Firebase(URL);
        progressDialog = new ProgressDialog(ShopDataFetcher.this);
        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Connecting to shop . . .");

        PHONE_NO = getIntent().getStringExtra("PHONE");
        Log.d("Aravi","Dashhhhhhhhhh "+PHONE_NO);

        Firebase dataget = fb.child("ECHOSHOPPER").child("SELLER").child(PHONE_NO);

        dataget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot ds : dataSnapshot.getChildren())
                {
                    shopData.add(String.valueOf(ds.getValue()));
                    // Toast.makeText(getApplicationContext(),""+ds,Toast.LENGTH_SHORT).show();
                }

                progressDialog.show();

                File data = new File("/sdcard/Download","fire.txt");

                try {
                    FileOutputStream outputStream = new FileOutputStream(data);
                    PrintWriter printWriter = new PrintWriter(outputStream);

                    for (String temp : shopData)
                    {
                        printWriter.write(temp + "++++");
                    }

                    printWriter.close();
                    outputStream.close();

                    PhotoURLgrabber();



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void PhotoURLgrabber() {

        Firebase dataget = fb.child("ECHOSHOPPER").child("SELLER").child(PHONE_NO).child("SHOP_PHOTOS");

        dataget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                PHOTO_URL = dataSnapshot.child("PHOTO1").getValue(String.class);
                DownloadPhoto();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    private void DownloadPhoto() {

        progressDialog.setMessage("Loading image . . .");

        AndroidNetworking.initialize(getApplicationContext());
        File ImageFile = new File(Environment.getExternalStorageDirectory() + "/imageViewFolder","shop1.jpeg");
        AndroidNetworking.download(PHOTO_URL,Environment.getExternalStorageDirectory() + "/imageViewFolder","shop1.jpeg")
                .build()
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Connection Complete",Toast.LENGTH_SHORT).show();
                        intentPush();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }


    private void intentPush() {

        Intent intent = new Intent(getApplicationContext(), ShopInsight.class);
        intent.putExtra("PHONE",PHONE_NO);
        startActivity(intent);

    }
}
