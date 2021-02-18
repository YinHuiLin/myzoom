package com.lins.myzoom.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyBeanUtils
 * @Description TODO
 * @Author lin
 * @Date 2021/2/6 16:07
 * @Version 1.0
 **/
public class MyBeanUtils {
    //以数组的形式获取所有属性值为空的属性名
    public static String[] getNullPropertyNames(Object source){
        BeanWrapper beanWrapper=new BeanWrapperImpl(source);
        PropertyDescriptor[] pds=beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames=new ArrayList<>();
        for(PropertyDescriptor pd:pds){
            if (beanWrapper.getPropertyValue(pd.getName())==null){
                nullPropertyNames.add(pd.getName());
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
