package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.utils.Utils;

public class RegisterAccountActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_register_next_step;
    private EditText edit_register_account_edit;
    private CheckBox cb_register_protocol;
    private LinearLayout lin_check_protocol;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_register_account;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);
        initView();

    }

    private void initView() {
        btn_register_next_step = (Button) findViewById(R.id.btn_register_next_step);
        edit_register_account_edit = (EditText) findViewById(R.id.edit_register_account_edit);
        cb_register_protocol = (CheckBox) findViewById(R.id.cb_register_protocol);
        lin_check_protocol = (LinearLayout) findViewById(R.id.lin_check_protocol);

        addListener();
    }

    private void addListener() {
        btn_register_next_step.setOnClickListener(this);
        lin_check_protocol.setOnClickListener(this);
    }

    private String getAccountString() {
        return edit_register_account_edit.getText().toString();
    }

    @Override
    public View getSnackView() {
        return btn_register_next_step;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (R.id.btn_register_next_step == id) {
            //判断是否同意协议
            if (cb_register_protocol.isChecked()) {
//                if (Utils.isEmail(getAccountString())) {
                if (!TextUtils.isEmpty(getAccountString())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("account", getAccountString());
                    readyGo(RegisterActivity.class, bundle);
//                }
                }
            }
        } else if (R.id.lin_check_protocol == id) {
            cb_register_protocol.setChecked(!cb_register_protocol.isChecked());
        }

    }
}
