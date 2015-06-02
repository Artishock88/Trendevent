package de.artmedia.artyom.trendevent_screentest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Artyom on 22.05.2015.
 */
public class MyBlockAdapter extends RecyclerView.Adapter<MyBlockAdapter.ViewHolder> {

    private final Context context;

    private String mTITLE[], mCONTENT[], mNAME[], mUPCOUNT[], mBANNER[], mPIC[];
    private int mID[];
    private int id;

    private Bitmap bmp_banner;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context contxt;
        ImageButton block_banner;
        ImageView block_pic;
        TextView block_upcount, block_title, block_content, block_name, lade_text;

        public ViewHolder(View itemView, int viewType, Context c) {
            super(itemView);
            contxt = c;

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            block_pic = (ImageView) itemView.findViewById(R.id.block_pic);
            block_banner = (ImageButton) itemView.findViewById(R.id.banner);
            block_upcount = (TextView) itemView.findViewById(R.id.upcount);
            block_title = (TextView) itemView.findViewById(R.id.block_title);
            block_content = (TextView) itemView.findViewById(R.id.block_inhalt);
            block_name = (TextView) itemView.findViewById(R.id.block_referent);

        }

        @Override
        public void onClick(View v) {
            int usedPos = getPosition();

            //Korrekten TOP auswaehlen und oeffnen
            Intent i = new Intent(context, Top_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            id = mID[usedPos];
            bundle.putInt("item",id);
            i.putExtras(bundle);


            TextView loading = (TextView) v.getRootView().findViewById(R.id.lade_text);
            loading.setVisibility(View.VISIBLE);
            View dimmer = (View) v.getRootView().findViewById(R.id.dimmer);
            dimmer.setVisibility(View.VISIBLE);
            contxt.startActivity(i);
        }
    }

    public MyBlockAdapter(String[] title, String[] content, String[] name, String[] upcount, String[] banner, String[] pic, int[] ID, Context passedContext) {

        mTITLE = title;
        mCONTENT = content;
        mNAME = name;
        mUPCOUNT = upcount;
        mBANNER = banner;
        mPIC = pic;
        mID = ID;
        this.context = passedContext;

    }

    @Override
    public MyBlockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_now_item, parent, false);
        ViewHolder vhItem = new ViewHolder(v, viewType, context);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(MyBlockAdapter.ViewHolder holder, int position) {

        holder.block_upcount.setText(mUPCOUNT[position]);
        holder.block_title.setText(mTITLE[position]);
        holder.block_content.setText(mCONTENT[position]);
        holder.block_name.setText(mNAME[position]);
        Picasso.with(context).load(mBANNER[position]).into(holder.block_banner);
        Picasso.with(context).load(mPIC[position]).into(holder.block_pic);
    }

    @Override
    public int getItemCount() {
        return mTITLE.length;
    }


}
