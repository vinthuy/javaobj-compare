package com.vinthuy.unitils.core;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vinthuy.unitils.processor.BeforeCompareProcessor;
import com.vinthuy.unitils.processor.PathConvertProcessor;
import com.vinthuy.unitils.processor.impl.JsonPathConvertProcessor;
import com.vinthuy.unitils.processor.impl.KvPathConvertProcessor;
import com.vinthuy.unitils.reflectionassert.report.StructureDiff;
import com.vinthuy.unitils.util.ProcessorUtil;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.comparator.impl.*;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.report.impl.StructureDifferenceView;

import java.util.*;

/**
 * 对比组件API入口
 */
public class ReflectComparator {

    private ReflectionComparator comparator;

    private CompareConfig config;

    public ReflectComparator(CompareConfig config) {
        List<Comparator> userDefineComparators = Lists.newArrayList();
        List<ReflectionComparatorMode> compareOptions = Lists.newArrayList();
        if (config != null) {
            if (config.isIgnoreCollOrder()) {
                compareOptions.add(ReflectionComparatorMode.LENIENT_ORDER);
            }
            if (config.isIgnoreDateValue()) {
                compareOptions.add(ReflectionComparatorMode.LENIENT_DATES);
            }
            if (config.isIgnoreEmptyCollection()) {
                compareOptions.add(ReflectionComparatorMode.IGNORE_EMPTY_COLL_AND_NULL);
            }

            if (config.isIgnoreNullOrStringEmpty()) {
                compareOptions.add(ReflectionComparatorMode.IGNORE_NULL_STRING_EMPTY);
            }

            if (config.getIgnoredClassFieldValue() != null && config.getIgnoredClassFieldValue().size() != 0) {
                ExcludeObjectWithCertainFieldValueComparator fvc = new ExcludeObjectWithCertainFieldValueComparator();
                Map<String, String> cfMap = Maps.newHashMap();
                for (String fieldDef : config.getIgnoredClassFieldValue()) {
                    String[] arr = fieldDef.split("\\|");
                    if (arr.length == 2) {
                        cfMap.put(arr[0], arr[1]);
                    }
                }
                fvc.setSpecifyFieldValueMap(cfMap);
                userDefineComparators.add(fvc);
            }

            if (config.getSpecifyClassFields() != null) {
                SpecifyFieldComparator sfc = new SpecifyFieldComparator();
                sfc.setFilterClassFields(Sets.newHashSet(config.getSpecifyClassFields()));
                userDefineComparators.add(sfc);
            } else if (config.getIgnoredClassField() != null) {
                ExcludeFieldComparator efc = new ExcludeFieldComparator();
                efc.setFilterClassFields(Sets.newHashSet(config.getIgnoredClassField()));
                userDefineComparators.add(efc);
            }

            if (config.getCustomClassCompareMap() != null) {
                SpecifyClassesComparator scc = new SpecifyClassesComparator();
                scc.setCompareTools(config.getCustomClassCompareMap());
                userDefineComparators.add(scc);
            }

            //value processor--v这儿有顺序关系
            if (config.getSpecifyPathJsonString() != null) {
                config.addPathConvertProcessor(new JsonPathConvertProcessor(config.getSpecifyPathJsonString()));
            }
            if (config.getSpecifyPathKvAttribute() != null) {
                config.addPathConvertProcessor(new KvPathConvertProcessor(config.getSpecifyPathKvAttribute()));
            }
            if (config.hasIngorePaths()) {
                ExcludePathComparator excludePathComparator = new ExcludePathComparator(config);
                userDefineComparators.add(excludePathComparator);
            }

//            if (config.getPathConvertProcessors() != null) {
//                SpecifyPathComparator specifyPathComparator = new SpecifyPathComparator(config);
//                userDefineComparators.add(specifyPathComparator);
//            }


        }
        this.config = config;
        if (config.getPreCompareProcessorMap() == null || config.getPreCompareProcessorMap().size() == 0) {
            comparator = ReflectionComparatorFactory.createRefectionComparator(userDefineComparators,
                    compareOptions.toArray(new ReflectionComparatorMode[0]));
        } else {
            BeforeCompareProcessor bcp = new BeforeCompareProcessor();
            bcp.setProssorMap(config.getPreCompareProcessorMap());
            comparator = ReflectionComparatorFactory.createRefectionComparator(bcp, userDefineComparators,
                    compareOptions.toArray(new ReflectionComparatorMode[0]));
        }
        //设置配置
        comparator.setCompareConfig(config);
    }

