package com.vinthuy.unitils.processor;

import java.util.List;
import java.util.Map;
public abstract class PathConvertProcessor {

    private List<String> configs;

    /**
     * ת��ʵ�ַ���
     *
     * @param value
     * @return
     */
    public abstract Map<String, Object> convert(String path, Object value) throws Exception;

    public List<String> getConfigs() {
        return configs;
    }

    public void setConfigs(List<String> configs) {
        this.configs = configs;
    }

    public abstract boolean isNeedProcess(String path);
}
