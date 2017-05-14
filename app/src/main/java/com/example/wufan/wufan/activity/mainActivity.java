package com.example.wufan.wufan.activity;

import android.os.Bundle;
import com.example.wufan.wufan.R;
import com.example.wufan.wufan.fragment.homeFragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by larry on 2017/4/4.
 */

public class mainActivity extends baseActivity implements BottomNavigation.OnMenuItemSelectionListener {

    @BindView(R.id.navigation_view)
    BottomNavigation mBottomNavigationView;
    @BindView(R.id.fragment_container)
    FrameLayout mFrameLayout;

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        getUserInfo();

        //避免重复add fragment
        if (savedInstanceState == null) {
            initFragments();
        }

    }

    @Override
    public void initViews(){
        mBottomNavigationView.setOnMenuItemClickListener(this);
        mBottomNavigationView.setDefaultSelectedIndex(0);
        //BadgeProvider badgeProvider = mBottomNavigationView.getBadgeProvider();
        //badgeProvider.show(R.id.action_home);
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : mFragmentList) {
            fragmentTransaction.hide(fragment);
        }
    }

    @Override
    public void onMenuItemSelect(@IdRes final int itemId, final int position,
                                 final boolean fromUser) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentList.get(position);
        hideFragments(fragmentTransaction);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentList.add(homeFragment.newInstance());
        mFragmentList.add(messageFragment.newInstance());
        mFragmentList.add(addFragment.newInstance());
        mFragmentList.add(discoverFragment.newInstance());
        mFragmentList.add(profileFragment.newInstance());

        //默认选中主页
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, mFragmentList.get(0))
                .commit();
    }


    private void getUserInfo() {
        BaseRequestParams request = new BaseRequestParams();
        HttpRequestFactory.getInstance()
                .getUserInfo(request, new SimpleHttpRequestCallback<UserRes>() {
                    @Override
                    public void onSuccess(UserRes userRes) {
                        App.getInstance().setUser(userRes);
                        SharedPreUtils.pusString(Constants.SharedPreferences.KEY_USER_ID,
                                userRes.getId());
                    }

                    @Override
                    public void onFail(ApiException apiException) {
                    }
                });
    }
}