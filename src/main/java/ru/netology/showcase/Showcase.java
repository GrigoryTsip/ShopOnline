package ru.netology.showcase;

import ru.netology.goods.Goods;
import ru.netology.goods.GoodsCondition;

import java.util.ArrayList;
import java.util.HashMap;

public class Showcase implements GoodsFilter, SetHashGoods {

    protected static HashMap<String, Goods> showGoods = new HashMap<>();

    protected ArrayList<String> name;
    protected float priceTop;
    protected float priceBottom;
    protected ArrayList<String> vendor;
    protected ArrayList<GoodsCondition> condition;
    protected ArrayList<String> keyWord;

    public Showcase() {
        this.name = null;
        this.vendor = null;
        this.condition = null;
        this.priceTop = 0;
        this.priceBottom = 0;
    }


    @Override
    public ArrayList<Goods> filterGoods() {

        ArrayList<Goods> selection = new ArrayList<>();

        if (name != null) {
            for (String key : name) {
                selection.add(showGoods.get(key));
            }
        }
        return null;
    }

    @Override
    public ArrayList<Goods> getAllGoods() {
        if (showGoods.isEmpty()) return null;
        return (ArrayList<Goods>) showGoods.values();
    }

    @Override
    public void setName(String name) {
        if (!this.name.contains(name)) this.name.add(name);
    }

    @Override
    public void setTopPrice(float tprice) {
        this.priceTop = tprice;
    }

    @Override
    public void setBottomPrice(float bprice) {
        this.priceBottom = bprice;
    }

    @Override
    public void setVendor(String name) {
        if (!this.vendor.contains(name)) this.vendor.add(name);
    }

    @Override
    public void setKeyWord(String name) {
        if (!this.keyWord.contains(name)) this.keyWord.add(name);
    }

    @Override
    public void setCondition(GoodsCondition condition) {
        if (!this.condition.contains(condition)) this.condition.add(condition);
    }

    @Override
    public void setHashGoods(Goods goods) {
        showGoods.put(goods.getGoodsName(), goods);
        int index = 0;
        String key = goods.getKeyWord(index);
        while (key != null) {
            showGoods.put(key, goods);
            index++;
            key = goods.getKeyWord(index);
        }
    }
}
