package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivara.aravi.echo.R;

import java.io.File;

public class DescriptionActivity extends AppCompatActivity {

    private TextView desc_title;
    private TextView desc_sender;
    private TextView desc_msg;
    private ImageView desc_img;
    private String title, msg, sender, photo_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        desc_img = (ImageView)findViewById(R.id.description_iv);
        desc_msg = (TextView)findViewById(R.id.msg_desc);
        desc_sender = (TextView)findViewById(R.id.sender_desc);
        desc_title = (TextView)findViewById(R.id.title_desc);

        title = getIntent().getStringExtra("TIT");
        msg = getIntent().getStringExtra("MSG");
        sender = getIntent().getStringExtra("SENDER");
        photo_name = getIntent().getStringExtra("cat");

        if (!title.equals(null))
        {
            desc_title.setText(title);
        }

        if (!msg.equals(null))
        {
            desc_msg.setText(msg);
        }

        if (!sender.equals(null))
        {
            desc_sender.setText(sender);
        }

        if (!photo_name.equals(null))
        {
            File ImageFile = new File(Environment.getExternalStorageDirectory() + "/imageViewFolder/"+photo_name+".jpeg");
            if (ImageFile.exists())
            {

                Bitmap bitmap = BitmapFactory.decodeFile(ImageFile.getAbsolutePath());
                desc_img.setImageBitmap(bitmap);

            }
        }

    }
}
