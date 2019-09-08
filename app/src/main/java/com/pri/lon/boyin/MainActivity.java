package com.pri.lon.boyin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.pri.lon.boyin.ui.HomeFragment;
import com.pri.lon.boyin.ui.MineFragment;
import com.pri.lon.boyin.ui.RechargeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottom_navigation_bar;

    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBottomView();
        initFragments();
        setDefaultFragment();

    }

    private void initBottomView() {
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "视频").setActiveColor(R.color.colorAccent));
//        bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "美图").setActiveColor(R.color.colorAccent));
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "充值").setActiveColor(R.color.colorAccent));
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "我的").setActiveColor(R.color.colorAccent));
        bottom_navigation_bar.setFirstSelectedPosition(0).initialise();
        bottom_navigation_bar.setTabSelectedListener(this);

    }

    private void initFragments() {
        mFragments.add(HomeFragment.newInstance());
//        mFragments.add(BeautyFragment.newInstance());
        mFragments.add(RechargeFragment.newInstance());
        mFragments.add(MineFragment.newInstance());
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, mFragments.get(0));
        transaction.commit();

    }

    @Override
    public void onTabSelected(int position) {

        if (mFragments != null) {
            if (position < mFragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                Fragment fragment = mFragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.container, fragment);
                } else {
                    ft.add(R.id.container, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabUnselected(int position) {

        if (mFragments != null) {
            if (position < mFragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = mFragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabReselected(int position) {

    }
}
