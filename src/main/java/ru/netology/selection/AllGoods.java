package ru.netology.selection;

import ru.netology.goods.AboutGoods;
import ru.netology.goods.Goods;
import java.util.ArrayList;

//вывод массива товаров
public class AllGoods {

    //тукущая выборка товаров
    public static ArrayList<Goods> goods = GoodsSort.currentGoodsSample;

    public static void showGoods(ArrayList<Goods> goods) {
        if (goods.isEmpty()) {
            System.out.println("=================Указанный товар не найден.\nПовторите поиск с другими параметрами\n");
            GoodsSort.initialGoodsSample();
        } else {
            for (Goods gds : goods) {
                AboutGoods gd = (AboutGoods) gds;
                System.out.println(gd.toString(false));
            }
        }
    }

    public static void showAdvice() {

        ArrayList<Goods> gooods = GoodsSort.currentGoodsSample;

        if (gooods.isEmpty()) {
            System.out.println("=================Вы не выбрали товар - показывать нечего...\n");
            GoodsSort.initialGoodsSample();
        } else {
            for (Goods gds : gooods) {
                AboutGoods gd = (AboutGoods) gds;
                String s = gd.toString(true) + "\n" + gd.getGoodsRecommendations();
                System.out.println(s);
            }
        }
    }
}
