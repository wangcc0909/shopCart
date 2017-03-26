package com.itheima.shop.bean;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

/**
 * Created by mwqi on 2017/3/8.
 */

public class Hot extends BasePageBean<Hot.ListEntity>{


    /**
     * id : 1
     * categoryId : 5
     * campaignId : 1
     * name : 联想（Lenovo）拯救者14.0英寸游戏本（i7-4720HQ 4G 1T硬盘 GTX960M 2G独显 FHD IPS屏 背光键盘）黑
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_55c1e8f7N4b99de71.jpg
     * price : 5979
     * sale : 8654
     */

    public List<ListEntity> list;

    @Override
    public List<ListEntity> getItemDatas() {
        return list;
    }

    public static class ListEntity {
        public int id;
        public int categoryId;
        public int campaignId;
        public String name;
        public String imgUrl;
        public double price;
        public int sale;
    }


//    public List<ItemHomeHotBean> list;
//
//    @Override
//    public List<ItemHomeHotBean> getItemDatas() {
//        return list;
//    }
//
//
//    public static class ItemHomeHotBean {
//        public int id;
//        public String name;
//        public String imgUrl;
//        public String description;
//        public double price;
//        public int sale;
//    }
}
