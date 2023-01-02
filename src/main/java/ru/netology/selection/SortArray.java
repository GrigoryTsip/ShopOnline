package ru.netology.selection;

import ru.netology.goods.Goods;
import java.util.ArrayList;

public interface SortArray {

    public ArrayList<Goods> sortBy(ArrayList<Goods> listOfGoods,
                                   TypeOfSelection selectBy,
                                   boolean direction) throws NullPointerException;
}
