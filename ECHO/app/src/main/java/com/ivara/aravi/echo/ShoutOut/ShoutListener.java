package com.ivara.aravi.echo.ShoutOut;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivara.aravi.echo.R;
import com.ivara.aravi.echo.Screamer.Screamer_Activity;
import com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags.ShoutRepo;
import com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags.ShoutStorage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class ShoutListener extends AppCompatActivity {

    private static String FIREBASE_URL = "https://abbsmeet.firebaseio.com/";

    private Firebase firebase;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_listener);
        Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(this);

        addr = (TextView)findViewById(R.id.Address);

        final String lat = getIntent().getStringExtra("LAT");
        final String longi = getIntent().getStringExtra("LONG");

        final List<Address>[] address;

        final double lati = Double.parseDouble(lat);
        final double longit = Double.parseDouble(longi);

        //Toast.makeText(getBaseContext(),"LAT :"+lat+" Long :"+longi,Toast.LENGTH_LONG).show();

        addr.setText(lati+" "+longit);

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());

                 try {
                     List<Address> address = gc.getFromLocation(lati, longit, 1);
                     if (address.size() > 0)
                     {
//                         String adrr = (address.get(0).getAddressLine(1).replaceAll("[0-9]","")).trim();
//                         StringTokenizer st = new StringTokenizer(adrr,",");
                         String stringLocale = address.get(0).getLocality();//adrr.substring(adrr.lastIndexOf(",")+1);
                         addr.setText(address.get(0).getLocality());
                         dataPresentinBase(stringLocale);
                     }
                 } catch (IOException e) {
                     e.printStackTrace();
                 }



            }
        });

//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//                try {
//                    List<Address> address = gc.getFromLocation(lati, longit, 1);
//                    if (address.size() > 0)
//                    {
//                        String adrr = (address.get(0).getAddressLine(1).replaceAll("[0-9]","")).trim();
//                        StringTokenizer st = new StringTokenizer(adrr,",");
//                        String stringLocale = adrr.substring(adrr.lastIndexOf(",")+1);
//                        addr.setText(stringLocale);
//                        dataPresentinBase(stringLocale);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        timer.schedule(timerTask,1000*3);


    }

    private void dataPresentinBase(final String stringLocale) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("LOCATIONS").addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(stringLocale))
                {
                    dataPushorAvailabe(stringLocale);
                }
                else {
                    databaseReference.child("LOCATIONS").child(stringLocale).child("Dummy").setValue("dummy");
                    dataPushorAvailabe(stringLocale);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        firebase = new Firebase(FIREBASE_URL+"LOCATIONS/");
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        Firebase dataCheck = firebase.child("Tamil Nadu").child("Dummy");
//        String textData = databaseReference.toString()+" ";
//        Toast.makeText(getApplicationContext()," "+textData,Toast.LENGTH_LONG).show();
    }

    private void dataPushorAvailabe(String stringLocale) {
        Intent intent = new Intent(getApplicationContext(),ShoutStorage.class); // ShoutsViewer.class is Changed to ShoutRepo.class for Testing !!!
        intent.putExtra("LOCALE",stringLocale);
        startActivity(intent);
    }
}
