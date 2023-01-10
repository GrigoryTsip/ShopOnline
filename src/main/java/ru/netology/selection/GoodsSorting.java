package ru.netology.selection;

import ru.netology.goods.AboutGoods;
import ru.netology.goods.Goods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class GoodsSorting implements SortArray {

    static Comparator<Goods> byName = new CompareByName();
    static Comparator<Goods> byPrice = new CompareByPrice();
    static Comparator<Goods> byVendor = new CompareByVendor();
    public static ArrayList<Map.Entry<Integer, Float>> goodsRating;


    public static ArrayList<Goods> sortBy(ArrayList<Goods> listOfGoods, TypeOfSelection selectBy, boolean direction) throws NullPointerException {

        Comparator<Goods> cmpr;
        switch (selectBy) {
            case BY_NAME -> cmpr = byName;
            case BY_PRICE -> cmpr = byPrice;
            case BY_VENDOR -> cmpr = byVendor;
            default -> throw new IllegalStateException("Unexpected value: " + selectBy);
        }

        if (direction) {
            listOfGoods.sort(cmpr);
        } else {
            listOfGoods.sort(cmpr.reversed());
        }
        return listOfGoods;
    }

    public static void showGoodsRating() {

        System.out.println("""

                Рейтинг продаваемости товаров
                -----------------------------
                """);

        for (Map.Entry<Integer,Float> entry : goodsRating) {
            AboutGoods gds = (AboutGoods) GoodsSelecting.getGoodsByID(entry.getKey());
            assert gds != null;
            String s = gds.toString(true) +
                    "Sold: " + gds.getGoodsSold() +
                    "\t\tRating: " + gds.getGoodsRating() + "\n";
            System.out.println(s);
        }
    }
}
