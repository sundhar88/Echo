package com.ivara.aravi.echolistener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Firebase fb;
    private String URLl = "https://abbsmeet.firebaseio.com/";
    private String VideoURL;
    private Button loc;
    private VideoView videoView;
    Double LAT,LONGI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        loc = (Button)findViewById(R.id.loc);
        videoView = (VideoView)findViewById(R.id.videoView);

        fb = new Firebase(URLl);

        fb.child("Emergency").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VideoURL = dataSnapshot.getValue(String.class);
                videoView.setVideoPath(VideoURL);
                TriggerDataFetch();

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        setVide();
                    }
                };

                timer.schedule(timerTask,1000*3);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("LAT",String.valueOf(LAT));
                intent.putExtra("LONGI",String.valueOf(LONGI));
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                };

                timer.schedule(timerTask,1000*3);
            }
        });

    }

    private void setVide() {



        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);
        videoView.requestFocus();

    }

    private void TriggerDataFetch() {

        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LAT = Double.parseDouble(dataSnapshot.child("LAT").getValue(String.class));
                LONGI = Double.parseDouble(dataSnapshot.child("LONGI").getValue(String.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
