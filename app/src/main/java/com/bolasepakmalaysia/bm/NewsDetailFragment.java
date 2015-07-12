package com.bolasepakmalaysia.bm;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.bolasepakmalaysia.bm.adapter.ListViewCommentAdapter;
import com.bolasepakmalaysia.bm.app.AppController;
import com.bolasepakmalaysia.bm.model.ApiCommentViewModel;
import com.bolasepakmalaysia.bm.model.ApiPostViewModel;
import com.bolasepakmalaysia.bm.util.GsonRequest;
import com.bolasepakmalaysia.bm.view.CommentLayout;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsDetailFragment.OnNewsDetailFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSTID = "postid";

    // TODO: Rename and change types of parameters
    private int mPostId;

    private OnNewsDetailFragmentInteractionListener mListener;

    // Views
    private TextView title;
    private TextView body;
    private NetworkImageView img;
    private ProgressBar pb;
    private Button buttonPostComment;
    private EditText etPostComment;
    private ScrollView scroll;

    //private ListView lvComments;
    //private ListViewCommentAdapter lvCommentsAdapter;
    private List<ApiCommentViewModel> mApiCommentViewModels;

    private LinearLayout llComments;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param postid Post Id.
     * @return A new instance of fragment NewsDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsDetailFragment newInstance(int postid) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSTID, postid);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = getArguments().getInt(ARG_POSTID);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // get post
        this.title = (TextView) view.findViewById(R.id.newsdetailfragment_title);
        this.body = (TextView) view.findViewById(R.id.newsdetailfragment_body);
        this.img = (NetworkImageView) view.findViewById(R.id.newsdetailfragment_image);
        this.pb = (ProgressBar) view.findViewById(R.id.newsdetailfragment_progressBar);
        this.buttonPostComment = (Button) view.findViewById(R.id.newsdetailfragment_buttonpostcomment);
        this.etPostComment = (EditText) view.findViewById(R.id.newsdetailfragment_textpostcomment);

        this.mApiCommentViewModels = new ArrayList<ApiCommentViewModel>();
        this.mApiCommentViewModels.add(new ApiCommentViewModel(11, "asdasd", "asdasd", "asdasd", ""));
        this.mApiCommentViewModels.add(new ApiCommentViewModel(12, "2asdasd", "2asdasd", "2asdasd", ""));

        //this.lvCommentsAdapter = new ListViewCommentAdapter(getActivity(), mApiCommentViewModels);
        //this.lvComments = (ListView) view.findViewById(R.id.newsdetailfragment_comments);
        //this.lvComments.setAdapter(this.lvCommentsAdapter);

        this.llComments = (LinearLayout) view.findViewById(R.id.newsdetailfragment_llcomments);
        this.scroll = (ScrollView) view.findViewById(R.id.newsdetailfragment_sv);

        //this.lvComments.setVisibility();
        //return view;
       // new GetPostAsyncTask(title,body,img).execute(Integer.toString(mPostId));

        // set listeners
        this.buttonPostComment.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String commentbody = etPostComment.getText().toString();

                Toast.makeText(getActivity().getApplication().getApplicationContext(), "Tekan", Toast.LENGTH_SHORT);
                // POST
                final String postcommenturl = "http://www.bolasepakmalaysia.com/api/comments/";

                JSONObject jj = new JSONObject();
                try {
                    jj.put("body", commentbody);
                    jj.put("postid", mPostId);
                } catch(JSONException e) {

                }

                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, postcommenturl, jj,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {

                                //loadPostAndComments();

                                try {

                                    String htmlbody = response.getString("body");

                                    ApiCommentViewModel cmm =
                                            new ApiCommentViewModel(response.getInt("id"),
                                                    response.getString("username"),
                                                    htmlbody, response.getString("userid"),
                                                    response.getString("rouserimage"));

                                    CommentLayout cll = new CommentLayout(getActivity());
                                    cll.setComment(cmm);
                                    llComments.addView(cll);

                                } catch (JSONException e) {

                                }

                                // clean and scroll to bottom
                                etPostComment.setText("");
                                scroll.post(new Runnable(){
                                    @Override
                                    public void run() {
                                        scroll.fullScroll(View.FOCUS_DOWN);
                                    }
                                });


                            }
                        },

                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                String mm = error.getMessage();
                                String vv = "";



                            }
                        })
                {
                    /*
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("body", commentbody);
                        params.put("postid", Integer.toString(mPostId));
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("Content-Type","application/json; charset=utf-8");

                        return params;
                    }
                    */
                };

                AppController.getInstance().addToRequestQueue(jor);
            }
        });


        loadPostAndComments();

        return view;

    }

    private void loadPostAndComments(){


        String url = "http://www.bolasepakmalaysia.com/api/posts/" + Integer.toString(mPostId);
        GsonRequest gNewsReq = new GsonRequest(Request.Method.GET, url, ApiPostViewModel.class, null,
                new Response.Listener<ApiPostViewModel>(){

                    @Override
                    public void onResponse(ApiPostViewModel response) {
                        title.setText(response.getTitle());

                        //body.setText(Html.fromHtml(response.getArticle(), new Html.ImageGetter(), null));
                        body.setText(Html.fromHtml(response.getArticle()));
                        //body.setText(response.getArticle());
                        //img.setImageDrawable(getView().getResources().getDrawable(R.drawable.postimage));
                        img.setImageUrl("http://www.bolasepakmalaysia.com" + response.getImageUrl(), AppController.getInstance().getImageLoader());

                        mApiCommentViewModels = response.getComments();
                        //lvCommentsAdapter = new ListViewCommentAdapter(getActivity(), mApiCommentViewModels);
                        //lvComments.setAdapter(lvCommentsAdapter);

                        for (int i =0; i < mApiCommentViewModels.size(); i++){
                            CommentLayout cll = new CommentLayout(getActivity());
                            cll.setComment(mApiCommentViewModels.get(i));
                            llComments.addView(cll);
                        }

                        pb.setVisibility(View.GONE);

                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        AppController.getInstance().addToRequestQueue(gNewsReq);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnNewsDetailFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNewsDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /*
    private class GetPostAsyncTask extends AsyncTask<String, Integer, ApiPostViewModel> {

        private TextView mTvTitle;
        private TextView mTvArticle;
        private ImageView mIvImage;

        public GetPostAsyncTask(TextView mTvTitle, TextView mTvArticle,
                                ImageView mIvImage){

            this.mTvTitle = mTvTitle;
            this.mTvArticle = mTvArticle;
            this.mIvImage = mIvImage;

        }

        @Override
        protected ApiPostViewModel doInBackground(String... params) {

            int postid = Integer.parseInt(params[0]);
            ApiPostViewModel post = new ApiPostViewModel();

            try {
                URL url = new URL("http://www.bolasepakmalaysia.com/api/posts/" + postid);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                if (con.getResponseCode() != 200) {
                    throw new RuntimeException("HTTP error code : " + con.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                Gson gson = new Gson();
                post = gson.fromJson(br, ApiPostViewModel.class);
                con.disconnect();

            } catch (Exception e) {
                //vm.issuccess = false;
            }

            return post;
        }

        @Override
        protected void onPostExecute(ApiPostViewModel vm) {


            mTvTitle.setText(vm.getTitle());
            mTvArticle.setText(Html.fromHtml(vm.getArticle()));
            mIvImage.setImageDrawable(getView().getResources().getDrawable(R.drawable.postimage));
            //mIvImage.setImageDrawable(getView().getResources().getDrawable(R.drawable.postimage));

        }
    }
    */
}
