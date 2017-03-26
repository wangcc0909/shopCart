package com.itheima.shop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itheima.shop.R;
import com.itheima.shop.bean.Shopping;
import com.itheima.shop.utils.CarProvider;
import com.itheima.shop.view.CustomCarGoodsCounterView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.itheima.recycler.adapter.BaseRecyclerAdapter;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.itheima.shop.R.id.check_box;

/**
 * Created by mwqi on 2017/3/15.
 */

public class CarFragment extends BaseFragment {
    @BindView(R.id.tv_editext)
    TextView mTvEditext;
    @BindView(R.id.recycler_view)
    ItheimaRecyclerView mRecyclerView;
    @BindView(check_box)
    CheckBox mCheckBoxAll;
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    @BindView(R.id.ll_middle_view)
    LinearLayout mLlMiddleView;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    BaseRecyclerAdapter adapter;

    @Override
    protected void free() {

    }

    @Override
    public void initData() {
        adapter = new BaseRecyclerAdapter(mRecyclerView
                , MyRecyclerViewHolder.class
                , R.layout.item_home_car_recyclerview
                , null);


    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initBottomUI(String s) {

        mCheckBoxAll.setChecked(CarProvider.getInstance(mContext).isAllChecked());
        mTvTotalMoney.setText("¥" + CarProvider.getInstance(mContext).getTotalProice());


    }


    boolean flag = false;
    @OnClick({R.id.tv_editext, check_box, R.id.tv_delete, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_editext:

                if(flag){
                    mTvEditext.setText("编辑");

                    mLlMiddleView.setVisibility(View.VISIBLE);

                    mTvCommit.setVisibility(View.VISIBLE);

                    mTvDelete.setVisibility(View.GONE);

                    flag = false;
                }else{

                    mTvEditext.setText("完成");

                    mLlMiddleView.setVisibility(View.INVISIBLE);

                    mTvCommit.setVisibility(View.GONE);

                    mTvDelete.setVisibility(View.VISIBLE);

                    CarProvider.getInstance(mContext).setCheckState(false);


                    flag = true;


                }


                adapter.addDatas(false,CarProvider.getInstance(mContext).getMemoryAll());
                break;
            case check_box:

                List<Shopping> cars = CarProvider.getInstance(mContext).getMemoryAll();

                mCheckBoxAll.setChecked(CarProvider.getInstance(mContext).setCheckState(mCheckBoxAll.isChecked()));

                adapter.addDatas(false,cars);

                break;
            case R.id.tv_delete:

                CarProvider.getInstance(mContext).deleteCarGoods();


                adapter.addDatas(false,CarProvider.getInstance(mContext).getMemoryAll());


                adapter.notifyDataSetChanged();

                break;
            case R.id.tv_commit:
                break;
        }
    }


    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<Shopping> implements CustomCarGoodsCounterView.UpdateGoodsNumberListener {
        @BindView(check_box)
        CheckBox mCheckBox;
        @BindView(R.id.iv_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.car_goods_counter)
        CustomCarGoodsCounterView mCarGoodsCounter;
        @BindView(R.id.item_recycler_root)
        LinearLayout mItemRecyclerRoot;


        public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);
            mCarGoodsCounter.setUpdateGoodsNumberListener(this);
        }

        /**
         * 绑定数据的方法，在mData获取数据（mData声明在基类中）
         */
        @Override
        public void onBindRealData() {
            mCheckBox.setChecked(mData.isChecked);
            Glide.with(mContext).load(mData.imageUrl).into(mIvIcon);

            mTvTitle.setText(mData.name);

            mTvPrice.setText("￥" + mData.price);


            System.out.println("----" + mData.count );


            mCarGoodsCounter.setGoodsNumber(mData.count);

        }


        @OnClick(check_box)
        public void onClick() {

            mData.isChecked = !mData.isChecked;

            mCheckBox.setChecked(mData.isChecked);

            //更新购物车
            CarProvider.getInstance(mContext).updateGoodsCar(mData);

            EventBus.getDefault().post(new String());

        }


        @Override
        public void updateGoodsNumber(int number) {




            mData.count = number;

            //更新购物车
            CarProvider.getInstance(mContext).updateGoodsCar(mData);


            EventBus.getDefault().post(new String());

            Toast.makeText(mContext, "haha", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onResume() {
        super.onResume();


        List<Shopping> mCars = CarProvider.getInstance(mContext).getMemoryAll();

        adapter.addDatas(false, mCars);

        initBottomUI(null);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.home_car_fragment, null);
        return view;
    }


}
