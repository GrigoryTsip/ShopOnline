package ru.netology;

import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.parser.ParseException;
import ru.netology.goods.AboutGoods;
import ru.netology.goods.ReadWriteGoods;
import ru.netology.menu.MenuDefinition;
import ru.netology.order.ReadWriteOrder;
import ru.netology.selection.AllGoods;
import ru.netology.selection.GoodsSelecting;

import java.io.IOException;
import java.util.ArrayList;

import static ru.netology.menu.SystemMenu.MAIN_MENU;
import static ru.netology.order.Order.orderGlobalList;
import static ru.netology.selection.AllGoods.goods;

public class Main {
    /*
    Структурируем программу по пакетам согласно процессу деятельности магазина:
    Goods - товар,
    Selection - выбор товара,
    Order - заказ,
    Tracking - доставка товара,
    Menu - все, что связано с организацией взаимодействия с пользователем.


    Строим код по следующим принципам:
    Magic - все константы необходимые либо именуем, либо определяем соответствующий enumeration.  Все они public,
            а константы еще и final static.
    Single-Responsibility - строго соблюдаем правило: класс - это об одном: о товаре, заказе, сортировке и т.д.
    Open-Closed - для каждого класса основные переменные делаем невидимыми извне, и реализуем все необходимые интерфейсы.
    Liskov substitution - применен один случай наследования: Goods (товар) родительский класс для AboutGoods (дополнительная
            информация о товаре), выдержанный строго по принципу LSP. Считаю такое решение не слишком удачным - лучше было
            бы создать связанный с товаром класс.
    Interface segregation - не всегда выдержано, но выполнено для основных классов: Goods, AboutGoods, Order, Ordering,
            ReadWriteGoods, интерфейсы для классов сортировки и поиска (SelertArray и SortArray).
    Dependency inversion - реализация DIP наиболее последовательно проведена в решении по структуре взаимодействия с
            пользователем (см. ниже).

    Несколько слов о реализации.
        1.  Структура программы определялась двумя обстоятельствами: предметной областью, определившей существенные классы,
            и представлением о взаимодействии с пользователем, определившем структуру кода программы.
        2.  В основе реализации меню лежит класс Menu, содержащим две основные компоненты: обработчиков выбора вариантов
            (execMenu) и ввода значений параметров (stringMenu). Организация же собственно меню (в соответствии с DIP)
            реализована через класс MenuDefinition, который производит вызов необходимых программных компонент.
        3.  Имитация доставки товара (трекинг) выполнена как диалог клиента (служба заказа) и сервера (служба доставки).
            Поэтому для запуска необходимо запустить сервер (модуль Server).
        4.  Для поиска товара реализованы интексы в классе GoodsSelecting. Ключом к для поиска служат наименование товара,
            ключевые слова и производитель. Отдельный индекс сделан для поска товара по ID.
        5.  Справочник товаров хранится в csv-файле, а заказы в json-файле (не спрашивайте почему так решил).
        6.  Хорошее комментирование, как и глубокое тестирование выполнить не удалось - времени не хватило...
    */

    // Из файла goods.csv создаем объекты класса Goods и сохраняем в общедоступной коллекции goods

    public static void main(String[] args) throws IOException, CsvValidationException, ParseException {

        //Скачивам товары
        ReadWriteGoods readGoods = new ReadWriteGoods();
        ArrayList<AboutGoods> aboutGoods;

        aboutGoods = readGoods.readGoodsList();
        goods.addAll(aboutGoods);
        //показываем все товары
        AllGoods.showGoods(goods);

        //Скачиваем заказы
        ReadWriteOrder readOrder = new ReadWriteOrder();

        readOrder.readOrderList();

        //строим необходимые индексы
        GoodsSelecting buildIndex = new GoodsSelecting();
        buildIndex.makeHash(goods);

        //Формируем статистику продаж и рейтинг товаров
        readOrder.setGoodsSold();

        while (true) {
            MenuDefinition myMenu = new MenuDefinition();

            if (myMenu.menuIndex(MAIN_MENU) == 0) break;
        }
        readGoods.writeGoodsList(goods, false);
        readOrder.writeOrderList(orderGlobalList);

        System.out.println("\nРабота программы завершена");
    }
}