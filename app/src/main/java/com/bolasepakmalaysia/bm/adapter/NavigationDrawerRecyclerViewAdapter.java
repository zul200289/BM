package com.bolasepakmalaysia.bm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolasepakmalaysia.bm.R;

/**
 * Created by zul on 06-Mar-15.
 */
public class NavigationDrawerRecyclerViewAdapter
    extends RecyclerView.Adapter<NavigationDrawerRecyclerViewAdapter.ViewHolder>
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String[] mNavTitles;
    private int[] mIcons;

    private String name;
    private int profile;
    private String email;

    public NavigationDrawerRecyclerViewAdapter(
            String[] navTitles, int[] icons, String name, int profile, String email ){

        mNavTitles = navTitles;
        mIcons = icons;
        this.name = name;
        this.profile = profile;
        this.email = email;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if ( viewType == TYPE_ITEM ){

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigationdrawer_itemrow, parent, false);

            ViewHolder vhItem = new ViewHolder(v, viewType);
            return vhItem;

        } else if ( viewType == TYPE_HEADER ) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigationdrawer_header, parent, false);

            ViewHolder vhHeader = new ViewHolder(v, viewType);
            return vhHeader;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder.holderid == 1) {

            holder.textview.setText(mNavTitles[position-1]);
            holder.imageView.setImageResource(mIcons[position-1]);

        } else {

        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        int holderid;

        TextView textview;
        ImageView imageView;
        ImageView ivProfile;
        TextView tvName;
        TextView tvEmail;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            // set appropriate view
            if (viewType == TYPE_ITEM) {
                textview = (TextView) itemView.findViewById(R.id.navigationdrawer_itemrow_rowtext);
                imageView = (ImageView) itemView.findViewById(R.id.navigationdrawer_itemrow_rowicon);
                holderid = 1;
            } else {
                tvName = (TextView) itemView.findViewById(R.id.navigationdrawer_header_username);
                tvEmail = (TextView) itemView.findViewById(R.id.navigationdrawer_header_email);
                ivProfile = (ImageView) itemView.findViewById(R.id.navigationdrawer_header_picture);
                holderid = 0;
            }
        }
    }

}
