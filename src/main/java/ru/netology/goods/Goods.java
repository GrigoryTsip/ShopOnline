package ru.netology.goods;

import java.util.ArrayList;
import java.util.List;

public class Goods implements GoodsDefinition, GoodsOnStock {

    protected static int idGoods = 0;

    int id;
    String name;
    float price;
    String vendor;
    float numberOfGoods;
    // UnitsOfMesure unit;
    List<String> keyWords;
    //List<GoodsCondition> conditions;

    public static final double PRICE_MATCHING_ACCURACY = 0.001;
    public static final int GOODS_ID = 0;
    public static final int NAME = 1;
    public static final int PRICE = 2;
    public static final int VENDOR = 3;
    public static final int QUANTITY = 4;
    public static final int KEYWORDS = 5;

    public Goods(String name, float price, String vendor) {
        idGoods++;
        this.id = idGoods;
        this.name = name;
        this.price = price;
        this.vendor = vendor;
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
    public int getGoodsID() {
        return this.id;
    }

    @Override
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public String getVendor() {
        return vendor;
    }

    @Override
    public int numberOfKeyWords() {
        if (this.keyWords == null) return 0;
        return this.keyWords.size();
    }

    @Override
    public void setKeyWord(String keyWord) {
        if (this.keyWords == null) this.keyWords = new ArrayList<>();
        if (!this.keyWords.contains(keyWord)) this.keyWords.add(keyWord);
    }

    @Override
    public String getKeyWord(int index) {
        if (keyWords.isEmpty() || index >= keyWords.size()) return null;
        return keyWords.get(index);
    }

    @Override
    public ArrayList<String> getKeyWordList() {
        if (keyWords.isEmpty()) return null;
        return (ArrayList<String>) keyWords;
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
        return this.name.equals(that.name) &&
                this.vendor.equals(that.vendor) &&
                Math.abs(this.price - that.price) <= PRICE_MATCHING_ACCURACY;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder("[")
                .append("Product: ").append(this.name).append(", ")
                .append("Price: ").append(this.price).append(", ")
                .append("Vendor: ").append(this.vendor).append(", ")
                .append("In stock: ").append(this.numberOfGoods).append(", ")
                //.append(this.unit).append("\nKey words: ")
        ;
        for (String kw : this.keyWords) {
            s.append(" ").append(kw);
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public boolean setNumberOfGoods(float numberOfGoods) {
        this.numberOfGoods = numberOfGoods;
        return true;
    }

    @Override
    public float getNumberOfGoods() {
        return numberOfGoods;
    }

    @Override
    public boolean increaseGoodsStock(float number) {
        if( number < 0 ) return false;
        this.numberOfGoods += number;
        return true;
    }

    @Override
    public boolean reduceGoodsStock(float number) {
        if (number > this.numberOfGoods) return false;
        this.numberOfGoods -= number;
        return true;
    }
}
