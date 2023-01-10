package ru.netology.order;

import ru.netology.goods.AboutGoods;
import ru.netology.goods.Goods;
import ru.netology.selection.GoodsSelecting;
import ru.netology.selection.GoodsSort;

import java.text.SimpleDateFormat;
import java.util.*;

import static ru.netology.order.DeliveryCondition.ORDER_RECEIVED;
import static ru.netology.order.DeliveryCondition.ORDER_WILLBE_PLASED;

public class Order implements OrderInterface {

    protected static int nextOrderID = 1;
    protected int orderID;
    protected HashMap<Integer, Float> orderGoods;
    protected String customer;
    protected String address;
    protected boolean paid;
    protected DeliveryCondition delivery;
    protected boolean returned;
    protected boolean canceled;
    protected String orderData;
    protected String returnData;
    protected String deliveryData;
    protected String cancellationData;

    protected float amount;

    public static ArrayList<Order> orderGlobalList = new ArrayList<>();

    public static HashMap<Integer, Order> orderIndex = new HashMap<>();

    public static final String[] orderColumnMapping = {
            "orderID", "orderGoods", "customer",
            "address", "paid", "delivery",
            "returned", "canceled", "orderData",
            "returnData", "deliveryData", "cancellationData"
    };

    public Order() {
    }

    public Order(String customer, String address) {
        this.orderID = nextOrderID++;
        this.customer = customer;
        this.address = address;
        this.orderData = convertDateToString(new GregorianCalendar().getTime());
        this.delivery = ORDER_WILLBE_PLASED;

        orderIndex.put(this.orderID,this);

        initGoodsAttr();
    }

    public Order(int orderID, String customer, String address) {
        this.orderID = orderID;
        this.customer = customer;
        this.address = address;

        orderIndex.put(this.orderID,this);

        initGoodsAttr();
    }

    private void initGoodsAttr() {
        this.orderGoods = new HashMap<>();
        for (Goods gds : GoodsSort.currentGoodsSample) this.orderGoods.put(gds.getGoodsID(), 0F);

        this.orderData = "";
        this.returnData = "";
        this.deliveryData = "";
        this.cancellationData = "";

        orderGlobalList.add(this);
    }

    @Override
    public int getOrderID() {
        return this.orderID;
    }

    @Override
    public void setMaxOrderID(int maxID) {
        nextOrderID = maxID;
    }

    @Override
    public HashMap<Integer, Float> getOrderGoods() {
        return this.orderGoods;
    }

    @Override
    public void setOrderGoods(HashMap<Integer, Float> goodsList) {
        this.orderGoods.putAll(goodsList);
    }

    @Override
    public float getGoodsQuantity(Goods gds) {
        return this.orderGoods.get(gds.getGoodsID());
    }

    @Override
    public void setGoodsQuantity(Goods gds, float quantity) {
        this.orderGoods.put(gds.getGoodsID(), quantity);
    }

    @Override
    public String getOrderCustomer() {
        return this.customer;
    }

    @Override
    public boolean isOrderPaid() {
        return this.paid;
    }

    @Override
    public void setOrderPaid(boolean cond) {
        this.paid = cond;
    }

    @Override
    public String getDeliveryAddress() {
        return this.address;
    }

    @Override
    public boolean isOrderCancelled() {
        return this.canceled;
    }

    @Override
    public void setOrderCancellation(boolean cancel) {
        this.cancellationData = convertDateToString(new GregorianCalendar().getTime());
        this.canceled = cancel;
    }

    @Override
    public DeliveryCondition getDeliveryCondition() {
        return this.delivery;
    }

    @Override
    public void setDeliveryCondition(DeliveryCondition cond) {
        if (cond.equals(ORDER_RECEIVED))
            this.deliveryData = convertDateToString(new GregorianCalendar().getTime());
        this.delivery = cond;
    }

    @Override
    public boolean isOrderReturn() {
        return !this.returned;
    }

    @Override
    public void setOrderReturn(boolean orderReturn) {
        this.returnData = convertDateToString(new GregorianCalendar().getTime());
        this.returned = orderReturn;
    }

