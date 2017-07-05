package com.vinthuy.unitils.test.caze;

import com.vinthuy.unitils.test.DataA;
import com.vinthuy.unitils.core.CompareConfig;

/**
 * Created by ruiyong.hry on 16/6/3.
 */
public abstract class BaseCase {

    public abstract void setUpCompareConfig(CompareConfig compareConfig);


    public abstract void processData(DataA expect, DataA actual);
}
