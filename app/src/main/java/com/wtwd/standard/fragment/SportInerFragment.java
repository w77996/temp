package com.wtwd.standard.fragment;

import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class SportInerFragment extends BaseFragment {
    private static SportInerFragment mInstance;

    public static SportInerFragment getSportInerFragment() {
        if (null == mInstance) {
            mInstance = new SportInerFragment();
        }
        return mInstance;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_sport_iner;
    }

    @Override
    public void initFragmentView(View mView) {

    }
}
