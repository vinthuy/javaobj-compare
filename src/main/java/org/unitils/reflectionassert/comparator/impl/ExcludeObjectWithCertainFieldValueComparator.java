package org.unitils.reflectionassert.comparator.impl;

import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.ReflectionComparator;

import java.lang.reflect.Field;
import java.util.Map;

/**特殊类对象排查
 * @author huruiyong
 */
public class ExcludeObjectWithCertainFieldValueComparator implements Comparator {
    public void setSpecifyFieldValueMap(Map<String, String> specifyFieldValueMap) {
        this.specifyFieldValueMap = specifyFieldValueMap;
    }

    //key: classname-> value fieldName,fieldValue
    private Map<String,String> specifyFieldValueMap;

    @Override
    public boolean canCompare(Object left, Object right) {
        if(specifyFieldValueMap==null){
            return false;
        }
        if(left==null){
            return false;
        }

        String str =  specifyFieldValueMap.get(left.getClass().getName());
        if(str!=null){
            String[] arr = str.split(":");
            if(arr.length!=2){
                return false;
            }
            try {
                Field f = left.getClass().getField(arr[0]);
                f.setAccessible(true);
                Object v = f.get(left);
                if(v==null&&"null".equals(arr[1])){
                    return true;
                }
                if(v!=null&&arr[1].equals(v.toString())){
                    return true;
                }

            } catch (NoSuchFieldException e) {
                return false;
            } catch (IllegalAccessException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public Difference compare(Object left, Object right, boolean onlyFirstDifference, ReflectionComparator reflectionComparator) {
        return null;
    }
}
