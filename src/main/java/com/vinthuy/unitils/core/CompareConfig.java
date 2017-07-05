package com.vinthuy.unitils.core;


import com.vinthuy.unitils.processor.Processor;
import org.unitils.face.ICompare;
import com.vinthuy.unitils.processor.PathConvertProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对比配置
 */
public class CompareConfig {
    /**
     * 是否忽略时间值，仅对比是否包含时间，不比较值大小
     */
    private boolean ignoreDateValue = false;
    /**
     * 对于集合类型是否忽略顺序
     */
    private boolean ignoreCollOrder = false;
    /**
     * 是否在第一次比较失败后立即退出
     */
    private boolean breakFirstCompareFail = false;

    private boolean isIgnoreEmptyCollection = false;
    /**
     * 自定义的比较器
     */
    private Map<String, ICompare> customClassCompareMap;


    private Map<String, Processor> preCompareProcessorMap;


    private transient List<PathConvertProcessor> pathConvertProcessors;

    /**
     * 排除具有某个值的class
     * fullClassName|fieldName:fieldValue
     */
    private List<String> ignoredClassFieldValue;
    /**
     * 忽略class中的field的对比，注意，如果@specifyClassFields这个值已经被设置，那么@ignoredClassFields的设置失效
     * <p/>
     * fullClassName.fieldName
     */
    private List<String> ignoredClassField;
    /**
     * 指定class中的field对比
     */
    private List<String> specifyClassFields;

    /**
     * 是否排除null与""对比
     */
    private boolean ignoreNullOrStringEmpty = false;

    /**
     * 忽略指定路径不一致的对比
     * <br/>
     * 例子：
     * a.b.c 绝对路径排除
     * .a.b.c 相对路径排除
     */
    private List<String> ignorePaths;

    /**
     * 设置path集合，指定该path下的字符串按JSON转换后对比
     */
    private List<String> specifyPathJsonString;
    /**
     * 设置path集合+分隔符，指定该path下的字符串转化为Map对比
     * <p/>
     * 例如：a.b.c路径下按分;隔属性，:分隔kv
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