    public CompareResult compare(Object expected, Object actual) {

        if (expected == null && actual == null) {
            return CompareResult.CR_SUCCESS;
        }


        if (expected == null) {
            StructureDiff diff = new StructureDiff();
            StructureDiff.DiffItem diffItem = new StructureDiff.DiffItem();
            diffItem.setRightValue("[object]");
            diff.addDiff(diffItem);
            return new CompareResult(diff);
        }
        if (actual == null) {
            StructureDiff diff = new StructureDiff();
            StructureDiff.DiffItem diffItem = new StructureDiff.DiffItem();
            diffItem.setLeftValue("[object]");
            diff.addDiff(diffItem);
            return new CompareResult(diff);
        }

        Difference diff = comparator.getDifference(expected, actual, config.isBreakFirstCompareFail());

        if (diff != null) {
            StructureDiff structureDiff = new StructureDifferenceView().createView(diff);

            if (!structureDiff.isEqual()) {

//                if (config.getSpecifyPathJsonString() != null) {
//                    config.addPathConvertProcessor(new JsonPathConvertProcessor(config.getSpecifyPathJsonString()));
//                }
//                if (config.getSpecifyPathKvAttribute() != null) {
//                    config.addPathConvertProcessor(new KvPathConvertProcessor(config.getSpecifyPathKvAttribute()));
//                }

                //排序
                List<StructureDiff.DiffItem> diffItems = structureDiff.getDiffItems();
                Collections.sort(diffItems, new java.util.Comparator<StructureDiff.DiffItem>() {
                    @Override
                    public int compare(StructureDiff.DiffItem l, StructureDiff.DiffItem r) {
                        String lp = l.getPath();
                        String rp = r.getPath();
                        if (lp.length() > rp.length()) {
                            return 1;
                        } else if (lp.length() == rp.length()) {
                            return lp.compareTo(rp);
                        } else {
                            return -1;
                        }
                    }
                });
                Set<String> pathSet = new HashSet<String>();
                for (StructureDiff.DiffItem diffItem : diffItems) {
                    pathSet.add(diffItem.getPath());
                }

                //json对比处理
//                handleValueByProcessor(structureDiff, diffItems);

            }
            //根据路径配置忽略字段
//            handleIgnorePathDiff(structureDiff);
            CompareResult bcr = new CompareResult(structureDiff);
            return bcr;
        } else {
            return CompareResult.CR_SUCCESS;
        }
    }

    private void handleValueByProcessor(StructureDiff structureDiff, List<StructureDiff.DiffItem> diffItems) {
        if (config.getPathConvertProcessors() != null && config.getPathConvertProcessors().size() > 0) {
            Map<String, Map<String, Object>> toCompareLeft = new HashMap<String, Map<String, Object>>();
            Map<String, Map<String, Object>> toCompareRight = new HashMap<String, Map<String, Object>>();

            //根据路径配置忽略字段
            Iterator<StructureDiff.DiffItem> itemIterator = structureDiff.getDiffItems().iterator();
            List<StructureDiff.DiffItem> toReAddItems = new LinkedList<StructureDiff.DiffItem>();
            while (itemIterator.hasNext()) {
                StructureDiff.DiffItem diffItem = itemIterator.next();
                String itemPath = ProcessorUtil.convertPath(diffItem.getPath());

                boolean isProcessed = false;
                for (PathConvertProcessor processor : config.getPathConvertProcessors()) {
                    if (processor.isNeedProcess(itemPath)) {
                        try {
                            toCompareLeft.put(diffItem.getPath(), processor.convert(itemPath, diffItem.getLeftValue()));
                            toCompareRight.put(diffItem.getPath(), processor.convert(itemPath, diffItem.getRightValue()));
                            isProcessed = true;
                            itemIterator.remove();
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!isProcessed) {
                    toReAddItems.add(diffItem);
                }
            }

            diffItems.addAll(toReAddItems);
            //做一次补充对比
            if (toCompareLeft.size() > 0) {
                CompareConfig compareConfig = new CompareConfig();
                compareConfig.setIgnorePaths(config.getIgnorePaths());
                ReflectComparator reflectComparator = new ReflectComparator(compareConfig);
                CompareResult cr = reflectComparator.compare(toCompareLeft, toCompareRight);
                if (!cr.isEqual()) {
                    diffItems.addAll(cr.getStructureDiff().getDiffItems());
                }
            }

        }
    }

    private void handleIgnorePathDiff(StructureDiff structureDiff) {
        List<String> ignorePaths = config.getIgnorePaths();
        if (ignorePaths != null && ignorePaths.size() > 0) {
            Iterator<StructureDiff.DiffItem> itemIterator = structureDiff.getDiffItems().iterator();
            while (itemIterator.hasNext()) {
                StructureDiff.DiffItem diffItem = itemIterator.next();
                String itemPath = ProcessorUtil.convertPath(diffItem.getPath());

                boolean isDeleted = false;

                for (String ignorePath : ignorePaths) {
                    if (ignorePath.startsWith(".")) {
                        if (itemPath.contains(ignorePath)) {
                            if (!isDeleted) {
                                itemIterator.remove();
                                isDeleted = true;
                            }
                        }
                    } else {
                        if (itemPath.startsWith(ignorePath)) {
                            if (!isDeleted) {
                                itemIterator.remove();
                                isDeleted = true;
                            }
                        }
                    }
                }
            }
        }
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

//    private String convertPath(String path) {
//        if (StringUtils.isEmpty(path)) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder();
//        boolean isIgnore = false;
//        for (int i = 0; i < path.length(); i++) {
//            char ch = path.charAt(i);
//            if (ch == '[') {
//                isIgnore = true;
//            } else if (ch == ']') {
//                isIgnore = false;
//            } else {
//                if (!isIgnore) {
//                    sb.append(ch);
//                }
//            }
//        }
//        return sb.toString();
//    }

    public static void main(String[] args) {
        p(ProcessorUtil.convertPath("a.b[3].c[434]"));


        CompareConfig cc = new CompareConfig();
        ReflectComparator comparator = new ReflectComparator(cc);
        p(comparator.compare("A", "b"));

    }


    public static void p(Object v) {
        System.out.println(v);
    }
}
