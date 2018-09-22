package com.ivara.aravi.echo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ivara.aravi.echo.Screamer.Screamer_Activity;
import com.ivara.aravi.echo.Sliders.MyIntro;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class MainActivity extends AppCompatActivity {

    static final String APP_ID = "61145";
    static final String AUTH_KEY = "Your-QuickBlox-AUTH-KEY";
    static final String AUTH_SECRET = "Your-QuickBlox-AUTH-SECRET";
    static final String ACCOUNT_KEY = "Your-QuickBlox-ACCOUNT-KEY";

    static final int REQUEST_CODE = 1000;

    Button btnLogin, btnSignUp;
    ImageButton btnScream;
    EditText editPassword, editLogin;
    ImageButton shoppp;

    public boolean isFirstStart;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestRuntimePermission();

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
                    Intent i = new Intent(MainActivity.this, MyIntro.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();


        initializeFramework();

        btnLogin = (Button)findViewById(R.id.main_btnLogin);
        btnSignUp = (Button)findViewById(R.id.main_btnRegister);
        btnScream = (ImageButton) findViewById(R.id.Screaming);
        shoppp = (ImageButton)findViewById(R.id.open_shopper);

        editPassword = (EditText)findViewById(R.id.main_editPassword);
        editLogin = (EditText)findViewById(R.id.main_editUser);

        btnScream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchShop = getPackageManager().getLaunchIntentForPackage("com.ivara.aravi.echoscreamer");
                if (launchShop != null)
                {
                    startActivity(launchShop);
                }
            }
        });

        shoppp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchShop = getPackageManager().getLaunchIntentForPackage("com.ivara.aravi.echoshopping");
                if (launchShop != null)
                {
                    startActivity(launchShop);
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user = editLogin.getText().toString().trim();
                final String password = editPassword.getText().toString().trim();

                QBUser qbUser = new QBUser(user, password);

                QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(getApplicationContext(),"Login SuccessFul",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(),ChatDialogsActivity.class);
                        intent.putExtra("USER",user);
                        intent.putExtra("PASS",password);
                        startActivity(intent);
                        finish(); // close login activity
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(getApplicationContext(),"Credential Mismatch !!! "+ e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    private void requestRuntimePermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CODE :
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getBaseContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getBaseContext(),"Permission Denied, Allow permission to Proceed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initializeFramework() {
        QBSettings.getInstance().init(getApplicationContext(),APP_ID,AUTH_KEY,AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}
