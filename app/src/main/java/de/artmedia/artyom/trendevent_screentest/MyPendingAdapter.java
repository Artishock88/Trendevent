package de.artmedia.artyom.trendevent_screentest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Artyom on 22.05.2015.
 */
public class MyPendingAdapter extends RecyclerView.Adapter<MyPendingAdapter.ViewHolder> {

    private final Context context;




    private String mTITLE[], mCONTENT[], mNAME[], mBANNER[], mPIC[];




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context contxt;

        ImageView block_pic;
        ImageButton block_banner;
        TextView block_title, block_content, block_name, lade_text;

        public ViewHolder(View itemView, int viewType, Context c) {
            super(itemView);
            contxt = c;

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            block_pic = (ImageView) itemView.findViewById(R.id.block_pic);
            block_banner = (ImageButton) itemView.findViewById(R.id.banner);
            block_title = (TextView) itemView.findViewById(R.id.block_title);
            block_content = (TextView) itemView.findViewById(R.id.block_inhalt);
            block_name = (TextView) itemView.findViewById(R.id.block_referent);

        }

        @Override
        public void onClick(View v) {
            int usedPos = getPosition();

            //HARDCODED, aendern mit entsprechender Logik
            Intent i = new Intent(context, Top_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            /*Bundle bundle = new Bundle();
            bundle.putInt("item",xxxx);
            i.putExtras(bundle);
             */

            contxt.startActivity(i);
        }
    }

    public MyPendingAdapter(String[] title, String[] content, String[] name, String[] banner, String[] pic, Context passedContext) {

        mTITLE = title;
        mCONTENT = content;
        mNAME = name;
        mBANNER = banner;
        mPIC = pic;
        this.context = passedContext;

        Log.d("name_adapter",mNAME[0]);
    }

    @Override
    public MyPendingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_item, parent, false);
        ViewHolder vhItem = new ViewHolder(v, viewType, context);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(MyPendingAdapter.ViewHolder holder, int position) {

        holder.block_title.setText(mTITLE[position]);
        holder.block_content.setText(mCONTENT[position]);
        holder.block_name.setText(mNAME[position]);

        //Download Banner
        try {
            URL bannerUrl = new URL(mBANNER[position]);
            Bitmap bmp_banner = BitmapFactory.decodeStream(bannerUrl.openConnection().getInputStream());
            holder.block_banner.setImageBitmap(bmp_banner);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Download Pic
        try {
            URL picUrl = new URL(mPIC[position]);
            Bitmap bmp_pic = BitmapFactory.decodeStream(picUrl.openConnection().getInputStream());
            holder.block_pic.setImageBitmap(bmp_pic);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mTITLE.length;
    }


}
