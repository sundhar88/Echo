package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivara.aravi.echo.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Aravi on 27-08-2017.
 */

public class HumanityAdapter extends RecyclerView.Adapter<HumanityAdapter.MyHumanityViewHolder> {

    private String title,urlPhoto,msg,sender;
    private Context context;

    public HumanityAdapter(Context context) {

        this.context = context;

    }

    @Override
    public HumanityAdapter.MyHumanityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_tab3_items, parent, false);
        MyHumanityViewHolder mhvh = new MyHumanityViewHolder(v);
        return mhvh;

    }

    @Override
    public void onBindViewHolder(MyHumanityViewHolder holder, int position) {

        File file = new File("/sdcard/download", "Requesting.psk");
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

        holder.HumanityTextView.setText(title);

        File ImageFile = new File(Environment.getExternalStorageDirectory() + "/imageViewFolder/Requesting.jpeg");
        if (ImageFile.exists())
        {

            Bitmap bitmap = BitmapFactory.decodeFile(ImageFile.getAbsolutePath());
            holder.HumanityImageView.setImageBitmap(bitmap);

        }

        holder.HumanityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDescription(title,msg,sender);
            }
        });

        holder.HumanityCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDescription(title,msg,sender);
            }
        });

        holder.HumanityTextView.setOnClickListener(new View.OnClickListener() {
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
        intent.putExtra("cat","Requesting");
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class MyHumanityViewHolder extends RecyclerView.ViewHolder {

        public CardView HumanityCardView;
        public TextView HumanityTextView;
        public ImageView HumanityImageView;

        public MyHumanityViewHolder(View itemView) {
            super(itemView);
            HumanityCardView = (CardView)itemView.findViewById(R.id.card_view_tab3);
            HumanityTextView = (TextView)itemView.findViewById(R.id.tv_textView_card3);
            HumanityImageView = (ImageView)itemView.findViewById(R.id.iv_imageview_card3);
        }

    }

}
