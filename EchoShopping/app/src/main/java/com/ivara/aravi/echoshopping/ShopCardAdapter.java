package com.ivara.aravi.echoshopping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aravi on 11-09-2017.
 */

public class ShopCardAdapter extends RecyclerView.Adapter<ShopCardAdapter.MyViewHolder> {

    private String SHOP_NAME, PHONE_NO;
    private Context context;

    public ShopCardAdapter(String SHOP_NAME, Context context, String PHONE_NO) {
        this.SHOP_NAME = SHOP_NAME;
        this.context = context;
        this.PHONE_NO = PHONE_NO;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_card_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopCardAdapter.MyViewHolder holder, int position) {

        holder.SHOP_NAMEE.setText(SHOP_NAME);
        holder.SHOP_NAMEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intentHopper();

            }
        });

        holder.TAPPED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentHopper();
            }
        });

        holder.SHOP_IMAGE_DUMMY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentHopper();
            }
        });

        holder.SHOP_CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentHopper();
            }
        });

    }

    private void intentHopper() {

        Intent intent = new Intent(context,ShopDataFetcher.class);
        intent.putExtra("PHONE",PHONE_NO);
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView SHOP_NAMEE, TAPPED;
        private ImageView SHOP_IMAGE_DUMMY;
        private CardView SHOP_CARD;

        public MyViewHolder(View itemView) {
            super(itemView);
            SHOP_NAMEE = (TextView)itemView.findViewById(R.id.Shop_availabe_name);
            TAPPED = (TextView)itemView.findViewById(R.id.tap_card_tv);
            SHOP_IMAGE_DUMMY = (ImageView)itemView.findViewById(R.id.Card_view_image);
            SHOP_CARD = (CardView)itemView.findViewById(R.id.shop_availabe_card);
        }
    }
}
