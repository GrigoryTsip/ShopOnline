package ru.netology.selection;

import ru.netology.goods.Goods;
import java.util.ArrayList;

//вывод массива товаров
public class AllGoods {

    //коллекция всех товаров
    public static ArrayList<Goods> goods = new ArrayList<>();

    public void showGoods(ArrayList<Goods> goods) {
        if (goods.isEmpty()) {
            System.out.println("Указанный товар не найден");
        } else {
            for (Goods gds : goods) {
                System.out.println(gds.toString());
            }
        }
    }

}
