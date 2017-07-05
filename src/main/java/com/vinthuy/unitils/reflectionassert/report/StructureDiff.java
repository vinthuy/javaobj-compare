package com.vinthuy.unitils.reflectionassert.report;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ruiyong.hry on 16/6/6.
 * <p/>
 * 结构化的对比不一致输出
 */
public class StructureDiff {

    public static class DiffItem {
        private String classField;
        private String leftValue;
        private String rightValue;
        private String path;

        public String getClassField() {
            return classField;
        }

        public void setClassField(String classField) {
            this.classField = classField;
        }

        public String getLeftValue() {
            return leftValue;
        }

        public void setLeftValue(String leftValue) {
            this.leftValue = leftValue;
        }

        public String getRightValue() {
            return rightValue;
        }

        public void setRightValue(String rightValue) {
            this.rightValue = rightValue;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String toString() {
            return JSON.toJSONString(this);
        }
    }


    private List<DiffItem> diffItems = new LinkedList<DiffItem>();

    public List<DiffItem> getDiffItems() {
        return diffItems;
    }

    public void setDiffItems(List<DiffItem> diffItems) {
        this.diffItems = diffItems;
    }

    /**
     * 是否相等，如果没有不一致则说明相等
     *
     * @return
     */
    public boolean isEqual() {
        return diffItems == null || diffItems.size() == 0;
    }

    public void addDiff(DiffItem diffItem) {
        this.diffItems.add(diffItem);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isEqual()) {
            stringBuilder.append("equal");
        } else {
            for (DiffItem item : diffItems) {
                stringBuilder.append(item).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
