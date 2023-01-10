package ru.netology.goods;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static ru.netology.goods.Goods.*;

public class ReadWriteGoods implements RWGoods {

    public static String fileName = "src/main/resources/goods.csv";

    @Override
    public ArrayList<AboutGoods> readGoodsList() throws CsvValidationException, IOException {

        ArrayList<AboutGoods> goods = new ArrayList<>();
        AboutGoods goodsFromFile;

        try (CSVReader rcsv = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            while ((nextLine = rcsv.readNext()) != null) {
                int goodsID = Integer.parseInt(nextLine[GOODS_ID]);
                float price = Float.parseFloat(nextLine[PRICE]);
                float quantity = Float.parseFloat(nextLine[QUANTITY]);

                goodsFromFile = new AboutGoods(nextLine[NAME], price, nextLine[VENDOR]);
                //количество товара
                goodsFromFile.setNumberOfGoods(quantity);
                goodsFromFile.setGoodsRecommendations(nextLine[RECOMMENDATION]);
                //ключевые слова (если указаны)
                for (int i = KEYWORDS; i < nextLine.length; i++) {
                    if (nextLine[i] != null) goodsFromFile.setKeyWord(nextLine[i]);
                }
                goods.add(goodsFromFile);
            }
        }
        return goods;
    }

    @Override
    public void writeGoodsList(ArrayList<Goods> newGoodsW, boolean orAddRep) {


        ArrayList<AboutGoods> newGoods = new ArrayList<>();
        for (Goods gds : newGoodsW) newGoods.add(gds.getChild(gds));

        try (CSVWriter wcsv = new CSVWriter(new FileWriter(fileName, orAddRep))) {

            for (AboutGoods goodsToFile : newGoods) {
                String[] str = toFileString(goodsToFile).split(",");
                wcsv.writeNext(str, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toFileString(AboutGoods goodsToFile) {

        StringBuilder s = new StringBuilder()
                .append(goodsToFile.getGoodsID()).append(",")
                .append(goodsToFile.getGoodsName()).append(",")
                .append(goodsToFile.getGoodsPrice()).append(",")
                .append(goodsToFile.getVendor()).append(",")
                .append(goodsToFile.numberOfGoods).append(",")
                .append(goodsToFile.getGoodsRecommendations()).append(",");

        for (int i = 0; i < goodsToFile.numberOfKeyWords(); i++) {
            if ((i == 0)) {
                s.append(goodsToFile.getKeyWord(0));
            } else {
                s.append(",").append(goodsToFile.getKeyWord(i));
            }
        }
        return s.toString();
    }
}

