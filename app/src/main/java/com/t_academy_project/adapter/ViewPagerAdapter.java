package com.t_academy_project.adapter;

import com.t_academy_project.F_menu1;
import com.t_academy_project.F_menu2;
import com.t_academy_project.F_menu3;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs; //tab의 갯수

        public ViewPagerAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                F_menu1 tab1 = new F_menu1();
                return tab1;
            case 1:
                F_menu2 tab2 = new F_menu2();
                return tab2;
            case 2:
                F_menu3 tab3 = new F_menu3();
                return tab3;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
