package com.wtwd.standard.fragment;

import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class SportOutDoorFragment extends BaseFragment {
    private static SportOutDoorFragment mInstance;


    public static SportOutDoorFragment getSportOutDoorFragment() {
        if (null == mInstance) {
            mInstance = new SportOutDoorFragment();
        }
        return mInstance;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_sport_out_door;
    }

    @Override
    public void initFragmentView(View mView) {

    }
}
