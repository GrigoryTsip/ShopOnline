package ru.netology.selection;

import ru.netology.menu.MenuDefinition;
import ru.netology.menu.SystemMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoodsSort {

    MenuDefinition menu = new MenuDefinition();

    public int sortGoods() throws IOException {
        while (true) {
            int index = menu.menuIndex(SystemMenu.SHOWCASE_MENU);
            if (index == 0) return 0;
        }
    }

    public void selectGoods() throws IOException {

        AllGoods goodsCurrentList = new AllGoods();
        ArrayList<String> seachParam = new ArrayList<>();
        ArrayList<String> param;

        GoodsSelecting exec = new GoodsSelecting();

        exec.makeHash(AllGoods.goods);

        seachParam.add("Наименование товара");
        seachParam.add("Ключевые слова");
        seachParam.add("Производитель товара");
        seachParam.add("Цена от");
        seachParam.add("Цена до");

        MenuDefinition menu = new MenuDefinition();
        param = menu.menuList(SystemMenu.SELECT_GOODS_MENU, seachParam);

        exec.setSearchParam(param);

        goodsCurrentList.showGoods(exec.selectBy());
    }
}
