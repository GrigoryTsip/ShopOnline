package ru.netology.menu;

import ru.netology.NewGoods;
import ru.netology.goods.Goods;
import ru.netology.selection.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.netology.selection.AllGoods.goods;
import static ru.netology.selection.TypeOfSelection.*;

public class MenuDefinition {
    private ArrayList<String> menuOptions;
    private int indexAnswer;
    // текущая выборка - первоначально весь товар
    public static ArrayList<Goods> goodsCurentList = new ArrayList<>();
    //для вывода товара
    AllGoods gds = new AllGoods();
    //для сортировки массива товаров
    GoodsSorting select = new GoodsSorting();

    Menu myMenu = new Menu();

    public int menuIndex(SystemMenu opt) throws IllegalStateException, RuntimeException, IOException {

        goodsCurentList = goods; // первоначальная выборка - весь товар

        System.out.println("-------------Для выхода из меню наберите 0");

        switch (opt) {

            case MAIN_MENU:
                // Main menu
                menuOptions = new ArrayList<>();
                menuOptions.add("Вводить новый товар");
                menuOptions.add("Выбрать товар для покупки");

                indexAnswer = myMenu.execMenu("Что вы собираетесь делать?", menuOptions);
                switch (indexAnswer) {
                    case 1:
                        NewGoods newg = new NewGoods();
                        newg.addNewGoods();
                        break;
                    case 2:
                        GoodsSort gsel = new GoodsSort();
                        gsel.sortGoods();
                        break;
                }
                break;
            case NEW_GOODS_MENU:
                break;
            case KEYWORDS_MENU:
                break;
            case EXIT_MENU:
                break;
            case SHOWCASE_MENU:
                menuOptions = new ArrayList<>();
                menuOptions.add("Показать весь товар");
                menuOptions.add("Сортировать по наименованию");
                menuOptions.add("Сортировать по цене");
                menuOptions.add("Сортировать по производителю");
                menuOptions.add("Выбрать товар для покупки");

                indexAnswer = myMenu.execMenu("Что вы хотите сделать?", menuOptions);

                switch (indexAnswer) {
                    case 1:
                        gds.showGoods(goodsCurentList);
                        break;
                    case 2:
                        ArrayList<Goods> sort = select.sortBy(goodsCurentList, BY_NAME, true);
                        gds.showGoods(sort);
                        break;
                    case 3:
                        sort = select.sortBy(goodsCurentList, BY_PRICE, true);
                        gds.showGoods(sort);
                        break;
                    case 4:
                        sort = select.sortBy(goodsCurentList, BY_VENDOR, true);
                        gds.showGoods(sort);
                        break;
                    case 5:
                        GoodsSort gsel = new GoodsSort();
                        gsel.selectGoods();
                        break;
                }
                break;
            case RATING_MENU:
                break;
            case ADVICE_MENU:
                break;
            case ORDER_MENU:
                break;
            case DELIVERY_MENU:
                break;
            case ORDER_REPEAT_MENU:
                break;
            case GOODS_RETURN_MENU:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opt);
        }
        return indexAnswer;
    }

    public ArrayList<String> menuList(SystemMenu opt, ArrayList<String> menuOptions) throws IllegalStateException {

        ArrayList<String> listAnswer = new ArrayList<>();

        switch (opt) {
            case NEW_GOODS_MENU:
                listAnswer = myMenu.stringMenu("Введите товар", menuOptions);
                break;
            case KEYWORDS_MENU:
                listAnswer = myMenu.stringMenu("Введите ключевые слова через запятую", menuOptions);
                break;
            case EXIT_MENU:
                listAnswer = myMenu.stringMenu("Продолжить?", menuOptions);
                break;
            case SELECT_GOODS_MENU:
                listAnswer = myMenu.stringMenu("Введите параметры поиска. Если параметров одношо вида несколько, вводите через запятую", menuOptions);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opt);
        }
        return listAnswer;
    }
}





