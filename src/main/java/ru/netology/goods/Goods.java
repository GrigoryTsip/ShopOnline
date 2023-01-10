package ru.netology.goods;

import jdk.internal.org.jline.utils.Colors;

public class Goods implements GoodsInterface {

    protected static int idGoods = 0;

    int id;
    String name;
    float price;


    public static final double PRICE_MATCHING_ACCURACY = 0.001;
    public static final int GOODS_ID = 0;
    public static final int NAME = 1;
    public static final int PRICE = 2;
    public static final int VENDOR = 3;
    public static final int QUANTITY = 4;
    public static final int RECOMMENDATION = 5;
    public static final int KEYWORDS = 6;

    public static final String[] goodsColumnMapping = {
            "id", "name", "price",
            "vendor", "numberOfGoods", "keyWords"
    };

    public Goods() {
    }

    public Goods(String name, float price) {
        idGoods++;
        this.id = idGoods;
        this.name = name;
        this.price = price;
    }

    public Goods(int id, String name, float price) {
        this.id = idGoods;
        this.name = name;
        this.price = price;
    }
/*
    @Override
    public void setGoodsCondititon(GoodsCondition goodsCondition) {
        if (!this.conditions.contains(goodsCondition)) this.conditions.add(goodsCondition);
    }

    @Override
    public void removeGoodsCondition(GoodsCondition goodsCondition) {
        this.conditions.remove(goodsCondition);
    }

    @Override
    public GoodsCondition getCondition(int index) {
        if (conditions.isEmpty() || index >= conditions.size()) return null;
        return conditions.get(index);
    }

 */

    @Override
    public void setMaxGoodsID(int maxID) {
        idGoods = maxID;
    }

    @Override
    public int getGoodsID() {
        return this.id;
    }


    @Override
    public String getGoodsName() {
        return name;
    }

    @Override
    public float getGoodsPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods that = (Goods) o;

        boolean equal = this.name.equals(that.name) &&
                Math.abs(this.price - that.price) <= PRICE_MATCHING_ACCURACY;
        //проверяем определены ли потомки класса и совпадают ли там vendors
        AboutGoods aboutGoods = this.getChild(this);
        AboutGoods aboutGoodsO = that.getChild(that);
        if (aboutGoods != null && aboutGoodsO != null)
            equal = equal && (aboutGoods.getVendor().equals(aboutGoodsO.getVendor()));
        return equal;
    }

    @Override
    public AboutGoods getChild(Goods parent) {
        return (parent instanceof AboutGoods) ? (AboutGoods) parent : null;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
}
