package com.vinthuy.unitils.test;


import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.core.CompareResult;
import com.vinthuy.unitils.core.ReflectComparator;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by ruiyong.hry on 16/6/29.
 */
public class SimpleCompareTest {

    @Test
    public void test() {

        DataA expect = new DataA(2);
        expect.setName("abc");
        DataA actual = new DataA(2);
        actual.setName("def");
        CompareConfig cc = new CompareConfig();
        cc.ignoreDateValue();
        ReflectComparator tool = new ReflectComparator(cc);
        CompareResult cr = null;
        cr = tool.compare(new ValueHolder(expect), new ValueHolder(actual));

        System.out.println(cr);
        assertTrue(cr.isEqual());
    }


    @Test
    public void test_2() {

        DataA expect = new DataA(2);
        expect.setName("abc");
        DataA actual = new DataA(2);
        actual.setName("def");
        CompareConfig cc = new CompareConfig();
        cc.ignoreDateValue();
        ReflectComparator tool = new ReflectComparator(cc);
        CompareResult cr = null;
        cr = tool.compare(new ValueHolder(expect), new ValueHolder(actual));

        System.out.println(cr);
        assertTrue(cr.isEqual());
    }
}
