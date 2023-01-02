package ru.netology.selection;

import ru.netology.goods.Goods;

import java.util.Comparator;

public class CompareByName implements Comparator<Goods> {
    @Override
    public int compare(Goods o1, Goods o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Not defined object");
        }
        return o1.getGoodsName().compareTo(o2.getGoodsName());
    }
}
