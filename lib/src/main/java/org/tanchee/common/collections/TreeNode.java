package org.tanchee.common.collections;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<D> {
    private final D data;
    private final List<TreeNode<?>> children;
    private TreeNode<?> parent;

    public TreeNode(TreeNode<?> parent, List<TreeNode<?>> children, D data) {
        this.data = data;
        this.parent = parent;
        this.children = children;
    }

    public TreeNode(TreeNode<?> parent, D data) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public TreeNode(D data) {
        this.data = data;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode<?> child) {
        children.add(child);
        child.setParent(this);
    }

    public D getData() { return this.data; } 

    public List<TreeNode<?>> getChildren() { return this.children; }

    private void setParent(TreeNode<?> parent) {
        if (parent != null)
            throw new IllegalStateException("TreeNode already has parent, can't reassign");
        this.parent = parent;
    }

    public TreeNode<?> getParent() { return this.parent; }
}
