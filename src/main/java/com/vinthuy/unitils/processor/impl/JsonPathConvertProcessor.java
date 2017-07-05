package com.vinthuy.unitils.processor.impl;

import com.alibaba.fastjson.JSON;
import com.vinthuy.unitils.processor.PathConvertProcessor;
import com.vinthuy.unitils.util.ProcessorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruiyong.hry on 16/6/8.
 *  ��jsonֵ���бȽϻ��Ĵ���
 * @author ruiyong.hry
 */
public class JsonPathConvertProcessor extends PathConvertProcessor {


    public JsonPathConvertProcessor(List<String> paths) {
        this.setConfigs(paths);
    }

    @Override
    public Map<String, Object> convert(String path, Object value) throws Exception {

        if (value == null) {
            return new HashMap<String, Object>();
        }

        if (value instanceof String) {
            if (((String) value).startsWith("{") || ((String) value).startsWith("[")) {
                //���ܸ�ʽ����
                return JSON.parseObject((String) value);
            } else {
                return null;
            }
        }
        return null;
//        throw new IllegalArgumentException("JsonPathConvertProcessor convert error,value not string");
    }

    @Override
    public boolean isNeedProcess(String path) {
        if (getConfigs() == null) {
            return false;
        }
        for (String config : getConfigs()) {
            if (ProcessorUtil.isPathMatch(path, config)) {
                return true;
            }
        }
        return false;
    }
}
