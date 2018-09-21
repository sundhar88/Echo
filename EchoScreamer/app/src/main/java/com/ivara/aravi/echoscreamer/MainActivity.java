package com.ivara.aravi.echoscreamer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    private ImageView rec,push;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        rec = (ImageView)findViewById(R.id.rec_screamer);
        push = (ImageView)findViewById(R.id.push_screamer);

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent record = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (record.resolveActivity(getPackageManager()) != null) {
                    mAuth.signInWithEmailAndPassword("aravinu19@gmail.com","12345678").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            startActivityForResult(record,REQUEST_VIDEO_CAPTURE);
                        }
                    });

                }
            }
        });

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword("aravinu19@gmail.com","12345678").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        startActivity(new Intent(getApplicationContext(),LocScreamer.class));
                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK)
        {

            progressDialog.setMessage("Uploading Video . . . .");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Uri videoUri = data.getData();

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
                    progressDialog.dismiss();

                    startActivity(new Intent(getApplicationContext(),LocScreamer.class));

                }
            });

        }

    }
}
