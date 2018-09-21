package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ShopImageUpload extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private int PHOTO_COUNT;
    private String USER_TYPE, USER_EMAIL, PHONE_NO;
    private ImageView SHOP_PHOTO_CANVAS;
    private ImageButton PUSH_PHOTO;
    private ProgressDialog progressDialog ;
    private AlertDialog dialog;

    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_image_upload);

        initView();

        SHOP_PHOTO_CANVAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }
        });

        PUSH_PHOTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Registration Complete !",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {

            Uri shop_image = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), shop_image);
                SHOP_PHOTO_CANVAS.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            StorageReference mRef = storageReference.child("Shop_Photos").child(PHONE_NO).child("PHOTO"+PHOTO_COUNT).child(shop_image.getLastPathSegment());



            mRef.putFile(shop_image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(),"Image Uploaded To ECHO BASE !",Toast.LENGTH_SHORT).show();

                }
            });

            mRef.putFile(shop_image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isComplete())
                    {
                        @SuppressWarnings("VisibleForTests") Uri downloadURL = task.getResult().getMetadata().getDownloadUrl();
                        databaseReference.child("ECHOSHOPPER").child(USER_TYPE).child(PHONE_NO).child("SHOP_PHOTOS").child("PHOTO"+PHOTO_COUNT).setValue(downloadURL.toString());


                    }
                }
            });

            UpdatePhotoCount(); // increment the photos of shop uploaded in integer 1

        }

    }

    private void UpdatePhotoCount() {
        databaseReference.child("ECHOSHOPPER").child(USER_TYPE).child(PHONE_NO).child("SHOP_PHOTOS").child("PHOTO_COUNT").setValue(++PHOTO_COUNT);
    }

    private void initView() {

        USER_EMAIL = getIntent().getStringExtra("USER_EMAIL");
        PHONE_NO = getIntent().getStringExtra("PHONE");
        USER_TYPE = getIntent().getStringExtra("USER_TYPE");

        SHOP_PHOTO_CANVAS = (ImageView)findViewById(R.id.shop_photos_for_echo);
        PUSH_PHOTO = (ImageButton)findViewById(R.id.push_photo_echo);

        storageReference = FirebaseStorage.getInstance().getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        DatabaseReference photoBase = databaseReference.child("ECHOSHOPPER").child(USER_TYPE).child(PHONE_NO).child("SHOP_PHOTOS");
        photoBase.child("PHOTO_COUNT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PHOTO_COUNT = dataSnapshot.getValue(int.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
