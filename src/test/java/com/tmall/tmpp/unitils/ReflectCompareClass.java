package com.vinthuy.unitils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vinthuy.unitils.classFeild.ClassFeildIngoreTest;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.comparator.impl.ExcludeFieldComparator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReflectCompareClass {

    @Test
    public void testClassIngoreClassFeild() {
        ClassFeildIngoreTest obj1 = new ClassFeildIngoreTest();
        List<String> obj1List = new ArrayList<String>();
        obj1List.add("1");
        obj1.setErrorList(obj1List);
        obj1.setCurrentPage(1);
        ClassFeildIngoreTest obj2 = new ClassFeildIngoreTest();
        List<String> obj2List = new LinkedList<String>();
        obj2List.add("2");
        obj2.setErrorList(obj2List);
        List<Comparator> x = Lists.newArrayList();
        obj1.setCurrentPage(2);
        ExcludeFieldComparator spec = new ExcludeFieldComparator();
        x.add(spec);
        spec.setFilterClassFields(Sets.newHashSet(new String[]{"com.vinthuy.unitils.classFeild.ClassFeildIngoreTest.errorList",

                "com.vinthuy.unitils.classFeild.ClassFeildIngoreTest.currentPage"
        }));
        ReflectionComparator comparator;
        comparator = ReflectionComparatorFactory.createRefectionComparator(x,
                new ReflectionComparatorMode[]{});

        p(comparator.isEqual(obj1, obj2));
//        A a1 = new A(1, "t_1", new String[] { });
//        A a2 = new A(2, "t_2", null);
//        Difference diff = comparator.getDifference(a1, a2, true);
//        assertTrue(diff != null);
//
//        A a3 = new A(3, "t_2", null);
//        diff = comparator.getDifference(a3, a2, true);
//        assertTrue(diff == null);

//        CompareConfig cc=new CompareConfig().ignoreCollOrder().ignoreEmptyCollection();
//        ReflectComparator tool=new ReflectComparator(cc);
//        CompareResult cr= tool.compare(obj1, obj2);
        p(JSON.toJSONString(comparator.getDifference(obj1, obj2)));
    }

    public void p(Object o) {
        System.out.println(o);
    }


}
