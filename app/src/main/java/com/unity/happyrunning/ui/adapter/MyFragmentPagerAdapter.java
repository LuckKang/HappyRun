package com.unity.happyrunning.ui.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.unity.happyrunning.ui.activity.MainActivity;
import com.unity.happyrunning.ui.fragment.Home_Fragment;
import com.unity.happyrunning.ui.fragment.Run_Fragment;
import com.unity.happyrunning.ui.fragment.Setting_Fragment;
import com.unity.happyrunning.ui.fragment.Walk_Fragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT=4;
    Home_Fragment homeFragment=null;
    Run_Fragment runFragment=null;
    Setting_Fragment settingFragment=null;
    Walk_Fragment walkFragment=null;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        homeFragment=new Home_Fragment();
        runFragment=new Run_Fragment();
        settingFragment=new Setting_Fragment();
        walkFragment=new Walk_Fragment();
    }


    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem( ViewGroup container, int position,Object object) {
        super.destroyItem(container, position, object);
    }

    @Override

    public Fragment getItem(int i) {
       Fragment fragment=null;
       switch (i)
       {
           case MainActivity.PAGE_ONE:
               fragment=homeFragment;
               break;
           case MainActivity.PAGE_TWO:
               fragment=runFragment;
               break;
           case MainActivity.PAGE_THREE:
               fragment=walkFragment;
               break;
           case MainActivity.PAGE_FOUR:
               fragment=settingFragment;
       }
       return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
