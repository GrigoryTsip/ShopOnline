package ru.netology.selection;

import ru.netology.goods.Goods;
import ru.netology.menu.MenuDefinition;
import ru.netology.menu.SystemMenu;

import java.io.IOException;
import java.util.ArrayList;

import static ru.netology.selection.AllGoods.*;

public class GoodsSort {

    // текущая выборка товара
    public static ArrayList<Goods> currentGoodsSample = new ArrayList<>();

    public static MenuDefinition menu = new MenuDefinition();

    public static void sortGoods() throws IOException {

        initialGoodsSample();
        while (true) {
            if (menu.menuIndex(SystemMenu.SHOWCASE_MENU) == 0) break;
        }
    }

    public static void selectGoodsMenu() throws IOException {
        while (true) {
            if (menu.menuIndex(SystemMenu.SELECT_GOODS_MENU) == 0) break;
        }
    }

    public static void sortGoodsMenu() throws IOException {
        while (true) {
            if (menu.menuIndex(SystemMenu.SORTING_MENU) == 0) break;
        }
    }

    public static void initialGoodsSample() {
        currentGoodsSample = (ArrayList<Goods>) goods.clone();
    }

    //Сформировать выборку товаров для заказа
    public static void selectGoods() {

        ArrayList<String> searchParam = new ArrayList<>();
        ArrayList<String> param;

        GoodsSelecting exec = new GoodsSelecting();

        exec.makeHash(goods);

        searchParam.add("Наименование товара");
        searchParam.add("Ключевые слова");
        searchParam.add("Производитель товара");
        searchParam.add("Цена от");
        searchParam.add("Цена до");

        MenuDefinition menu = new MenuDefinition();
        boolean correctAnswer = false;

        while (!correctAnswer) {
            param = menu.menuList(SystemMenu.SELECT_GOODS_MENU, searchParam);
            correctAnswer = exec.setSearchParam(param);
        }

        exec.selectBy();

        showGoods(currentGoodsSample);
    }
}
