package ru.netology.goods;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;

public interface RWGoods {

    ArrayList<AboutGoods> readGoodsList() throws CsvValidationException, IOException;

    void writeGoodsList(ArrayList<Goods> newGoods, boolean orAddRep);
}