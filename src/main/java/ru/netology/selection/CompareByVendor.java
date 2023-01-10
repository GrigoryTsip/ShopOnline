package ru.netology.selection;

import ru.netology.goods.Goods;

import java.util.Comparator;

public class CompareByVendor implements Comparator<Goods> {
    @Override
    public int compare(Goods o1, Goods o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Not defined object");
        }
        return o1.getChild(o1).getVendor().compareTo(o2.getChild(o2).getVendor());
    }
}
