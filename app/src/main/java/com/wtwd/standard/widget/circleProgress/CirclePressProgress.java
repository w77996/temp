package com.wtwd.standard.widget.circleProgress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.wtwd.standard.R;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class CirclePressProgress extends View {

    private static final String TAG = "CirclePressProgress";

    /**
     * 内圆背景
     */
    private Drawable mInnerCircleBg;
    /**
     * 文字颜色
     */
    private int mTextColor;
    /**
     * 文字大小
     */
    private int mTextSize;
    /**
     * 外圆底部颜色
     */
    private int mOutCircleColor;
    /**
     * 外圆上部颜色
     */
    private int mOutUpCircleColor;
    /**
     * 内圆中心文字
     */
    private String mInnerCenterString;

    /***
     * 内圆背景画笔
     */
    private Paint mInnerCircleBgPaint;
    /**
     * 内圆文字画笔
     */
    private Paint mInnerTextPaint;
    /**
     * 外圆底部画笔
     */
    private Paint mOutCirclePaint;
    /**
     * 外圆上部画笔
     */
    private Paint mOutUpCirclePaint;

    /**
     * 外圆stroke宽度
     */
    private float mOutCircleWidth;

    /***
     * 外圆当前进度
     */
    private float mOutUpCircleProgress;
    /**
     * 是否处于被按压状态
     */
    private boolean mPressSelf;

    /**
     * 是否为点击事件
     */
    private boolean mOnSingleClick = true;

    /**
     * 线程是否正在运行
     */
    private boolean mCircleRunnableRunning;

    /**
     * 是否绘制外圆
     */
    private boolean mDrawOutCircle;


    private ICircleCloseInter mCircleCloseListener;

    public CirclePressProgress(Context context) {
        this(context, null);
    }

    public CirclePressProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePressProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CirclePressProgress, defStyleAttr, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CirclePressProgress_press_inner_circle_bg:
                    mInnerCircleBg = a.getDrawable(attr);
                    break;

                case R.styleable.CirclePressProgress_press_text_color:
                    mTextColor = a.getColor(attr, ContextCompat.getColor(context, R.color.color_white));
                    break;

                case R.styleable.CirclePressProgress_press_text_size:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.CirclePressProgress_press_text_string:
                    mInnerCenterString = a.getString(attr);
                    break;

                case R.styleable.CirclePressProgress_press_out_circle_bg_color:
                    mOutCircleColor = a.getColor(attr, ContextCompat.getColor(context, R.color.color_white));
                    break;

                case R.styleable.CirclePressProgress_press_out_circle_up_color:
                    mOutUpCircleColor = a.getColor(attr, ContextCompat.getColor(context, R.color.color_white));
                    break;
            }
        }

        initPaint();

    }

    private void initPaint() {
        mInnerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mInnerCircleBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCircleBgPaint.setStyle(Paint.Style.FILL);

        mOutCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutCirclePaint.setStyle(Paint.Style.STROKE);
        mOutCirclePaint.setColor(mOutCircleColor);

        mOutUpCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutUpCirclePaint.setStyle(Paint.Style.STROKE);
        mOutUpCirclePaint.setColor(mOutUpCircleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        int mRadius = Math.min(getWidth() / 2, getHeight() / 2);

        int mOutCircleRadius = (int) (mRadius * 0.9);
        int mInnerCircleRadius = (int) (mRadius * 0.8);

        mOutCircleWidth = (float) (mOutCircleRadius * 0.1);

        drawInnerCircle(mInnerCircleRadius, canvas);

        if (mDrawOutCircle) {
            drawOutCircle(mOutCircleRadius, canvas);
        }


    }


    /***
     * 绘制外圆
     * @param mRadius 外圆半径
     * @param canvas 画布
     */
    private void drawOutCircle(int mRadius, Canvas canvas) {
        mOutCirclePaint.setStrokeWidth(mOutCircleWidth);
        mOutUpCirclePaint.setStrokeWidth(mOutCircleWidth);

        canvas.drawCircle(0, 0, mRadius, mOutCirclePaint);

        RectF mArc = new RectF();
        mArc.top = -mRadius;
        mArc.bottom = mRadius;
        mArc.left = -mRadius;
        mArc.right = mRadius;
        canvas.drawArc(mArc, -90, (mOutUpCircleProgress / 100f) * 360, false, mOutUpCirclePaint);
    }

    /**
     * 绘制内圆
     *
     * @param mRadius 内圆半径
     * @param canvas  画布
     */
    private void drawInnerCircle(int mRadius, Canvas canvas) {
        Shader mInnerBgShader = new LinearGradient((float) (-mRadius * (Math.sin(45)))
                , (float) (mRadius * (Math.sin(45)))
                , (float) (mRadius * (Math.sin(45)))
                , (float) (-mRadius * (Math.sin(45)))
                , ContextCompat.getColor(getContext(), R.color.color_register_btn_start)
                , ContextCompat.getColor(getContext(), R.color.color_register_btn_end), Shader.TileMode.CLAMP);
        mInnerCircleBgPaint.setShader(mInnerBgShader);

        canvas.drawCircle(0, 0, mRadius, mInnerCircleBgPaint);

        drawInnerText(mRadius, canvas);
    }


    /**
     * 绘制内圆中心文字
     *
     * @param mRadius 内圆半径，用来计算文字高度
     * @param canvas  画布
     */
    private void drawInnerText(int mRadius, Canvas canvas) {
        float mTextHalfHeight = measureTextSize(mInnerCenterString, mRadius);
        mInnerTextPaint.setColor(mTextColor);
        mInnerTextPaint.setTextSize(2 * mTextHalfHeight);
        mInnerTextPaint.setTextAlign(Paint.Align.CENTER);
        float mTextWidth = mInnerTextPaint.measureText(mInnerCenterString);

//        mInnerTextPaint.setTextSize((float) (mRadius * Math.sin(45) / mInnerCenterString.length()));


//        canvas.drawText(mInnerCenterString, (float) (-mTextWidth / 2), (float) mRadius * 2f / 7, mInnerTextPaint);
        canvas.drawText(mInnerCenterString, 0, mTextHalfHeight, mInnerTextPaint);
    }

    private float measureTextSize(String str, int mRadius) {
        int length = str.length();
        if (2 == length) {
            return (float) mRadius * 2f / 7;
        } else if (4 == length) {
            return (float) mRadius / 5f;
        }
        return 0;
    }


    public void setInnerCenterString(String mInnerCenterString) {
        this.mInnerCenterString = mInnerCenterString;
        invalidate();
    }

    public String getInnerCenterstring() {
        return mInnerCenterString;
    }

    public void setOnSingleClick(boolean mOnSingleClick) {
        this.mOnSingleClick = mOnSingleClick;
    }

    public boolean getOnSingleClick() {
        return mOnSingleClick;
    }

    public void setCircleCloseListener(ICircleCloseInter c) {
        this.mCircleCloseListener = c;
    }

    private void setOutUpCircleProgress(float mOutUpCircleProgress) {
        this.mOutUpCircleProgress = mOutUpCircleProgress;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "action down");
                if (!mOnSingleClick) {
                    mPressSelf = true;
                    mDrawOutCircle = true;
                    if (!mCircleRunnableRunning) {
                        new Thread(new CircleThread()).start();
                    }

                }
                break;

            case MotionEvent.ACTION_UP:
                if (!mOnSingleClick) {
                    mPressSelf = false;
                } else {
                    performClick();
                }
                return true;


        }

        return super.onTouchEvent(event);
    }


    private class CircleThread implements Runnable {


        @Override
        public void run() {
            Log.e(TAG, "thread run ");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    mCircleRunnableRunning = true;
                    Thread.sleep(10);

                    if (mPressSelf) {
                        //按压状态

                        mOutUpCircleProgress += 1;

                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = mOutUpCircleProgress;
                        mHandler.sendMessage(msg);

                        if (mOutUpCircleProgress >= 100) {
                            mDrawOutCircle = false;
                            mOutUpCircleProgress = 0;
                            break;
                        }

                    } else {
                        //松开状态

                        mOutUpCircleProgress -= 1;

                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = mOutUpCircleProgress;
                        mHandler.sendMessage(msg);

                        if (mOutUpCircleProgress <= 0) {
                            mDrawOutCircle = false;
                            mOutUpCircleProgress = 0;
                            break;
                        }
                    }


//                    setOutUpCircleProgress(mOutUpCircleProgress);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    Thread.currentThread().interrupt();
                }
            }
            mCircleRunnableRunning = false;
            Log.e(TAG, "thread stop ");
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (1 == msg.what) {
                float mProgress = (float) msg.obj;
                if (100f == mProgress) {
                    setOutUpCircleProgress(0);
                    mCircleCloseListener.onCircleCloseListener();
                } else {
                    setOutUpCircleProgress(mProgress);
                }
            }
        }
    };


}
