package org.unitils.pathtrace;

import com.vinthuy.unitils.core.CompareConfig;
//·��������
public class PathTrace {


    private static ThreadLocal<ClassPathTraceTreeNode> visitingNode = new ThreadLocal<ClassPathTraceTreeNode>();

    public static ClassPathTraceTreeNode findCurrentNode() {
        return visitingNode.get();
    }

    public static void setVisitingNode(ClassPathTraceTreeNode node) {
        visitingNode.set(node);
    }

    public static void enterNode(String nodeName) {
        ClassPathTraceTreeNode currentNode = findCurrentNode();
        if (currentNode == null) {
            currentNode = new ClassPathTraceTreeNode(null, nodeName);
            PathTrace.setVisitingNode(currentNode);
        } else {
            ClassPathTraceTreeNode visitNode = new ClassPathTraceTreeNode(currentNode, nodeName);
            currentNode.addChild(visitNode);
            PathTrace.setVisitingNode(visitNode);
        }
    }

    public static boolean isHandleNode(CompareConfig compareConfig) {
        return compareConfig.hasIngorePaths() ||
                (compareConfig.getPathConvertProcessors() != null && compareConfig.getPathConvertProcessors().size() > 0);
    }


    public static void exitNode(String nodeName) {
        ClassPathTraceTreeNode currentNode = findCurrentNode();
        if (currentNode != null) {
            ClassPathTraceTreeNode parentNode = currentNode.getParent();
            //ֻ��һ���ڵ�,ֱ������Ϊ��
            if (parentNode == null) {
                PathTrace.setVisitingNode(null);
            } else {
                //ɾ���ڵ��Ϊ�������
                parentNode.removeChild(currentNode);
                currentNode.setParent(null);
                PathTrace.setVisitingNode(parentNode);
            }
        }
    }

}
