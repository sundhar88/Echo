package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SELLER extends AppCompatActivity {

    private Button upload,gallery,msgs,request;
    private StorageReference storageReference;
    private String PHONE_NO;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        PHONE_NO = getIntent().getStringExtra("PHONE");

        progressDialog = new ProgressDialog(SELLER.this);

        upload = (Button)findViewById(R.id.priceListUpload);
        gallery = (Button)findViewById(R.id.gallery);
        msgs = (Button)findViewById(R.id.msgs);
        request = (Button)findViewById(R.id.requestss);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Connecting to Cloud . . .");
                uploadLIST();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Sorry to say this, this feature is not ready yet !",Toast.LENGTH_LONG).show();
            }
        });

        msgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Toast.makeText(getApplicationContext(),"Sorry to say this, this feature is not ready yet !",Toast.LENGTH_LONG).show();
            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Requestings.class);
                intent.putExtra("PHONE",PHONE_NO);
                startActivity(intent);
            }
        });

    }

    private void uploadLIST() {

        storageReference = FirebaseStorage.getInstance().getReference();

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,44);

        progressDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 44 && resultCode == RESULT_OK)
        {
            Uri uri = data.getData();

            StorageReference path = storageReference.child("DOCS").child(uri.getLastPathSegment());

            path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.setMessage("Uploading Document . . .");
                }
            });

            path.putFile(uri).addOnCompleteListener(SELLER.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isComplete())
                    {
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = task.getResult().getMetadata().getDownloadUrl();
                        FirebaseDatabase base = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = base.getReference();
                        databaseReference.child("ECHOSHOPPER").child("DOCS").setValue(downloadUrl.toString());
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Upload Completed.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}
