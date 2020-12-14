package com.william.boss.utils;


import com.william.boss.exception.BeanOperationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Locale.ENGLISH;

/**
 * bean 操作工具类
 * 部分参考 spring BeanUtils工具类。
 * @author john
 */
public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
    private static final String GET_PREFIX = "get";
    /**
     * 如果有确定不需要copy的, 把Collections.emptyList() 换成 Arrays.asList("a","b") ,并把不需copy的属性塞进去
     */
    private static final List<String> IGNORE_PROPERTIES =  new ArrayList<>(Arrays.asList("createTime","password"));

    /**
     * 单个 bean 属性 copy
     * @param source 数据源
     * @param target 目标对象
     * @param ignoreProperties 声明式忽略的属性
     * @param excludeEmptyProperties 是否排除数据源里只为null的属性。true:排除  false:不排除
     */
    public static void copyProperties(Object source, Object target, List<String> ignoreProperties, boolean excludeEmptyProperties) {
        if (excludeEmptyProperties ) {
            List<String> emptyProperties = getEmptyProperties(source);
            ignorePropertiesWrapper(ignoreProperties, emptyProperties);
        }
        if (ignoreProperties != null) {
            IGNORE_PROPERTIES.addAll(ignoreProperties);
        }
        copyProperties(source, target);
    }

    /**
     * 集合 bean 属性 copy
     * @param sources 数据源
     * @param ignoreProperties 声明式忽略的属性
     * @param clazz 目标对象class
     * @return 目标对象集合
     */
    public static <T> List<T> copyProperties(List<?> sources, List<String> ignoreProperties, Class<T> clazz) {
        List<T> targets = new ArrayList<>();
        for (Object source:sources) {
            try {
                T target = clazz.newInstance();
                copyProperties(source, target, ignoreProperties, true);
                targets.add(target);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("target bean instancing error", e);
            }
        }
        return targets;
    }

    private static void copyProperties(Object source, Object target) {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        PropertyDescriptor[] targetPds = new PropertyDescriptor[0];
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            targetPds = beanInfo.getPropertyDescriptors();
            // 解决 @Accessors(chain = true) 注解导致获取不到set方法问题
            targetPdsWrapper(targetPds, beanInfo.getMethodDescriptors());
        } catch (IntrospectionException e) {
            logger.error("an exception happens during target class introspection ", e);
        }
        for (PropertyDescriptor targetPd : targetPds) {
            // 包含在忽略属性里,跳过继续执行
            if (IGNORE_PROPERTIES.contains(targetPd.getName())) {
                continue;
            }
            // 目标对象的set方法或者属性名称不存在,跳过继续执行
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod == null || StringUtils.isBlank(targetPd.getName())) {
                continue;
            }
            // 源对象的同名属性不存在,跳过继续执行
            PropertyDescriptor sourcePd = null;
            try {
                sourcePd = new PropertyDescriptor(targetPd.getName(), source.getClass(), GET_PREFIX + capitalize(targetPd.getName()), null);
            } catch (IntrospectionException e) {
                logger.error(MessageFormat.format("an exception happens during construct source propertyDescriptor for {0}", targetPd.getName()));
            }
            if (sourcePd == null) {
                continue;
            }

            Method readMethod = sourcePd.getReadMethod();
            if (readMethod != null && readMethod.getReturnType().isAssignableFrom(writeMethod.getParameterTypes()[0])) {
                try {
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(source);
                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                        writeMethod.setAccessible(true);
                    }
                    writeMethod.invoke(target, value);
                }
                catch (Throwable ex) {
                    throw new BeanOperationException(
                            "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                }
            }
        }
    }

    private static void targetPdsWrapper(PropertyDescriptor[] targetPds, MethodDescriptor[] methodDescriptors) {
        for(MethodDescriptor methodDescriptor:methodDescriptors) {
            if (isCandidateWriteMethod(methodDescriptor.getMethod())) {
                String propertyName = Introspector.decapitalize(methodDescriptor.getMethod().getName().substring(3));
                for (PropertyDescriptor propertyDescriptor:targetPds) {
                    if (propertyDescriptor.getName().equals(propertyName) && propertyDescriptor.getWriteMethod() == null) {
                        try {
                            propertyDescriptor.setWriteMethod(methodDescriptor.getMethod());
                        } catch (IntrospectionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    private static boolean isCandidateWriteMethod(Method method) {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        int nParams = parameterTypes.length;
        return (methodName.length() > 3 && methodName.startsWith("set") && Modifier.isPublic(method.getModifiers()) &&
                (!void.class.isAssignableFrom(method.getReturnType()) || Modifier.isStatic(method.getModifiers())) &&
                (nParams == 1 || (nParams == 2 && int.class == parameterTypes[0])));
    }

    private static List<String> getEmptyProperties(Object source) {
        List<String> emptyProperties = new ArrayList<>(10);
        try {
            PropertyDescriptor[] sourcePds = Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor sourcePd:sourcePds) {
                Method readMethod = sourcePd.getReadMethod();
                if (readMethod == null) {
                    emptyProperties.add(sourcePd.getName());
                    continue;
                }

                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                Object value = readMethod.invoke(source);
                if (value == null) {
                    emptyProperties.add(sourcePd.getName());
                }
            }

            return emptyProperties;
        } catch (Exception e) {
            return null;
        }
    }

    private static void ignorePropertiesWrapper(List<String> ignoreProperties, List<String> emptyProperties) {
        if (ignoreProperties != null) {
            IGNORE_PROPERTIES.addAll(ignoreProperties);
        }
        if (emptyProperties != null) {
            IGNORE_PROPERTIES.addAll(emptyProperties);
        }
    }

    public static void main(String[] args) {
        Source source = new Source();
        PlainBean bean = new PlainBean();
        bean.setPro1("beanPro2").setPro2("beanPro2");
        source.setPro1(null).setPro2("sPro2").setPro3(bean);
        Target target = new Target().setPro1("haveValue");
        BeanUtil.copyProperties(source, target, null, true);
        System.out.println(source);
        System.out.println(target);

//        System.out.println(void.class.getSimpleName());
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    private static class Source {
        private String pro1;

        private String pro2;

        private PlainBean pro3;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    private static class Target {
        private String pro1;

        private String pro2;

        private PlainBean pro3;

        private String pro4;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @ToString
    private static class PlainBean {
        private String pro1;

        private String pro2;
    }

}
