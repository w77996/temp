package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.adapter.MessageCenterAdapter;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

import java.util.ArrayList;
import java.util.List;

public class MessageCenterActivity extends BaseActivity {
    private RecyclerView rv_msg_center;

    private MessageCenterAdapter mMessageCenterAdapter;
    private List<String> mMsgs = new ArrayList<>();
    LinearLayoutManager ml;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);
        initToolBar();
        initRecyclerView();
        initData();
    }

    private void initToolBar() {
        text_tool_bar_title.setText("我的信息");
        img_tool_bar_right.setImageResource(R.mipmap.msg_center_clear);
        img_tool_bar_right.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        rv_msg_center = (RecyclerView) findViewById(R.id.rv_msg_center);

        ml = new LinearLayoutManager(this);
        ml.setOrientation(LinearLayoutManager.VERTICAL);
        rv_msg_center.setLayoutManager(ml);
        mMessageCenterAdapter = new MessageCenterAdapter(R.layout.item_msg_center, mMsgs);
        rv_msg_center.setAdapter(mMessageCenterAdapter);
    }

    /**
     * 测试数据
     */
    private void initData() {
        mMsgs.clear();
        for (int i = 0; i < 10; i++) {
            mMsgs.add("msg " + i);
        }
        mMessageCenterAdapter.notifyDataSetChanged();
    }

    @Override
    public View getSnackView() {
        return rv_msg_center;
    }



}
