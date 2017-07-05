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
package org.unitils.reflectionassert.report.impl;

import com.vinthuy.unitils.reflectionassert.report.StructureDiff;
import com.vinthuy.unitils.reflectionassert.report.StructureView;
import org.unitils.core.Collection;
import org.unitils.core.util.ObjectFormatter;
import org.unitils.reflectionassert.difference.*;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.ClassUtils.getShortClassName;
import static org.unitils.reflectionassert.report.impl.DefaultDifferenceReport.MatchType.NO_MATCH;

/**
 * Formatter that will output all leaf differences in the tree and, in case of an unordered collection difference,
 * the difference of all best matches.
 *
 * @author Tim Ducheyne
 * @author Filip Neven
 */
public class StructureDifferenceView implements StructureView {

    /**
     * True when an unordered collection is being formatted.
     */
    protected boolean outputtingUnorderedCollectionDifference = false;

    /**
     * Formatter for object values.
     */
    protected ObjectFormatter objectFormatter = new ObjectFormatter();

    /**
     * The visitor for visiting the difference tree
     */
    protected DifferenceFormatterVisitor differenceFormatterVisitor = new DifferenceFormatterVisitor();

    /**
     * Creates a string representation of the given difference tree.
     *
     * @param difference The root difference, not null
     * @return The string representation, not null
     */
    public StructureDiff createView(Difference difference) {
        return difference.accept(differenceFormatterVisitor, null);
        //return null;
    }


    /**
     * Creates a string representation of a simple difference.
     *
     * @param difference The difference, not null
     * @param fieldName  The current fieldName, null for root
     * @return The string representation, not null
     */
    protected StructureDiff formatDifference(Difference difference, String fieldName) {
        StructureDiff.DiffItem diffItem = formatValues(fieldName, difference.getFieldClass(), difference.getLeftValue(), difference.getRightValue());
        StructureDiff diff = new StructureDiff();
        diff.addDiff(diffItem);
        return diff;
    }


    /**
     * Creates a string representation of an object difference.
     *
     * @param objectDifference The difference, not null
     * @param fieldName        The current fieldName, null for root
     * @return The string representation, not null
     */
    protected StructureDiff formatDifference(ObjectDifference objectDifference, String fieldName) {
        StructureDiff diff = new StructureDiff();
        for (Map.Entry<String, Difference> fieldDifference : objectDifference.getFieldDifferences().entrySet()) {
            String innerFieldName = createFieldName(fieldName, fieldDifference.getKey(), true);
            Object obj = objectDifference.getLeftValue() == null ? objectDifference.getRightValue() : objectDifference.getLeftValue();
            //输出不一致字段所在类的类型
            Difference difference = fieldDifference.getValue().buildFieldClass(obj == null ? null : obj.getClass());
            StructureDiff subDiff = difference.accept(differenceFormatterVisitor, innerFieldName);
            diff.getDiffItems().addAll(subDiff.getDiffItems());
        }
        return diff;
    }


    protected StructureDiff formatDifference(ClassDifference classDifference, String fieldName) {
        StructureDiff diff = new StructureDiff();
        StructureDiff.DiffItem diffItem = new StructureDiff.DiffItem();
        diffItem.setLeftValue(getShortClassName(classDifference.getLeftClass()));
        diffItem.setRightValue(getShortClassName(classDifference.getRightClass()));
        diffItem.setClassField(getClassFieldName(classDifference.getFieldClass(), fieldName));
        diffItem.setPath(fieldName);
        diff.addDiff(diffItem);
        return diff;
    }


