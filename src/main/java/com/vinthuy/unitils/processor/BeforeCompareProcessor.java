package com.vinthuy.unitils.processor;

import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.difference.Difference;

import java.util.Map;

public class BeforeCompareProcessor implements Comparator {


    public Map<String, Processor> getProssorMap() {
        return prossorMap;
    }

    public void setProssorMap(Map<String, Processor> prossorMap) {
        this.prossorMap = prossorMap;
    }

    private Map<String,Processor> prossorMap;

    @Override
    public boolean canCompare(Object left, Object right) {
        if(left==null&&right==null){
            return false;
        }
        Object obj = left==null?right:left;
        String classsName = obj.getClass().getName();
        classsName  = classsName.replaceAll("\\$",".");
        if(prossorMap!=null) {
            Processor processor = prossorMap.get(classsName);
            if (processor != null) {
                processor.process(left, right);
            }
        }
        return false;
    }

    @Override
    public Difference compare(Object left, Object right, boolean onlyFirstDifference, ReflectionComparator reflectionComparator) {
        return null;
    }
}
