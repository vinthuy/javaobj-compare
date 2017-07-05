package com.vinthuy.unitils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.core.CompareResult;
import com.vinthuy.unitils.core.ReflectComparator;
import com.vinthuy.unitils.processor.BeforeCompareProcessor;
import com.vinthuy.unitils.processor.Processor;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.comparator.impl.ExcludeFieldComparator;
import org.junit.Test;
import org.unitils.reflectionassert.comparator.impl.SpecifyFieldComparator;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.report.impl.DefaultDifferenceReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ReflectCompareTest {

    public static class A {
        public A(long id, String name, String[] strs) {
            this.id = id;
            this.name = name;
            if (strs != null) {
                this.strList = Lists.newArrayList(strs);
            }
        }

        private long id;

        private String name;

        private List<String> strList;
    }

    public static class B {
        public B(long id, String[] strs, A[] aArr) {
            this.id = id;
            this.aList = Lists.newArrayList(aArr);
        }

        private long id;

        private List<String> strList;

        private List<A> aList;
    }

    @Test
    public void test_default() {
        ReflectionComparator comparator = ReflectionComparatorFactory
                .createRefectionComparator(new ReflectionComparatorMode[]{});
        A a1 = new A(1, "t_1", new String[]{"A", "B"});
        A a2 = new A(1, "t_1", new String[]{"A", "D"});
        Difference diff = comparator.getDifference(a1, a2, true);
        p(new DefaultDifferenceReport().createReport(diff));
        if (diff != null) {
            p(String.format("%s\n%s\n%s", diff.getMessage(), diff.getLeftValue(), diff.getRightValue()));
        }
    }

    @Test
    public void test_specfy() {
        List<Comparator> x = Lists.newArrayList();
        SpecifyFieldComparator spec = new SpecifyFieldComparator();
        x.add(spec);
        spec.setFilterClassFields(Sets.newHashSet(new String[]{"com.vinthuy.unitils.ReflectCompareTest.A.name"}));
        ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator(x,
                new ReflectionComparatorMode[]{});
        A a1 = new A(1, "t_1", new String[]{"A", "B"});
        A a2 = new A(2, "t_2", new String[]{"A", "D"});
        Difference diff = comparator.getDifference(a1, a2, true);
        assertTrue(diff != null);

        A a3 = new A(3, "t_2", new String[]{"D", "A"});
        diff = comparator.getDifference(a3, a2, true);
        assertTrue(diff == null);
    }

    @Test
    public void test_exclude_field() {
        List<Comparator> x = Lists.newArrayList();
        ExcludeFieldComparator spec = new ExcludeFieldComparator();
        x.add(spec);
        spec.setFilterClassFields(Sets.newHashSet(new String[]{"com.vinthuy.unitils.ReflectCompareTest.A.id"}));
        ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator(x,
                new ReflectionComparatorMode[]{ReflectionComparatorMode.LENIENT_ORDER});
        A a1 = new A(1, "t_1", new String[]{"A", "B"});
        A a2 = new A(2, "t_2", new String[]{"A", "B"});
        Difference diff = comparator.getDifference(a1, a2, true);
        assertTrue(diff != null);

        A a3 = new A(3, "t_2", new String[]{"B", "A"});
        diff = comparator.getDifference(a3, a2, true);

        assertTrue(diff == null);
    }

    @Test
    public void test_exclude_field_arr() {
        List<Comparator> x = Lists.newArrayList();
        ExcludeFieldComparator spec = new ExcludeFieldComparator();
        x.add(spec);
        spec.setFilterClassFields(Sets.newHashSet(new String[]{"com.vinthuy.unitils.ReflectCompareTest.A.id"}));
        ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator(x,
                new ReflectionComparatorMode[]{});
        A a1 = new A(1, "t_1", new String[]{});
        A a2 = new A(2, "t_2", null);
        Difference diff = comparator.getDifference(a1, a2, true);
        assertTrue(diff != null);

        A a3 = new A(3, "t_2", null);
        diff = comparator.getDifference(a3, a2, true);
        assertTrue(diff == null);
    }


    @Test
    public void test_preprocess() {
        List<Comparator> x = Lists.newArrayList();
        Map<String, Processor> processorMap = Maps.newHashMap();
        processorMap.put("com.vinthuy.unitils.ReflectCompareTest.A", new Processor() {
            @Override
            public void process(Object left, Object right) {
                A t = (A) left;
                A d = (A) right;
                t.id = 0;
                d.id = 0;
                t.name = "";
                d.name = "";
                t.strList = null;
                d.strList = null;
            }
        });
        BeforeCompareProcessor spec = new BeforeCompareProcessor();

        spec.setProssorMap(processorMap);
        x.add(spec);
        ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator(x,
                new ReflectionComparatorMode[]{});
        A a1 = new A(1, "t_1", new String[]{});
        A a2 = new A(2, "t_2", null);
        Difference diff = comparator.getDifference(a1, a2, true);
        assertTrue(diff == null);


    }


    public static class C {
        public List<Long> list = new ArrayList<Long>();
    }

    @Test
    public void test_list_long() {
        CompareConfig cc = new CompareConfig().ignoreCollOrder();
        ReflectComparator tool = new ReflectComparator(cc);
        C c1 = null, c2 = null;
        c1 = new C();
        c2 = new C();
        for (int i = 0; i < 1000; i++) {

            c1.list.add(1000L);
            c2.list.add(i + 0L);
        }
        assertTrue(!tool.compare(c1, c2).isEqual());
    }


    @Test
    public void test_new_api() {
        A obj1 = new A(1, "t_1", new String[]{});
        A obj2 = new A(2, "t_2", null);
        CompareConfig cc = new CompareConfig().ignoreCollOrder().ignoreEmptyCollection();
        ReflectComparator tool = new ReflectComparator(cc);
        CompareResult cr = tool.compare(obj1, obj2);
        p(cr.getSummary());
    }

    @Test
    public void test_sort_coll() {
        final List<String> l1 = new ArrayList<String>();
        l1.add("1");
        l1.add("2");
        List<String> l2 = new ArrayList<String>();
        l2.add("2");
        l2.add("1");
        CompareConfig cc = new CompareConfig();//.ignoreCollOrder();

        cc.setPreCompareProcessorMap(new HashMap<String, Processor>() {{
            put("java.util.ArrayList", new Processor() {
                @Override
                public void process(Object left, Object right) {
                    List<String> list = (List<String>) left;
                    for (String str : list) {
                        str.toString();
                    }
                    //String str = (String) left;
                    //str.intern();
                }
            });
        }});
        ReflectComparator tool = new ReflectComparator(cc);
        CompareResult cr = tool.compare(l1, l2);
        p(cr.getSummary());
        p("l1:" + l1);
        p("l2:" + l2);
    }

    public void p(Object o) {
        System.out.println(o);
    }


}
