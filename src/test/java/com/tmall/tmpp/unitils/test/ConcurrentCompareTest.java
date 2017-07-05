package com.vinthuy.unitils.test;

import com.alibaba.fastjson.JSON;
import com.vinthuy.unitils.test.caze.BaseCase;
import com.vinthuy.unitils.test.caze.SepcifyJSONCase;
import com.vinthuy.unitils.test.caze.SepcifyKvCase;
import com.vinthuy.unitils.test.caze.SepcifyKvCase2;
import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.core.CompareResult;
import com.vinthuy.unitils.core.ReflectComparator;
import com.vinthuy.unitils.reflectionassert.report.StructureDiff;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertTrue;

/**
 * Created by ruiyong.hry on 16/6/3.
 */
public class ConcurrentCompareTest {


    ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Test
    public void base_test() throws InterruptedException {
        final List<BaseCase> cases = new ArrayList<BaseCase>();
        cases.add(new SepcifyKvCase());
        cases.add(new SepcifyJSONCase());

        test_task(cases);
        Thread.sleep(100000);
    }

    @Test
    public void doConcurrentTest() throws InterruptedException {

        final AtomicInteger executeCount = new AtomicInteger(0);
        final int all = 1;
        final List<BaseCase> cases = new ArrayList<BaseCase>();
//        cases.add(new IgnoreFieldCase());
//        cases.add(new IgnorePathCase());
//        cases.add(new SepcifyJSONCase());
//        cases.add(new SepcifyKvCase());
        cases.add(new SepcifyKvCase2());
        for (int i = 0; i < all; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int c = executeCount.incrementAndGet();
                    if (c % 100 == 0) {
                        System.out.println("test_task@" + c + "--" + all);
                    }
                    try {
                        test_task(cases);
                    }catch (Throwable e){
                        e.printStackTrace(System.err);
                    }
                }
            });
        }

        while (all > executeCount.get()) {
            Thread.sleep(1000);
        }
    }

    public void test_task(List<BaseCase> cases) {


        boolean info = false;

        for (BaseCase baseCase : cases) {
            DataA expect = new DataA(2);
            DataA actual = new DataA(2);
            baseCase.processData(expect, actual);

            CompareConfig cc = new CompareConfig();
            cc.ignoreDateValue();
            ReflectComparator tool = new ReflectComparator(cc);
            CompareResult cr = null;
            try {
                cr = tool.compare(new ValueHolder(expect), new ValueHolder(actual));
            }catch (Exception e){
                e.printStackTrace();;
            }
            //p("processData",cr);
            if (cr.isEqual()) {
                p("data prepare", "invalid data,actual and expect maybe same!");
                p("expect", expect);
                p("actual", actual);
            }

            for (StructureDiff.DiffItem diffItem : cr.getStructureDiff().getDiffItems()) {
                if (diffItem.getPath().equals("value.id")) {
                    assertTrue(Integer.parseInt(diffItem.getLeftValue()) < Integer.parseInt(diffItem.getRightValue()));
                }
            }

            if (info) {
                p("", cr);
                p("cr", cr.getSummary());
            }
            assertTrue(!cr.isEqual());
            baseCase.setUpCompareConfig(cc);
            expect = new DataA(2);
            actual = new DataA(2);
            tool = new ReflectComparator(cc);
            baseCase.processData(expect, actual);

            if (cc.getIgnorePaths() == null) {
                cc.setIgnorePaths(new ArrayList<String>());
            }
            cc.getIgnorePaths().add("value.id");
            cr = tool.compare(new ValueHolder(expect), new ValueHolder(actual));

            if (!cr.isEqual()) {
                p("data compare", "compare fail!");
                p("expect", expect);
                p("actual", actual);
                p("r", cr);
                p("summery", cr.getSummary());
            }
            //p("case end", baseCase.getClass().getName());
            assertTrue(cr.isEqual());
        }
    }

    public void p(String tag, Object o) {
        System.out.println(tag + ":" + (o instanceof String ? o : JSON.toJSONString(o)));
    }


}
