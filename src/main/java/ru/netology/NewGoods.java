package ru.netology;

import com.opencsv.CSVWriter;
import ru.netology.goods.Goods;
import ru.netology.menu.MenuDefinition;
import ru.netology.selection.AllGoods;
import ru.netology.selection.GoodsSelecting;
import ru.netology.selection.SelectArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.netology.Main.fileName;
import static ru.netology.goods.Goods.*;
import static ru.netology.menu.SystemMenu.*;

public class NewGoods implements AddGoods {

    private Goods goods;
    private List<Goods> newGoods = new ArrayList<>();
    private ArrayList<String> options;
    protected MenuDefinition param = new MenuDefinition();

    public void addNewGoods() throws IOException {

        while (true) {
            options = new ArrayList<>();
            options.add("Наименование товара");
            options.add("Цена товара");
            options.add("Производитель товара");
            options.add("Количество товара");
            List<String> goodsParam = param.menuList(NEW_GOODS_MENU, options);


            float price = Float.parseFloat(goodsParam.get(PRICE));
            float quantity = Float.parseFloat(goodsParam.get(QUANTITY));
            goods = new Goods(goodsParam.get(NAME), price, goodsParam.get(VENDOR));
            goods.setNumberOfGoods(quantity);

            options = new ArrayList<>();
            options.add("Ключевые слова");
            goodsParam = param.menuList(KEYWORDS_MENU, options);
            String[] keys = goodsParam.get(0).split(",");
            for (String key : keys) {
                key = key.trim();
                goods.setKeyWord(key);
            }

            newGoods.add(goods);
            AllGoods.goods.add(goods);

            GoodsSelecting exec = new GoodsSelecting();
            exec.setMakeNewIndex(true);

            options = new ArrayList<>();
            options.add("[Y/N]");
            goodsParam = param.menuList(EXIT_MENU, options);
            if (!goodsParam.get(0).equals("y")) break;
        }
        try (CSVWriter wcsv = new CSVWriter(new FileWriter(fileName, true))) {

            for (Goods gds : newGoods) {
                StringBuilder s = new StringBuilder();
                s.append(gds.getGoodsName()).append(",")
                        .append(gds.getGoodsPrice()).append(",")
                        .append(gds.getVendor()).append(",")
                        .append(gds.getNumberOfGoods()).append(",");

                int size = gds.numberOfKeyWords();
                for (int i = 0; i < size; i++) {
                    s.append(gds.getKeyWord(i)).append(" ");
                }


                String[] str = s.toString().split(",");
                wcsv.writeNext(str);
            }
            // wcsv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
