package com.bolasepakmalaysia.bm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bolasepakmalaysia.bm.R;
import com.bolasepakmalaysia.bm.app.AppController;
import com.bolasepakmalaysia.bm.model.ApiCommentViewModel;

import java.util.List;

/**
 * Created by zul on 21-Jan-15.
 */
public class ListViewCommentAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<ApiCommentViewModel> comments;

    public ListViewCommentAdapter(Activity activity, List<ApiCommentViewModel> comments){
        this.activity = activity;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return this.comments.size();
    }

    @Override
    public Object getItem(int position) {
        return this.comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.comments.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_comment, null);
        }

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.listview_comment_thumbnail);
        TextView usernametv = (TextView) convertView.findViewById(R.id.listview_comment_username);
        TextView commenttv = (TextView) convertView.findViewById(R.id.listview_comment_comment);

        ApiCommentViewModel p = this.comments.get(position);

        // todo: change to internet image
        //thumbnail.setImageDrawable(convertView.getResources().getDrawable(R.drawable.postimage));
        thumbnail.setImageUrl(p.getRouserimagecleaned(), AppController.getInstance().getImageLoader());
        usernametv.setText(p.getUsername());
        commenttv.setText(p.getBody());


        return convertView;
    }
}
