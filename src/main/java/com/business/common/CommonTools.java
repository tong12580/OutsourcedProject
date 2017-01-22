package com.business.common;

import com.business.common.http.HttpUtil;
import com.business.common.json.JsonUtil;
import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * Created by yuton on 2016/8/2.
 */
@Slf4j
public class CommonTools {
    private static final String PATTERN_HAVE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_DAY = "yyyy-MM-dd";
    private static final String PATTERN_NOT_HAVE_TIME = "yyyy-MM-dd 00:00:00";
    private static final String LINUX = "Linux";
    private static final String WINDOWS = "Windows";

    /**
     * 判断对象是否为空
     *
     * @param obj {@link Object}
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * 判断对象数组是否为空
     *
     * @param objects {@link Object[]}
     * @return boolean
     */
    public static boolean isEmpty(Object[] objects) {
        if (null == objects || objects.length == 0) return true;
        for (Object object : objects) {
            if (isEmpty(object)) return true;
        }
        return false;
    }

    /**
     * 判断整形是否为空或0
     *
     * @param integer {@link Integer}
     * @return
     */
    public static boolean isEmpty(Integer integer) {
        return null == integer || 0 == integer;
    }

    /**
     * 字符是否为空或"null"
     *
     * @param str {@link String}
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 字符是否为空或"null"或空白条
     *
     * @param str {@link String}
     * @return boolean
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * List是否为空
     *
     * @param list {@link List}
     * @return boolean
     */
    public static <T> boolean isEmpty(List<T> list) {
        return CollectionUtils.isEmpty(list);
    }

    /**
     * Map是否为空
     *
     * @param map {@link Map}
     * @return boolean
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return CollectionUtils.isEmpty(map);
    }

    /**
     * @param strings
     * @return boolean
     * @Title: isEmpty
     * @Description: 判断字符串是否为空
     */
    public static boolean isEmpty(String... strings) {
        if (isEmpty(strings) || 0 == strings.length) {
            return true;
        }
        for (String string : strings) {
            isBlank(string);
        }
        return false;
    }

