package ru.netology.goods;

public interface GoodsOnStock {

    void setNumberOfGoods(float numberOfGoods);

    float getNumberOfGoods();

    void increaseGoodsStock(float number);

    void reduceGoodsStock(float number);
}
