package com.df4j.xcoria.tree;

import com.df4j.xcoria.utils.PojoUtils;

import java.util.List;

public class TreeNode<ID, REF> {
    // id
    private ID id;
    // 上级id
    private ID parentId;
    // 名称
    private String name;
    // 代码
    private String code;
    // 引用的原记录
    private REF ref;
    // 排序值 默认 从小到大排序
    private int orderNum = 0;
    // 上级
    private TreeNode<ID, REF> parent;
    // 下级
    private List<TreeNode<ID, REF>> children;

    public TreeNode() {

    }

    public TreeNode(ID id, ID parentId, String name, String code, REF ref, int orderNum) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.code = code;
        this.ref = ref;
        this.orderNum = orderNum;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getParentId() {
        return parentId;
    }

    public void setParentId(ID parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public REF getRef() {
        return ref;
    }

    public void setRef(REF ref) {
        this.ref = ref;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public TreeNode<ID, REF> getParent() {
        return parent;
    }

    public void setParent(TreeNode<ID, REF> parent) {
        this.parent = parent;
    }

    public List<TreeNode<ID, REF>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<ID, REF>> children) {
        this.children = children;
    }

    @SuppressWarnings("EqualsDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return PojoUtils.safeEquals(this, obj);
    }
}
