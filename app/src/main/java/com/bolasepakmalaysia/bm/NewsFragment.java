package com.bolasepakmalaysia.bm;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bolasepakmalaysia.bm.adapter.ListViewPostAdapter;
import com.bolasepakmalaysia.bm.app.AppController;
import com.bolasepakmalaysia.bm.model.ApiPostViewModel;
import com.bolasepakmalaysia.bm.util.GsonRequest;
import com.bolasepakmalaysia.bm.view.CommentLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link com.bolasepakmalaysia.bm.NewsFragment.OnFragmentInteractionListener}
 * interface.
 */
public class NewsFragment extends android.support.v4.app.Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<ApiPostViewModel> mPosts;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    //private ListAdapter mAdapter;
    private ListViewPostAdapter mAdapter;

    private ProgressBar mProgressBar;

    // TODO: Rename and change types of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        //mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //        android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);

        this.mPosts = new ArrayList<ApiPostViewModel>();
        mAdapter = new ListViewPostAdapter(getActivity(), mPosts);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // get data

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mProgressBar = (ProgressBar) view.findViewById(R.id.newsfragment_progressbar);
        //new GetPostsAsyncTask(progressBar).execute("");

        // only do a request if there is no news
        if ( mPosts.size() == 0 ){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        // get posts
        String url = "http://www.bolasepakmalaysia.com/api/posts";

        GsonRequest<ApiPostViewModel[]> gNewsReq = new GsonRequest(Request.Method.GET, url, ApiPostViewModel[].class, null,
                new Response.Listener<ApiPostViewModel[]>(){
                    @Override
                    public void onResponse(ApiPostViewModel[] response) {

                        for (int i = 0; i < response.length; i++) {
                            mPosts.add(response[i]);
                        }

                        mAdapter = new ListViewPostAdapter(getActivity(), mPosts);
                        mListView.setAdapter(mAdapter);
                        mProgressBar.setVisibility(View.INVISIBLE);

                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        // only do a request if there is no news
        if ( mPosts.size() == 0 ){
            AppController.getInstance().addToRequestQueue(gNewsReq);
        }


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {

            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            int postid = mPosts.get(position).getId();
            mListener.onNewsFragmentNewsDetailSelected(postid);

        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onNewsFragmentNewsDetailSelected(int id);
    }

    private class GetPostsAsyncTask extends AsyncTask<String, Integer, ApiPostViewModel[]> {

        //private ProgressDialog progressDialog;
        private ProgressBar progressBar;

        public GetPostsAsyncTask(ProgressBar progressBar){
            //progressDialog = new ProgressDialog(getActivity());
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute(){

           // this.progressDialog.setMessage(getString(R.string.news_fragment_getpostsasynctask_gettingnews));
           // this.progressDialog.show();

            // show progress bar
            //getActivity().findViewById(R.id.newsfragment_progressbar).setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);



        }

        @Override
        protected void onPostExecute(ApiPostViewModel[] vm){

            // change mAdapter to show content
            //mAdapter = new ArrayAdapter<String>(getActivity(),
            //        android.R.layout.simple_list_item_1, android.R.id.text1, vm);

            for (int i = 0; i < vm.length; i++){
                mPosts.add(vm[i]);
            }

            mAdapter = new ListViewPostAdapter(getActivity(), mPosts);
            mListView.setAdapter(mAdapter);

            //if (this.progressDialog.isShowing()) {
            //    this.progressDialog.hide();
            //}

            // hide progress bar
            //getActivity().findViewById(R.id.newsfragment_progressbar).setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);


            return;

        }

        @Override
        protected ApiPostViewModel[] doInBackground(String... params) {

            // TODO: might get error
            ApiPostViewModel[] posts = new ApiPostViewModel[10];

            try {
                URL url = new URL("http://www.bolasepakmalaysia.com/api/posts");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                if (con.getResponseCode() != 200) {
                    throw new RuntimeException("HTTP error code : " + con.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                Gson gson = new Gson();
                posts = gson.fromJson(br, ApiPostViewModel[].class);
                con.disconnect();

            } catch (Exception e) {
                //vm.issuccess = false;
            }

            return posts;
        }
    }

}
