package com.ivara.aravi.echoshopping;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class ShopInsight extends AppCompatActivity {

    private TextView SHOPNAME,SELLERNAME,SHOPCATEGORY;
    private Button desc;
    private ImageView imgBG;

    private String PHONE_NO;
    private ArrayList<String> shopData = new ArrayList<>();
    private ArrayList<String> intenting = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Firebase fb ;
    private String URL = "https://abbsmeet.firebaseio.com/";
    private int count;
    private String LAT, LONGI, DUMMY, SELLER_PHONE_NO, SELLER_TYPE, SHOP_TYPE, SELLER_DP, SHOP_OWNER, SHOP_NAME, SELLER_MAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_insight);
        Firebase.setAndroidContext(this);

        initValuesOrObjects();

        PHONE_NO = getIntent().getStringExtra("PHONE");
        Log.d("Aravi","Dashhhhhhhhhh "+PHONE_NO);

//        Firebase dataget = fb.child("ECHOSHOPPER").child("SELLER").child(PHONE_NO);

//        dataget.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                for (com.firebase.client.DataSnapshot ds : dataSnapshot.getChildren())
//                {
//                    shopData.add(String.valueOf(ds.getValue()));
//                   // Toast.makeText(getApplicationContext(),""+ds,Toast.LENGTH_SHORT).show();
//                }
//
//                File data = new File("/sdcard/Download","fire.txt");
//
//                try {
//                    FileOutputStream outputStream = new FileOutputStream(data);
//                    PrintWriter printWriter = new PrintWriter(outputStream);
//
//                    for (String temp : shopData)
//                    {
//                        printWriter.write(temp + "++++");
//                    }
//
//                    printWriter.close();
//                    outputStream.close();
//
//                    TrigerDataSet();
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        TrigerDataSet();

//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                TrigerDataSet();
//            }
//        };
//
//        timer.schedule(timerTask,1000*2);

        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAllTheData();
            }
        });

    }

    private void intentAllTheData() {

        Intent intent = new Intent(getApplicationContext(),Details_Of_Shop.class);
        intent.putExtra("LAT",LAT);
        intent.putExtra("LONGI",LONGI);
        intent.putStringArrayListExtra("PHOTO_URL",intenting);
        intent.putExtra("SELLER_PHONE",SELLER_PHONE_NO);
        intent.putExtra("SHOPCAT",SHOP_TYPE);
        intent.putExtra("SELLERCAT",SELLER_TYPE);
        intent.putExtra("SELLERDP",SELLER_DP);
        intent.putExtra("SELLERNAME",SHOP_OWNER);
        intent.putExtra("SHOPNAME",SHOP_NAME);
        intent.putExtra("SELLERMAIL",SELLER_MAIL);
        startActivity(intent);

    }

    private void TrigerDataSet() {

        TokenCreation();
        PhotoSet();

    }

    private void TokenCreation() {

        File fileData = new File("/sdcard/Download","fire.txt");
        try {
            FileReader fileReader = new FileReader(fileData.getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String All = bufferedReader.readLine();
            StringTokenizer dataGrabber = new StringTokenizer(All, "++++");
            LAT = dataGrabber.nextToken();
            LONGI = dataGrabber.nextToken();
            DUMMY = dataGrabber.nextToken();
            SELLER_PHONE_NO = dataGrabber.nextToken();
            SELLER_TYPE = dataGrabber.nextToken();
            SHOP_TYPE = dataGrabber.nextToken();
            SELLER_DP = dataGrabber.nextToken();
            SHOP_OWNER = dataGrabber.nextToken();
            SHOP_NAME = dataGrabber.nextToken();
            SELLER_MAIL = dataGrabber.nextToken();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SHOPNAME.setText(SHOP_NAME);
        SHOPCATEGORY.setText(SHOP_TYPE);
        SELLERNAME.setText(SHOP_OWNER);

    }



    private void PhotoSet() {

        Toast.makeText(getApplicationContext(),"Processing Data . . .",Toast.LENGTH_LONG).show();

        File set = new File(Environment.getExternalStorageDirectory() + "/imageViewFolder/shop1.jpeg");
        if (set.exists())
        {
            Bitmap bitmap = BitmapFactory.decodeFile(set.getAbsolutePath());
            imgBG.setImageBitmap(bitmap);
        }

    }

    private void initValuesOrObjects() {

        PHONE_NO = getIntent().getStringExtra("PHONE");
        Log.d("Aravi","Dashhhhhhhhhh "+PHONE_NO);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        fb = new Firebase(URL);

        SHOPNAME = (TextView)findViewById(R.id.shop_name_overlay);
        SELLERNAME = (TextView)findViewById(R.id.seller_name_ret);
        SHOPCATEGORY = (TextView)findViewById(R.id.seller_shop_category);

        desc = (Button)findViewById(R.id.details);

        imgBG = (ImageView)findViewById(R.id.image_bg_shop);

    }
}
