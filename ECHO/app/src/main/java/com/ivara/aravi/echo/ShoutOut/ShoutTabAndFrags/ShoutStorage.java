package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivara.aravi.echo.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class ShoutStorage extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDB;
    private String[] category = new String[] {"News","People Programs","Requesting"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_storage);

        mFireBaseDB = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mFireBaseDB.getReference();
//        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
//        progressDialog.setMessage("Processing data from network . . . .");
//        progressDialog.show();

        for (final String tabName : category) {

            dbRef.child("LOCATIONS").child("Category").child(tabName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String tempData = dataSnapshot.child("event_TITLE").getValue(String.class);
                    String msg = dataSnapshot.child("event_MESSAGE").getValue(String.class) + " ";
                    String sender = dataSnapshot.child("event_MESSAGE").getValue(String.class) + " ";
                    String https = dataSnapshot.child("photo_URL").getValue(String.class) + " ";
                    ShouterAdapter shouterAdapter = new ShouterAdapter(msg, sender, tempData, https);
                    if (shouterAdapter.getEVENT_TITLE() != null) {
                        try {
                            File file = new File("/sdcard/download", tabName + ".psk");
                            FileWriter fw = new FileWriter(file.getAbsoluteFile());
                            BufferedWriter fos = new BufferedWriter(fw);
                            fos.write(shouterAdapter.getEVENT_TITLE());
                            fos.write("+++");
                            fos.write(shouterAdapter.getEVENT_MESSAGE());
                            fos.write("+++");
                            fos.write(shouterAdapter.getEVENT_SENDER());
                            fos.write("+++");
                            fos.write(shouterAdapter.getPHOTO_URL());
                            fos.close();

                            AndroidNetworking.initialize(getApplicationContext());
                            File ImageFile = new File(Environment.getExternalStorageDirectory() + "/imageViewFolder", tabName + ".jpeg");
                            AndroidNetworking.download(shouterAdapter.getPHOTO_URL(), Environment.getExternalStorageDirectory() + "/imageViewFolder", tabName + ".jpeg")
                                    .build()
                                    .startDownload(new DownloadListener() {
                                        @Override
                                        public void onDownloadComplete() {
                                            Toast.makeText(getApplicationContext(), "IMG LOADED", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(ANError anError) {

                                        }
                                    });

                            Toast.makeText(getApplicationContext(), "Success " + shouterAdapter.getEVENT_TITLE(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), ShoutRepo.class));
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "failed" + shouterAdapter.getEVENT_TITLE(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

//        progressDialog.dismiss();

    }
}
