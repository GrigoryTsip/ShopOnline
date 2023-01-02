package ru.netology.selection;

import ru.netology.goods.Goods;
import java.util.*;
import static ru.netology.goods.Goods.*;

//поиск товара для покупки
public class GoodsSelecting implements SelectArray {

    private static boolean makeNewIndex = true; //true - надо строить новый индекс
    private static HashMap<String, ArrayList<Goods>> indexHash = new HashMap<>();

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
                for (Goods ng : index) newGoods.remove(ng);
                indexHash.put(goodsName, index);
            }

            newGoods = (ArrayList<Goods>) goods.clone();
            while (!newGoods.isEmpty()) {
                Goods neg = newGoods.get(0);
                String vendorName = neg.getVendor();

                ArrayList<Goods> index = new ArrayList<>();
                for (Goods ngd : newGoods) {
                    if (ngd.getVendor().equals(vendorName)) index.add(ngd);
                }
                for (Goods ng : index) newGoods.remove(ng);
                indexHash.put(vendorName, index);
            }

            //by keywords
            newGoods = (ArrayList<Goods>) goods.clone();
            while (!newGoods.isEmpty()) {
                Goods neg = newGoods.get(0);
                ArrayList<String> keyWrds = neg.getKeyWordList();

                while (!keyWrds.isEmpty()) {
                    String key = keyWrds.get(0);
                    ArrayList<Goods> index = new ArrayList<>();

                    if (!indexHash.containsKey(key)) {
                        for (Goods ngd : newGoods) {
                            if (!ngd.getKeyWordList().isEmpty()
                                    && ngd.getKeyWordList().contains(key)) index.add(ngd);
                        }
                        indexHash.put(key, index);
                    }
                    keyWrds.remove(0);
                }
                newGoods.remove(0);
            }
            makeNewIndex = false;
        }
    }

    @Override
    public void setSearchParam(ArrayList<String> param) {

        int[] paramType = {NAME, KEYWORDS, VENDOR, PRICE, PRICE + 10};
        for (int i = 0; i < param.size(); i++) {
            String par = param.get(i);
            if (!par.equals("")) {
                String[] para = par.split(",");
                for (int j = 0; j < para.length; j++) {
                    para[j].trim();
                    switch (paramType[i]) {
                        case NAME:
                            name.add(para[j]);
                            break;
                        case KEYWORDS:
                            keyWords.add(para[j]);
                            break;
                        case VENDOR:
                            vendor.add(para[j]);
                            break;
                        case PRICE:
                            downPrice = Float.parseFloat(para[j]);
                            break;
                        case PRICE + 10:
                            upPrice = Float.parseFloat(para[j]);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + paramType);
                    }
                }
            }

        }
    }

    @Override
    public ArrayList<Goods> selectBy() {

        ArrayList<Goods> sample = new ArrayList<>();

        if (!keyWords.isEmpty()) {
            for (String key : keyWords) {
                if (indexHash.containsKey(key)) {
                    ArrayList<Goods> smp = indexHash.get(key);
                    sample.addAll(smp);
                }
            }
        }

        if (!name.isEmpty()) {
            for (String nam : name) {
                if (indexHash.containsKey(nam)) {
                    ArrayList<Goods> smp = new ArrayList<>();
                    ArrayList<Goods> gds = indexHash.get(nam);

                    if(!sample.isEmpty()) {
                        for (Goods gd : sample) {
                            if (!gds.contains(gd)) smp.add(gd);
                        }
                        if (!smp.isEmpty()) sample.removeAll(smp);
                    } else {
                        sample.addAll(gds);
                    }
                }
            }
        }

        if (!vendor.isEmpty()) {
            for (String ven : vendor) {
                if (indexHash.containsKey(ven)) {
                    ArrayList<Goods> smp = new ArrayList<>();
                    ArrayList<Goods> gds = indexHash.get(ven);

                    if (!sample.isEmpty()) {
                        for (Goods gd : sample) {
                            if (!gds.contains(gd)) smp.add(gd);
                        }
                        if (!smp.isEmpty()) sample.removeAll(smp);
                    } else {
                        sample.addAll(gds);
                    }
                }
            }
        }

        if (sample.isEmpty()) sample = AllGoods.goods;

        if (!(downPrice == 0 && upPrice == 0)) {
            upPrice = (upPrice == 0 || upPrice < downPrice) ? (float) 10E10 : upPrice;
            ArrayList<Goods> smp = new ArrayList<>();
            for (Goods gds : sample) {
                float prc = gds.getGoodsPrice();
                if (!(prc >= downPrice && prc <= upPrice)) smp.add(gds);
            }
            sample.removeAll(smp);
        }
        return sample;
    }
}

