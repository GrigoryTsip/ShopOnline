package ru.netology.selection;

import java.util.Comparator;
import java.util.Map;

public class RatingComparator implements Comparator<Map.Entry<Integer,Float>> {

    @Override
    public int compare(Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Not defined object");
        }
        return o1.getValue().compareTo(o2.getValue());
    }
}
