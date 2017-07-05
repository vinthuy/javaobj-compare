package org.unitils.reflectionassert.comparator.impl;


import java.util.Set;

/**
 * �ų�����Ҫ�ȶԵ��ֶΣ��������ֶ���ȶ�
 *
 * @author huruiyong
 */
public class ExcludeFieldComparator extends ExcludePathComparator {

    private Set<String> filterClassFields;

    @Override
    protected boolean fieldNeedCompare(String className, String fieldName) {

        String flag = className + "." + fieldName;
        flag = flag.replaceAll("\\$", ".");
        boolean res = false;
        if (filterClassFields == null) {
            res = true;
        } else {
            res = !filterClassFields.contains(flag);
        }
        if(res){
            return super.fieldNeedCompare(className,fieldName);
        }
        return res;
    }


    public Set<String> getFilterClassFields() {
        return filterClassFields;
    }

    public void setFilterClassFields(Set<String> filterClassFields) {
        this.filterClassFields = filterClassFields;
    }
}
