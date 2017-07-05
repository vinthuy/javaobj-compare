package com.vinthuy.unitils.test.caze;

import com.vinthuy.unitils.test.DataA;
import com.vinthuy.unitils.core.CompareConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruiyong.hry on 16/6/7.
 */
public class IgnorePathCase extends BaseCase{
    @Override
    public void setUpCompareConfig(CompareConfig compareConfig) {
        List<String> ignoreFields = new ArrayList<String>();

        ignoreFields.add(".score");
        ignoreFields.add(".dataBs");
        compareConfig.setIgnorePaths(ignoreFields);
    }

    @Override
    public void processData(DataA expect, DataA actual) {
        actual.getDataBList().get(0).setScore(-1D);
        actual.getDataBs()[0] = null;
    }
}
