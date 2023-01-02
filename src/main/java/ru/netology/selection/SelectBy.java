package ru.netology.selection;

import ru.netology.goods.Goods;

import java.util.Comparator;

//объявление о типе сортировки: по имени, по производителю, по цене
public class SelectBy {

    private TypeOfSelection seLectBy;
    private boolean initClass = false;

    private SelectBy(TypeOfSelection seLectBy) {
        this.seLectBy = seLectBy;
        this.initClass = true;
    }

    public void setTypeOfSelection(TypeOfSelection seLectBy) {
        if (!this.initClass) new SelectBy(seLectBy);
        this.seLectBy = seLectBy;
    }

    public TypeOfSelection getTypeOfSelection() {
        return this.seLectBy;
    }
}
