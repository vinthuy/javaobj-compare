package com.vinthuy.unitils.test;

import java.util.*;

/**
 * Created by ruiyong.hry on 16/6/3.
 */
public class DataA {

    public static long IDX = 0;

    private long id;
    private Map<String, DataB> dataBMap = new HashMap<String, DataB>();
    private List<DataB> dataBList = new ArrayList<DataB>();
    private DataB[] dataBs;
    private Date date = new Date();
    private Set<String> name;


    public String getName() {
        return name+"";
    }

    public void setName(String name) {
        this.name = new HashSet<String>();
        this.name.add(name);
    }

    public DataA(int i) {
        IDX++;
        id = IDX;
        dataBs = new DataB[i];
        for (int x = 0; x < i; x++) {
            DataB dataB = new DataB(i);
            dataBList.add(dataB);
            dataBs[x] = dataB;
            dataBMap.put("_" + x, dataB);
        }

    }

    public Map<String, DataB> getDataBMap() {
        return dataBMap;
    }

    public void setDataBMap(Map<String, DataB> dataBMap) {
        this.dataBMap = dataBMap;
    }

    public List<DataB> getDataBList() {
        return dataBList;
    }

    public void setDataBList(List<DataB> dataBList) {
        this.dataBList = dataBList;
    }

    public DataB[] getDataBs() {
        return dataBs;
    }

    public void setDataBs(DataB[] dataBs) {
        this.dataBs = dataBs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
