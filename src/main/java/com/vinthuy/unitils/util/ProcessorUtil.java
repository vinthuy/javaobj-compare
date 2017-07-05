package com.vinthuy.unitils.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author ruiyong.hry
 */
public class ProcessorUtil {

    public static boolean isPathMatch(String path, String pattern) {

        if (path == null || pattern == null) {
            return false;
        }

        if (pattern.startsWith(".")) {
            if (path.contains(pattern)) {
                return true;
            }
        } else {
            if (path.startsWith(pattern)) {
                return true;
            }
        }
        return false;
    }


    public static String convertPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean isIgnore = false;
        for (int i = 0; i < path.length(); i++) {
            char ch = path.charAt(i);
            if (ch == '[') {
                isIgnore = true;
            } else if (ch == ']') {
                isIgnore = false;
            } else {
                if (!isIgnore) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }
}