    /**
     * @param integers
     * @return boolean
     * @Title isEmpty
     * @Description: 判断intArry是否为空
     */
    public static boolean isEmpty(Integer... integers) {
        if (isEmpty(integers) || 0 == integers.length) {
            return true;
        }
        for (Integer integer : integers) {
            if (isEmpty(integer)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 遍历Map并除去空值
     *
     * @param reqMap {@Link Map}
     * @return
     */
    public static <K, V> Map<K, V> checkParamsAndDelEmpty(Map<K, V> reqMap) {
        reqMap.entrySet().stream().filter(map -> isEmpty(map.getValue())).forEach(map -> reqMap.remove(map.getKey()));
        return reqMap;
    }

    /***
     * 判断字符串是否只有数字及字母
     * @param str {@Link String}
     * @return
     */
    public static boolean isNumChar(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (((c < 'a') || (c > 'z')) && ((c < 'A') || (c > 'Z'))) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /***
     * 判断是否为整数
     * @param str {@Link String}
     * @return
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /***
     * 判断是否为浮点数
     * @param str {@link String}
     * @return
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断是否为正确日期
     *
     * @param str，格式为：yyyy-MM-dd HH:mm:ss {@link String}
     * @return
     */
    public static boolean isDate(String str) {

        try {
            DateFormat f = new SimpleDateFormat(PATTERN_HAVE_TIME);
            Date d = f.parse(str);
            String s = f.format(d);
            return s.equals(str);
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * 不大于0
     *
     * @param number
     * @return
     */
    public static boolean notGreaterZero(BigDecimal number) {
        return number.compareTo(new BigDecimal(0)) < 1;
    }

    /**
     * 是否大0
     *
     * @param number
     * @return
     */
    public static boolean isGreaterZero(BigDecimal number) {
        return number.compareTo(new BigDecimal(0)) > 0;
    }

    /**
     * 生成 pwdLength长度的随机码
     *
     * @param a
     * @param pwdLength
     * @return int
     */
    public static int getRandomCode(int a, int pwdLength) {
        Random random = new Random();
        int b = random.nextInt(10);
        a = a * 10 + b;
        if (a < 100000) {
            return getRandomCode(a, pwdLength);
        }
        return a;
    }

    /**
     * @return int
     * @Title: getCode6
     * @Description: 获取6位随机数
     * @date 2014-9-2 下午07:15:07
     */
    public static int getRandomCode6() {
        int intCount = (new Random()).nextInt(999999);// 最大值位9999
        if (intCount < 100000)
            intCount += 100000; // 最小值位10000001
        return intCount;
    }

    /***
     * 随机数
     * @param n
     * @return
     */
    public static String getRandStr(int n) {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < n; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand = sRand + rand;
        }
        return sRand;
    }

    /***
     * 字符转数值 int
     * 判断是否为0
     * @param str
     * @return int
     */
    public static int parseInt(String str) {
        return isEmpty(str) ? 0 : Integer.parseInt(str.trim());
    }

    /***
     * 字符转数值 Integer
     * 判断是否为空
     * @param intstr
     * @return Integer
     */
    public static Integer parseInteger(String intstr) {
        return isEmpty(intstr) ? null : Integer.parseInt(intstr.trim());
    }

    /***
     * 对象转数值  Integer
     * @param object
     * @return Integer
     */
    public static Integer parseInteger(Object object) {
        if (isEmpty(object)) return null;
        return Integer.parseInt(object.toString());
    }

    /***
     * 字符转数值float
     * @param intstr
     * @return float
     */
    public static float parseFloat(String intstr) {
        return isEmpty(intstr) ? 0.0F : Float.parseFloat(intstr.trim());
    }

    /***
     * 对象转数值Float
     * @param object
     * @return Float
     */
    public static Float parseFloat(Object object) {
        if (isEmpty(object)) return null;
        return Float.parseFloat(object.toString());
    }

    /***
     * 字符转数值double
     * @param str
     * @return double
     */
    public static double parseDouble(String str) {
        return isEmpty(str) ? 0.0D : Double.parseDouble(str.trim());
    }

    /***
     * 对象转数值Double
     * @param object
     * @return Double
     */
    public static Double parseDouble(Object object) {
        if (isEmpty(object)) return null;
        return Double.parseDouble(object.toString());
    }

    /***
     * 检查一个数组中是否包含某个特定的值
     * @param arr
     * @param targetValue
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

    /***
     * 正则校验(入参 params 不能为 null)
     * @param params      入参
     * @param macroDefine 宏定义（正则表达式）
     * @return boolean
     */
    public static boolean regExpCheck(String params, String macroDefine) {
        return params.matches(macroDefine);
    }

    /***
     * 判断两个时间是否相同
     * @param date1 等待比较第一个时间
     * @param date2 等待比较第二个时间
     * @return 比较结果
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    /***
     * 比较两个日历类数据是否相同
     * @param cal1 比较第一个日历类
     * @param cal2 比较第二个日历类
     * @return 比较结果
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameDay(cal1, cal2);
    }

    /***
     * 新增年份
     * @param date 需要新增时间
     * @param year 增加年份
     * @return 增加后年份
     */
    public static Date addYears(Date date, int year) {
        return DateUtils.addYears(date, year);
    }

    /***
     * 对时间格式进行格式化
     * @param date 时间类型
     * @return yyyy-MM-dd {@link String}
     */
    public static String format(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_DATE_FORMAT
                .getPattern());
    }

    /***
     * 对时间格式进行格式化
     * @param date 时间类型
     * @return yyyy-MM-dd'T'HH:mm:ss {@link String}
     */
    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_DATETIME_FORMAT
                .getPattern());
    }

    /***
     * 格式化时间
     * @param date    时间参数
     * @param pattern 格式化参数类型
     * @return {@link String}
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /***
     * 格式化时间参数
     * @param date 时间参数
     * @return HH:mm:ss {@link String}
     */
    public static String formatTime(Date date) {
        return DateFormatUtils.format(date,
                DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern());
    }

    /***
     * 当前时间
     * @return {@link String}
     */
    public static String getCurDatetime() {
        return format(new Date(), PATTERN_HAVE_TIME);
    }

    /***
     * 当前时间
     * @return {@link String}
     */
    public static String getCurDatetime(String pattern) {
        return format(new Date(), pattern);
    }

    /***
     * 格式化时间
     * @param date yyyy-MM-dd
     * @return {@link Date}
     */
    public static Date getCurDate(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /***
     * 增加月份
     * @param date  传入时间
     * @param month 需要增加月份
     * @return 增加月份
     */
    public static Date addMonths(Date date, int month) {
        return DateUtils.addMonths(date, month);
    }

    /***
     * 增加周
     * @param date   当前时间
     * @param amount 需要增加周
     * @return 增加后时间
     */
    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /***
     * 增加天
     * @param date   当前时间
     * @param amount 需要增加天数
     * @return 增加后时间
     */
    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    /***
     * 增加小时
     * @param date   当前时间
     * @param amount 增加小时数
     * @return 增加后时间
     */
    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /***
     * 增加分钟
     * @param date   当前时间
     * @param amount 增加分钟数
     * @return 增加后时间
     */
    public static Date addMinutes(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /***
     * 增加秒
     * @param date   当前时间
     * @param amount 增加秒数
     * @return 增加后时间
     */
    public static Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /***
     * 添加毫秒
     * @param date   当前时间
     * @param amount 增加毫秒
     * @return 增加后时间
     */
    public static Date addMilliseconds(Date date, int amount) {
        return DateUtils.addMilliseconds(date, amount);
    }


    /***
     * 判断是否是周末
     * @param date
     * @return
     */
    public static boolean isWeekEnd(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;
        return false;
    }

    /***
     * 当前日期前n个交易日
     * @param date
     * @return
     */
    public Date tradingTomorrowDay(Date date, int n) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }

    /***
     * 计算两个时间相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        Long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return between_days.intValue();
    }

    /***
     * 计算两个时间相差的天数 先格式化时间
     * @param date1
     * @param date2
     * @return {@link Integer}
     */
    public static int daysBetween(Date date1, Date date2, String pattern) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        date1 = getCurDate(format(date1, pattern), pattern);
        date2 = getCurDate(format(date2, pattern), pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param time1
     * @param time2
     * @return int
     * @Title: getTimestamp
     * @Description: 获取1-2的时间差，单位为ms
     */
    public static int getTimestamp(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(time1);
        long m1 = cal.getTimeInMillis();
        cal.setTime(time2);
        long m2 = cal.getTimeInMillis();
        long between_sec = (m2 - m1);
        return Integer.parseInt(String.valueOf(between_sec));
    }


    /**
     * @return boolean
     * @Title: isNumeric
     * @Description: 判断字符串是否为数字
     * @Param @param str
     * @Param @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断手机号是否合法
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (isBlank(phone)) {
            return false;
        }
        return regExpCheck(phone, "^[1][3,4,5,7,8][0-9]{9}$");
    }

    /**
     * 判断邮箱号是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        return regExpCheck(email, "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    /**
     * @param name
     * @return String
     * @Title: getFileSuffix
     * @Description: 获取文件后缀，返回如：.jpg
     */
    public static String getFileSuffix(String name) {
        int loc = name.lastIndexOf('.');
        if (loc != -1) {
            return name.substring(loc);
        }
        return null;
    }

    /**
     * @return String
     * @Title: getAgeByBirthday
     * @Description: 计算年龄
     */
    public static String getAgeByBirthday(Date birthday) {
        int days = daysBetween(birthday, new Date());
        int year = days / 365;
        int month = (days % 365) / 30;
        String age = "";
        if (year > 0) {
            age = year + "岁";
            if (month > 0) {
                age = age + month + "个月";
            }
        } else if (month > 0) {
            age = month + "个月";
        }
        return age;
    }

    /**
     * @param path
     * @return void
     * @Title: makeDir
     * @Description: 创建目录，如果存在则不创建
     */
    public static boolean makeDir(String path) {
        return new File(path).mkdirs();
    }


    /***
     * 隐藏号码
     * @param param
     * @return
     */
    public static String putConcealParam(String param) {

        String str = param;
        str = str.substring(0, 3) + "****" + str.substring(str.length() - 4, str.length());
        return str;
    }

    /***
     * Map转Bean
     * @param tClass
     * @param map
     * @param <T>
     * @return
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

    /***
     * UUid 字串
     */
    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 获取短UUid
     *
     * @return
     */
    public static String getShortUUid() {
        StringBuilder shortUUid = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortUUid.append(chars[x % 0x3E]);
        }
        return shortUUid.toString();

    }

    /***
     * json to bean
     * @param json
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T getBean(String json, Class<T> tClass) throws IOException {
        return JsonUtil.jsonToBean(json, tClass, PATTERN_HAVE_TIME);
    }

    /***
     * 成功提示 无返回参数
     * @param resultMessage {@link ResultMessage}
     * @return {@link IResult}
     */
    public static IResult successResult(ResultMessage resultMessage) {
        return new Result(resultMessage);
    }

    /***
     * 成功提示 有返回
     * @param resultMessage {@link ResultMessage}
     * @param result {@link T}
     * @param <T>
     * @return {@link IResult}
     */
    public static <T> IResult<T> successResult(ResultMessage resultMessage, T result) {
        return new Result<>(resultMessage, result);
    }


    /***
     * 错误提示 无返回
     * @param resultMessage {@link ResultMessage}
     * @return {@link IResult}
     */
    public static IResult errorResult(ResultMessage resultMessage) {
        return new Result<>(resultMessage);
    }

    /***
     * 错误提示
     * @param code
     * @param msg
     * @return {@link IResult}
     */
    public static IResult errorResult(int code, String msg) {
        IResult result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /***
     * 错误提示
     * @param resultMessage {@link ResultMessage}
     * @return {@link IResult}
     */
    public static IResult errorResult(ResultMessage resultMessage, String specificMsg) {
        IResult result = new Result();
        specificMsg = isBlank(specificMsg) ? "" : specificMsg;
        result.setCode(resultMessage.getCode());
        result.setMsg(resultMessage.getMsg().replace("{}", specificMsg));
        return result;
    }

    /**
     * @return
     * @description 获取本机公网ip
     */
    public static String getLocalPublicIp() {
        String html = HttpUtil.httpGet(HttpUtil.SELECT_PUBLIC_IP_ADDRESS);
        return html.substring(html.indexOf("[") + 1, html.indexOf("]"));
    }


    /**
     * @return
     * @description 获取本机操作系统名称
     */
    public static String getLocalOS() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith(WINDOWS)) {
            return WINDOWS;
        } else {
            return LINUX;
        }
    }

    /**
     * @return
     * @description 获取当前系统中 JVM最大可用堆空间
     */
    public static Long getJVMUsableMemory() {
        long max = Runtime.getRuntime().maxMemory();
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        return max - total + free;
    }
}
