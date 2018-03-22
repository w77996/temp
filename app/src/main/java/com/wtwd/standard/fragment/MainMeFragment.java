package com.wtwd.standard.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wtwd.standard.R;
import com.wtwd.standard.activity.FeedBackActivity;
import com.wtwd.standard.activity.MessageCenterActivity;
import com.wtwd.standard.activity.MyDeviceActivity;
import com.wtwd.standard.activity.UserInfoActivity;
import com.wtwd.standard.base.BaseFragment;
import com.wtwd.standard.utils.DialogUtil;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class MainMeFragment extends BaseFragment implements View.OnClickListener {
    private static MainMeFragment mInstance;
    private LinearLayout lin_my_device;
    private LinearLayout lin_feed_back;
    private RelativeLayout rela_user_info;
    private LinearLayout lin_msg_center;




    public static MainMeFragment getMainMeFragment() {
        if (null == mInstance) {
            mInstance = new MainMeFragment();
        }
        return mInstance;
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_main_me;
    }

    @Override
    public void initFragmentView(View mView) {
        initToolBar();
        lin_my_device = (LinearLayout) mView.findViewById(R.id.lin_my_device);
        lin_feed_back = (LinearLayout) mView.findViewById(R.id.lin_feed_back);
        rela_user_info = (RelativeLayout) mView.findViewById(R.id.rela_user_info);
        lin_msg_center = (LinearLayout) mView.findViewById(R.id.lin_msg_center);


        addListener();
    }

    private void initToolBar() {
        text_tool_bar_title.setText("个人中心");
        img_tool_bar_left.setVisibility(View.GONE);
        img_tool_bar_right.setVisibility(View.VISIBLE);
        img_tool_bar_right.setImageResource(R.mipmap.main_me_setting);

    }

    private void addListener() {
        lin_my_device.setOnClickListener(this);
        lin_feed_back.setOnClickListener(this);
        rela_user_info.setOnClickListener(this);
        lin_msg_center.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        int mViewId = v.getId();
        if (R.id.lin_my_device == mViewId) {
            readyGo(MyDeviceActivity.class);
        } else if (R.id.lin_feed_back == mViewId) {
            readyGo(FeedBackActivity.class);
        } else if (R.id.rela_user_info == mViewId) {
            readyGo(UserInfoActivity.class);
        } else if (R.id.lin_msg_center == mViewId) {
            readyGo(MessageCenterActivity.class);
        }
    }
}
