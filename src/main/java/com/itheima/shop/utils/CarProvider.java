package com.itheima.shop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.itheima.shop.bean.Hot;
import com.itheima.shop.bean.Shopping;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by mwqi on 2017/3/15.
 */

public class CarProvider {

    /**
     * 购买商品；
     * 1 内存
     * 2 本地（持久化）
     * 3 网络
     *
     *
     */

    public volatile static WeakReference<CarProvider> sInstance = null;

    //购物车
    public SparseArray<Shopping> mSparseArray = null;


    public Context mContext;

    public CarProvider(Context context) {
        mContext = context;
        mSparseArray = new SparseArray<Shopping>();
        loadLocalToMemory();
    }

    /**
     * 加载本地数据到内存中
     */
    public void loadLocalToMemory() {
        List<Shopping> cars = getLoadAll();
        if(null != cars && cars.size() > 0){
            for (Shopping car : cars) {
                mSparseArray.put(car.id, car);
            }
        }

    }

    private List<Shopping> getLoadAll() {
        String json = (String) SPUtils.get(mContext, SPUtils.CAR_JSON, "");
        List<Shopping> mCars = null;
        if(!TextUtils.isEmpty(json)){
            mCars = GsonUtils.parseToBean(json, new TypeToken<List<Shopping>>() {
            }.getType());

//            mCars = GsonUtils.parseToList(json, (Class<?>) new TypeToken<List<Shopping>>() {
//            }.getType());
        }
        return mCars;
    }

    public static CarProvider getInstance(Context context) {

        if(sInstance == null || sInstance.get() == null){

            synchronized (CarProvider.class){
                if(sInstance == null || sInstance.get() == null){
                    sInstance = new WeakReference<CarProvider>(new CarProvider(context.getApplicationContext()));
                }
            }
        }

        return sInstance.get();
    }

    //存放商品
    public void put(Hot.ListEntity data) {
        put(toShoppingCar(data));
    }

    private void put(Shopping shopping) {
        //获取内存里面的商品
        Shopping shop = mSparseArray.get(shopping.id);
        //判断当前的商品内存当中是否已经存在
        if(shop != null){
            shop.count++;
        }else{
            mSparseArray.put(shopping.id,shopping);
        }
        //保存到本地，进行持久化操作
        saveAllGoodsLocal();
    }

    /**
     * 从内存当中获取所有的商品，并且进行持久化操作
     */

    public void saveAllGoodsLocal() {
        List<Shopping> cars  =  getMemoryAll();
        for (int i = 0; i < cars.size(); i++) {
            String json = GsonUtils.parseListToJson(cars);
            SPUtils.put(mContext,SPUtils.CAR_JSON,json);
        }
    }

    /**
     * 从内存获取所有的商品
     * @return
     */
    public List<Shopping> getMemoryAll() {

        List<Shopping> cars = new ArrayList<>();
        int carSize = mSparseArray.size();
        for (int i = 0; i < carSize; i++) {
            cars.add(mSparseArray.get(mSparseArray.keyAt(i)));
        }
        return cars;
    }

    private Shopping toShoppingCar(Hot.ListEntity data) {
        Shopping shopping = new Shopping();
        shopping.id = data.id;
        shopping.name = data.name;
        shopping.imageUrl = data.imgUrl;
        shopping.price = data.price;
        return shopping;
    }



    public boolean isAllChecked() {
        return getSelectCount() == mSparseArray.size();
    }

    private int getSelectCount() {

        int count = 0;

        int size = mSparseArray.size();

        for (int i = 0; i < size; i++) {
            if(mSparseArray.get(mSparseArray.keyAt(i)).isChecked){
                count++;
            }
        }


        return count;
    }


    /**
     * 获取到商品的总价格
     * @return
     */
    public double getTotalProice() {

        double tempTotalPrice = 0;
        int goodsSize = mSparseArray.size();
        for (int i = 0; i < goodsSize; i++) {
            Shopping tempCar = mSparseArray.get(mSparseArray.keyAt(i));
            if (tempCar.isChecked) {
                tempTotalPrice = Arith.add(tempTotalPrice, Arith.mul(tempCar.price, tempCar.count));
            }
        }
        return tempTotalPrice;
    }


    public void updateGoodsCar(Shopping data) {
        mSparseArray.remove(data.id);
        mSparseArray.put(data.id,data);
        saveAllGoodsLocal();
    }

    public boolean setCheckState(boolean state) {

        int size = mSparseArray.size();

        for (int i = 0; i < size; i++) {

            mSparseArray.get(mSparseArray.keyAt(i)).isChecked = state;
        }

        saveAllGoodsLocal();

        return state;
    }

    public void deleteCarGoods() {

        int size = mSparseArray.size();

        for (int i = size - 1; i >= 0; i--) {
            if(mSparseArray.get(mSparseArray.keyAt(i)).isChecked){
                mSparseArray.removeAt(i);
            }
        }
        saveAllGoodsLocal();

    }
}
