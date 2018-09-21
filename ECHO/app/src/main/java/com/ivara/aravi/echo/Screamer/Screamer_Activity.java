package com.ivara.aravi.echo.Screamer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ivara.aravi.echo.R;

public class Screamer_Activity extends AppCompatActivity {

    private ImageView rec,push;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private Intent takeVideoIntent;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
//    private LocationManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screamer_);

//        createNetErrorDialog();

        initViews();

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Recorder",Toast.LENGTH_SHORT).show();

                RecorderVideoDa();
            }
        });

    }

    private void RecorderVideoDa() {

        takeVideoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK)
        {

            progressDialog.setMessage("Uploading Video . . . .");
            progressDialog.show();

            Uri videoUri = takeVideoIntent.getData();

            final StorageReference filepath = storageReference.child("Emergency").child(videoUri.getLastPathSegment());

            filepath.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Video Upload Complete . . .",Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = task.getResult().getMetadata().getDownloadUrl();
                        DatabaseReference setDataImagePath = firebaseDatabase.getReference();
                        setDataImagePath.child("Emergency").setValue(downloadUrl.toString());
                        progressDialog.dismiss();

                        
                    }

                }
            });

        }



    }

    private void initViews() {

        rec = (ImageView)findViewById(R.id.rec_screamer);
        push = (ImageView)findViewById(R.id.push_screamer);

        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(getApplicationContext());

    }

    protected void createNetErrorDialog() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You need internet connection for this app. Please turn on mobile network or Wi-Fi in Settings.")
                    .setTitle("Unable to connect")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                    startActivity(i);
                                }
                            }
                    )
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Screamer_Activity.this.finish();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

