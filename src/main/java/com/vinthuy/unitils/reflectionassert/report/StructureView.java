package com.vinthuy.unitils.reflectionassert.report;
;
import org.unitils.reflectionassert.difference.Difference;

/**
 * @author ruiyong.hry
 */
public interface StructureView {
    public StructureDiff createView(Difference difference);
}
