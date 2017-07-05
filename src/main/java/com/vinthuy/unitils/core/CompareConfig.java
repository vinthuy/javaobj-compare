package com.vinthuy.unitils.core;


import com.vinthuy.unitils.processor.Processor;
import org.unitils.face.ICompare;
import com.vinthuy.unitils.processor.PathConvertProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * �Ա�����
 */
public class CompareConfig {
    /**
     * �Ƿ����ʱ��ֵ�����Ա��Ƿ����ʱ�䣬���Ƚ�ֵ��С
     */
    private boolean ignoreDateValue = false;
    /**
     * ���ڼ��������Ƿ����˳��
     */
    private boolean ignoreCollOrder = false;
    /**
     * �Ƿ��ڵ�һ�αȽ�ʧ�ܺ������˳�
     */
    private boolean breakFirstCompareFail = false;

    private boolean isIgnoreEmptyCollection = false;
    /**
     * �Զ���ıȽ���
     */
    private Map<String, ICompare> customClassCompareMap;


    private Map<String, Processor> preCompareProcessorMap;


    private transient List<PathConvertProcessor> pathConvertProcessors;

    /**
     * �ų�����ĳ��ֵ��class
     * fullClassName|fieldName:fieldValue
     */
    private List<String> ignoredClassFieldValue;
    /**
     * ����class�е�field�ĶԱȣ�ע�⣬���@specifyClassFields���ֵ�Ѿ������ã���ô@ignoredClassFields������ʧЧ
     * <p/>
     * fullClassName.fieldName
     */
    private List<String> ignoredClassField;
    /**
     * ָ��class�е�field�Ա�
     */
    private List<String> specifyClassFields;

    /**
     * �Ƿ��ų�null��""�Ա�
     */
    private boolean ignoreNullOrStringEmpty = false;

    /**
     * ����ָ��·����һ�µĶԱ�
     * <br/>
     * ���ӣ�
     * a.b.c ����·���ų�
     * .a.b.c ���·���ų�
     */
    private List<String> ignorePaths;

    /**
     * ����path���ϣ�ָ����path�µ��ַ�����JSONת����Ա�
     */
    private List<String> specifyPathJsonString;
    /**
     * ����path����+�ָ�����ָ����path�µ��ַ���ת��ΪMap�Ա�
     * <p/>
     * ���磺a.b.c·���°���;�����ԣ�:�ָ�kv
     * a.b.c@:;
     */
    private List<String> specifyPathKvAttribute;

    public boolean isBreakFirstCompareFail() {
        return breakFirstCompareFail;
    }

    public boolean isIgnoreDateValue() {
        return ignoreDateValue;
    }

    public CompareConfig breakFirstCompareFail() {
        breakFirstCompareFail = true;
        return this;
    }

    public boolean ReflectionComparatorMode() {
        return ignoreDateValue;
    }

    public CompareConfig ignoreDateValue() {
        this.ignoreDateValue = true;
        return this;
    }

    public boolean isIgnoreCollOrder() {
        return ignoreCollOrder;
    }

    public CompareConfig ignoreCollOrder() {
        this.ignoreCollOrder = true;
        return this;
    }

    public CompareConfig ignoreEmptyCollection() {
        this.isIgnoreEmptyCollection = true;
        return this;
    }

    public Map<String, ICompare> getCustomClassCompareMap() {
        return customClassCompareMap;
    }

    public CompareConfig setCustomClassCompareMap(
            Map<String, ICompare> customClassCompareMap) {
        this.customClassCompareMap = customClassCompareMap;
        return this;
    }

    public CompareConfig setSpecifyClassFields(List<String> specifyClassFields) {
        this.specifyClassFields = specifyClassFields;
        return this;
    }

    public List<String> getSpecifyClassFields() {
        return specifyClassFields;
    }

    public boolean isIgnoreEmptyCollection() {
        return isIgnoreEmptyCollection;
    }

    public List<String> getIgnoredClassFieldValue() {
        return ignoredClassFieldValue;
    }

    public void setIgnoredClassFieldValue(List<String> ignoredClassFieldValue) {
        this.ignoredClassFieldValue = ignoredClassFieldValue;
    }

    public Map<String, Processor> getPreCompareProcessorMap() {
        return preCompareProcessorMap;
    }

    public void setPreCompareProcessorMap(Map<String, Processor> preCompareProcessorMap) {
        this.preCompareProcessorMap = preCompareProcessorMap;
    }

    public List<String> getIgnoredClassField() {
        return ignoredClassField;
    }

    public void setIgnoredClassField(List<String> ignoredClassField) {
        this.ignoredClassField = ignoredClassField;
    }

    public boolean isIgnoreNullOrStringEmpty() {
        return ignoreNullOrStringEmpty;
    }

    public CompareConfig ignoreNullOrStringEmpty() {
        this.ignoreNullOrStringEmpty = true;
        return this;
    }

    public void setIgnoreDateValue(boolean ignoreDateValue) {
        this.ignoreDateValue = ignoreDateValue;
    }

    public void setIgnoreCollOrder(boolean ignoreCollOrder) {
        this.ignoreCollOrder = ignoreCollOrder;
    }

    public void setBreakFirstCompareFail(boolean breakFirstCompareFail) {
        this.breakFirstCompareFail = breakFirstCompareFail;
    }

    public void setIgnoreEmptyCollection(boolean ignoreEmptyCollection) {
        isIgnoreEmptyCollection = ignoreEmptyCollection;
    }

    public void setIgnoreNullOrStringEmpty(boolean ignoreNullOrStringEmpty) {
        this.ignoreNullOrStringEmpty = ignoreNullOrStringEmpty;
    }

    public List<String> getIgnorePaths() {
        return ignorePaths;
    }

    public boolean hasIngorePaths() {
        return ignorePaths != null && ignorePaths.size() > 0;
    }


    public void setIgnorePaths(List<String> ignorePaths) {
        this.ignorePaths = ignorePaths;
    }

    public List<String> getSpecifyPathJsonString() {
        return specifyPathJsonString;
    }

    public void setSpecifyPathJsonString(List<String> specifyPathJsonString) {
        this.specifyPathJsonString = specifyPathJsonString;
    }

    public List<String> getSpecifyPathKvAttribute() {
        return specifyPathKvAttribute;
    }

    public void setSpecifyPathKvAttribute(List<String> specifyPathKvAttribute) {
        this.specifyPathKvAttribute = specifyPathKvAttribute;
    }

    public void addPathConvertProcessor(PathConvertProcessor pathConvertProcessor) {
        if (pathConvertProcessors == null) {
            pathConvertProcessors = new ArrayList<PathConvertProcessor>();
        }
        pathConvertProcessors.add(pathConvertProcessor);

    }

    public List<PathConvertProcessor> getPathConvertProcessors() {
        return pathConvertProcessors;
    }
}
