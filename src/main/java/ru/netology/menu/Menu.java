package ru.netology.menu;

import java.util.*;

import static ru.netology.menu.SystemMenu.EXIT_MENU;

public class Menu {

    protected float floatDidit;
    protected int integerDigit;

    Scanner scan = new Scanner(System.in);

    public int execMenu(String menuName, ArrayList<String> options) {

        System.out.println(menuName);
        int i = 1;
        for (String opt : options) {
            System.out.println(i + ". " + opt);
            i++;
        }
        int index;
        while (true) {
            System.out.print("Введите номер опции: ");
            String digit = scan.nextLine();
            index = setDigit(digit,"i").integerDigit;
            if (index >= 0) {
                if (index < i) return index;
                System.out.println("Нет такой опции!");
            }
        }
    }

    public ArrayList<String> stringMenu(String menuName, ArrayList<String> options) {

        System.out.print(menuName + "  ");
        ArrayList<String> answ = new ArrayList<>();

        for (String opt : options) {
            if (!options.get(0).equals("")) {
                System.out.println("\n" + opt + ": ");
            }
            answ.add(scan.nextLine());
        }
        return answ;
    }

    public boolean answerYesNo() {
        ArrayList<String> options = new ArrayList<>();
        MenuDefinition param = new MenuDefinition();

        options.add("[Д/Н]");
        ArrayList<String> goodsParam = param.menuList(EXIT_MENU, options);

        String ans = goodsParam.get(0).toLowerCase(Locale.ROOT);
        return ans.contains("y") || ans.contains("д");
    }

    public Menu setDigit(String answ, String type) {
        this.floatDidit = -1F;
        this.integerDigit =-1;
         try {
            switch (type) {
                case "f" -> this.floatDidit = Float.parseFloat(answ);
                case "i" -> this.integerDigit = Integer.parseInt(answ);
            }
        } catch (NumberFormatException ex) {
            System.out.println("=============Некорректный ввод");
        }
        return this;
    }
    public int getIntegerDigit() { return this.integerDigit;}
    public float getFloatDidit() {return this.floatDidit;}
}
