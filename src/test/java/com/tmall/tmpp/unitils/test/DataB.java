package com.vinthuy.unitils.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruiyong.hry on 16/6/3.
 */
public class DataB {

    String name;
    Long id;
    Double score;
    List<String> list = new ArrayList<String>();
    List<Datac> datacList = new ArrayList<Datac>();

    public DataB(int i) {
        this.name = "dataB" + i;
        this.id = Long.valueOf(i);
        this.score = i / 100.0;
        for (int x = 0; x < i; x++) {
            list.add("ele" + x);
            datacList.add(new Datac(x));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<Datac> getDatacList() {
        return datacList;
    }

    public void setDatacList(List<Datac> datacList) {
        this.datacList = datacList;
    }
}
