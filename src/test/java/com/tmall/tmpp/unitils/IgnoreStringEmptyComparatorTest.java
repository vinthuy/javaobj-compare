package com.vinthuy.unitils;

import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.core.CompareResult;
import com.vinthuy.unitils.core.ReflectComparator;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.difference.Difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huruiyong on 5/5/16.
 */
public class IgnoreStringEmptyComparatorTest {


    public static class A{

        public A(String name){
            setName(name);
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    @Test
    public void testNullOrStringEmpty() {
//        CompareConfig cc = new CompareConfig().ignoreNullOrStringEmpty();
//        ReflectComparator tool=new ReflectComparator(cc);
//        CompareResult cr= tool.compare(null, "");
//        System.out.println(cr.getSummary());
        A a1=new A("");
        A a2 = new A(null);

        ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator();
        Difference difference = comparator.getDifference(a1,a2);
        assert (difference == null);
    }

    @Test
    public void testNullOrStringEmptyUseConfig() {
        A a1=new A("");
        A a2 = new A(null);
        CompareConfig cc = new CompareConfig().ignoreNullOrStringEmpty();
        ReflectComparator tool=new ReflectComparator(cc);
        CompareResult cr= tool.compare(a1, a2);
        System.out.println(cr.isEqual());

        System.out.println(cr.getSummary());
    }


    @Test
    public void testListMap() {
        List<Map<Object,Object>> lst1 = new ArrayList<Map<Object,Object>>();
        Map<Object,Object> map11 = new HashMap<Object, Object>();
        map11.put("name","");
        map11.put("id",null);
        map11.put("id1","");
        lst1.add(map11);

//        Map<Object,Object> map12 = new HashMap<Object, Object>();
//        map12.put("i1","i1");
//        map12.put("i2","i2");
//        lst1.add(map12);

        List<Map<Object,Object>> lst2 = new ArrayList<Map<Object,Object>>();
        Map<Object,Object> map21 = new HashMap<Object, Object>();
        map21.put("name",null);
        map21.put("id","");
//        map21.put("key","");
        lst2.add(map21);

//        Map<Object,Object> map22 = new HashMap<Object, Object>();
//        map22.put("i1","i1");
//        map22.put("i2","i23");
//        lst2.add(map22);

        CompareConfig cc = new CompareConfig().ignoreNullOrStringEmpty();
        ReflectComparator tool=new ReflectComparator(cc);
        CompareResult cr= tool.compare(lst1, lst2);
        System.out.println(cr.isEqual());

        System.out.println(cr.getSummary());
    }
}
