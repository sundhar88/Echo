package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class SignUpActivity extends AppCompatActivity {

    private EditText email, password,phone;
    private Button reg;

    private String USER_TYPE;
    private String TYPE_OF_USER;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        initViews();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                String phoneNo = phone.getText().toString();

                if (!Email.equals(null))
                {
                    if (!Password.equals(null))
                    {
                        if (!phoneNo.equals(null)) {
                            RegisterUSER(Email, Password, phoneNo);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Mobile No is needed to proceed !",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Password is Empty",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Email is Empty",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void RegisterUSER(final String email, String password, final String phoneNo) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Registration Successful",Toast.LENGTH_SHORT).show();
                            DatabaseReference databaseReference = firebaseDatabase.getReference();
                            databaseReference.child("ECHOSHOPPER").child(USER_TYPE).child(phoneNo).child("CREATED").setValue(email);
                            databaseReference.child("ECHOSHOPPER").child("EMAILS").child(phoneNo).child("USE").setValue(TYPE_OF_USER);
                            databaseReference.child("ECHOSHOPPER").child("EMAILS").child(phoneNo).child("MAIL").setValue(email);
                            databaseReference.child("ECHOSHOPPER").child("EMAILS").child(phoneNo).child("PHONE").setValue(phoneNo);
                            databaseReference.child("ECHOSHOPPER").child("EMAILS").child(getBACK(email)).child("useCASE").setValue(getBACK(email));
                            if (USER_TYPE.equals("SELLER"))
                            {
                                ShopInfoSetter(email,phoneNo);
                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"Error : "+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private String getBACK(String email) {
        String emd ;
        StringTokenizer stringTokenizer = new StringTokenizer(email,"@");
        emd = stringTokenizer.nextToken();
        return emd;
    }

    private void ShopInfoSetter(String email, String phoneNo) {

        Intent intent = new Intent(getApplicationContext(), ShopOwner.class);
        intent.putExtra("EMAIL",email);
        intent.putExtra("USER_TYPE",USER_TYPE);
        intent.putExtra("PHONE",phoneNo);
        startActivity(intent);

    }

    private void initViews() {

        email = (EditText)findViewById(R.id.email_echo);
        password = (EditText)findViewById(R.id.pass_echo);
        phone = (EditText)findViewById(R.id.phone_user);

        reg = (Button)findViewById(R.id.register_echo_up);

        USER_TYPE = getIntent().getStringExtra("TYPE").toString().toUpperCase();

        if (USER_TYPE.equals("SELLER"))
        {
            TYPE_OF_USER = "SELLER";
        }
        else {
            TYPE_OF_USER = "CONSUMER";
        }

    }
}
