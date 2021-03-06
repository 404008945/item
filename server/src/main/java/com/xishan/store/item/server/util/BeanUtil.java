package com.xishan.store.item.server.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class BeanUtil {

    /**
     * 功能描述：浅拷贝 - 转换Bean对象
     *
     * @param sourceObject
     * @param clazz
     * @return
     * @author Elivense White
     */
    public static <T> T convertToBean(Object sourceObject, Class<T> clazz) {
        T result = null;
        if (sourceObject != null) {
            try {
                result = clazz.newInstance();
            } catch (Exception e) {
                log.error("convert error", e);
                e.printStackTrace();
            }
            BeanUtils.copyProperties(sourceObject, result);
        }
        return result;
    }


    /**
     * 功能描述：浅拷贝 - 转换List<bean> 对象
     *
     * @param sourceList
     * @param clazz
     * @return
     * @author Elivense White
     */
    public static <T> List<T> convertToBeanList(List<?> sourceList, Class<T> clazz) {
        List<T> resultList = null;
        try {
            if (sourceList != null && sourceList.size() > 0) {
                resultList = new ArrayList<T>();

                Iterator<?> iterator = sourceList.iterator();
                while (iterator.hasNext()) {
                    T t = clazz.newInstance();
                    Object sourceObject = iterator.next();
                    BeanUtils.copyProperties(sourceObject, t);
                    resultList.add(t);
                }
            }
        } catch (Exception e) {
            log.error("convert error", e);
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }


}