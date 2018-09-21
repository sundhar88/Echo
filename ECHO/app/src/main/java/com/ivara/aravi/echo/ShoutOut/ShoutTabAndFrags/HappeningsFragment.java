package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ivara.aravi.echo.R;

import java.util.ArrayList;

/**
 * Created by Aravi on 27-08-2017.
 */

public class HappeningsFragment extends Fragment {

    private Context context;
    private ArrayList<String> arrayList;
    private FirebaseDatabase mFireBaseDB;
    private Firebase fb;
    private StorageReference mRootDrive;
    private String fburl = "https://abbsmeet.firebaseio.com/LOCATIONS/Category/News";
    private String[] alt = new String[] {"Happenings One","Happenings Two","Happenings Three","Happenings Four", "Happenings Five" , "Happenings Six" , "Happenings Seven"};

    public HappeningsFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFireBaseDB = FirebaseDatabase.getInstance();

        DatabaseReference setDataImagePath = mFireBaseDB.getReference();
        final String Title[] = new String[1];
        final String[] url = new String[1];
        final String[] temp = new String[1];

        setDataImagePath.child("LOCATIONS").child("Category").child("News").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//               arrayList.add(dataSnapshot.child("event_TITLE").getValue(String.class));
//               Log.e("Aravi's Data Tester", arrayList.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        setDataImagePath.child("LOCATIONS").child("Category").child("News").child("photo_URL").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                url[0] = dataSnapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        ShouterAdapter sa = new ShouterAdapter();

//        fb = new Firebase(fburl);
//        fb.addValueEventListener(new com.firebase.client.ValueEventListener() {
//            @Override
//            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                Title[0] = dataSnapshot.child("event_TITLE").getValue(String.class);
//                temp[0] = String.valueOf(Title[0]);
//                url[0] = dataSnapshot.child("photo_URL").getValue(String.class);
//                Log.e("Aravi Data Tester",Title[0] + url[0]);
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        View rootView = inflater.inflate(R.layout.fragment_happenings, container, false);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rv_recyclerview_tab2);
        rv.setHasFixedSize(true);
//        if (Title.equals(null)) {
//            HappeningsAdapter HA = new HappeningsAdapter(alt, url[0]);
//            rv.setAdapter(HA);
//        }
//        else {
            HappeningsAdapter HA = new HappeningsAdapter(context);
            rv.setAdapter(HA);
//        }

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rootView;
    }
}
