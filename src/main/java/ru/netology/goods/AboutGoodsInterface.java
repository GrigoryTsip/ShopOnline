package ru.netology.goods;

import java.util.ArrayList;

public interface AboutGoodsInterface {

    void setVendor(String vendor);

    String getVendor();

    boolean isKeyWords();
    int numberOfKeyWords();

    void setKeyWord(String keyWord);

    String getKeyWord(int index);

    void setKeyWordList(ArrayList<String> keyWordList);

    ArrayList<String> getKeyWordList();

    String getGoodsRecommendations();

    void setGoodsRecommendations(String recommendations);

    int getGoodsRating();

    void setGoodsRating(int rating);

    float getGoodsSold();

    float setGoodsSold(float sold);

    AboutGoods getChild(Goods parent);

    String toString(boolean inOrder);

}
