package ru.netology.goods;

import java.util.ArrayList;

public class AboutGoods extends Goods implements AboutGoodsInterface, GoodsOnStock {

    String vendor;
    float numberOfGoods;
    ArrayList<String> keyWords;
    //List<GoodsCondition> conditions;

    String recommendation;
    protected int rating;
    protected float goodsSold;

    public AboutGoods() {
        super();
    }

    public AboutGoods(String name, float price, String vendor) {
        super(name, price);
        this.vendor = vendor;
    }

    public AboutGoods(int id, String name, float price, String vendor) {
        super(id, name, price);
        this.vendor = vendor;
    }

    @Override
    public String getVendor() {
        return vendor;
    }

    @Override
    public boolean isKeyWords() {
        return this.keyWords != null;
    }

    @Override
    public void setVendor(String vendor) {
        this.vendor = vendor;
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
        if (this.keyWords.isEmpty() || index >= keyWords.size()) return null;
        return keyWords.get(index);
    }

    @Override
    public void setKeyWordList(ArrayList<String> keyWordList) {
        this.keyWords = keyWordList;
    }

    @Override
    public ArrayList<String> getKeyWordList() {
        if (keyWords == null || keyWords.isEmpty()) return null;
        return keyWords;
    }

    @Override
    public String getGoodsRecommendations() {
        return this.recommendation;
    }

    @Override
    public void setGoodsRecommendations(String recommendations) {
        this.recommendation = recommendations;
    }

    @Override
    public int getGoodsRating() {
        return rating;
    }

    @Override
    public void setGoodsRating(int rating) {
        this.rating = rating;

    }

    @Override
    public float getGoodsSold() {
        return goodsSold;
    }

    @Override
    public float setGoodsSold(float sold) {
        goodsSold = sold;
        return sold;
    }

    @Override
    public AboutGoods getChild(Goods parent) {
        return (parent instanceof AboutGoods) ? (AboutGoods) parent : null;
    }

    @Override
    public void setNumberOfGoods(float numberOfGoods) {
        this.numberOfGoods = numberOfGoods;
    }

    @Override
    public float getNumberOfGoods() {
        return numberOfGoods;
    }

    @Override
    public void increaseGoodsStock(float number) {
        if (number < 0) return;
        this.numberOfGoods += number;
    }

    @Override
    public void reduceGoodsStock(float number) {
        if (number > this.numberOfGoods) return;
        this.numberOfGoods -= number;
    }

    @Override
    public String toString(boolean inOrder) {

        StringBuilder s = new StringBuilder(this.id);

        s.append("id=").append(this.id).append("\t")
                .append("Товар: ").append(this.name).append(", \t\t")
                .append("Цена: ").append(this.price).append(", \t\t")
                .append("Поставщик: ").append(this.vendor).append(", \t\t");

        if (!inOrder) {
            s.append("На складе: ").append(this.numberOfGoods).append(", \t\t")
                    .append("Ключевые слова: ");
            for (int i = 0; i < this.numberOfKeyWords(); i++) {
                if ((i == 0)) {
                    s.append(this.getKeyWord(i));
                } else {
                    s.append(", ").append(this.getKeyWord(i));
                }
            }
            //s.append("\n\t\tО товаре: ").append(this.recommendation).append("\n");
        }
        return s.toString();
    }
}
