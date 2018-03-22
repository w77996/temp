package com.wtwd.standard.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseFragment;
import com.wtwd.standard.fragment.MainMeFragment;
import com.wtwd.standard.fragment.MainPageFragment;
import com.wtwd.standard.fragment.MainSportFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private Button main_page;
    private Button main_sport;
    private Button main_me;

    private List<BaseFragment> mFragments = new ArrayList<BaseFragment>();
    private List<Button> mButtons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        main_page = (Button) findViewById(R.id.main_page);
        main_sport = (Button) findViewById(R.id.main_sport);
        main_me = (Button) findViewById(R.id.main_me);

        addListener();
        initPage();
    }

    public void addListener() {
        mButtons.add(main_page);
        mButtons.add(main_sport);
        mButtons.add(main_me);
        main_page.setOnClickListener(this);
        main_sport.setOnClickListener(this);
        main_me.setOnClickListener(this);
    }


    private void initPage() {
        prepareFragment();
        changePage(0);
    }

    public void changePage(int page) {
//        currentPage = page;
//        toolbar.setTitle(titles[page]);
        updateFragment(page);
//        if (currentPage == a1) {
//            ((CharacteristicListFragment) fragments.get(a1)).showData();
//        } else if (currentPage == a2) {
//            ((CharacteristicOperationFragment) fragments.get(a2)).showData();
//        }
    }

    private void prepareFragment() {
        // TODO: 2018/a1/26 0026 添加fragment实例到mFragments集合中
        mFragments.add(MainPageFragment.getMainPageFragment());
        mFragments.add(MainSportFragment.getMainSportFragment());
        mFragments.add(MainMeFragment.getMainMeFragment());
        for (BaseFragment fragment : mFragments) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, fragment).hide(fragment).commit();
        }
    }

    /**
     * 切换fragment,且设置button selector状态
     */
    private void updateFragment(int position) {
        if (position > mFragments.size() - 1) {
            return;
        }
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BaseFragment fragment = mFragments.get(i);
            if (i == position) {
                mButtons.get(i).setSelected(true);
                transaction.show(fragment);
            } else {
                mButtons.get(i).setSelected(false);
                transaction.hide(fragment);
            }
            transaction.commit();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.main_page == id) {
            changePage(0);
        } else if (R.id.main_sport == id) {
            changePage(1);
        } else if (R.id.main_me == id) {
            changePage(2);
        }


    }
}
