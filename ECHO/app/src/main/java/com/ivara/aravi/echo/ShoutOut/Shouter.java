package com.ivara.aravi.echo.ShoutOut;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ivara.aravi.echo.MainActivity;
import com.ivara.aravi.echo.R;
import com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags.ShouterAdapter;

public class Shouter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button mSelectImage;
    private TextView mEventTitle;
    private TextView mEventMessage;
    private Spinner mEventCategory;
    private String SELECTED_CATEGORY;
    private String[] Category = {"People Programs","News","Requesting"};
    private String pathToImage = "gs://abbsmeet.appspot.com/";
    private ProgressDialog progressDialog;

    private StorageReference mStorage;
    private FirebaseDatabase mRootFireBase;

    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouter);

        mRootFireBase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(Shouter.this);

        mEventCategory = (Spinner)findViewById(R.id.spinnerEvents);
        mEventCategory.setOnItemSelectedListener(this);

        mEventTitle = (TextView)findViewById(R.id.EventTitle);
        mEventMessage = (TextView)findViewById(R.id.EventMessage);

        mSelectImage = (Button)findViewById(R.id.upload);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mEventTitle.getText().toString().equals(null)) {

                    Intent intent = new Intent(Intent.ACTION_PICK);

                    intent.setType("image/*");

                    startActivityForResult(intent, GALLERY_INTENT);
                }else {
                    Toast.makeText(getApplicationContext(),"Title Should not be Empty !",Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, Category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEventCategory.setAdapter(arrayAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK ){

            progressDialog.setMessage("Uploading Image . . .");
            progressDialog.show();

            Uri uri = data.getData();

            final StorageReference filepath = mStorage.child("Photos").child(SELECTED_CATEGORY).child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(),"File Uploading Complete !",Toast.LENGTH_SHORT).show();
                    //setDataImagePath.child("LOCATIONS").child("Category").child(SELECTED_CATEGORY).setValue(taskSnapshot.getDownloadUrl().toString());

                }
            });

            filepath.putFile(uri).addOnCompleteListener(Shouter.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isComplete()){
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = task.getResult().getMetadata().getDownloadUrl();
                        ShouterAdapter shouterAdapter = new ShouterAdapter("Hai People "+mEventMessage.getText().toString(),"Aravi",mEventTitle.getText().toString(),downloadUrl.toString());
                        DatabaseReference setDataImagePath = mRootFireBase.getReference();
                        setDataImagePath.child("LOCATIONS").child("Category").child(SELECTED_CATEGORY).setValue(shouterAdapter);
                        progressDialog.dismiss();
                        // Change this place to add this Events to Coresponding States . . .
                    }
                }
            });

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SELECTED_CATEGORY = Category[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        SELECTED_CATEGORY = "News";
        Toast.makeText(getApplicationContext(),"By default News Category is Selected !",Toast.LENGTH_SHORT).show();
    }
}
