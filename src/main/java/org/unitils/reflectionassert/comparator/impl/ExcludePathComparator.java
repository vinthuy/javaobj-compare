package org.unitils.reflectionassert.comparator.impl;


import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.processor.PathConvertProcessor;
import com.vinthuy.unitils.util.ProcessorUtil;
import org.unitils.pathtrace.ClassPathTraceTreeNode;
import org.unitils.pathtrace.PathTrace;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.difference.Difference;

import java.util.*;

/**
 * 根据类路径进行排除
 */
public class ExcludePathComparator extends BaseExtendedObjectComparator {


    CompareConfig compareConfig;

    @Override
    protected boolean fieldNeedCompare(String className, String fieldName) {
        if (compareConfig.hasIngorePaths()) {
            ClassPathTraceTreeNode node = PathTrace.findCurrentNode();
            if (node != null) {
                String nodePathDesc = node.getPathDesc();
                for (String ignorePath : compareConfig.getIgnorePaths()) {
                    if (ProcessorUtil.isPathMatch(nodePathDesc, ignorePath)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Difference compare(Object left, Object right, boolean onlyFirstDifference, ReflectionComparator reflectionComparator) {
        setCompareConfig(reflectionComparator.getCompareConfig(),false);
        return super.compare(left, right, onlyFirstDifference, reflectionComparator);
    }

    public void setCompareConfig(CompareConfig compareConfig, boolean force) {
        if (this.compareConfig == null || force) {
            this.compareConfig = compareConfig;
        }
    }

    @Override
    protected TransferValueWrapper transformValue(Object left, Object right) {
        ClassPathTraceTreeNode node = PathTrace.findCurrentNode();
        if (node != null && compareConfig.getPathConvertProcessors() != null) {
            Map<String, Object> leftMap = null;
            Map<String, Object> rightMap = null;
            for (PathConvertProcessor processor : compareConfig.getPathConvertProcessors()) {
                if (processor.isNeedProcess(node.getPathDesc())) {
                    try {
                        leftMap = processor.convert(node.getPathDesc(), left);
                        rightMap = processor.convert(node.getPathDesc(), right);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if ((leftMap != null && leftMap.size() > 0) && (rightMap != null && rightMap.size() > 0)) {
                TransferValueWrapper transferValueWrapper = new TransferValueWrapper(true);
                transferValueWrapper.setTleft(leftMap);
                transferValueWrapper.setTright(rightMap);
                return transferValueWrapper;
            }
        }
        return super.transformValue(left, right);
    }


    public ExcludePathComparator(CompareConfig compareConfig) {
        this.compareConfig = compareConfig;
    }


    public ExcludePathComparator() {
    }
}
