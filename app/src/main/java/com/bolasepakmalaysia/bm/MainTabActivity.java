package com.bolasepakmalaysia.bm;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.bolasepakmalaysia.bm.adapter.MainTabViewPagerAdapter;
import com.bolasepakmalaysia.bm.adapter.NavigationDrawerRecyclerViewAdapter;
import com.bolasepakmalaysia.bm.widget.SlidingTabLayout;


public class MainTabActivity extends ActionBarActivity
       implements NewsFragment.OnFragmentInteractionListener  {

    android.support.v7.widget.Toolbar mToolbar;
    ViewPager pager;
    MainTabViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    // Tabs
    CharSequence Titles[];
    int NumbOfTabs = 0;

    // Navigation drawer
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mRecyclerViewAdapter;
    RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    String TITLES[] = {"Home","Events","Mail","Shop","Travel"};
    int ICONS[] = {R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
    String NAME = "Akash Bangad";
    String EMAIL = "akash.bangad@android4devs.com";
    int PROFILE = R.drawable.common_full_open_on_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        // set Tab Titles
        Titles = new String[]{
            this.getString(R.string.maintabactivity_tab1_title),
                    this.getString(R.string.maintabactivity_tab2_title),
                    this.getString(R.string.maintabactivity_tab3_title),
                    this.getString(R.string.maintabactivity_tab4_title)
        };
        NumbOfTabs = Titles.length;

        // set toolbar
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.maintabtoolbar);
        setSupportActionBar(mToolbar);

        // set adapter
        adapter = new MainTabViewPagerAdapter(getSupportFragmentManager(), Titles, NumbOfTabs);

        // set pager
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // sliding tab layout view
        this.tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);

        // navigation drawer
        mRecyclerView = (RecyclerView) findViewById(R.id.maintabactivity_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewAdapter = new NavigationDrawerRecyclerViewAdapter(TITLES,ICONS,NAME,PROFILE,EMAIL);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        mDrawer = (DrawerLayout) findViewById(R.id.maintabactivity_drawerlayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        };

        mDrawer.setDrawerListener(mActionBarDrawerToggle); // Drawer Listener set to the Drawer toggle
        mActionBarDrawerToggle.syncState();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewsFragmentNewsDetailSelected(int id) {

    }
}
