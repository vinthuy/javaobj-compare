package com.vinthuy.unitils.test.caze;

import com.vinthuy.unitils.test.DataA;
import com.vinthuy.unitils.core.CompareConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruiyong.hry on 16/6/8.
 */
public class SepcifyKvCase extends BaseCase {
    @Override
    public void setUpCompareConfig(CompareConfig compareConfig) {
        List<String> configs = new ArrayList<String>();
        configs.add("value.dataBs.name@:;");
        configs.add("value.dataBList.name@:;");
        configs.add("value.dataBMap._0.name@:;");

        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("value.dataBMap._0.name.b");
        ignorePath.add("value.dataBList.name.b");
        ignorePath.add("value.dataBs.name.b");
        compareConfig.setSpecifyPathKvAttribute(configs);
        compareConfig.setIgnorePaths(ignorePath);
    }

    @Override
    public void processData(DataA expect, DataA actual) {
        actual.getDataBList().get(0).setName("a:1;b:2");
        expect.getDataBList().get(0).setName("a:1;b:3");
    }
}
