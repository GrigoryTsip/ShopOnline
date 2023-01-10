package ru.netology.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.netology.goods.AboutGoods;
import ru.netology.goods.Goods;
import ru.netology.selection.GoodsSelecting;
import ru.netology.selection.GoodsSorting;
import ru.netology.selection.RatingComparator;

import java.io.*;
import java.util.*;

import static ru.netology.order.DeliveryCondition.ORDER_PROCESSED;
import static ru.netology.order.Order.orderGlobalList;

public class ReadWriteOrder implements RWOrder {

    static Comparator<Map.Entry<Integer, Float>> forRating = new RatingComparator();

    protected final String fileName = "src/main/resources/orders.json";
    protected int maxOrderID = 0;

    @Override
    public void writeOrderList(ArrayList<Order> orders) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .create();

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(gson.toJson(orders));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readOrderList() throws ParseException {


        StringBuilder jString = new StringBuilder();


        orderGlobalList.clear(); // очищаем список заказов в Order

        int orderID = 0;
        HashMap<Integer, Float> orderGoods = null;
        String customer = null;
        String address = null;
        boolean paid = false;
        DeliveryCondition delivery = ORDER_PROCESSED;
        boolean returned = false;
        boolean canceled = false;
        String orderData = null;
        String returnData = null;
        String deliveryData = null;
        String cancellationData = null;

        try (BufferedReader jsonFile = new BufferedReader((new FileReader(fileName)))) {
            String s;
            while ((s = jsonFile.readLine()) != null) jString.append(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // разбираем строку на объекты классов AboutGoods и Order
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jString.toString());
        JSONArray jsonObj = (JSONArray) obj;

        for (Object o : jsonObj) {

            JSONObject jsonElement = (JSONObject) o;
            for (String orderAttrName : Order.orderColumnMapping) {
                String attr = String.valueOf(jsonElement.get(orderAttrName));
                switch (orderAttrName) {
                    case "orderID" -> orderID = classIDValue(attr);
                    case "orderGoods" -> orderGoods = readGoods(attr);
                    case "customer" -> customer = attr;
                    case "address" -> address = attr;
                    case "paid" -> paid = Boolean.parseBoolean(attr);
                    case "delivery" -> delivery = delivery.getOrderDelivered(attr);
                    case "returned" -> returned = Boolean.parseBoolean(attr);
                    case "canceled" -> canceled = Boolean.parseBoolean(attr);
                    case "orderData" -> orderData = attr;
                    case "returnData" -> returnData = attr;
                    case "deliveryData" -> deliveryData = attr;
                    case "cancellationData" -> cancellationData = attr;
                    default -> throw new IllegalStateException("Unexpected value: " + orderAttrName);
                }
            }
            Order newOrder = new Order(orderID, customer, address);
            newOrder.setOrderGoods(orderGoods);
            newOrder.setOrderPaid(paid);
            newOrder.setDeliveryCondition(delivery);
            newOrder.setOrderReturn(returned);
            newOrder.setOrderCancellation(canceled);
            newOrder.setOrderData(orderData);
            newOrder.setOrderReturnData(returnData);
            newOrder.setOrderDeliveryData(deliveryData);
            newOrder.setOrderCancelData(cancellationData);

            newOrder.setMaxOrderID(maxOrderID + 1);
        }
    }

    @Override
    public void setGoodsSold() {


        HashMap<Integer, Float> soldList = new HashMap<>();

        for (Order order : orderGlobalList) {
            HashMap<Integer, Float> goodsList = order.getOrderGoods();
            if (!order.isOrderCancelled() && order.isOrderReturn()) {
                for (Map.Entry<Integer, Float> entry : goodsList.entrySet()) {
                    Goods gds = GoodsSelecting.getGoodsByID(entry.getKey());
                    if (gds != null) {
                        AboutGoods agds = gds.getChild(gds);
                        Float goodsOrderSold = entry.getValue();
                        Float goodsSold = agds.getGoodsSold();
                        if (goodsSold == 0) {
                            goodsSold = goodsOrderSold;
                        } else {
                            goodsSold += goodsOrderSold;
                        }
                        agds.setGoodsSold(goodsSold);
                        if (goodsSold != 0) soldList.put(agds.getGoodsID(), goodsSold);
                    }
                }
            }
        }
        GoodsSorting.goodsRating = new ArrayList<>();
        GoodsSorting.goodsRating.addAll(soldList.entrySet());
        setGoodsRating();
    }

    @Override
    public void setGoodsRating() {

        GoodsSorting.goodsRating.sort(forRating.reversed());

        for (int i = 0; i < GoodsSorting.goodsRating.size(); i++) {
            Map.Entry<Integer, Float> entry = GoodsSorting.goodsRating.get(i);
            Goods gds = GoodsSelecting.getGoodsByID(entry.getKey());
            assert gds != null;
            gds.getChild(gds).setGoodsRating(i + 1);
        }
    }

    private HashMap<Integer, Float> readGoods(String jsonElement) throws ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonElement);
        HashMap<String, Double> jsonMap = (HashMap<String, Double>) obj;
        HashMap<Integer, Float> goodsMap = new HashMap<>();

        for (Map.Entry<String, Double> entry : jsonMap.entrySet()) {
            int id = Integer.parseInt(entry.getKey());
            double qn = entry.getValue();
            float quan = (float) qn;
            goodsMap.put(id, quan);
        }
        return goodsMap;
    }

    private int classIDValue(String id) {
        int orderID = Integer.parseInt(id);
        maxOrderID = Math.max(maxOrderID, orderID);
        return orderID;
    }
}
