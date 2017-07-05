package org.unitils.core;

import java.util.Iterator;

/**
 * Abstract Collection compare interface.
 * Created by ruiyong.hry
 */
public abstract class Collection implements Comparable {
    public abstract int size();

    public boolean isCollection() {
        return true;
    }

    public abstract Comparable get(int idx);


    public abstract Iterator<Comparable> iterator();
}