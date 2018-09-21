package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ivara.aravi.echo.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Aravi on 27-08-2017.
 */

public class HappeningsAdapter extends RecyclerView.Adapter<HappeningsAdapter.MyHappeningsViewHolder> {

    private Context context;
    private String title,urlPhoto,msg,sender;

    public HappeningsAdapter(Context context) {

        this.context = context;

    }

    @Override
    public MyHappeningsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_tab2_items, parent, false);
        MyHappeningsViewHolder mhvh = new MyHappeningsViewHolder(v);
        return mhvh;

    }

    @Override
    public void onBindViewHolder(MyHappeningsViewHolder holder, int position) {
        File file = new File("/sdcard/download", "News.psk");
        try {
            FileReader fileReader = new FileReader(file.getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String all = bufferedReader.readLine();
            StringTokenizer newsEvent = new StringTokenizer(all,"+++");
            title = newsEvent.nextToken();
            msg = newsEvent.nextToken();
            sender = newsEvent.nextToken();
            urlPhoto = newsEvent.nextToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.HappeningsTextView.setText(title);

        File ImageFile = new File(Environment.getExternalStorageDirectory() + "/imageViewFolder/News.jpeg");
        if (ImageFile.exists())
        {

            Bitmap bitmap = BitmapFactory.decodeFile(ImageFile.getAbsolutePath());
            holder.HappeningsImageView.setImageBitmap(bitmap);

        }

        holder.HappeningsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDescription(title,msg,sender);
            }
        });

        holder.HappeningsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDescription(title,msg,sender);
            }
        });

        holder.HappeningsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDescription(title,msg,sender);
            }
        });

    }

    private void showDescription(String title, String msg, String sender) {

        Intent intent = new Intent(context,DescriptionActivity.class);
        intent.putExtra("TIT",title);
        intent.putExtra("MSG",msg);
        intent.putExtra("SENDER",sender);
        intent.putExtra("cat","News");
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyHappeningsViewHolder extends RecyclerView.ViewHolder {

        public CardView HappeningsCardView;
        public TextView HappeningsTextView;
        public ImageView HappeningsImageView;

        public MyHappeningsViewHolder(View itemView) {
            super(itemView);
            HappeningsCardView = (CardView)itemView.findViewById(R.id.card_view_tab2);
            HappeningsTextView = (TextView)itemView.findViewById(R.id.tv_textView_card2);
            HappeningsImageView = (ImageView)itemView.findViewById(R.id.iv_imageview_card2);
        }
    }
}
