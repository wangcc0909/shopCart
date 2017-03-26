package com.itheima.shop.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.itheima.shop.R;
import com.itheima.shop.application.JDApp;
import com.itheima.shop.bean.Hot;
import com.itheima.shop.utils.CarProvider;

import org.itheima.recycler.adapter.BaseRecyclerAdapter;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by mwqi on 2017/3/15.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    ItheimaRecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    BaseRecyclerAdapter adapter;
    private int mCurPage = 1;
    private int mPageSize = 10;

    @Override
    protected void free() {

    }

    @Override
    public void initData() {

        Request request = ItheimaHttp.newGetRequest("");//apiUrl格式："xxx/xxxxx"
        request.putParams("curPage", String.valueOf(mCurPage))
                .putParams("pageSize", String.valueOf(mPageSize));
        Call call = ItheimaHttp.send(request, new HttpResponseListener<Hot>() {
            @Override
            public void onResponse(Hot bean, Headers headers) {


                adapter = new BaseRecyclerAdapter(mRecyclerView
                        , MyRecyclerViewHolder.class
                        , R.layout.item_homehot_recylcerview
                        , bean.list);
            }

        });


    }

    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<Hot.ListEntity> {



        @BindView(R.id.iv_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.butt_submit)
        Button mButtSubmit;
        @BindView(R.id.root_view)
        LinearLayout mRootView;

        public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);
        }

        /**
         * 绑定数据的方法，在mData获取数据（mData声明在基类中）
         */
        @Override
        public void onBindRealData() {
            mTvTitle.setText(mData.name);
            mTvPrice.setText("￥" + mData.price + "");

            Glide.with(mContext).load(mData.imgUrl).into(mIvIcon);
        }


        @OnClick(R.id.butt_submit)
        public void onClick() {
            //存放购买的商品
            CarProvider.getInstance(mContext).put(mData);

            Toast.makeText(mContext, "添加购物车", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public View initView() {

        View view = View.inflate(JDApp.mContext, R.layout.home_fragment, null);

        return view;
    }


}
