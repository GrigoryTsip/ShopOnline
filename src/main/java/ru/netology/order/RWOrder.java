package ru.netology.order;

import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public interface RWOrder {

    void writeOrderList(ArrayList<Order> orders);

   void readOrderList() throws ParseException;

   void setGoodsSold();
   void setGoodsRating();
}
