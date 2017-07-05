package com.vinthuy.unitils.core;

import com.vinthuy.unitils.reflectionassert.report.StructureDiff;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 对比结果
 */
public class CompareResult {
    private boolean isEqual;
    private StructureDiff structureDiff;

    public static CompareResult CR_SUCCESS = new CompareResult(null);

    public CompareResult(StructureDiff structureDiff) {
        this.isEqual = structureDiff == null ? true : structureDiff.isEqual();
        this.structureDiff = structureDiff;
    }

    public boolean isEqual() {
        return isEqual;
    }

    public void setEqual(boolean isEqual) {
        this.isEqual = isEqual;
    }

    public String getSummary() {
        return structureDiff.toString();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public StructureDiff getStructureDiff() {
        return structureDiff;
    }

    public void setStructureDiff(StructureDiff structureDiff) {
        this.structureDiff = structureDiff;
    }
}
