package com.wtwd.standard.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wtwd.standard.R;

/**
 * Created by Administrator on 2018/3/20 0020.
 */

public class DialogUtil {


    /**
     * 选择左右手弹框
     *
     * @param mActivity   Activity对象
     * @param mDialog     Dialog对象
     * @param mLeftClick  选择左手点击事件监听
     * @param mRightClick 选择右手点击事件监听
     */
    public static void chooseHandsDialog(Activity mActivity, Dialog mDialog, View.OnClickListener mLeftClick, View.OnClickListener mRightClick) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_choose_hands, null, false);

        TextView text_left_hand = (TextView) view.findViewById(R.id.text_left_hand);
        TextView text_right_hand = (TextView) view.findViewById(R.id.text_right_hand);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        text_left_hand.setOnClickListener(mLeftClick);
        text_right_hand.setOnClickListener(mRightClick);

        mDialog.show();
        setDialoglayoutParams(mActivity, mDialog);
    }


    public static void commitDialog(Activity mActivity, final Dialog mDialog, String title, View.OnClickListener mRightCommitClick){
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_commit,null,false);

        TextView text_commit_title = (TextView)view.findViewById(R.id.text_commit_title);
        TextView text_refuse = (TextView)view.findViewById(R.id.text_refuse);
        TextView text_commit = (TextView)view.findViewById(R.id.text_commit);
        text_commit_title.setText(title);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        text_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        text_commit.setOnClickListener(mRightCommitClick);

        mDialog.show();

        setDialoglayoutParams(mActivity,mDialog);


    }


    public static void setDialoglayoutParams(Activity mActivity, Dialog selectedDialog) {
        Window dialogWindow = selectedDialog.getWindow();
        WindowManager m = mActivity.getWindowManager();

        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();

        //设置高度和宽度
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        p.width = (int) (d.getWidth());
        //设置位置
        p.gravity = Gravity.BOTTOM;
        p.y = 0; //设置Dialog与底部的margin值，与左右一致

        //设置Dialog本身透明度
//        p.alpha = 0.5f;
        dialogWindow.setAttributes(p);
    }

//    public static void setDialoglayoutParams(Activity mActivity, Dialog selectedDialog) {
//        Window dialogWindow = selectedDialog.getWindow();
//        WindowManager m = mActivity.getWindowManager();
//
//        Display d = m.getDefaultDisplay();
//        WindowManager.LayoutParams p = dialogWindow.getAttributes();
//
//        //设置高度和宽度
//        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        p.width = (int) (d.getWidth() * 0.9);
//        //设置位置
//        p.gravity = Gravity.BOTTOM;
//        p.y = (int) (d.getWidth() * 0.05); //设置Dialog与底部的margin值，与左右一致
//
//        //设置Dialog本身透明度
////        p.alpha = 0.5f;
//        dialogWindow.setAttributes(p);
//    }

}
