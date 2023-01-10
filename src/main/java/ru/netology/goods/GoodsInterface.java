package ru.netology.goods;

import java.util.ArrayList;

public interface GoodsInterface {
    /*
        public void setGoodsCondititon(GoodsCondition goodsCondition);

        public void removeGoodsCondition(GoodsCondition goodsCondition);

        GoodsCondition getCondition(int index);

     */
    void setMaxGoodsID(int maxID);

    int getGoodsID();

    String getGoodsName();

    float getGoodsPrice();

    boolean equals(Object o);

    AboutGoods getChild(Goods parent);

    String toString();
}
