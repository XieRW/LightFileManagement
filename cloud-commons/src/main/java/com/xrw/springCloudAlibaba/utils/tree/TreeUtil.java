package com.xrw.springCloudAlibaba.utils.tree;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.reflect.FieldUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author zhicaili
 * @description TODO 获取树形结构 使用本工具类，对应的返回的实体类必须要有三个字段 id, parentId ,children
 * 其中children不是数据库字段
 * @email zhicailiwork@163.com
 * @date 2019-05-25 11:24:55
 */
public class TreeUtil {


    /**
     * 在一个顺序存储的树中根据id查找一个节点，并返回祖籍
     *
     * @param list 树
     * @param id
     * @return java.util.List<T>
     * @author ljy
     * @date 2021/7/31 14:16
     */
    public static <T> List<T> findChildById(List<T> list, Object id, String idName, String parentName) throws NoSuchFieldException, IllegalAccessException {

        List<T> nodes = new ArrayList<>();

        if (list == null || list.size() == 0) {
            return nodes;
        }

        Class<?> aClass = list.get(0).getClass();
        Field idField = aClass.getDeclaredField(idName);
        idField.setAccessible(true);
        Field parentIdField = aClass.getDeclaredField(parentName);
        parentIdField.setAccessible(true);


        while (id != null) {
            Object finalTeamTypeId = id;
            Optional<T> first = list.stream().filter(i -> {
                try {
                    return idField.get(i).equals(finalTeamTypeId);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                }
            }).findFirst();
            if (first.isPresent()) {
                T t = first.get();
                id = parentIdField.get(t);
                nodes.add(t);
            } else {
                break;
            }
        }
        Collections.reverse(nodes);
        return nodes;
    }


    /**
     * 根据id获取树节点
     *
     * @param tree 树
     * @param id   id
     * @return java.util.List<T>
     * @author ljy
     * @date 2021/7/20 14:44
     */
    public static <T> T findById(List<T> tree, Serializable id) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        if (tree == null) {
            return null;
        }
        for (T t : tree) {
            Object key = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(t, "id");
            if (key.equals(id)) {
                return t;
            }
            List<T> child = (List<T>) BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(t, "children");
            T find = findById(child, id);

            if (find != null) {
                return find;
            }
        }

