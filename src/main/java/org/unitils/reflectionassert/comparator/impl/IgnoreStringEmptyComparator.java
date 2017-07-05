package org.unitils.reflectionassert.comparator.impl;

import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.difference.Difference;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 忽略null,""的对比
 * Created by ruiyong.rhy
 */
public class IgnoreStringEmptyComparator implements Comparator {

    /**
     * 1.判断left==null,right==""|| left=='',right==null
     *  返回true
     * @param left  The left object
     * @param right The right object
     * @return
     */
    @Override
    public boolean canCompare(Object left, Object right) {
         if(left==null && ObjectUtils.equals(StringUtils.EMPTY,right) ||
                 right==null && ObjectUtils.equals(StringUtils.EMPTY,left) ){
             return  true;
         }
         return false;
    }

    /**
     * 返回为空,表示没有问题
     * 若不为空,则表示存在错误的case.
     * @param left                 The left object
     * @param right                The right object
     * @param onlyFirstDifference  True if only the first difference should be returned
     * @param reflectionComparator The root comparator for inner comparisons, not null
     * @return
     */
    @Override
    public Difference compare(Object left, Object right, boolean onlyFirstDifference, ReflectionComparator reflectionComparator) {
        return null;
    }
}
