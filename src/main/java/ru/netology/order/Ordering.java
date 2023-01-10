package ru.netology.order;

import ru.netology.tracking.OrderTracking;
import ru.netology.goods.AboutGoods;
import ru.netology.goods.Goods;
import ru.netology.menu.Menu;
import ru.netology.menu.MenuDefinition;
import ru.netology.menu.SystemMenu;
import ru.netology.selection.GoodsSelecting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ru.netology.selection.GoodsSort.menu;

public class Ordering {

    protected static Order currentOrder;

    public static void orderMenu() throws IOException {
        while (true) {
            if (menu.menuIndex(SystemMenu.ORDER_MENU) == 0) break;
        }
    }

    public static void initOrdering() {

        ArrayList<String> orderParam = new ArrayList<>();
        ArrayList<String> param;

        orderParam.add("Введите имя заказчика");
        orderParam.add("Введите адрес заказчика");

        MenuDefinition menu = new MenuDefinition();
        param = menu.menuList(SystemMenu.ORDER_MENU, orderParam);
        currentOrder = new Order(param.get(0), param.get(1));

        //Показываем заказ
        System.out.println(currentOrder);

        orderProcessing();

        //сохраняем заказ
        ReadWriteOrder readWriteOrder = new ReadWriteOrder();
        readWriteOrder.writeOrderList(Order.orderGlobalList);
        // для трекинга заказа
        OrderTracking.deliveryOrder = currentOrder;
    }

    public static void orderProcessing() {

        HashMap<Integer, Float> orderGoods = currentOrder.getOrderGoods();
        ArrayList<Goods> zeroGoods = new ArrayList<>();
        ArrayList<String> param = new ArrayList<>();
        ArrayList<String> answ;
        MenuDefinition menu = new MenuDefinition();
        Menu bmenu = new Menu();

        if (!currentOrder.isOrderCancelled() && currentOrder.isOrderReturn()) {
            for (Map.Entry<Integer, Float> entry : orderGoods.entrySet()) {
                AboutGoods gds = (AboutGoods) GoodsSelecting.getGoodsByID(entry.getKey());
                assert gds != null;
                System.out.println(gds.toString(true));

                float quantity = (float) 10E10;
                float number = gds.getChild(gds).getNumberOfGoods();
                while (number < quantity) {
                    param.clear();
                    param.add("");
                    answ = menu.menuList(SystemMenu.QUANTITY_ORDER_GOODS_MENU, param);
                    quantity = bmenu.setDigit(answ.get(0), "f").getFloatDidit();
                    if (quantity > 0 && number < quantity)
                        System.out.println("========На складе нет такого количества!");
                }
                gds.reduceGoodsStock(quantity);
                orderGoods.put(gds.getGoodsID(), quantity);

                if (quantity == 0) zeroGoods.add(gds);
            }

            // удаляем из заказа товары с нулевым количеством
            for (Goods gds : zeroGoods) orderGoods.remove(gds.getGoodsID());

            System.out.println(currentOrder.toString());

            //оформляем заказ
            if (!currentOrder.getOrderGoods().isEmpty()) {
                float quantity;
                float sum = 0F;
                float amnt = currentOrder.getOrderAmount();
                while (true) {
                    answ = menu.menuList(SystemMenu.PAYMENT_MENU, param);
                    quantity = bmenu.setDigit(answ.get(0), "f").getFloatDidit();
                    if (quantity > 0) {
                        sum += quantity;
                        if (sum < amnt) {
                            System.out.println("\nЗаказ №" + currentOrder.getOrderID() + " оплачен не полностью. Необходимо доплатить " + (amnt - sum));
                        } else {
                            break;
                        }
                    }
                }
                System.out.println("\nЗаказ №" + currentOrder.getOrderID() + " оплачен. Сумма оплаты " + sum);

                currentOrder.setOrderPaid(true);
                currentOrder.setDeliveryCondition(DeliveryCondition.ORDER_PROCESSED);

            } else {
                currentOrder.setOrderCancellation(true);
                System.out.println("========В заказе нет товара. Заказ аннулирован");
            }
        } else {
            System.out.println("========Выбранный заказ отменен или возвращен");
        }
    }

    public static void orderSearchByNumber() throws IOException {

        ArrayList<String> param = new ArrayList<>();
        ArrayList<String> answ;
        MenuDefinition menu = new MenuDefinition();
        Menu bmenu = new Menu();

        int orderNumber;
        while (true) {
            param.add("");
            answ = menu.menuList(SystemMenu.ORDER_SEARCH_MENU, param);
            orderNumber = bmenu.setDigit(answ.get(0), "i").getIntegerDigit();
            if (orderNumber > 0) break;
        }
        Order order = orderSearchByID(orderNumber);

        if (!(order == null)) {
            currentOrder = order;
            System.out.println(order);

            // вернуть или повторить найденный заказ
            menu.menuIndex(SystemMenu.ORDER_SEARCH_MENU);

        } else {
            System.out.println("========Заказ с указанным номером не найден");
        }
        // для трекинга заказа
        OrderTracking.deliveryOrder = currentOrder;
    }

    // возвращаем заказ и деньги
    public static void orderReturn() {

        if (currentOrder.isOrderReturn() && !currentOrder.isOrderCancelled()) {
            // возвращаем товар на склад и считаем стоимость заказа
            String s;
            float amount = currentOrder.returnOrder();
            if (amount == 0 || !currentOrder.isOrderPaid()) {
                // отменяем заказ
                currentOrder.setOrderCancellation(true);
                s = "\n========Заказ аннулирован\n";
            } else {
                s = "\n========Заказ аннулирован. Оплата в размере " + amount + " возвращена\n";
            }
            System.out.println(s);

            ArrayList<Order> myOrders = new ArrayList<>();
            myOrders.add(currentOrder);
            showMyOrders(myOrders);
        } else {
            if (currentOrder.isOrderCancelled()) {
                System.out.println("========Заказ уже был аннулирован " + currentOrder.getOrderCancelData());
            } else {
                System.out.println("========Заказ уже был возвращен " + currentOrder.getOrderReturnDate());
            }
        }
    }


    // повторяем заказ
    public static void orderRepeat() {
        currentOrder = currentOrder.repeatOrder();
        ArrayList<Order> myOrders = new ArrayList<>();
        myOrders.add(currentOrder);
        showMyOrders(myOrders);

        orderProcessing();
        //сохраняем заказ
        ReadWriteOrder readWriteOrder = new ReadWriteOrder();
        readWriteOrder.writeOrderList(Order.orderGlobalList);
        // для трекинга заказа
        OrderTracking.deliveryOrder = currentOrder;
    }

    // поиск и вывод заказов; формирование currentOrder
    public static void showOrders() {
        ArrayList<Order> myOrders;
        myOrders = Order.orderGlobalList;
        showMyOrders(myOrders);
    }

    private static void showMyOrders(ArrayList<Order> myOrders) {
        for (Order ord : myOrders) {
            System.out.println(ord.toString() + "\n===========================================");
        }
    }

    public static Order orderSearchByID(int orderID) {
        Order order = null;
        for (Order ord : Order.orderGlobalList) {
            if (ord.getOrderID() == orderID) {
                order = ord;
                break;
            }
        }
        return order;
    }
}