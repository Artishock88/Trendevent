package de.artmedia.artyom.trendevent_screentest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Artyom on 15.06.2015.
 */
public class MyDrawerAdapter extends RecyclerView.Adapter<MyDrawerAdapter.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];
    private String name;
    private int evlogo;
    private String claim;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        int Holderid;

        TextView item_text;
        ImageView item_icon;
        ImageView logo;
        TextView Name;
        TextView Claim;
        ImageView divider;


        public ViewHolder(View itemView,int ViewType) {
            super(itemView);

            if(ViewType == TYPE_ITEM)
            {
                item_text = (TextView) itemView.findViewById(R.id.row_text);
                item_icon = (ImageView) itemView.findViewById(R.id.row_icon);
                Holderid = 1;
            }else{
                Name = (TextView) itemView.findViewById(R.id.event_title);
                Claim = (TextView) itemView.findViewById(R.id.event_claim);
                logo = (ImageView) itemView.findViewById(R.id.event_logo);
                Holderid = 0;
            }

        }

    }

    MyDrawerAdapter(String Titles[],int Icons[],String Name,String Claim,int Logo)
    {
        mNavTitles = Titles;
        mIcons = Icons;
        name = Name;
        claim = Claim;
        evlogo = Logo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType);
            return vhItem;
        }else if(viewType == TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);
            return vhHeader;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.Holderid == 1)
        {
            holder.item_text.setText(mNavTitles[position-1]);
            holder.item_icon.setImageResource(mIcons[position-1]);
        }else{
            holder.logo.setImageResource(evlogo);
            holder.Name.setText(name);
            holder.Claim.setText(claim);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }

    @Override
    public int getItemViewType(int position){
        if(isPositionHeader(position)){
            return TYPE_HEADER;}

            return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
