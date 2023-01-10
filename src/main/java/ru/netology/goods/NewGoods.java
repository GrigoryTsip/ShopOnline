package ru.netology.goods;

import ru.netology.menu.Menu;
import ru.netology.menu.MenuDefinition;
import ru.netology.selection.AllGoods;
import ru.netology.selection.GoodsSelecting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.netology.goods.Goods.*;
import static ru.netology.menu.SystemMenu.*;

public class NewGoods implements AddGoods {

    protected static ArrayList<Goods> newGoods = new ArrayList<>();
    protected static ArrayList<String> options;
    protected static MenuDefinition param = new MenuDefinition();
    protected static Menu menu = new Menu();
    ReadWriteGoods readWriteGoods = new ReadWriteGoods();
    AboutGoods aboutGoods = new AboutGoods();

    public void addNewGoods() {

        while (true) {
            options = new ArrayList<>();
            options.add("Наименование товара");
            options.add("Цена товара");
            options.add("Производитель товара");
            options.add("Количество товара");
            options.add("Информация о товаре");
            List<String> goodsParam = param.menuList(NEW_GOODS_MENU, options);


            float price = menu.setDigit(goodsParam.get(PRICE - 1), "f").getFloatDidit();
            if (price > 0) {
                float quantity = menu.setDigit(goodsParam.get(QUANTITY - 1), "f").getFloatDidit();
                if (quantity > 0) {
                    Goods goods = new AboutGoods(goodsParam.get(NAME - 1), price, goodsParam.get(VENDOR - 1));
                    aboutGoods.getChild(goods).setNumberOfGoods(quantity);

                    options = new ArrayList<>();
                    options.add("Ключевые слова");
                    goodsParam = param.menuList(KEYWORDS_MENU, options);
                    String[] keys = goodsParam.get(0).split(",");
                    for (String key : keys) {
                        key = key.trim();
                        aboutGoods.getChild(goods).setKeyWord(key);
                    }

                    newGoods.add(goods);
                    AllGoods.goods.add(goods);

                    //говорим, что необходимо перестроить индекс, т.к. добавлен новый товар
                    GoodsSelecting exec = new GoodsSelecting();
                    exec.setMakeNewIndex(true);
                    //спрашиваем: еще вводитиь новый товар?
                    Menu myMenu = new Menu();
                    if (!myMenu.answerYesNo()) break;
                }
            }
        }
        //сохраняем товар в файле
        readWriteGoods.writeGoodsList(newGoods, true);
    }
}
