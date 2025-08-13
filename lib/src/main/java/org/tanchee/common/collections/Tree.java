package org.tanchee.common.collections;

public class Tree {

    private TreeNode<?> root;

    private Tree() {}

    private Tree(TreeNode<?> root) {
        this.root = root;
    }

    public static <D> Tree takeRoot(D data) {
        TreeNode<D> rootNode = new TreeNode<>(data);
        return new Tree(rootNode);
    }

}
