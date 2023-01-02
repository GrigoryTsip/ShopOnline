package ru.netology.goods;

public interface GoodsOnStock {

    public boolean setNumberOfGoods(float numberOfGoods);

    public float getNumberOfGoods();

    public boolean increaseGoodsStock(float number);

    public boolean reduceGoodsStock(float number);
}
