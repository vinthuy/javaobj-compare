package org.unitils.reflectionassert.comparator.impl;

import java.util.Set;

/*
 *  
 * 只比较需要比较的字段
 *  * @author ruiyongh.hry
 */
public class SpecifyFieldComparator extends BaseExtendedObjectComparator {

	private Set<String> filterClassFields;

	@Override
	protected boolean fieldNeedCompare(String className, String fieldName) {
		String flag = className + "." + fieldName;
		flag = flag.replaceAll("\\$", ".");
		if (filterClassFields == null) {
			return false;
		} else {
			return filterClassFields.contains(flag);
		}
	}

	public Set<String> getFilterClassFields() {
		return filterClassFields;
	}

	public void setFilterClassFields(Set<String> filterClassFields) {
		this.filterClassFields = filterClassFields;
	}

}
