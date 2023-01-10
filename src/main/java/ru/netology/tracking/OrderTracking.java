package ru.netology.tracking;

import ru.netology.menu.Menu;
import ru.netology.order.DeliveryCondition;
import ru.netology.order.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OrderTracking {

    public static Order deliveryOrder = null;

    public static void orderTracking() {

        String host = "netology.homework";
        int port = 8089;
        String response;

        //
        Menu menu = new Menu();

        if ((deliveryOrder != null)
                && (!deliveryOrder.isOrderCancelled()
                || deliveryOrder.isOrderReturn()
                || !deliveryOrder.getDeliveryCondition().equals(DeliveryCondition.ORDER_PROCESSED))) {

            System.out.print("\n---------Для доставки заказа необходимо запустить сервер. Сервер запущен? ");
            if (menu.answerYesNo()) {

                try (Socket clientSocket = new Socket(host, port);
                     PrintWriter out = new
                             PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new
                             InputStreamReader(clientSocket.getInputStream()))) {

                    //
                    String inquiry = "Поступил заказ №" + deliveryOrder.getOrderID() + ". Примите к доставке";
                    out.println(inquiry);
                    out.println(deliveryOrder.getOrderID());
                    System.out.println("\nСлужба заказа: " + inquiry);
                    DeliveryCondition condition = deliveryOrder.getDeliveryCondition();
                    writeOrderCondition(condition.name());

                    // Thread.sleep(1000);
                    response = in.readLine(); // Принято! Зкаказ № получен на формирование
                    System.out.println("Служба доставки: " + response);
                    response = in.readLine(); //состояние заказа
                    writeOrderCondition(response);
                    //
                    response = in.readLine(); // Заказ № сформирован. готовимся к отправке
                    System.out.println("Служба доставки: " + response);
                    response = in.readLine(); //состояние заказа
                    writeOrderCondition(response);

                    inquiry = "Принято! Заказ сформирован и готов к отправке";
                    out.println(inquiry);
                    System.out.println("Служба заказа: Принято! Заказ сформирован и готов к отправке");

                    //
                    response = in.readLine(); // Зкаказ № отправлен
                    System.out.println("Служба доставки: " + response);
                    response = in.readLine(); //состояние заказа
                    writeOrderCondition(response);

                    inquiry = "Принято! Заказ отправлен";
                    out.println(inquiry);
                    System.out.println("Служба заказа: Принято! Заказ отправлен");

                    //
                    response = in.readLine(); // Зкаказ № доставлен
                    System.out.println("Служба доставки: " + response);
                    response = in.readLine(); //состояние заказа
                    writeOrderCondition(response);

                    inquiry = "Принято! Заказ доставлен";
                    out.println(inquiry);
                    System.out.println("Служба заказа: Принято! Заказ доставлен");

                    //
                    response = in.readLine(); // Зкаказ № получен
                    System.out.println("Служба доставки: " + response);
                    response = in.readLine(); //состояние заказа
                    writeOrderCondition(response);

                    inquiry = "Принято! Заказ получен " + deliveryOrder.getOrderDeliveryData();
                    out.println(inquiry);
                    System.out.println("Служба заказа: " + inquiry + "\n\n");

                    //чтобы избежать повторной отправки
                    //deliveryOrder = null;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("==============Нет заказа для доставки - выберите заказ");
        }
    }

    private static void writeOrderCondition(String condition) {

        DeliveryCondition cond = DeliveryCondition.ORDER_PROCESSED;

        deliveryOrder.setDeliveryCondition(cond.getOrderDelivered(condition));
        System.out.println("----------Состояние заказа №"
                + deliveryOrder.getOrderID() +
                ": " + condition);
    }
}
