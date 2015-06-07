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

    private static int NUM_PAGES = 14;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int res;

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

    }

    public void instantiatePager()
    {
        mPager = null;
        mPagerAdapter = null;

        //Viewpager instanziieren
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
        mPager.setOffscreenPageLimit(6);
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
        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }


        @Override
        public Fragment getItem(int position)
        {

            /*switch (position)
            {
                case (0):
                    return new Archive();
                case (1):
                    Bundle block1 = new Bundle();
                    block1.putString("blockUrl", "block1.php");
                    LiveNow liveNow = new LiveNow();
                    liveNow.setArguments(block1);
                    return liveNow;
                case (2):
                    Bundle block2 = new Bundle();
                    block2.putString("blockUrl", "block2.php");
                    Pending pending1 = new Pending();
                    pending1.setArguments(block2);
                    return pending1;
                case (3):
                    Bundle block3 = new Bundle();
                    block3.putString("blockUrl", "block3.php");
                    Pending pending2 = new Pending();
                    pending2.setArguments(block3);
                    return pending2;
                case (4):
                    Bundle block4 = new Bundle();
                    block4.putString("blockUrl", "block4.php");
                    Pending pending3 = new Pending();
                    pending3.setArguments(block4);
                    return pending3;
                case (5):
                    Bundle block5 = new Bundle();
                    block5.putString("blockUrl", "block5.php");
                    Pending pending4 = new Pending();
                    pending4.setArguments(block5);
                    return pending4;
                case (6):
                    Bundle block6 = new Bundle();
                    block6.putString("blockUrl", "block6.php");
                    Pending pending5 = new Pending();
                    pending5.setArguments(block6);
                    return pending5;
                case (7):
                    Bundle block7 = new Bundle();
                    block7.putString("blockUrl", "block7.php");
                    Pending pending6 = new Pending();
                    pending6.setArguments(block7);
                    return pending6;
                case (8):
                    Bundle block8 = new Bundle();
                    block8.putString("blockUrl", "block8.php");
                    Pending pending7 = new Pending();
                    pending7.setArguments(block8);
                    return pending7;
                case (9):
                    Bundle block9 = new Bundle();
                    block9.putString("blockUrl", "block9.php");
                    Pending pending8 = new Pending();
                    pending8.setArguments(block9);
                    return pending8;
                case (10):
                    Bundle block10 = new Bundle();
                    block10.putString("blockUrl", "block10.php");
                    Pending pending9 = new Pending();
                    pending9.setArguments(block10);
                    return pending9;
                case (11):
                    Bundle block11 = new Bundle();
                    block11.putString("blockUrl", "block11.php");
                    Pending pending10 = new Pending();
                    pending10.setArguments(block11);
                    return pending10;
                case (12):
                    Bundle block12 = new Bundle();
                    block12.putString("blockUrl", "block12.php");
                    Pending pending11 = new Pending();
                    pending11.setArguments(block12);
                    return pending11;
                case (13):
                    Bundle block13 = new Bundle();
                    block13.putString("blockUrl", "block13.php");
                    Pending pending12 = new Pending();
                    pending12.setArguments(block13);
                    return pending12;
            }*/
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

    public void onNotify(View view)
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
                NUM_PAGES = 14;
                instantiatePager();
        }
    }
}
