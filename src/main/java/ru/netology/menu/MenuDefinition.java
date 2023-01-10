package ru.netology.menu;

import ru.netology.goods.NewGoods;
import ru.netology.goods.Goods;
import ru.netology.order.Ordering;
import ru.netology.selection.*;
import ru.netology.tracking.OrderTracking;

import java.io.IOException;
import java.util.ArrayList;

import static ru.netology.selection.TypeOfSelection.*;

public class MenuDefinition {
    protected ArrayList<String> menuOptions;

    //копия текущей выборки GoodsSort.currentGoodsSample - первоначально весь товар
    public static ArrayList<Goods> goodsCurrentList = new ArrayList<>();
    //для вывода товара - AllGoods gds = new AllGoods();
    //для сортировки массива товаров - GoodsSorting select = new GoodsSorting();
    //для хранения текущей выборки - GoodsSort smpl = new GoodsSort();
    NewGoods newg = new NewGoods();
    Menu myMenu = new Menu();

    public int menuIndex(SystemMenu opt) throws RuntimeException, IOException {

        goodsCurrentList = GoodsSort.currentGoodsSample; // первоначальная выборка - весь товар
        int indexAnswer;

        System.out.println("\n-------------Для выхода из меню наберите 0");

        switch (opt) {

            case MAIN_MENU:
                // Main menu
                menuOptions = new ArrayList<>();
                menuOptions.add("Вводить новый товар");
                menuOptions.add("Показать рейтинг товаров");
                menuOptions.add("Выбрать товар для покупки");

                int indexAnsw = myMenu.execMenu("Что вы собираетесь делать?", menuOptions);
                switch (indexAnsw) {
                    case 1 -> newg.addNewGoods();
                    case 2 -> GoodsSorting.showGoodsRating();
                    case 3 -> GoodsSort.sortGoods();
                }
                indexAnswer = indexAnsw;
                break;

            case SHOWCASE_MENU:
                menuOptions = new ArrayList<>();
                menuOptions.add("Определиться с товаром для покупки");
                menuOptions.add("Показать описание выбранного товара");
                menuOptions.add("Оформить заказ");

                indexAnsw = myMenu.execMenu("Что вы хотите сделать?", menuOptions);

                switch (indexAnsw) {
                    case 1 -> GoodsSort.selectGoodsMenu();
                    case 2 -> AllGoods.showAdvice();
                    case 3 -> Ordering.orderMenu();
                }
                indexAnswer = indexAnsw;
                break;

            case SELECT_GOODS_MENU:
                menuOptions = new ArrayList<>();
                menuOptions.add("Выбрать товар для покупки");
                menuOptions.add("Сортировать выборку товара");
                menuOptions.add("Показать выбранный товар0");
                menuOptions.add("Сбросить выборку товара");

                indexAnsw = myMenu.execMenu("Что вы хотите сделать?", menuOptions);

                switch (indexAnsw) {
                    case 1 -> GoodsSort.selectGoods();
                    case 2 -> GoodsSort.sortGoodsMenu();
                    case 3 -> AllGoods.showGoods(goodsCurrentList);
                    case 4 -> GoodsSort.initialGoodsSample();
                }
                indexAnswer = indexAnsw;
                break;

            case SORTING_MENU:
                menuOptions = new ArrayList<>();
                menuOptions.add("Сортировать по наименованию");
                menuOptions.add("Сортировать по цене");
                menuOptions.add("Сортировать по производителю");

                indexAnsw = myMenu.execMenu("Что вы хотите сделать?", menuOptions);

                switch (indexAnsw) {
                    case 1 -> {
                        ArrayList<Goods> sort = GoodsSorting.sortBy(goodsCurrentList, BY_NAME, true);
                        AllGoods.showGoods(sort);
                    }
                    case 2 -> {
                        ArrayList<Goods> sort = GoodsSorting.sortBy(goodsCurrentList, BY_PRICE, true);
                        AllGoods.showGoods(sort);
                    }
                    case 3 -> {
                        ArrayList<Goods> sort = GoodsSorting.sortBy(goodsCurrentList, BY_VENDOR, true);
                        AllGoods.showGoods(sort);
                    }
                }
                indexAnswer = indexAnsw;
                break;
            case ORDER_MENU:
                menuOptions = new ArrayList<>();
                menuOptions.add("Оформлять заказ");
                menuOptions.add("Доставить заказ");
                menuOptions.add("Показать историю заказов");
                menuOptions.add("Выбрать заказ по номеру");

                indexAnsw = myMenu.execMenu("Что вы хотите сделать?", menuOptions);

                switch (indexAnsw) {
                    case 1 -> Ordering.initOrdering();
                    case 2 -> OrderTracking.orderTracking();
                    case 3 -> Ordering.showOrders();
                    case 4 -> Ordering.orderSearchByNumber();
                }
                indexAnswer = indexAnsw;
                break;
            case ORDER_SEARCH_MENU:
                menuOptions = new ArrayList<>();
                menuOptions.add("Вернуть заказ");
                menuOptions.add("Повторить заказ");

                indexAnsw = myMenu.execMenu("Что вы хотите сделать?", menuOptions);

                switch (indexAnsw) {
                    case 1 -> Ordering.orderReturn();
                    case 2 -> Ordering.orderRepeat();
                }
                indexAnswer = indexAnsw;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opt);
        }
        return indexAnswer;
    }

    public ArrayList<String> menuList(SystemMenu opt, ArrayList<String> menuOptions) throws
            IllegalStateException {

        ArrayList<String> listAnswer;

        switch (opt) {
            case NEW_GOODS_MENU -> listAnswer = myMenu.stringMenu("Введите товар", menuOptions);
            case KEYWORDS_MENU -> listAnswer = myMenu.stringMenu("Введите ключевые слова через запятую", menuOptions);
            case EXIT_MENU -> listAnswer = myMenu.stringMenu("Продолжить?", menuOptions);
            case SELECT_GOODS_MENU ->
                    listAnswer = myMenu.stringMenu("Введите параметры поиска. Если параметров одного вида несколько, вводите через запятую", menuOptions);
            case ORDER_MENU -> listAnswer = myMenu.stringMenu("Введите данные для заказа", menuOptions);
            case QUANTITY_ORDER_GOODS_MENU -> listAnswer = myMenu.stringMenu("Введите количество товара", menuOptions);
            case PAYMENT_MENU -> listAnswer = myMenu.stringMenu("Введите сумму оплаты", menuOptions);
            case ORDER_SEARCH_MENU ->
                    listAnswer = myMenu.stringMenu("Введите номер заказа, который ищете", menuOptions);

            default -> throw new IllegalStateException("Unexpected value: " + opt);
        }
        return listAnswer;
    }
}





