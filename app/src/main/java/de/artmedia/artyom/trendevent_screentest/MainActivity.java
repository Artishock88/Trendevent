package de.artmedia.artyom.trendevent_screentest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;


public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 6;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
        mPager.setOffscreenPageLimit(4);
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
            switch (position)
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
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return NUM_PAGES;
        }
    }
}
