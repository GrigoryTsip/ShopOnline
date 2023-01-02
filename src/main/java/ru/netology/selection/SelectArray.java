package ru.netology.selection;

import ru.netology.goods.Goods;

import java.util.ArrayList;

public interface SelectArray {

    public void setMakeNewIndex(boolean ind);

    public void makeHash(ArrayList<Goods> goods);

    public void setSearchParam(ArrayList<String> param);

    public ArrayList<Goods> selectBy();
}
