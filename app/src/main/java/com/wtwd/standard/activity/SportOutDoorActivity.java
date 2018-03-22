package com.wtwd.standard.activity;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.widget.circleProgress.CirclePressProgress;
import com.wtwd.standard.widget.circleProgress.ICircleCloseInter;

import java.util.Timer;
import java.util.TimerTask;

public class SportOutDoorActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SportOutDoor";
    //    private Dialog mCountDownTimerDialog;
    private MyTimerCount mTimerCount;
    //    private RelativeLayout lin_go;
    private TextView text_go;
    private Chronometer chron_count_up;
    private CirclePressProgress circle_press_progress;


    //    private ProgressBar progress_sport;
    TextView text_count_down_timer;
    private LinearLayout lin_close;
    private LinearLayout lin_go_on;

    //    private boolean mResetCountUp = true;
    int miss = 0;


//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (1 == msg.what) {
//                progress_sport.setProgress(mProgress);
//                if (100 == mProgress) {
//                    chron_count_up.stop();
//                    visibleCanClickView(INVISIBLE_LIN_GO);
//                }
//            }
//        }
//    };

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_sport_out_door;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.PURE_PICTURE_TITLE);
        initToolBar();
    }

    private void initToolBar() {
        text_tool_bar_title.setText("户外运动");
        img_tool_bar_right.setImageResource(R.mipmap.main_me_setting);
//        img_tool_bar_left.setImageResource(R.mipmap.main_sport_history_record);
//        img_tool_bar_left.setVisibility(View.VISIBLE);
        img_tool_bar_right.setVisibility(View.VISIBLE);
        tool_bar.setNavigationIcon(R.mipmap.main_sport_history_record);
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        initView();
    }

    private void initView() {
//        lin_go = (RelativeLayout) findViewById(R.id.lin_go);
        text_count_down_timer = (TextView) findViewById(R.id.text_count_down_timer);
        text_go = (TextView) findViewById(R.id.text_go);
        circle_press_progress = (CirclePressProgress) findViewById(R.id.circle_press_progress);
//        progress_sport = (ProgressBar) findViewById(R.id.progress_sport);
        chron_count_up = (Chronometer) findViewById(R.id.chron_count_up);
        lin_close = (LinearLayout) findViewById(R.id.lin_close);
        lin_go_on = (LinearLayout) findViewById(R.id.lin_go_on);


        chron_count_up.setText(formatMiss(0));
        mTimerCount = new MyTimerCount(5000, 1000, text_count_down_timer);

        addListener();
    }

    // 将秒转化成小时分钟秒
    private String formatMiss(int miss) {
        String hh = miss / 3600 > 9 ? miss / 3600 + "" : "0" + miss / 3600;
        String mm = (miss % 3600) / 60 > 9 ? (miss % 3600) / 60 + "" : "0" + (miss % 3600) / 60;
        String ss = (miss % 3600) % 60 > 9 ? (miss % 3600) % 60 + "" : "0" + (miss % 3600) % 60;
        return hh + ":" + mm + ":" + ss;
    }


    private void addListener() {
//        lin_go.setOnClickListener(this);
        lin_go_on.setOnClickListener(this);
        lin_close.setOnClickListener(this);
        circle_press_progress.setOnClickListener(this);
        chron_count_up.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer ch) {
                miss++;
                ch.setText(formatMiss(miss));
            }
        });

        circle_press_progress.setCircleCloseListener(new ICircleCloseInter() {
            @Override
            public void onCircleCloseListener() {
                circle_press_progress.setVisibility(View.GONE);
                translationXOffset(lin_go_on, 0, lin_go_on.getWidth());
                translationXOffset(lin_close, 0, -(lin_close.getWidth()));
                chron_count_up.stop();
            }
        });
    }

    private void translationXOffset(LinearLayout mView, float... offSet) {

        ObjectAnimator.ofFloat(mView, View.TRANSLATION_X, offSet).setDuration(800).start();


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.lin_go == id) {
//            if (mResetCountUp) {
//                mTimerCount.start();
//                mResetCountUp = false;
//            }

        } else if (R.id.lin_go_on == id) {
//            mProgress = 0;
//            progress_sport.setProgress(0);
            resetView();
//            visibleCanClickView(VISIBLE_LIN_GO);
            chron_count_up.start();
            circle_press_progress.setInnerCenterString("长按暂停");

        } else if (R.id.lin_close == id) {
            resetView();
//            visibleCanClickView(VISIBLE_LIN_GO);
            chron_count_up.stop();
            miss = 0;
            chron_count_up.setText(formatMiss(miss));
            circle_press_progress.setOnSingleClick(true);
            circle_press_progress.setInnerCenterString("GO");
        } else if (R.id.circle_press_progress == id) {
            mTimerCount.start();
            circle_press_progress.setEnabled(false);
            circle_press_progress.setOnSingleClick(false);
            circle_press_progress.setInnerCenterString("长按暂停");
        }
    }

    private void resetView() {
        translationXOffset(lin_go_on, (lin_go_on.getWidth()), 0);
        translationXOffset(lin_close, -(lin_close.getWidth()), 0);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circle_press_progress.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 800);

    }


    @Override

    public View getSnackView() {
        return text_tool_bar_title;
    }

    private static final int VISIBLE_LIN_GO = 0x01;
    private static final int INVISIBLE_LIN_GO = 0x02;

    private void visibleCanClickView(int type) {
        if (VISIBLE_LIN_GO == type) {
            circle_press_progress.setVisibility(View.VISIBLE);
        } else if (INVISIBLE_LIN_GO == type) {
            circle_press_progress.setVisibility(View.GONE);
        }


    }


//    private int mProgress = 0;

    private class MyTimerCount extends CountDownTimer {

        private TextView mTextView;


        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimerCount(long millisInFuture, long countDownInterval, TextView mTextView) {
            super(millisInFuture, countDownInterval);
            this.mTextView = mTextView;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            final long currentCount = millisUntilFinished / 1000L;
            Log.e("TAG", "current count : " + currentCount);
            if (1 == currentCount) {
                circle_press_progress.setEnabled(true);
                mTextView.setText("GO");
                mHandle.sendEmptyMessageDelayed(1, 1000);
            } else {
                mTextView.setText((currentCount - 1) + "");
            }

            AnimationSet mAnimationSet = new AnimationSet(true);

            final AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
            //设置动画持续时间
            alphaAnimation.setDuration(1000);
            mAnimationSet.addAnimation(alphaAnimation);
//            mTextView.startAnimation(alphaAnimation);

            // 设置缩放渐变动画
            final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 15f, 1.0f, 15f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(1000);
            mAnimationSet.addAnimation(scaleAnimation);
            mTextView.startAnimation(mAnimationSet);
        }

        @Override
        public void onFinish() {
            mTextView.setText("");
        }

        @SuppressLint("HandlerLeak")
        private Handler mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    mTextView.setText("");
                    chron_count_up.start();
                    text_go.setText("长按暂停");
                }
            }
        };
    }


}
