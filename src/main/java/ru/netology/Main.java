package ru.netology;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ru.netology.goods.Goods;
import ru.netology.menu.MenuDefinition;
import ru.netology.selection.AllGoods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static ru.netology.menu.SystemMenu.MAIN_MENU;
import static ru.netology.selection.AllGoods.goods;

public class Main {
    /*
    Структурируем программу по пакетам согласно процессу деятельности магазина:
    Goods - товар,
    Stock - товары на складе,
    Showcase - представление товара (витрина),
    Selection - выбор товара,
    Order - заказ,
    Delivery - доставка товара,
    Accounting - учет товара,
    Advice - рейтинг товаров и рекомендации.

    Строим код по следующим принципам:
    Magic - все константы необходимые либо именуем, либо определяем соответствующий enumeration.  Все они public,
            а константы еще и final static.
    Single-Responsibility - строго соблюдаем правило: класс - это об одном: о товаре, витрине, сортировке и т.д.
    Open-Closed - для каждого класса переменные делаем либо privat, либо protected, и реализуем все необходимые интерфейсы.
    Liskov substitution - .
    Interface segregation -
    Dependency inversion
    */

    public static String fileName = "src/main/resources/goods.csv";

    // Из файла goods.csv создаем объекты класса Goods и сохраняем в общедоступной коллекции goods

    public static void main(String[] args) throws IOException {

        AllGoods.goods = parseCSV(fileName);
        AllGoods gds = new AllGoods();
        gds.showGoods(goods);

        while (true) {
            MenuDefinition myMenu = new MenuDefinition();

            if (myMenu.menuIndex(MAIN_MENU) == 0) break;
        }
        System.out.println("\nРабота программы завершена");
    }

    public static String readString(String fileName) {

        StringBuilder jString = new StringBuilder();

        try (BufferedReader jsonFile = new BufferedReader((new FileReader(fileName)))) {
            String s;
            while ((s = jsonFile.readLine()) != null) jString.append(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return jString.toString();
    }

    public static ArrayList<Goods> parseCSV(String fileName) {

        ArrayList<Goods> goods = new ArrayList<>();
        Goods gds;

        try (CSVReader rcsv = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            while ((nextLine = rcsv.readNext()) != null) {
                float price = Float.parseFloat(nextLine[1]);
                float quantity = Float.parseFloat(nextLine[3]);
                gds = new Goods(nextLine[0], price, nextLine[2]);
                //количесево товара
                gds.setNumberOfGoods(quantity);
                //ключевые слова (если указаны)
                for (int i = 4; i < nextLine.length; i++) {
                    if (nextLine[i] != null) gds.setKeyWord(nextLine[i]);
                }
                goods.add(gds);
            }
            return goods;
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}