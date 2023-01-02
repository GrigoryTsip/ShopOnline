package ru.netology.selection;

import ru.netology.goods.Goods;
import java.util.Comparator;
import static ru.netology.goods.Goods.PRICE_MATCHING_ACCURACY;

public class CompareByPrice implements Comparator<Goods> {
    @Override
    public int compare(Goods o1, Goods o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Not defined object");
        }
        float delta = o1.getGoodsPrice() - o2.getGoodsPrice();
        if (Math.abs(delta) < PRICE_MATCHING_ACCURACY) return 0;
        return (int) delta;
    }
}
