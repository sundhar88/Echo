package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.StringTokenizer;

public class ShopOwner extends AppCompatActivity {

    private ImageView dp;
    private EditText seller_name;
    private Button proceed;
    private static final int GALLERY_INTENT = 2;

    private String EMAIL, USER_TYPE, SELLER_NAME, PHOTO_SELLER_DP, PHONE_NO;

    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner);

        initViews();
        getINTENTEDvalues();

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELLER_NAME = seller_name.getText().toString().trim();
                if (!SELLER_NAME.equals(null))
                {
                    InitDataAboutSeller();
                    pushDataIntent();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"SELLER NAME IS ESSENTIAL TO PROCEED !",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

    }

    private void InitDataAboutSeller() {

        databaseReference.child("ECHOSHOPPER").child("SELLERS").child(getBACK(EMAIL)).child("EMAIL").setValue(EMAIL);
        databaseReference.child("ECHOSHOPPER").child("SELLERS").child(getBACK(EMAIL)).child("PHONE").setValue(PHONE_NO);

    }

    private String getBACK(String email) {

        StringTokenizer stringTokenizer = new StringTokenizer(email,"@");
        return stringTokenizer.nextToken();

    }

    private void pushDataIntent() {

        Intent intent = new Intent(getApplicationContext(),ShopINFO.class);
        intent.putExtra("EMAIL",EMAIL);
        intent.putExtra("PHONE",PHONE_NO);
        intent.putExtra("USER_TYPE",USER_TYPE);
        intent.putExtra("SELLER_NAME",SELLER_NAME);
        intent.putExtra("IMAGE_URL",PHOTO_SELLER_DP);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {

            progressDialog.setMessage("updating profile . . .");
            progressDialog.show();

            Uri imageUri = data.getData();
            try {
                final InputStream inputStream = getContentResolver().openInputStream(imageUri);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                dp.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            final StorageReference mRef = storageReference.child(USER_TYPE).child("DP").child(PHONE_NO).child(imageUri.getLastPathSegment());

            mRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"DP updated",Toast.LENGTH_SHORT).show();
                }
            });

            mRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = task.getResult().getMetadata().getDownloadUrl();
                    PHOTO_SELLER_DP = downloadUrl.toString();
                    progressDialog.dismiss();
                }
            });

        }

    }

    private void getINTENTEDvalues() {

        EMAIL = getIntent().getStringExtra("EMAIL");
        USER_TYPE = getIntent().getStringExtra("USER_TYPE");
        PHONE_NO = getIntent().getStringExtra("PHONE");

    }

    private void initViews() {

        dp = (ImageView) findViewById(R.id.seller_dp_echo);
        seller_name = (EditText)findViewById(R.id.Seller_echo_name);
        proceed = (Button)findViewById(R.id.proceed_fur);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        progressDialog = new ProgressDialog(ShopOwner.this);

    }


}
