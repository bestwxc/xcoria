package com.df4j.xcoria.tree;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

public class TreeUtils {

    /**
     * 将list组装为Tree
     *
     * @param list       待组装数据
     * @param nodeSetter 节点转换
     * @param <ID>       ID类型
     * @param <REF>      数据类型
     * @return 树列表
     */
    public static <ID, REF> List<TreeNode<ID, REF>> buildTree(final Collection<REF> list,
                                                              final BiConsumer<TreeNode<ID, REF>, REF> nodeSetter) {
        List<TreeNode<ID, REF>> resultList = new ArrayList<>();
        // 转换为TreeNode,放入map
        Map<ID, TreeNode<ID, REF>> nodes = list.stream()
                .map(x -> {
                    TreeNode<ID, REF> treeNode = new TreeNode<>();
                    nodeSetter.accept(treeNode, x);
                    treeNode.setRef(x);
                    return treeNode;
                }).collect(toMap(TreeNode::getId, Function.identity()));
        // 分组并排序的结果
        Map<ID, List<TreeNode<ID, REF>>> groups = nodes.values().stream()
                .collect(
                        groupingBy(
                                TreeNode::getParentId,
                                collectingAndThen(
                                        toList(),
                                        l -> l.stream()
                                                .sorted(Comparator.comparingInt(TreeNode::getOrderNum))
                                                .collect(toList())
                                )
                        )
                );
        Set<ID> keySet = new HashSet<>(nodes.keySet());
        for (ID id : groups.keySet()) {
            List<TreeNode<ID, REF>> groupList = groups.get(id);
            TreeNode<ID, REF> parentNode = nodes.get(id);
            if (parentNode != null) {
                for (int i = 0; i < groupList.size(); i++) {
                    TreeNode<ID, REF> c = groupList.get(i);
                    parentNode.getChildren().add(c);
                    c.setParent(parentNode);
                    keySet.remove(c.getId());
                }
            }
        }
        for (ID id : keySet) {
            resultList.add(nodes.get(id));
        }
        return resultList;
    }

    /**
     * 查找指定的node
     *
     * @param tree      带查找的树
     * @param predicate 匹配条件
     * @param <ID>      ID类型
     * @param <REF>     数据类型
     * @return 查找结果
     */
    public static <ID, REF> TreeNode<ID, REF> findNode(TreeNode<ID, REF> tree, Predicate<TreeNode<ID, REF>> predicate) {
        TreeNode<ID, REF> matchNode = null;
        if (predicate.test(tree)) {
            matchNode = tree;
        } else {
            List<TreeNode<ID, REF>> list = tree.getChildren();
            for (TreeNode<ID, REF> node : list) {
                matchNode = findNode(node, predicate);
                if (matchNode != null) {
                    break;
                }
            }
        }
        return matchNode;
    }

    /**
     * 找寻所有的父节点
     *
     * @param child 待查找节点
     * @param <ID>  ID类型
     * @param <REF> 引用类型
     * @return 所有的父节点列表
     */
    public static <ID, REF> List<TreeNode<ID, REF>> findAllParent(TreeNode<ID, REF> child) {
        List<TreeNode<ID, REF>> res = new ArrayList<>();
        TreeNode<ID, REF> cur = child;
        if (cur != null) {
            while ((cur = cur.getParent()) != null) {
                res.add(cur);
            }
        }
        return res;
    }

    /**
     * 将treeNode转换为map树，用于输出
     *
     * @param node      需要处理的树
     * @param refParser ref类型转换为map的方法
     * @param <ID>      ID类型
     * @param <REF>     REF类型
     * @return map表示的树
     */
    public static <ID, REF> Map<String, Object> parseMap(TreeNode<ID, REF> node, Function<REF, Map<String, Object>> refParser) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", node.getId());
        map.put("parentId", node.getParentId());
        map.put("name", node.getName());
        map.put("code", node.getCode());
        map.put("orderNum", node.getOrderNum());
        REF ref = node.getRef();
        if (ref != null) {
            Map<String, Object> refMap = refParser.apply(ref);
            map.putAll(refMap);
        }
        List<TreeNode<ID, REF>> children = node.getChildren();
        List<Map<String, Object>> parsedChildren = new ArrayList<>();
        if (children != null && !children.isEmpty()) {
            for (TreeNode<ID, REF> child : children) {
                Map<String, Object> childMap = parseMap(child, refParser);
                parsedChildren.add(childMap);
            }
        }
        map.put("children", parsedChildren);
        return map;
    }
}
