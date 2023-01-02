package ru.netology.menu;

import java.util.*;

public class Menu {

    Scanner scan = new Scanner(System.in);

     public int execMenu(String menuName, ArrayList<String> options) {

        System.out.println(menuName);
        int i = 1;
        for (String opt : options) {
            System.out.println(i + ". " + opt);
            i++;
        }
        while (true) {
            System.out.print("Введите номер опции: ");
            int index = scan.nextInt();
            if (index >= 0 && index < i) return index;
            System.out.println("Нет такой опции!");
        }
    }

    public ArrayList<String> stringMenu(String menuName, ArrayList<String> options) {

        System.out.println(menuName);
        ArrayList<String> answ = new ArrayList<>();

        for (String opt : options) {
            System.out.println(opt + ": ");
            answ.add(scan.nextLine());
        }
        return answ;
    }

}
