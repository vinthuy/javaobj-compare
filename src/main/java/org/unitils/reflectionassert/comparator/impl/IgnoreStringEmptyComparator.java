package org.unitils.reflectionassert.comparator.impl;

import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.difference.Difference;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * ����null,""�ĶԱ�
 * Created by ruiyong.rhy
 */
public class IgnoreStringEmptyComparator implements Comparator {

    /**
     * 1.�ж�left==null,right==""|| left=='',right==null
     *  ����true
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
     * ����Ϊ��,��ʾû������
     * ����Ϊ��,���ʾ���ڴ����case.
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
