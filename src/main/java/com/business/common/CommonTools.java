package com.business.common;

import com.jokers.common.http.HttpUtil;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;


/**
 * @author yuton
 * @version 1.0
 * @description 公共校验方法
 * @since 2016/8/2 12:06
 */
@Slf4j
public class CommonTools {


    /**
     * 遍历Map并除去空值
     *
     * @param reqMap {@link Map}
     * @return {@link Map}
     */
    public static <K, V> Map<K, V> checkParamsAndDelEmpty(Map<K, V> reqMap) {
        reqMap.entrySet().stream().filter(map -> null == map.getValue()).forEach(map -> reqMap.remove(map.getKey()));
        return reqMap;
    }

    /***
     * 判断字符串是否只有数字及字母
     * @param str {@link String}
     * @return {@link boolean}
     */
    public static boolean isNumOrChar(String str) {
        return str.matches("^[a-z0-9A-Z]+$");
    }

    /**
     * @param n int
     * @description 生成若干位随机数字字符
     */
    public static String getRandomNum(int n) {
        return RandomStringUtils.randomNumeric(n);
    }

    /***
     * 随机若干位字符串只含字母
     * @param n {@link int}
     * @return {@link String}
     */
    public static String getRandomStr(int n) {
        return RandomStringUtils.randomAlphabetic(n);
    }

    /***
     * 检查一个数组中是否包含某个特定的值
     * @param arr {@link String []}
     * @param targetValue {@link String}
     * @return boolean
     */
    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param str {@link String}
     * @return {@link boolean}
     * 判断字符串是否为数字
     */
    public static boolean isNumber(String str) {
        return str.matches("[0-9]*");
    }

    /**
     * @param phone {@link String}
     * @return {@link boolean}
     * @description 判断手机号是否合法
     */
    public static boolean isPhone(String phone) {
        return StringUtils.isNotBlank(phone) && phone.matches("^[1][3,4,5,7,8][0-9]{9}$");
    }

    /**
     * @param email {@link String}
     * @return {@link boolean}
     * @description 判断邮箱号是否合法
     */
    public static boolean isEmail(String email) {
        return StringUtils.isNotBlank(email) && email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    /**
     * @param name {@link String}
     * @return String
     * getFileSuffix
     * 获取文件后缀，返回如：.jpg
     */
    public static String getFileSuffix(String name) {
        int loc = name.lastIndexOf(46);
        return loc != -1 ? name.substring(loc) : null;
    }

    /**
     * @param path {@link String}
     * @return void
     * makeDir
     * 创建目录，如果存在则不创建
     */
    public static boolean makeDir(String path) {
        return new File(path).mkdirs();
    }


    /***
     * 隐藏号码
     * @param param {@link String}
     * @return {@link String}
     */
    public static String putConcealParam(String param) {
        return param.substring(0, 3) + "****" + param.substring(param.length() - 4, param.length());
    }

    /***
     * Map转Bean
     * @param tClass {@link Class<T>}
     * @param map {@link Map}
     * @param <T> {@link T}
     * @return {@link T}
     */
    public static <T> T mapToBean(Class<T> tClass, Map<String, Object> map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
        T bean = tClass.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        if (propertyDescriptors != null && propertyDescriptors.length > 0) {
            String propertyName; // javaBean属性名
            Object propertyValue; // javaBean属性值
            for (PropertyDescriptor pd : propertyDescriptors) {
                propertyName = pd.getName();
                if (map.containsKey(propertyName)) {
                    propertyValue = map.get(propertyName);
                    pd.getWriteMethod().invoke(bean, propertyValue);
                }
            }
            return bean;
        }

        return null;
    }

    /**
     * @return {@link String}
     * @description 获取本机公网ip
     */
    public static String getLocalPublicIp() {
        String html = HttpUtil.httpGet(HttpUtil.SELECT_PUBLIC_IP_ADDRESS);
        return html.substring(html.indexOf("[") + 1, html.indexOf("]"));
    }

    /**
     * @return {@link String}
     * @description 获取本机操作系统名称
     */
    public static String getLocalOS() {
        return SystemUtils.OS_NAME;
    }

    /**
     * @return {@link Long}
     * @description 获取当前系统中 JVM最大可用堆空间
     */
    public static Long getJVMUsableMemory() {
        long max = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        return max - total + free;
    }
}
