package com.vinthuy.unitils.test.caze;

import com.vinthuy.unitils.test.DataA;
import com.vinthuy.unitils.test.ValueHolder;
import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.core.CompareResult;
import com.vinthuy.unitils.core.ReflectComparator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Created by ruiyong.hry on 16/6/8.
 */
public class SepcifyKvCase2 extends BaseCase {
    @Override
    public void setUpCompareConfig(CompareConfig compareConfig) {
        List<String> configs = new ArrayList<String>();
        configs.add("value.dataBs.name@{@}&");
        configs.add("value.dataBList.name@{@}&");
        configs.add("value.dataBMap._0.name@{@}&");

        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("value.dataBMap._0.name.b");
        ignorePath.add("value.dataBList.name.b");
        ignorePath.add("value.dataBs.name.b");
        ignorePath.add("value.id");
        compareConfig.setSpecifyPathKvAttribute(configs);
        compareConfig.setIgnorePaths(ignorePath);
    }

    @Override
    public void processData(DataA expect, DataA actual) {
        actual.getDataBList().get(0).setName("a@1&b@2");
        expect.getDataBList().get(0).setName("a@1&b@3");
    }

    @Test
    public void test() {

        BaseCase baseCase = new SepcifyKvCase2();
        DataA expect = new DataA(2);
        DataA actual = new DataA(2);
        baseCase.processData(expect, actual);
        CompareConfig cc = new CompareConfig();
        cc.ignoreDateValue();
        ReflectComparator tool = new ReflectComparator(cc);
        CompareResult cr = null;
        baseCase.setUpCompareConfig(cc);
        cr = tool.compare(new ValueHolder(expect), new ValueHolder(actual));

        System.out.println(cr);
        assertTrue(cr.isEqual());
    }
}
