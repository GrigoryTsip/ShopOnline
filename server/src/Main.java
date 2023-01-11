import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;
import static java.net.SocketOptions.SO_REUSEADDR;
import static ru.netology.order.DeliveryCondition.*;

public class Main {
    public static void main(String[] args) throws IOException {

        out.println("Server started");
        int port = 8089;
        String inquiry;

        try (ServerSocket serverSocket = new ServerSocket(port, SO_REUSEADDR)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    inquiry = in.readLine(); // Поступил заказ № . Примите к доставке
                    inquiry = in.readLine(); // order.id
                    int orderNumber = Integer.parseInt(inquiry);

                    out.println("Принято! Заказ №" + orderNumber + " получен на формирование");
                    out.println(ORDER_SENT_FORM);
                    //
                    out.println("Заказ №" + orderNumber + " сформиован. готовимся к отправке");
                    out.println(ORDER_FORMED);
                    //
                    inquiry = in.readLine(); // Принято! Заказ сформирован и готов к отправке
                    out.println("Заказ №" + orderNumber + " отправлен");
                    out.println(ORDER_SENT);
                    //
                    inquiry = in.readLine(); // Принято! Заказ доставлен
                    out.println("Заказ №" + orderNumber + " доставлен");
                    out.println(ORDER_DELIVERED);
                    //
                    inquiry = in.readLine(); // Принято! Заказ доставлен
                    out.println("Заказ №" + orderNumber + " получен");
                    out.println(ORDER_RECEIVED);
                    //
                    inquiry = in.readLine(); // Принято! Заказ получен

                    //clientSocket.close();
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}