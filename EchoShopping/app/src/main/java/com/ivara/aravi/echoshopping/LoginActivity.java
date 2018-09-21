package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivara.aravi.echoshopping.sliders.MyIntro;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ArrayList<String> listofUsers = new ArrayList<>();

    private EditText email, password_view;
    private Button signin, register;
    private ImageButton imageButton;
    private String USER_TYPE, PHONE_NO;
    private ProgressDialog progressDialog;

    public boolean isFirstStart;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Intro App Initialize SharedPreferences
                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

                //  Check either activity or app is open very first time or not and do action
                if (isFirstStart) {

                    //  Launch application introduction screen
                    Intent i = new Intent(LoginActivity.this, MyIntro.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("loading");

        initViews();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String password = password_view.getText().toString().trim();

                if (!user.equals(null))
                {
                    if (!password.equals(null))
                    {
                        progressDialog.setMessage("Connecting . . .");
                        progressDialog.show();
                        SignINUSER(user, password);
                    }
                    else Toast.makeText(getApplicationContext(),"Password is missing",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Email is missing",Toast.LENGTH_SHORT).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Demo Fill Credentials Activated !",Toast.LENGTH_SHORT).show();
                email.setText("aravinraj89@live.in");
                password_view.setText("12345678");
            }
        });


    }

    private void SignINUSER(final String user, String password) {

        progressDialog.setMessage("Authenticating . . .");

        mAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), " Welcome " + user, Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    UserTypeIdentifier(user);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Error : "+task.getResult() + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void UserTypeIdentifier(final String user) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        DatabaseReference getType = databaseReference.child("ECHOSHOPPER").child("EMAILS");

        getType.child(getBACK(user)).child("PHONE").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PHONE_NO = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        callTheDataCheckingTeam(user);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                progressDialog.dismiss();
                STARTintentByUserType(user);
            }
        };

        timer.schedule(timerTask,1000*3);


    }

    private String getBACK(String email) {
        String emd ;
        StringTokenizer stringTokenizer = new StringTokenizer(email,"@");
        emd = stringTokenizer.nextToken();
        return emd;
    }

    private void STARTintentByUserType(final String user) {

        if ("CONSUMER".equals(USER_TYPE))
        {
            Intent intent1 = new Intent(getApplicationContext(),CONSUMER.class);
            intent1.putExtra("MAIL",user);
            intent1.putExtra("PHONE",PHONE_NO);
            startActivity(intent1);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(),SELLER.class);
            intent.putExtra("MAIL",user);
            intent.putExtra("PHONE",PHONE_NO);
            startActivity(intent);
        }

    }

    private void callTheDataCheckingTeam(String user) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("ECHOSHOPPER").child("EMAILS").child(getBACK(user)).child("USE");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                USER_TYPE = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initViews() {

        email = (EditText)findViewById(R.id.email_echo);
        password_view = (EditText)findViewById(R.id.pass_echo);

        signin = (Button)findViewById(R.id.sign_echo);
        register = (Button)findViewById(R.id.register_echo);

        imageButton = (ImageButton)findViewById(R.id.imageButton);

    }
}