        return null;
    }


    /**
     * cwy list-----长度为0的列表 tree-----要转的树 将树转列表
     *
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<T> treeToList(List<T> list, List<T> tree) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (tree != null && tree.size() > 0) {
            for (T t : tree) {
                List<T> children = (List<T>) t.getClass().getMethod("getChildren").invoke(t);
                Method setChildren = t.getClass().getMethod("setChildren", List.class);
                setChildren.invoke(t, (List) null);
                list.add(t);
                treeToList(list, children);
            }
        }
        return list;
    }

    /**
     * 把列表转换为树结构
     *
     * @param originalList 原始list数据
     * @return 组装后的集合
     */
    public static <T> List<T> getTree(List<T> originalList) throws Exception {
        String keyName = "id";
        String parentFieldName = "parentId";
        String childrenFieldName = "children";
        return getTree(originalList, keyName, parentFieldName, childrenFieldName);
    }

    public static <T> List<T> getHuaweiTree(List<T> originalList) throws Exception {
        String keyName = "groupId";
        String parentFieldName = "parentId";
        String childrenFieldName = "children";
        return getTree(originalList, keyName, parentFieldName, childrenFieldName);
    }

    public static <T> List<T> getTree(List<T> originalList, String keyName, String parentFieldName,
                                      String childrenFieldName) throws Exception {
//        String keyName = "id";
//        String parentFieldName = "parentId";
//        String childrenFieldName = "children";
        // 获取根节点，即找出父节点为空的对象
        List<T> topList = new ArrayList<>();
        for (int i = 0; i < originalList.size(); i++) {
            T t = originalList.get(i);
            String parentId = BeanUtils.getProperty(t, parentFieldName);
            if ("0".equals(parentId)) {
                topList.add(t);
            }
        }

        // 将根节点从原始list移除，减少下次处理数据
        originalList.removeAll(topList);

        // 递归封装树
        fillTree(topList, originalList, keyName, parentFieldName, childrenFieldName);

        return topList;
    }

    /**
     * 把列表转换为树结构
     *
     * @param originalList 原始list数据
     * @return 组装后的集合
     */
    public static <T> List<T> getTree(List<T> originalList, Long topId) throws Exception {
        String keyName = "id";
        String parentFieldName = "parentId";
        String childrenFieldName = "children";
        return getTree(originalList, keyName, parentFieldName, childrenFieldName, topId);
    }

    public static <T> List<T> getTree(List<T> originalList, String keyName, String parentFieldName,
                                      String childrenFieldName, Long topId) throws Exception {
//        String keyName = "id";
//        String parentFieldName = "parentId";
//        String childrenFieldName = "children";
        // 获取根节点，即找出父节点为空的对象
        List<T> topList = new ArrayList<>();
        for (int i = 0; i < originalList.size(); i++) {
            T t = originalList.get(i);
            String parentId = BeanUtils.getProperty(t, parentFieldName);
            if (topId.equals(parentId)) {
                topList.add(t);
            }
        }

        // 将根节点从原始list移除，减少下次处理数据
        originalList.removeAll(topList);

        // 递归封装树
        fillTree(topList, originalList, keyName, parentFieldName, childrenFieldName);

        return topList;
    }

    /**
     * 封装树
     *
     * @param parentList        要封装为树的父对象集合
     * @param originalList      原始list数据
     * @param keyName           作为唯一标示的字段名称
     * @param parentFieldName   模型中作为parent字段名称
     * @param childrenFieldName 模型中作为children的字段名称
     */
    public static <T> void fillTree(List<T> parentList, List<T> originalList, String keyName, String parentFieldName,
                                    String childrenFieldName) throws Exception {
        for (int i = 0; i < parentList.size(); i++) {
            List<T> children = fillChildren(parentList.get(i), originalList, keyName, parentFieldName,
                    childrenFieldName);
            if (children.isEmpty()) {
                continue;
            }
            originalList.removeAll(children);
            fillTree(children, originalList, keyName, parentFieldName, childrenFieldName);
        }
    }

    /**
     * 封装子对象
     *
     * @param parent            父对象
     * @param originalList      待处理对象集合
     * @param keyName           作为唯一标示的字段名称
     * @param parentFieldName   模型中作为parent字段名称
     * @param childrenFieldName 模型中作为children的字段名称
     */
    public static <T> List<T> fillChildren(T parent, List<T> originalList, String keyName, String parentFieldName,
                                           String childrenFieldName) throws Exception {
        List<T> childList = new ArrayList<>();
        String parentId = BeanUtils.getProperty(parent, keyName);
        for (int i = 0; i < originalList.size(); i++) {
            T t = originalList.get(i);
            String childParentId = BeanUtils.getProperty(t, parentFieldName);
            if (parentId.equals(childParentId)) {
                childList.add(t);
            }
        }
        if (!childList.isEmpty()) {
            FieldUtils.writeDeclaredField(parent, childrenFieldName, childList, true);
        }
        return childList;
    }







    /**
     * 根据列表根父id，获取到这个列表里面所有的子孙id
     *
     * @param listAll
     * @param id
     * @param ids
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> List<Long> getSonIds(List<T> listAll, Long id, List<Long> ids) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<Long> list = recursionGetIds(listAll, id, ids);
        return list;
    }

    private static <T> List<Long> recursionGetIds(List<T> list, Long id, List<Long> ids) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (T p : list) {
            String parentId = BeanUtils.getProperty(p, "parentId");
            if (parentId.equals(id)) {
                String idvalue = BeanUtils.getProperty(p, "id");
                recursionGetIds(list, Long.valueOf(idvalue), ids);
                ids.add(Long.valueOf(idvalue));
            }
        }
        return ids;
    }


    /**
     * @author cwy
     * 2020-11-20
     * 从一个列表里面获取到某个点下所有的子孙节点的id
     * list----列表
     * id---节点id
     * ids--空列表
     **/
    public static <T> List<Long> getAllSonIds(List<T> list, Long id, List<Long> ids) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ids.add(id);
        for (T p : list) {
            String parentId = BeanUtils.getProperty(p, "parentId");
            if (parentId.equals("" + id)) {
                String idvalue = BeanUtils.getProperty(p, "id");
                getAllSonIds(list, Long.valueOf(idvalue), ids);
                ids.add(Long.valueOf(idvalue));
            }
        }
        return ids;
    }


    /**
     * @author cwy
     * 2020-11-20
     * 从一个列表里面获取到某个点所有父级节点包裹他自己
     * list----列表
     * id---节点id
     * ids--空列表
     **/
    public static <T> List<Long> getAllParentIds(List<T> list, Long id, List<Long> ids) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ids.add(id);
        for (T p : list) {
            String parentId = BeanUtils.getProperty(p, "parentId");
            String targetId = BeanUtils.getProperty(p, "id");
            if (targetId.equals(id + "") && !parentId.equals("0")) {
                getAllParentIds(list, Long.valueOf(parentId), ids);
            }

        }
        return ids;
    }
}
