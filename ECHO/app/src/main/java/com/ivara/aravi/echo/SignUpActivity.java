package com.ivara.aravi.echo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignUpActivity extends AppCompatActivity {

    static final String APP_ID = "61145";
    static final String AUTH_KEY = "783YOzRzR4gw-YJ";
    static final String AUTH_SECRET = "WDC5VeK9k-g4T-A";
    static final String ACCOUNT_KEY = "uWDNYGzogwZbA1fyZ9Kx";

    Button btnSignUp, btnCancel;
    EditText edtUser, edtPass,editFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerSession();

        btnSignUp = (Button)findViewById(R.id.signup_btnSignUp);
        btnCancel = (Button)findViewById(R.id.signup_btnSignUpCancel);

        edtUser = (EditText)findViewById(R.id.signup_editLogin);
        edtPass = (EditText)findViewById(R.id.signup_editPassword);
        editFullName = (EditText)findViewById(R.id.signup_editFullName);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = edtUser.getText().toString().trim();
                String password = edtPass.getText().toString().trim();
                String fullname = editFullName.getText().toString().trim();

                QBUser qbUser = new QBUser(user, password);

                qbUser.setFullName(fullname);

                QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(getApplicationContext(),"Registered Successfully !!!",Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    private void registerSession() {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("ERROR",e.getMessage());
            }
        });
    }
}
