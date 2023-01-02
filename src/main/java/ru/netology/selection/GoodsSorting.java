package ru.netology.selection;

import ru.netology.goods.Goods;

import java.util.ArrayList;
import java.util.Comparator;

public class GoodsSorting implements SortArray {

    Comparator<Goods> byName = new CompareByName();
    Comparator<Goods> byPrice = new CompareByPrice();
    Comparator<Goods> byVendor = new CompareByVendor();

    @Override
    public ArrayList<Goods> sortBy(ArrayList<Goods> listOfGoods, TypeOfSelection selectBy, boolean direction) throws NullPointerException {

        Comparator<Goods> cmpr = null;
        switch (selectBy) {
            case BY_NAME -> { cmpr = byName; }
            case BY_PRICE -> { cmpr = byPrice; }
            case BY_VENDOR -> { cmpr = byVendor; }
            default -> throw new IllegalStateException("Unexpected value: " + selectBy);
        }

        if (direction) {
            listOfGoods.sort(cmpr);
        } else {
            listOfGoods.sort(cmpr.reversed());
        }
        return listOfGoods;
    }
}
