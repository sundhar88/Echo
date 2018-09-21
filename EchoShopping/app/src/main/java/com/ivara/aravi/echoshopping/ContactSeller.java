package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ContactSeller extends AppCompatActivity {

    private TextView SellerName, SelllerPhone, SellerEmail, SellerShop;
    private String Seller_Name, Seller_Phone, Seller_Email, Seller_Shop, Seller_DP;
    private ImageView sellerDP;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);

        initValuesAndViews();

        progressDialog.show();

        getToLoadImage();

        SelllerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callSeller = new Intent(Intent.ACTION_CALL);
                callSeller.setData(Uri.parse("tel:" + Seller_Phone));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(getApplicationContext(),"Allow permission to make Call to Seller",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(callSeller);
            }
        });

        SellerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendEmail = new Intent(Intent.ACTION_SEND);
                sendEmail.setData(Uri.parse("mailto:"+Seller_Email));
                sendEmail.setType("text/plain");
                startActivity(sendEmail);
            }
        });

    }

    private void getToLoadImage() {

        Glide.with(getApplicationContext()).load(Seller_DP)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(sellerDP);

        progressDialog.dismiss();

    }

    private void initValuesAndViews() {

        progressDialog = new ProgressDialog(ContactSeller.this);
        progressDialog.setMessage("Fetching seller info . . .");

        SellerEmail = (TextView)findViewById(R.id.sel_email);
        SellerName = (TextView)findViewById(R.id.sel_name);
        SelllerPhone = (TextView)findViewById(R.id.sel_phone);
        SellerShop = (TextView)findViewById(R.id.shop_name_sel);
        sellerDP = (ImageView)findViewById(R.id.sel_dp);

        Seller_Name = getIntent().getStringExtra("SELLERNAME");
        Seller_Phone = getIntent().getStringExtra("SELLERPHONE");
        Seller_Email = getIntent().getStringExtra("SELLEREMAIL");
        Seller_Shop = getIntent().getStringExtra("SHOPNAME");
        Seller_DP = getIntent().getStringExtra("SELLERDP");

        SellerEmail.setText(Seller_Email);
        SellerName.setText(Seller_Name);
        SelllerPhone.setText(Seller_Phone);
        SellerShop.setText(Seller_Shop);


    }
}
