package com.vinthuy.unitils.test;

/**
 * Created by ruiyong.hry on 16/6/3.
 */
public class Datac {

    public Datac(int id) {
        this.id = Long.valueOf(id);
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
