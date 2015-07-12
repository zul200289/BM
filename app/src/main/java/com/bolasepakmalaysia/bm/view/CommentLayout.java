package com.bolasepakmalaysia.bm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bolasepakmalaysia.bm.R;
import com.bolasepakmalaysia.bm.app.AppController;
import com.bolasepakmalaysia.bm.model.ApiCommentViewModel;

/**
 * Created by zul on 04-Feb-15.
 */
public class CommentLayout extends RelativeLayout {

    private TextView tvUsername;
    private TextView tvBody;
    private NetworkImageView nivImage;

    public CommentLayout(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.listview_comment, this);

        loadViews();
    }

    private void loadViews(){
        this.tvUsername = (TextView) findViewById(R.id.listview_comment_username);
        this.tvBody = (TextView) findViewById(R.id.listview_comment_comment);
        this.nivImage = (NetworkImageView) findViewById(R.id.listview_comment_thumbnail);
    }

    public void setComment( ApiCommentViewModel comment) {
        this.tvUsername.setText(comment.getUsername());
        this.tvBody.setText(comment.getBody());
        this.nivImage.setImageUrl(comment.getRouserimagecleaned(), AppController.getInstance().getImageLoader());
    }

}
