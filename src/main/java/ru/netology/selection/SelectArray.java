package ru.netology.selection;

import ru.netology.goods.Goods;

import java.util.ArrayList;

public interface SelectArray {

    void setMakeNewIndex(boolean ind);

    void makeHash(ArrayList<Goods> goods);

    boolean setSearchParam(ArrayList<String> param);

    void selectBy();
}