    /**
     * Creates a string representation of a collection difference.
     *
     * @param collectionDifference The difference, not null
     * @param fieldName            The current fieldName, null for root
     * @return The string representation, not null
     */
    protected StructureDiff formatDifference(CollectionDifference collectionDifference, String fieldName) {

        StructureDiff diff = new StructureDiff();
        for (Map.Entry<Integer, Difference> elementDifferences : collectionDifference.getElementDifferences().entrySet()) {
            String innerFieldName = createFieldName(fieldName, "[" + elementDifferences.getKey() + "]", false);
            Difference difference = elementDifferences.getValue().buildFieldClass(Collection.class);
            StructureDiff diffSub = difference.accept(differenceFormatterVisitor, innerFieldName);
            diff.getDiffItems().addAll(diffSub.getDiffItems());
        }

        List<?> leftList = collectionDifference.getLeftList();
        List<?> rightList = collectionDifference.getRightList();
        for (Integer leftIndex : collectionDifference.getLeftMissingIndexes()) {
            String innerFieldName = createFieldName(fieldName, "[" + leftIndex + "]", false);
            StructureDiff.DiffItem diffItem = formatValues(innerFieldName, collectionDifference.getFieldClass(), leftList.get(leftIndex), NO_MATCH);
            diff.addDiff(diffItem);

        }
        for (Integer rightIndex : collectionDifference.getRightMissingIndexes()) {
            String innerFieldName = createFieldName(fieldName, "[" + rightIndex + "]", false);
            StructureDiff.DiffItem diffItem = formatValues(innerFieldName, collectionDifference.getFieldClass(), NO_MATCH, rightList.get(rightIndex));
            diff.addDiff(diffItem);

        }
        return diff;
    }


    /**
     * Creates a string representation of a map difference.
     *
     * @param mapDifference The difference, not null
     * @param fieldName     The current fieldName, null for root
     * @return The string representation, not null
     */
    protected StructureDiff formatDifference(MapDifference mapDifference, String fieldName) {
        StructureDiff diff = new StructureDiff();
        for (Map.Entry<Object, Difference> valueDifference : mapDifference.getValueDifferences().entrySet()) {
            String innerFieldName = createFieldName(fieldName, formatObject(valueDifference.getKey()), true);
            Difference difference = valueDifference.getValue().buildFieldClass(Map.class);
            StructureDiff diffSub = difference.accept(differenceFormatterVisitor, innerFieldName);
            diff.getDiffItems().addAll(diffSub.getDiffItems());
        }

        Map<?, ?> leftMap = mapDifference.getLeftMap();
        Map<?, ?> rightMap = mapDifference.getRightMap();
        for (Object leftKey : mapDifference.getLeftMissingKeys()) {
            String innerFieldName = createFieldName(fieldName, formatObject(leftKey), true);
            StructureDiff.DiffItem diffItem = formatValues(innerFieldName, Map.class, leftMap.get(leftKey), "");
            diff.addDiff(diffItem);
        }
        for (Object rightKey : mapDifference.getRightMissingKeys()) {
            String innerFieldName = createFieldName(fieldName, formatObject(rightKey), true);
//            StructureDiff.DiffItem diffItem = formatValues(innerFieldName, Map.class, rightMap.get(rightKey), "");
            StructureDiff.DiffItem diffItem = formatValues(innerFieldName, Map.class, "", rightMap.get(rightKey));
            diff.addDiff(diffItem);
        }
        return diff;
    }

    protected String formatObject(Object object) {
        if (object == NO_MATCH) {
            return "--no match--";
        }
        return objectFormatter.format(object);
    }


    /**
     * Creates a string representation of an unordered collection difference.
     *
     * @param unorderedCollectionDifference The difference, not null
     * @param fieldName                     The current fieldName, null for root
     * @return The string representation, not null
     */
    protected StructureDiff formatDifference(UnorderedCollectionDifference unorderedCollectionDifference, String fieldName) {
        StructureDiff diff = new StructureDiff();

        if (unorderedCollectionDifference.getRightList().size() != unorderedCollectionDifference.getLeftList().size()) {
            StructureDiff.DiffItem diffItem = new StructureDiff.DiffItem();
            diffItem.setLeftValue("size=" + unorderedCollectionDifference.getLeftList().size());
            diffItem.setRightValue("size=" + unorderedCollectionDifference.getRightList().size());
            diffItem.setPath(fieldName);
            diff.addDiff(diffItem);
        }

        Map<Integer, Integer> bestMatchingIndexes = unorderedCollectionDifference.getBestMatchingIndexes();
        for (Map.Entry<Integer, Integer> bestMatchingIndex : bestMatchingIndexes.entrySet()) {
            int leftIndex = bestMatchingIndex.getKey();
            int rightIndex = bestMatchingIndex.getValue();

            if (leftIndex == -1) {
                String innerFieldName = createFieldName(fieldName, "[x," + rightIndex + "]", false);
                StructureDiff.DiffItem diffItem = formatValues(innerFieldName, unorderedCollectionDifference.getFieldClass(), NO_MATCH, unorderedCollectionDifference.getRightList().get(rightIndex));
                diff.addDiff(diffItem);
                continue;
            }
            if (rightIndex == -1) {
                String innerFieldName = createFieldName(fieldName, "[" + leftIndex + ",x]", false);
                StructureDiff.DiffItem diffItem = formatValues(innerFieldName, unorderedCollectionDifference.getFieldClass(), unorderedCollectionDifference.getLeftList().get(leftIndex), NO_MATCH);
                diff.addDiff(diffItem);
                continue;
            }

            Difference difference = unorderedCollectionDifference.getElementDifference(leftIndex, rightIndex);
            if (difference == null) {
                continue;
            }

            String innerFieldName = createFieldName(fieldName, "[" + leftIndex + "," + rightIndex + "]", false);
            StructureDiff subDiff = difference.accept(differenceFormatterVisitor, innerFieldName);
            diff.getDiffItems().addAll(subDiff.getDiffItems());
        }
        return diff;
    }


