/*
 * Copyright 2008,  Unitils.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unitils.reflectionassert.comparator.impl;

import com.vinthuy.unitils.core.CompareConfig;
import org.apache.commons.lang.StringUtils;
import org.unitils.pathtrace.PathTrace;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.difference.MapDifference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.unitils.reflectionassert.ReflectionComparatorFactory.createRefectionComparator;

/**
 * Comparator for maps. This will compare all values with corresponding keys.
 *
 * @author Tim Ducheyne
 * @author Filip Neven
 * @author huruiyong
 */
public class MapComparator extends ExcludePathComparator {


    /**
     * Returns true when both values are not null and instance of Map
     *
     * @param left  The left object
     * @param right The right object
     * @return True for maps
     */
    public boolean canCompare(Object left, Object right) {
        if (left == null || right == null) {
            return false;
        }
        if ((left instanceof Map && right instanceof Map)) {
            return true;
        }
        return false;
    }


    /**
     * Compares the given maps by looping over the keys and comparing their values.
     * The key values are compared using a strict reflection comparison.
     *
     * @param left                 The left map, not null
     * @param right                The right map, not null
     * @param onlyFirstDifference  True if only the first difference should be returned
     * @param reflectionComparator The root comparator for inner comparisons, not null
     * @return A MapDifference or null if both maps are equal
     */
    public Difference compare(Object left, Object right, boolean onlyFirstDifference, ReflectionComparator reflectionComparator) {
        Map<?, ?> leftMap = (Map<?, ?>) left;
        Map<?, ?> rightMap = (Map<?, ?>) right;

        // Create copy from which we can remove elements.
        Map<Object, Object> rightCopy = new HashMap<Object, Object>(rightMap);
        CompareConfig compareConfig = reflectionComparator.getCompareConfig();
        this.compareConfig = compareConfig;
        ReflectionComparator keyReflectionComparator = createRefectionComparator();
        MapDifference difference = new MapDifference("Different elements", left, right, leftMap, rightMap);

        for (Map.Entry<?, ?> leftEntry : leftMap.entrySet()) {
            Object leftKey = leftEntry.getKey();
            Object leftValue = leftEntry.getValue();

            boolean found = false;
            Iterator<Map.Entry<Object, Object>> rightIterator = rightCopy.entrySet().iterator();
            while (rightIterator.hasNext()) {
                Map.Entry<Object, Object> rightEntry = rightIterator.next();
                Object rightKey = rightEntry.getKey();
                Object rightValue = rightEntry.getValue();
                // compare keys using strict reflection compare
                boolean isKeyEqual = keyReflectionComparator.isEqual(leftKey, rightKey);
                if (isKeyEqual) {
                    String leftKeyStr = leftKey.toString();
                    try {
                        if (PathTrace.isHandleNode(reflectionComparator.getCompareConfig())) {
                            PathTrace.enterNode(leftKey.toString());
                        }

                        found = true;
                        //����
                        rightIterator.remove();

                        TransferValueWrapper transferValueWrapper = transformValue(leftValue, rightValue);
                        if (!fieldNeedCompare(null, leftKeyStr)) {
                            continue;
                        }

                        // compare values
                        Difference elementDifference = reflectionComparator.getDifference(transferValueWrapper.getTleft(), transferValueWrapper.getTright(), onlyFirstDifference);
                        if (elementDifference != null) {
                            difference.addValueDifference(leftKey, elementDifference);
                            if (onlyFirstDifference) {
                                return difference;
                            }
                        }
                    } finally {
                        if (PathTrace.isHandleNode(reflectionComparator.getCompareConfig())) {
                            PathTrace.exitNode(leftKeyStr);
                        }
                    }
                    break;
                }
            }

            if (!found) {
                if (!isIgnoreMissing(reflectionComparator, compareConfig, leftKey, leftValue)) {
                    difference.addLeftMissingKey(leftKey);
                }
            }
        }


        for (Object rightKey : rightCopy.keySet()) {
            Object rightVal = rightCopy.get(rightKey);
            if (!isIgnoreMissing(reflectionComparator, compareConfig, rightKey, rightVal)) {
                difference.addRightMissingKey(rightKey);
            }
        }

        if (difference.getValueDifferences().isEmpty() && difference.getLeftMissingKeys().isEmpty() && difference.getRightMissingKeys().isEmpty()) {
            return null;
        }
        return difference;
    }


    private boolean isIgnoreMissing(ReflectionComparator reflectionComparator, CompareConfig compareConfig, Object key, Object targetValue) {
        if (compareConfig != null && compareConfig.isIgnoreNullOrStringEmpty()) {
            if (targetValue == null) {
                return true;
            } else if (targetValue instanceof String) {
                if (StringUtils.isEmpty((String) targetValue)) {
                    return true;
                }
            }
        }
        String keyStr = key.toString();
        try {
            if (PathTrace.isHandleNode(reflectionComparator.getCompareConfig())) {
                PathTrace.enterNode(keyStr);
            }
            if (!fieldNeedCompare(null, key.toString())) {
                return true;
            }
        } finally {
            if (PathTrace.isHandleNode(reflectionComparator.getCompareConfig())) {
                PathTrace.exitNode(keyStr);
            }
        }

        return false;
    }
}
