package com.itheima.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mwqi on 2017/3/15.
 */

public abstract class BaseFragment extends Fragment {


    private View mRootView;

    public Context mContext = null;
    private boolean mEventBus;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        free();
    }

    protected abstract void free();


    @Override
    public void onStop() {
        super.onStop();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }

    }


    @Override
    public void onStart() {
        super.onStart();

        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = initView();
            butterknife.ButterKnife.bind(this,mRootView);

            initData();

        }


        return mRootView;
    }

    public abstract void initData() ;

    public abstract View initView();

    public boolean isRegisterEventBus() {
        return false;
    }
}