    @Override
    public String getOrderData() {
        return this.orderData;
    }

    @Override
    public void setOrderData(String data) {
        this.orderData = data;
    }

    @Override
    public String getOrderReturnDate() {
        return this.returnData;
    }

    @Override
    public void setOrderReturnData(String data) {
        this.returnData = data;
    }

    @Override
    public String getOrderDeliveryData() {
        return this.deliveryData;
    }

    @Override
    public void setOrderDeliveryData(String data) {
        this.deliveryData = data;
    }

    @Override
    public String getOrderCancelData() {
        return this.cancellationData;
    }

    @Override
    public void setOrderCancelData(String data) {
        this.cancellationData = data;
    }

    @Override
    public Order repeatOrder() {
        Order repOrder = new Order(this.customer, this.address);
        repOrder.orderGoods = (HashMap<Integer, Float>) this.orderGoods.clone();
        //обнуляем количество товара
        for (Map.Entry<Integer, Float> entry : this.orderGoods.entrySet()) {
            repOrder.orderGoods.put(entry.getKey(), 0F);
        }
        return repOrder;
    }

    @Override
    public float returnOrder() {
        float amount = 0;
        this.setOrderReturn(true);

        for (Map.Entry<Integer, Float> entry : this.orderGoods.entrySet()) {
            int id = entry.getKey();
            Goods gds = GoodsSelecting.getGoodsByID(id);
            float quantity = entry.getValue();

            assert gds != null;
            AboutGoods aboutGoods = gds.getChild(gds);
            aboutGoods.increaseGoodsStock(quantity);
            aboutGoods.setGoodsSold(aboutGoods.getGoodsSold() - quantity);

            amount += gds.getGoodsPrice() * quantity;
        }
        return amount; // стоимость возвращаемого заказа
    }


    public String toString() {

        StringBuilder s = new StringBuilder("\nЗАКАЗ № ")
                .append(this.orderID).append("\n")
                .append("Время заказа: ").append(this.orderData).append("\n")
                .append("Заказчик: ").append(this.customer).append("\n")
                .append("Адрес доставки: ").append(this.address)
                .append("\nСписок товаров:\n");

        for (Map.Entry<Integer, Float> entry : this.orderGoods.entrySet()) {
            int id = entry.getKey();
            AboutGoods gds = (AboutGoods) GoodsSelecting.getGoodsByID(id);
            float quantity = entry.getValue();

            assert gds != null;
            s.append(gds.toString(true)).append("\t\tКоличество: ")
                    .append(quantity).append("\n");
        }
        s.append("\nСтоимость заказа: ").append(getOrderAmount()).append(" руб\n");

        if (this.canceled) {
            s.append("\nЗаказ отменен и расформирован ")
                    .append(this.cancellationData).append("\n");
        } else {
            s.append("Состояние оплаты: ");
            if (this.paid) {
                s.append("Оплачен\n");
            } else {
                s.append("Не оплачен\n");
            }

            if (this.delivery != null) {
                assert false;
                s.append("Состояние доставки: ").append(DeliveryCondition.toString(this.delivery)).append("\n");
            }

            assert false;
            if (this.delivery.equals(ORDER_RECEIVED)) {
                s.append("Дата доставки заказа: ")
                        .append(this.deliveryData).append("\n");
            }
            if (this.returned) {
                s.append("\nЗаказ возвращен. Дата возврата: ")
                        .append(this.returnData).append("\n");
            }
        }
        return s.toString();
    }

    public float getOrderAmount() {

        this.amount = 0F;

        for (Map.Entry<Integer, Float> entry : this.orderGoods.entrySet()) {
            int id = entry.getKey();
            AboutGoods gds = (AboutGoods) GoodsSelecting.getGoodsByID(id);
            float quantity = entry.getValue();

            assert gds != null;
            this.amount += gds.getGoodsPrice() * quantity;
        }
        return this.amount;
    }

    @Override
    public Order getOrderByID(int id) {
        return orderIndex.get(id);
    }

    public String convertDateToString(Date data) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return formatter.format(data);
    }
}

