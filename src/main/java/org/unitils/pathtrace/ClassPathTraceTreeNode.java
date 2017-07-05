package org.unitils.pathtrace;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 用于在比较过程中类路径的标记
 * 例如：a.b.c.a //类似于jsonpath
 */
public class ClassPathTraceTreeNode {

    private ClassPathTraceTreeNode parent;

    private String nodeName;

    private List<ClassPathTraceTreeNode> children = new LinkedList<ClassPathTraceTreeNode>();

    public ClassPathTraceTreeNode(ClassPathTraceTreeNode parent, String nodeName) {
        this.parent = parent;
        this.nodeName = nodeName;
    }

    public void addChild(ClassPathTraceTreeNode node) {
        children.add(node);
    }

    public void removeChild(ClassPathTraceTreeNode node) {
        ClassPathTraceTreeNode dNode = null;
        for (ClassPathTraceTreeNode child : children) {
            if (child.getNodeName().equals(node.getNodeName())) {
                dNode = child;
            }
        }
        if (dNode != null) {
            children.remove(dNode);
        }
    }


    public String getPathDesc() {
        ClassPathTraceTreeNode parent = null;
        ClassPathTraceTreeNode tmp = this;
        List<String> pathNodeNames = new ArrayList<String>();
        pathNodeNames.add(nodeName);
        while ((parent = tmp.getParent()) != null) {
            pathNodeNames.add(parent.getNodeName());
            tmp = parent;
        }
        Collections.reverse(pathNodeNames);
        return StringUtils.join(pathNodeNames, ".");
    }


    public List<ClassPathTraceTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<ClassPathTraceTreeNode> children) {
        this.children = children;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public ClassPathTraceTreeNode getParent() {
        return parent;
    }

    public void setParent(ClassPathTraceTreeNode parent) {
        this.parent = parent;
    }

}
