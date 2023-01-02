package ru.netology.showcase;

import ru.netology.goods.Goods;
import ru.netology.goods.GoodsCondition;

import java.util.ArrayList;

public interface GoodsFilter {

    public ArrayList<Goods> filterGoods();
    public ArrayList<Goods> getAllGoods();
    public void setName(String name);
    public void setTopPrice(float tprice);
    public void setBottomPrice(float bprice);
    public void setVendor(String name);
    public void setKeyWord(String key);
    public void setCondition(GoodsCondition condition);

}
