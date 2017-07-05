package com.vinthuy.unitils.test;

/**
 * Created by ruiyong.hry on 16/6/6.
 */
public class ValueHolder {

    public ValueHolder(Object value) {
        this.value = value;
    }

    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