    /**
     * Formats and appends the given fieldname and object values.
     *
     * @param fieldName  The field name, null if there is no field name
     * @param leftValue  The left value
     * @param rightValue The right value
     * @return The string representation, not null
     */
    protected StructureDiff.DiffItem formatValues(String fieldName, Class<?> fieldClass, Object leftValue, Object rightValue) {
        String leftValueFormatted = formatObject(leftValue);
        String rightValueFormatted = formatObject(rightValue);

        StructureDiff.DiffItem diffItem = new StructureDiff.DiffItem();
        diffItem.setLeftValue(leftValueFormatted);
        diffItem.setRightValue(rightValueFormatted);
        diffItem.setClassField(getClassFieldName(fieldClass, fieldName));
        diffItem.setPath(fieldName);
        return diffItem;
    }

    //简化map和collection输出
    private String getClassFieldName(Class<?> clazz, String field) {
        if (clazz == null) {
            return "";
        }
        if (clazz == Map.class) {
            return "M";
        }
        if (clazz == Collection.class) {
            return "C";
        }
        if (field == null) {
            return "";
        }
        String[] arr = field.split("\\.");
        String fieldName = arr[arr.length - 1];
        int quotIdx = fieldName.indexOf('[');
        if (quotIdx > 1) {
            fieldName = fieldName.substring(0, quotIdx);
        }

        return clazz.getName().replace("$", ".") + "." + fieldName;
    }

    /**
     * Adds the inner field name to the given field name.
     *
     * @param fieldName      The field
     * @param innerFieldName The field to append, not null
     * @param includePoint   True if a point should be added
     * @return The field name
     */
    protected String createFieldName(String fieldName, String innerFieldName, boolean includePoint) {
        if (fieldName == null) {
            return innerFieldName;
        }

        StringBuilder result = new StringBuilder();
        result.append(fieldName);
        if (includePoint) {
            result.append(".");
        }
        result.append(innerFieldName);
        return result.toString();
    }


    /**
     * The visitor for visiting the difference tree.
     */
    protected class DifferenceFormatterVisitor implements DifferenceVisitor<StructureDiff, String> {

        public StructureDiff visit(Difference difference, String fieldName) {
            return formatDifference(difference, fieldName);
        }

        public StructureDiff visit(ObjectDifference objectDifference, String fieldName) {
            return formatDifference(objectDifference, fieldName);
        }

        public StructureDiff visit(ClassDifference classDifference, String fieldName) {
            return formatDifference(classDifference, fieldName);
        }

        public StructureDiff visit(MapDifference mapDifference, String fieldName) {
            return formatDifference(mapDifference, fieldName);
        }

        public StructureDiff visit(CollectionDifference collectionDifference, String fieldName) {
            return formatDifference(collectionDifference, fieldName);
        }

        public StructureDiff visit(UnorderedCollectionDifference unorderedCollectionDifference, String fieldName) {
            return formatDifference(unorderedCollectionDifference, fieldName);
        }
    }

    public static void main(String[] args) {
        System.out.println("A$B".replace("$", "."));
    }
}