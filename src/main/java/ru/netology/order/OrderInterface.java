package ru.netology.order;

import ru.netology.goods.Goods;

import java.util.Date;
import java.util.HashMap;

public interface OrderInterface {

    int getOrderID();

    void setMaxOrderID(int maxID);

    HashMap<Integer, Float> getOrderGoods();

    void setOrderGoods(HashMap<Integer, Float> goodsList);

    float getGoodsQuantity(Goods gds);

    void setGoodsQuantity(Goods gds, float quantity);

    String getOrderCustomer();

    boolean isOrderPaid();

    void setOrderPaid(boolean cond);

    String getDeliveryAddress();

    boolean isOrderCancelled();

    void setOrderCancellation(boolean cancel);

    DeliveryCondition getDeliveryCondition();

    void setDeliveryCondition(DeliveryCondition cond);

    boolean isOrderReturn();

    void setOrderReturn(boolean orderReturn);

    String getOrderData();

    void setOrderData(String data);

    String getOrderReturnDate();

    void setOrderReturnData(String data);

    String getOrderDeliveryData();

    void setOrderDeliveryData(String data);

    String getOrderCancelData();

    void setOrderCancelData(String data);

    Order repeatOrder();

    float returnOrder();

    String toString();

    float getOrderAmount();

    Order getOrderByID(int id);

    String convertDateToString(Date data);
}
