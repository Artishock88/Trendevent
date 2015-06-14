package de.artmedia.artyom.trendevent_screentest;

import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static int NUM_PAGES = 6;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int res;

    private SlidingTabLayout tabs;
    CharSequence blockTimes[]={"Archiv","Block 1","Block 2","Block 3","Block 4","Block 5"};

    private Toolbar toolbar;

    private TextView lade_text;
    private View dimmer;

    Archive archive;
    LiveNow liveNow;
    Pending pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lade_text = (TextView) this.findViewById(R.id.lade_text);
        dimmer = (View) this.findViewById(R.id.dimmer);
        lade_text.setVisibility(View.GONE);
        dimmer.setVisibility(View.GONE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        instantiatePager();
        instntiateSlidingTabs();


    }

    public void instantiatePager()
    {
        mPager = null;
        mPagerAdapter = null;

        //Viewpager instanziieren
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), blockTimes);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
        mPager.setOffscreenPageLimit(6);
    }

    public void instntiateSlidingTabs()
    {
        //Assigning the Sliding Tab Layout
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        //Custom Color
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabScrollColor);

            }
        });
        tabs.setViewPager(mPager);
    }

    @Override
    public void onBackPressed()
    {
        if(mPager.getCurrentItem() == 1)
        {
            super.onBackPressed();
        }else if(mPager.getCurrentItem() == 0)
        {
            mPager.setCurrentItem(1);
        }
        else
        {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm, CharSequence[] blockTimes)
        {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return blockTimes[position];
        }


        @Override
        public Fragment getItem(int position)
        {

            //Dynamische Erstellung des ViewPagers

            if (position == 0)
            {
                archive = null;
                archive = new Archive();
                return archive;
            }
            if (position == 1)
            {
                liveNow = null;
                Bundle blockLive = new Bundle();
                blockLive.putString("blockUrl", "block1.php");
                liveNow = new LiveNow();
                liveNow.setArguments(blockLive);
                return liveNow;
            }
            if (position > 1) {
                for (int i = 2; i <= NUM_PAGES; i++) {
                    pending = null;
                    String combineUrl;
                    combineUrl = "block" + String.valueOf(position) + ".php";
                    Log.d("COMBINED_URL", combineUrl);
                    Bundle block = new Bundle();
                    block.putString("blockUrl", combineUrl);
                    pending = new Pending();
                    pending.setArguments(block);
                    return pending;
                }
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return NUM_PAGES;
        }
    }

   /*public void onNotify(View view)
    {
        switch (view.getId())
        {
            case (R.id.button):
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle("Testnotification");
                mBuilder.setContentText("This is the Text of the testnotification");

                NotificationManager mNManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNManager.notify(0, mBuilder.build());
        }
    }

    public void onDecrease(View view)
    {
        switch (view.getId())
        {
            case (R.id.button2):

                if(NUM_PAGES>2) {
                    NUM_PAGES = NUM_PAGES - 1;
                    instantiatePager();
                }
        }
    }

    public void onReset(View view)
    {
        switch (view.getId())
        {
            case (R.id.button3):
                if(NUM_PAGES<6){
                NUM_PAGES = 6;
                instantiatePager();}
        }
    }*/
}
