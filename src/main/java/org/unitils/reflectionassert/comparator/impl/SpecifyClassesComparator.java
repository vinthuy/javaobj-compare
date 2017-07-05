package org.unitils.reflectionassert.comparator.impl;

import org.unitils.face.ICompare;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.difference.Difference;

import java.util.Map;

public class SpecifyClassesComparator implements Comparator {

	private Map<String, ICompare> campareTools;

	@Override
	public boolean canCompare(Object left, Object right) {
		if (left == null && right == null) {
			return false;
		}
		Object one = left == null ? right : left;
		String className = one.getClass().getName();
		if (!campareTools.containsKey(className))
			return false;
		return true;
	}
	
	@Override
	public Difference compare(Object left, Object right, boolean onlyFirstDifference,
							  ReflectionComparator reflectionComparator) {
		Object one = left == null ? right : left;
		ICompare compare = campareTools.get(one.getClass().getName());

		return compare.getDifference(left, right);
	}

	public Map<String, ICompare> getCampareTools() {
		return campareTools;
	}

	public void setCompareTools(Map<String, ICompare> campareTools) {
		this.campareTools = campareTools;
	}

}
