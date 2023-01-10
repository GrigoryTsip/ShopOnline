package ru.netology.selection;

import ru.netology.goods.AboutGoods;
import ru.netology.goods.Goods;
import ru.netology.menu.Menu;

import java.util.*;

import static ru.netology.goods.Goods.*;

//поиск товара для покупки
public class GoodsSelecting implements SelectArray {

    private static boolean makeNewIndex = true; //true - надо строить новый индекс
    //индекс поиска товара по основным атрибутам: наименование, поставщик, ключевые слова
    private static final HashMap<String, ArrayList<Goods>> indexHash = new HashMap<>();

    //индекс поиска товара по идентификатору goodsID
    private static final HashMap<Integer, Goods> indexGoodsID = new HashMap<>();
    protected Menu menu = new Menu();

    // установленные параметры выборки
    protected ArrayList<String> name = new ArrayList<>();
    protected ArrayList<String> keyWords = new ArrayList<>();
    protected ArrayList<String> vendor = new ArrayList<>();
    protected float downPrice;
    protected float upPrice;

    @Override
    public void setMakeNewIndex(boolean ind) {
        makeNewIndex = ind;
    }

    @Override
    public void makeHash(ArrayList<Goods> goods) {

        if (makeNewIndex) {

            AboutGoods aboutGoods = new AboutGoods();
            indexHash.clear();

            // index by name and vendor
            ArrayList<Goods> newGoods = (ArrayList<Goods>) goods.clone();
            while (!newGoods.isEmpty()) {
                Goods neg = newGoods.get(0);
                String goodsName = neg.getGoodsName();

                ArrayList<Goods> index = new ArrayList<>();
                for (Goods ngd : newGoods) {
                    if (ngd.getGoodsName().equals(goodsName)) index.add(ngd);
                }
                newGoods.removeAll(index);
                indexHash.put(goodsName.toLowerCase(Locale.ROOT), index);
            }

            newGoods = (ArrayList<Goods>) goods.clone();
            while (!newGoods.isEmpty()) {
                AboutGoods neg = aboutGoods.getChild(newGoods.get(0));
                if (neg != null) {
                    String vendorName = neg.getVendor();
                    ArrayList<Goods> index = new ArrayList<>();

                    for (Goods ngd : newGoods) {
                        neg = aboutGoods.getChild(ngd);
                        if (neg.getVendor().equals(vendorName)) index.add(ngd);
                    }
                    newGoods.removeAll(index);
                    indexHash.put(vendorName.toLowerCase(Locale.ROOT), index);
                }
            }

            //by keywords
            newGoods = (ArrayList<Goods>) goods.clone();
            while (!newGoods.isEmpty()) {
                AboutGoods neg = aboutGoods.getChild(newGoods.get(0));
                ArrayList<String> keyWrds = new ArrayList<>();
                if (neg.isKeyWords())  keyWrds = (ArrayList<String>) neg.getKeyWordList().clone();

                while (!keyWrds.isEmpty()) {
                    String key = keyWrds.get(0);
                    ArrayList<Goods> index = new ArrayList<>();

                    if (!indexHash.containsKey(key)) {
                        for (Goods ngd : newGoods) {
                            neg = aboutGoods.getChild(ngd);
                            if (neg.isKeyWords()
                                    && neg.getKeyWordList().contains(key)) index.add(ngd);
                        }
                        indexHash.put(key.toLowerCase(Locale.ROOT), index);
                    }
                    keyWrds.remove(0);
                }
                newGoods.remove(0);
            }

            //by orderID
            indexGoodsID.clear();
            newGoods = (ArrayList<Goods>) goods.clone();
            while (!newGoods.isEmpty()) {
                Goods gds = newGoods.get(0);
                newGoods.remove(0);
                indexGoodsID.put(gds.getGoodsID(), gds);
            }

            makeNewIndex = false;
        }
    }

    @Override
    public boolean setSearchParam(ArrayList<String> param) {

        int[] paramType = {NAME, KEYWORDS, VENDOR, PRICE, PRICE + 10};
        for (int i = 0; i < param.size(); i++) {
            String par = param.get(i);
            if (!par.equals("")) {
                String[] para = par.split(",");
                for (String p : para) {
                    String parameter = p.trim().toLowerCase(Locale.ROOT);
                    switch (paramType[i]) {
                        case NAME -> name.add(parameter);
                        case KEYWORDS -> keyWords.add(parameter);
                        case VENDOR -> vendor.add(parameter);
                        case PRICE -> downPrice = menu.setDigit(parameter, "f").getFloatDidit();
                        case PRICE + 10 -> upPrice = menu.setDigit(parameter, "f").getFloatDidit();
                        default -> throw new IllegalStateException("Unexpected value: " + Arrays.toString(paramType));
                    }
                }
            }
        }
        return (downPrice >= 0) && (upPrice >= 0);
    }

    // выборки, сделанные по одной категории поиска (ключевые слова, имена, производители, цены)
    // объединяются (выборка по "ИЛИ"), а между категориями и исходной выборкой ищется пересечение (выборка по"И")
    @Override
    public void selectBy() {

        ArrayList<Goods> sample = GoodsSort.currentGoodsSample;
        ArrayList<Goods> sampleCategory = new ArrayList<>();

        if (!keyWords.isEmpty()) {
            for (String key : keyWords) {
                if (indexHash.containsKey(key)) {
                    sampleCategory.addAll(indexHash.get(key));
                }
            }
            setIntersection(sample, sampleCategory);
        }

        if (!name.isEmpty()) {
            sampleCategory.clear();
            for (String nam : name) {
                if (indexHash.containsKey(nam)) {
                    sampleCategory.addAll(indexHash.get(nam));
                }
            }
            setIntersection(sample, sampleCategory);
        }

        if (!vendor.isEmpty()) {
            for (String ven : vendor) {
                sampleCategory.clear();
                if (indexHash.containsKey(ven)) {
                    sampleCategory.addAll(indexHash.get(ven));
                }
            }
            setIntersection(sample, sampleCategory);
        }

        if (sample.isEmpty()) sample = (ArrayList<Goods>) AllGoods.goods.clone();

        if (!(downPrice == 0 && upPrice == 0)) {
            upPrice = (upPrice == 0 || upPrice < downPrice) ? (float) 10E10 : upPrice;
            ArrayList<Goods> smp = new ArrayList<>();
            for (Goods gds : sample) {
                float prc = gds.getGoodsPrice();
                if (!(prc >= downPrice && prc <= upPrice)) smp.add(gds);
            }
            sample.removeAll(smp);
        }
    }

    public static Goods getGoodsByID(int goodsID) {
        return (indexGoodsID.isEmpty()) ? null : indexGoodsID.get(goodsID);
    }

    private void setIntersection(ArrayList<Goods> sample, ArrayList<Goods> indexSet) {

        ArrayList<Goods> smp = new ArrayList<>();

        if (!sample.isEmpty()) {
            for (Goods gd : sample) {
                if (!indexSet.contains(gd)) smp.add(gd);
            }
            if (!smp.isEmpty()) sample.removeAll(smp);
        }
    }
}

