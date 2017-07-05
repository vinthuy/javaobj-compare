package org.unitils.reflectionassert.comparator.impl;

import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.difference.Difference;

import java.util.Collection;

/**
 *  ºöÂÔ¿ÕÅÅ³ý
 */
public class IgnoreEmptyCollectionComparator implements Comparator {

    @Override
    public boolean canCompare(Object left, Object right) {
        if(left==null && right==null){
            return false;
        }
        if(left!=null && right!=null){
                return false;
        }
        Object val = left==null?right:left;
        if(val.getClass().isArray()){
            Object[] arr = (Object[])val;
            if(arr.length==0){
                return true;
            }
        }


        if(val instanceof Collection){
            Collection coll = (Collection)val;
            if(coll.size()==0){
                return true;
            }
        }
         return false;
    }

    @Override
    public Difference compare(Object left, Object right, boolean onlyFirstDifference, ReflectionComparator reflectionComparator) {
        return null;
    }

    public static void main(String[] args) {
        byte[] test =new byte[100];
        System.out.println();
    }
}
