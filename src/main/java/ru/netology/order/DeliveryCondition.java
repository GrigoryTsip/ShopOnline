package ru.netology.order;

public enum DeliveryCondition {

    ORDER_WILLBE_PLASED ("ORDER_WILLBE_PLASED"),
    ORDER_PROCESSED ("ORDER_PROCESSED"), // оформлен
    ORDER_SENT_FORM ("ORDER_SENT_FORM"), // передан на формирование
    ORDER_FORMED ("ORDER_FORMED"), // сформирован
    ORDER_SENT ("ORDER_SENT"), // в пути
    ORDER_DELIVERED ("ORDER_DELIVERED"), // доставлен
    ORDER_RECEIVED ("ORDER_RECEIVED"); // получен

    DeliveryCondition(String name){}

    public DeliveryCondition getOrderDelivered(String name) {
        return DeliveryCondition.valueOf(name);
    }

    public String getDeliveryConditionName(DeliveryCondition cond) {
        return cond.name();
    }

    public static String toString(DeliveryCondition condition) {

        String s = null;
        switch (condition) {
            case ORDER_WILLBE_PLASED -> s = "Заказ оформляется";
            case ORDER_PROCESSED -> s = "Заказ оформлен и оплачен";
            case ORDER_SENT_FORM -> s = "Заказ передан на формирование";
            case ORDER_FORMED -> s = "Заказ сформирован";
            case ORDER_SENT -> s = "Заказ отправлен";
            case ORDER_DELIVERED -> s = "Заказ доставлен";
            case ORDER_RECEIVED -> s = "Заказ получен";
        }
        return s;
    }
}
