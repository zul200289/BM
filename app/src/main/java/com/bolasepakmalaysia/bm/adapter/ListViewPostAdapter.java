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
import com.bolasepakmalaysia.bm.model.ApiPostViewModel;

import java.util.List;

/**
 * Created by zul on 21-Jan-15.
 */
public class ListViewPostAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<ApiPostViewModel> posts;

    public ListViewPostAdapter(Activity activity, List<ApiPostViewModel> posts){
        this.activity = activity;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_post, null);
        }

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.listview_post_thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.listview_post_title);
        TextView summary = (TextView) convertView.findViewById(R.id.listview_post_summary);

        ApiPostViewModel p = this.posts.get(position);

        // todo: change to internet image
        //thumbnail.setImageDrawable(convertView.getResources().getDrawable(R.drawable.postimage));
        thumbnail.setImageUrl(p.getImageUrlCleaned(), AppController.getInstance().getImageLoader());
        title.setText(p.getTitle());
        summary.setText(p.getSummary());

        return convertView;
    }


}
