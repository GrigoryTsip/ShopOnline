package ru.netology.goods;

import java.util.ArrayList;

public interface GoodsDefinition {
/*
    public void setGoodsCondititon(GoodsCondition goodsCondition);

    public void removeGoodsCondition(GoodsCondition goodsCondition);

    GoodsCondition getCondition(int index);

 */

    public int getGoodsID();
    public void setVendor(String vendor);

    public String getVendor();

    public int numberOfKeyWords();

    public void setKeyWord(String keyWord);

    String getKeyWord(int index);
    ArrayList<String> getKeyWordList();

    public String getGoodsName();

    public float getGoodsPrice();

    public boolean equals(Object o);
}
