package com.wtwd.standard.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wtwd.standard.R;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.utils.Utils;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Toolbar tool_bar;
    public ImageView img_tool_bar_left;
    public ImageView img_tool_bar_right;
    public TextView text_tool_bar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResourceId() != 0) {
            setContentView(getLayoutResourceId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        tool_bar = (Toolbar) findViewById(R.id.tool_bar);
        img_tool_bar_left = (ImageView) findViewById(R.id.img_tool_bar_left);
        img_tool_bar_right = (ImageView) findViewById(R.id.img_tool_bar_right);
        text_tool_bar_title = (TextView) findViewById(R.id.text_tool_bar_title);
        tool_bar.setNavigationIcon(R.mipmap.common_back);

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        img_tool_bar_left.setImageResource(R.mipmap.common_back);
//        img_tool_bar_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        onCreateView(savedInstanceState);
    }

    public abstract int getLayoutResourceId();

    public abstract void onCreateView(Bundle saveInstanceState);

    public abstract View getSnackView();


    /**
     * show the success message,the text color is white
     */
    public void showSnackBarShort(String msg) {
        if (null != msg && !TextUtils.isEmpty(msg)) {
//            Snackbar.make(getSnackView(), msg, Snackbar.LENGTH_SHORT).show();
            customSuccessSnackBar(msg).show();
        }
    }

    /**
     * show the error or fail message,the text color is red
     */
    public void showSnackBarLong(String msg) {
        if (null != msg && !TextUtils.isEmpty(msg)) {
//            Snackbar.make(getSnackView(), msg, Snackbar.LENGTH_LONG).show();
            customFailSnackBar(msg).show();
        }
    }

    private Snackbar customSuccessSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(getSnackView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        textView.setTextColor(Color.WHITE);

        return snackbar;
    }

    private Snackbar customFailSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(getSnackView(), msg, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        textView.setTextColor(Color.RED);

        return snackbar;
    }

    public void setTitleToolbarStyle(int type) {
        if (type == CommonConstants.PURE_PICTURE_TITLE) {
            Utils.setMargins(tool_bar, 0, Utils.getStatusBarHeight(this), 0, 0);
            changeTitleBarColor();
        } else if (type == CommonConstants.SOLID_COLOR_TITLE) {
            tool_bar.setBackgroundColor(ContextCompat.getColor(this, R.color.step_blue));
            Utils.setWindowStatusBarColor(this, R.color.step_blue);
        }
    }


    private void changeTitleBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /***
     * start activity
     * @param clazz target activity
     */
    public void readyGo(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * start activity with bundle
     *
     * @param clazz  target activity
     * @param bundle bundle
     */
    public void readyGo(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * start activity then finish
     *
     * @param clazz target activity
     */
    public void readyGoThenKill(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * start activity with bundle than finish
     *
     * @param clazz  target activity
     * @param bundle bundle
     */
    public void readyGoThenKill(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * start activity for result
     *
     * @param clazz       target activity
     * @param requestCode request code
     */
    public void readyGoForResult(Class<? extends Activity> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }


    /**
     * start activity for result with bundle
     *
     * @param clazz       target activity
     * @param requestCode request code
     * @param bundle      bundle
     */
    public void readyGoForResult(Class<? extends Activity> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
