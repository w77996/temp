package com.wtwd.standard.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class SportTabFragmentAdapter extends FragmentStatePagerAdapter {

    private Context context;
    String[] tab;
    private List<BaseFragment> list;

    public SportTabFragmentAdapter(FragmentManager fm, String[] tab, List<BaseFragment> list) {
        super(fm);
        this.list = list;
        this.tab = tab;
//        tab = context.getResources().getStringArray(R.array.sport_item);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];
    }
}
