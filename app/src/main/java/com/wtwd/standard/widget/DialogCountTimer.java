package com.wtwd.standard.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class DialogCountTimer extends Dialog {
    public DialogCountTimer(@NonNull Context context) {
        super(context);
    }

    public DialogCountTimer(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogCountTimer(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }






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
            long currentCount = millisUntilFinished / 1000L;
            if (1 == currentCount) {
                mTextView.setText("GO");
            } else {
                mTextView.setText((int) (currentCount-1));
            }

            final AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            //设置动画持续时间
            alphaAnimation.setDuration(1000);
            mTextView.startAnimation(alphaAnimation);

            // 设置缩放渐变动画
            final ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 5f, 0.5f, 5f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(1000);
            mTextView.startAnimation(scaleAnimation);


        }

        @Override
        public void onFinish() {

        }
    }
}
