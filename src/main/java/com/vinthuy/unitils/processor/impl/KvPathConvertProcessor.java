package com.vinthuy.unitils.processor.impl;

import com.vinthuy.unitils.processor.PathConvertProcessor;
import com.vinthuy.unitils.util.ProcessorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1=1&a=c&a=d 类似这样的值进行处理
 */
public class KvPathConvertProcessor extends PathConvertProcessor {


    public KvPathConvertProcessor(List<String> configs) {
        this.setConfigs(configs);
    }

    @Override
    public Map<String, Object> convert(String path, Object value) throws Exception {

        if (value == null) {
            return new HashMap<String, Object>();
        }

        if (value instanceof String) {
            for (Map.Entry<String, String> entry : pathSplitters.entrySet()) {
                if (ProcessorUtil.isPathMatch(path, entry.getKey())) {
                    return convertKvMap((String) value, entry.getValue());
                }
            }
        }

        throw new IllegalArgumentException("JsonPathConvertProcessor convert error,value not string");
    }


    private Map<String, Object> convertKvMap(String content, String splitters) {
        String splitItem = String.valueOf(splitters.charAt(1));
        String splitKv = String.valueOf(splitters.charAt(0));
        String[] items = content.split(splitItem);
        Map<String, Object> kvMap = new HashMap<String, Object>();
        for (String item : items) {
            String[] kv = item.split(splitKv);
            if (kv.length == 2) {
                kvMap.put(kv[0], kv[1]);
            }
        }
        return kvMap;
    }

    private Map<String, String> pathSplitters;

    @Override
    public boolean isNeedProcess(String path) {

        if (getConfigs() == null) {
            return false;
        }

        synchronized (this) {
            if (pathSplitters == null) {
                pathSplitters = new HashMap<String, String>();
                for (String config : getConfigs()) {
                    //转义字符处理
                    config = config.replaceAll("\\{@}", "\001");
                    String[] arr = config.split("@");
                    if (arr.length == 2 && arr[1].length() == 2) {
                        //转义处理
                        pathSplitters.put(arr[0].replaceAll("\001", "@"),
                                arr[1].replaceAll("\001", "@"));
                    }
                }

            }
        }

        for (String pathPtn : pathSplitters.keySet()) {
            if (ProcessorUtil.isPathMatch(path, pathPtn)) {
                return true;
            }
        }

        return false;
    }
}
