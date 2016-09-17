package com.business.common;

import com.business.common.json.FastjsonUtil;
import com.business.common.message.ExceptionMessage;
import com.business.common.message.IDefineMsg;
import com.business.common.response.IResult;
import com.business.common.response.Result;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yuton on 2016/8/2.
 */
@Slf4j
public class CommonTools {
    /**
     * 判断对象是否为空
     *
     * @param obj {@link Object}
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        return false;
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

        if (null == integer) return true;
        if (integer instanceof Integer) {
            if (0 == integer) return true;
        }

        return false;
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
        if (list == null) return true;
        if (list.size() < 1) return true;
        if (list.isEmpty()) return true;
        return false;
    }

    /**
     * Map是否为空
     *
     * @param map {@link Map}
     * @return boolean
     */
    public static <T> boolean isEmpty(Map<T, T> map) {
        if (map == null) return true;
        if (map.size() < 1) return true;
        if (map.isEmpty()) return true;
        return false;
    }

    /**
     * @param strings
     * @return boolean
     * @Title: isEmpty
     * @Description: 判断字符串是否为空
     */
    public static boolean isEmpty(String... strings) {
        if (isEmpty(strings) || strings.length == 0) {
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

        if (isEmpty(integers) || integers.length == 0) {
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
     * 遍历校验Map中的空值
     *
     * @param reqMap {@Link Map}
     * @return IResult
     */
    public static IResult checkParamsEmpty(Map<String, Object> reqMap) {

        for (Map.Entry<String, Object> map : reqMap.entrySet()) {
            if (isEmpty(map.getValue()))
                return makerErrResults(map.getKey() + IDefineMsg.LACK_PARAM);
        }

        return makerSusResults(IDefineMsg.CHEACK_SUC);
    }

    /**
     * 遍历Map并除去空值
     *
     * @param reqMap {@Link Map}
     * @return
     */
    public static Map<String, Object> checkParamsAndDelEmpty(Map<String, Object> reqMap) {

        for (Map.Entry<String, Object> map : reqMap.entrySet()) {
            if (isEmpty(map.getValue()))
                reqMap.remove(map.getKey());
        }
        return reqMap;
    }

    /**
     * json-->map并校验是否为空
     *
     * @param json {@Link String}
     * @return {@link IResult} JosnMap<String,Object>
     */
    public static IResult checkJsonEmpty(String json) {
        if (isBlank(json))
            return makerErrResults("data" + IDefineMsg.LACK_PARAM);
        Map<String, Object> reqMap = FastjsonUtil.jsonChangeMap(json);

        for (Map.Entry<String, Object> map : reqMap.entrySet()) {
            if (isEmpty(map.getValue()))
                return makerErrResults(map.getKey() + IDefineMsg.LACK_PARAM);
        }

        return makerSusResults(IDefineMsg.CHEACK_SUC, reqMap);
    }

    /**
     * 检出IResult值
     *
     * @param rsMap {@Link Map}
     * @return
     */
    public static Map<String, Object> analysisIResult(IResult rsMap) {

        if (!rsMap.isSuccessful()) return null;
        List<Map<String, Object>> list = (List<Map<String, Object>>) rsMap.getResult();
        Map<String, Object> reqMap = list.get(0);
        return reqMap;
    }

    /**
     * 校验特定键值是否为空
     *
     * @param reqMap {@Link Map}
     * @param str
     * @return
     */
    public static IResult checkParamsEmpty(Map<String, Object> reqMap, String... str) {

        for (String key : str) {
            if (isEmpty(reqMap.get(key)))
                return makerErrResults("缺少参数[" + key + "]");
            if (isEmpty(valueOf(reqMap.get(key))))
                return makerErrResults("参数[" + key + "]不能为空");
        }
        return makerSusResults(IDefineMsg.CHEACK_SUC, reqMap);
    }

    /**
     * 判断字符串是否只有数字及字母
     *
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

    /**
     * 判断是否为整数
     *
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

    /**
     * 判断是否为浮点数
     *
     * @param str {@Link String}
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
     * 判断是否为正确日期,
     *
     * @param str，格式为：yyyy-MM-dd HH:mm:ss {@Link String}
     * @return
     */
    public static boolean isDate(String str) {

        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = f.parse(str);
            String s = f.format(d);
            return s.equals(str);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 转换对象为字符串类型
     *
     * @param obj {@Link Object}
     * @return
     */
    public static String valueOf(Object obj) {

        if (isEmpty(obj)) {
            return "";
        }
        if ((obj instanceof String)) {
            String strObj = obj.toString().trim();
            if ("null".equalsIgnoreCase(strObj)) {
                return "";
            }
            return strObj;
        }
        if ((obj instanceof BigDecimal)) {
            BigDecimal bigObj = (BigDecimal) obj;
            return bigObj.toString();
        }

        return obj.toString().trim();
    }

    /**
     * 转换数字类型对象为字符串类型
     *
     * @param obj
     * @return
     */
    public static String numValueOf(Object obj) {
        String str = valueOf(obj);
        if (str.trim().length() <= 0) {
            return "0";
        }
        if (!isDouble(str)) {
            return "0";
        }
        return str;
    }

    /**
     * 拆分传入的字符串，返回为键值对形式
     *
     * @param voucher
     * @return
     */
    public static List<Map<String, Object>> splitVoucher(String voucher) {
        List<Map<String, Object>> array = null;
        try {
            array = new ArrayList<>();
            String params = valueOf(voucher);
            params = params.replace("{", "");
            params = params.replace("}", "");
            String[] paramsArray = params.split(",");
            for (String str : paramsArray) {
                String[] temp = str.split(":");
                Map<String, Object> voucherMap = new HashMap<String, Object>();
                voucherMap.put("vouchercode", temp[0]);
                voucherMap.put("voucheramount", temp[1]);
                array.add(voucherMap);
            }
        } catch (Exception e) {
            log.error(ExceptionMessage.SPLIT_PARAMETERS_EXCEPTION.getExceptionMsg());
            log.error(e.getMessage());
        }
        return array;
    }

    /**
     * 取list 中 map的value值
     *
     * @param srcList
     * @param mapKey
     * @return
     */
    public static <T> String getListMapValue(List<T> srcList, String mapKey) {
        if ((srcList == null) || (srcList.isEmpty())) return "";
        Map<T, T> m = (Map<T, T>) srcList.get(0);
        String value = valueOf(m.get(mapKey));
        return value;
    }

    /**
     * 数值类对象转换为BigDecimal
     *
     * @param obj
     * @return
     */
    public static BigDecimal toBigDecimal(Object obj) {
        String strObj = valueOf(obj);
        BigDecimal decStrObj = new BigDecimal(0);
        decStrObj.setScale(2, RoundingMode.HALF_UP);
        try {
            decStrObj = new BigDecimal(strObj);
            decStrObj = decStrObj.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error(ExceptionMessage.TO_BIGDECIMAL_EXCEPTION.getExceptionMsg());
            return null;
        }
        return decStrObj;
    }

    /**
     * 数值转换为BigDecimal
     *
     * @param i
     * @return
     */
    public static BigDecimal toBigDecimal(int i) {
        BigDecimal bignumber = new BigDecimal(i);
        bignumber = bignumber.setScale(2, RoundingMode.HALF_UP);
        return bignumber;
    }

    /**
     * 数值字符串转换为BigDecimal
     *
     * @param amount
     * @return
     */
    public static BigDecimal toBigDecimal(String amount) {
        BigDecimal decimalAmount = new BigDecimal(0);
        try {
            decimalAmount = new BigDecimal(amount);
            decimalAmount = decimalAmount.setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.error(ExceptionMessage.TO_BIGDECIMAL_EXCEPTION.getExceptionMsg());
            log.error(e.getMessage());
            return null;
        }
        return decimalAmount;
    }

    /**
     * 数值字符串转换为BigInteger
     *
     * @param amount
     * @return
     */
    public static BigInteger toBigInteger(String amount) {
        BigInteger decimalAmount = null;
        try {
            decimalAmount = new BigInteger(amount);
        } catch (Exception e) {
            log.error(ExceptionMessage.TO_BIGDECIMAL_EXCEPTION.getExceptionMsg());
            return null;
        }
        return decimalAmount;
    }

    /**
     * 请求返回响应失败或返回结果结果为空
     *
     * @param rs
     * @return boolean
     */
    public static boolean isFailOrEmpty(IResult rs) {
        if (!rs.isSuccessful()) return true;
        List<?> rsList = rs.getResult(0);
        if (isEmpty(rsList)) return true;
        return false;
    }

    public static String calculateBuyerPay(String smrjg, String charge) {
        BigDecimal decimalSmrjg = toBigDecimal(smrjg);
        decimalSmrjg = decimalSmrjg.setScale(2, RoundingMode.HALF_UP);
        BigDecimal decimalCharge = toBigDecimal(charge);
        decimalCharge = decimalCharge.setScale(2, RoundingMode.HALF_UP);
        BigDecimal decimalBuyerPay = decimalSmrjg.add(decimalCharge);
        return valueOf(decimalBuyerPay);
    }

    /**
     * IResultToJson
     *
     * @param rs
     * @return Json
     */
    public static String IResultToJson(IResult rs) {
        String JsonData = null;
        try {
            Gson gson = new Gson();
            List list = rs.getResult(0);
            list.add(ImmutableMap.of("message", rs.getMessage(), "code", rs.getCode()));

            JsonData = gson.toJson(list);
        } catch (Exception e) {
        }
        return JsonData;
    }

    /**
     * 获取Result<List<Map>> 中 key对应的value值
     * 多个Result,或多个List时用
     *
     * @param rs
     * @param rsIndex
     * @param listIndex
     * @param mapKey
     * @return
     */
    public static String getResultMapValue(IResult rs, int rsIndex, int listIndex, String mapKey) {
        String value = "";
        try {
            List rsList = rs.getResult(rsIndex);
            Map rsMap = (Map) rsList.get(listIndex);
            value = valueOf(rsMap.get(mapKey));
        } catch (Exception e) {
            log. error("error", e);
        }
        return value.trim();
    }

    /**
     * get Result<List<Map>> 中 key对应的value值
     * 一个Result只有一个List,list只有一个map时使用
     *
     * @param rs
     * @param mapKey
     * @return
     */
    public static String getResultMapValue(IResult rs, String mapKey) {
        String value = "";
        try {
            List rsList = rs.getResult(0);
            Map rsMap = (Map) rsList.get(0);
            value = valueOf(rsMap.get(mapKey));
        } catch (Exception e) {
            log.error("error", e);
        }
        return value.trim();
    }


    /**
     * 获取Result中的map
     *
     * @param rs
     * @param rsIndex
     * @param listIndex
     * @return Map
     */
    public static Map<?, ?> getResultMap(IResult rs, int rsIndex, int listIndex) {
        Map rsMap = null;
        try {
            List rsList = rs.getResult(rsIndex);
            rsMap = (Map) rsList.get(listIndex);
        } catch (Exception e) {
            log.error("error", e);
        }
        return rsMap;
    }

    /**
     * 获取Result中的map
     *
     * @param rs
     * @return
     */
    public static <T> Map<T, T> getResultMap(IResult rs) {
        Map rsMap = null;
        try {
            List rsList = rs.getResult(0);
            if (rsList != null && !rsList.isEmpty()) {
                rsMap = (Map) rsList.get(0);
            }
        } catch (Exception e) {
            log.error("error", e);
        }
        return rsMap;
    }

    /**
     * 组装成一个Result 用于返回消息
     *
     * @param rsMap
     * @return
     */
    public static IResult makerResults(Map rsMap) {
        IResult rs = new Result();
        rs.setResType(IDefineMsg.WS_TYPE_LIST_MAP);
        rs.setResult(ImmutableList.of(rsMap));
        return rs;
    }

    /**
     * 组装成一个Result 用于返回消息
     *
     * @param rs
     * @param rsMap
     * @return
     */
    public static IResult makerResults(IResult rs, Map rsMap) {
        rs.setResType(IDefineMsg.WS_TYPE_LIST_MAP);
        rs.setResult(ImmutableList.of(rsMap));
        rs.setLengths(rsMap.size());
        return rs;
    }

    /**
     * 组装成一个Result 用于只返回单一值消息
     *
     * @param rs
     * @param key
     * @param value
     * @return
     */
    public static IResult makerResults(IResult rs, String key, String value) {
        rs.setResult(ImmutableList.of(ImmutableMap.of(key, value)));
        rs.setLengths(IDefineMsg.WS_TYPE_INT);
        rs.setResType(IDefineMsg.WS_TYPE_LIST_MAP);
        return rs;
    }

    public static IResult makerResults(String code, String msg) {
        IResult rs = new Result();
        rs.setCode(code);
        rs.setMessage(msg);
        return rs;
    }

    public static IResult makerSusResults(String msg) {
        IResult rs = new Result();
        rs.setCode(IDefineMsg.CODE_SUCCESS);
        rs.setMessage(msg);
        rs.setLengths(IDefineMsg.WS_TYPE_NULL);
        return rs;
    }

    public static IResult makerSusResults(String msg, List list) {
        IResult rs = new Result();
        rs.setCode(IDefineMsg.CODE_SUCCESS);
        rs.setMessage(msg);
        rs.setResType(IDefineMsg.WS_TYPE_LIST_MAP);
        rs.setResult(list);
        rs.setLengths(list.size());
        return rs;
    }

    public static IResult makerSusResults(String msg, Map rsMap) {
        IResult rs = new Result();
        rs.setCode(IDefineMsg.CODE_SUCCESS);
        rs.setMessage(msg);
        rs.setResType(IDefineMsg.WS_TYPE_LIST_MAP);
        rs.setLengths(rsMap.size());
        rs.setResult(ImmutableList.of(rsMap));
        return rs;
    }

    public static IResult makerSusResults(String msg, Object object) {
        IResult rs = new Result();

        rs.setCode(IDefineMsg.CODE_SUCCESS);
        rs.setMessage(msg);
        rs.setResType(IDefineMsg.WS_TYPE_OBJECT);
        rs.setResult(object);
        rs.setLengths(IDefineMsg.WS_TYPE_INT);
        return rs;
    }

    public static IResult makerErrResults(String msg, Map rsMap) {
        IResult rs = new Result();
        rs.setCode(IDefineMsg.CODE_ERROR);
        rs.setMessage(msg);
        rs.setResType(IDefineMsg.WS_TYPE_LIST_MAP);
        rs.setLengths(rsMap.size());
        rs.setResult(ImmutableList.of(rsMap));
        return rs;
    }

    public static IResult makerErrResults(String msg) {
        IResult rs = new Result();
        rs.setCode(IDefineMsg.CODE_ERROR);
        rs.setMessage(msg);
        return rs;
    }

    public static IResult makerResults(IResult rs, List rsList) {
        rs.setResult(rsList);
        rs.setResType(IDefineMsg.WS_TYPE_LIST_MAP);
        rs.setLengths(rsList.size());
        return rs;
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
     * 是否大0
     *
     * @param number
     * @return
     */
    public static boolean isGreaterZero(String number) {
        BigDecimal bNumber = toBigDecimal(number);
        if (isEmpty(bNumber)) {
            return false;
        }
        return isGreaterZero(bNumber);
    }


    /**
     * 生成 pwdLength长度的随机码
     *
     * @param pwdLength
     * @param pwdLength
     * @return string
     */
    public static String getRandomCode(int pwdLength) {
        int randomNum = getRandomCode(0, pwdLength);
        return valueOf(Integer.valueOf(randomNum));
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
     * @param @return
     * @return int
     * @Title: getCode6
     * @Description: 获取6位随机数
     * @author xiao.he
     * @date 2014-9-2 下午07:15:07
     */
    public static int getRandomCode6() {
        int intCount = (new Random()).nextInt(999999);// 最大值位9999
        if (intCount < 100000)
            intCount += 100000; // 最小值位10000001
        return intCount;
    }

    /**
     * 随机数
     *
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

    /**
     * 字符转数值 int
     * 判断是否为0
     *
     * @param intstr
     * @return int
     */
    public static int parseInt(String intstr) {
        return isEmpty(intstr) ? 0 : Integer.parseInt(intstr.trim());
    }

    /**
     * 字符转数值 Integer
     * 判断是否为空
     *
     * @param intstr
     * @return Integer
     */
    public static Integer parseInteger(String intstr) {
        return isEmpty(intstr) ? null : Integer.parseInt(intstr.trim());
    }

    /**
     * 对象转数值  Integer
     *
     * @param object
     * @return Integer
     */
    public static Integer parseInteger(Object object) {
        if (isEmpty(object)) return null;
        return Integer.parseInt(object.toString());
    }

    /**
     * 字符转数值float
     *
     * @param intstr
     * @return float
     */
    public static float parseFloat(String intstr) {
        return isEmpty(intstr) ? 0.0F : Float.parseFloat(intstr.trim());
    }

    /**
     * 对象转数值Float
     *
     * @param object
     * @return Float
     */
    public static Float parseFloat(Object object) {
        if (isEmpty(object)) return null;
        return Float.parseFloat(object.toString());
    }

    /**
     * 字符转数值double
     *
     * @param intstr
     * @return double
     */
    public static double parseDouble(String intstr) {
        return isEmpty(intstr) ? 0.0D : Double.parseDouble(intstr.trim());
    }

    /**
     * 对象转数值Double
     *
     * @param object
     * @return Double
     */
    public static Double parseDouble(Object object) {
        if (isEmpty(object)) return null;
        return Double.parseDouble(object.toString());
    }

    /**
     * 检查一个数组中是否包含某个特定的值
     *
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

    /**
     * 正则校验(入参 params 不能为 null)
     *
     * @param params      入参
     * @param macroDefine 宏定义（正则表达式）
     * @return boolean
     */
    public static boolean regExpCheck(String params, String macroDefine) {
        return params.matches(macroDefine);
    }

    /**
     * 判断两个时间是否相同
     *
     * @param date1 等待比较第一个时间
     * @param date2 等待比较第二个时间
     * @return 比较结果
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    /**
     * 比较两个日历类数据是否相同
     *
     * @param cal1 比较第一个日历类
     * @param cal2 比较第二个日历类
     * @return 比较结果
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameDay(cal1, cal2);
    }

    /**
     * 新增年份
     *
     * @param date 需要新增时间
     * @param year 增加年份
     * @return 增加后年份
     */
    public static Date addYears(Date date, int year) {
        return DateUtils.addYears(date, year);
    }

    /**
     * 对时间格式进行格式化
     *
     * @param date 时间类型
     * @return yyyy-MM-dd
     */
    public static String format(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_DATE_FORMAT
                .getPattern());
    }

    /**
     * 对时间格式进行格式化
     *
     * @param date 时间类型
     * @return yyyy-MM-dd'T'HH:mm:ss
     */
    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_DATETIME_FORMAT
                .getPattern());
    }

    /**
     * 格式化时间
     *
     * @param date    时间参数
     * @param pattern 格式化参数类型
     * @return
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 格式化时间参数
     *
     * @param date 时间参数
     * @return HH:mm:ss
     */
    public static String formatTime(Date date) {
        return DateFormatUtils.format(date,
                DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern());
    }

    /**
     * 增加月份
     *
     * @param date  传入时间
     * @param month 需要增加月份
     * @return 增加月份
     */
    public static Date addMonths(Date date, int month) {
        return DateUtils.addMonths(date, month);
    }

    /**
     * 增加周
     *
     * @param date   当前时间
     * @param amount 需要增加周
     * @return 增加后时间
     */
    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /**
     * 增加天
     *
     * @param date   当前时间
     * @param amount 需要增加天数
     * @return 增加后时间
     */
    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * 增加小时
     *
     * @param date   当前时间
     * @param amount 增加小时数
     * @return 增加后时间
     */
    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * 增加分钟
     *
     * @param date   当前时间
     * @param amount 增加分钟数
     * @return 增加后时间
     */
    public static Date addMinutes(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * 增加秒
     *
     * @param date   当前时间
     * @param amount 增加秒数
     * @return 增加后时间
     */
    public static Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * 添加毫秒
     *
     * @param date   当前时间
     * @param amount 增加毫秒
     * @return 增加后时间
     */
    public static Date addMilliseconds(Date date, int amount) {
        return DateUtils.addMilliseconds(date, amount);
    }


    /**
     * 判断是否是周末
     *
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

    /**
     * 当前日期前一个交易日
     *
     * @param date
     * @return
     */
    public Date tradingTomorrowDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date nowDate = new Date(calendar.getTimeInMillis());
        return nowDate;
    }

    /**
     * 当前日期前一个交易日
     *
     * @param date
     * @return
     */
    public Date tradingYesterday(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date nowDate = new Date(calendar.getTimeInMillis());
        return nowDate;
    }

    /**
     * 当前时间
     *
     * @return
     */
    public static String getCurDatetime() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间
     *
     * @return
     */
    public static String getCurDatetime(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 计算两个时间相差的天数
     *
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
     * @param offset 星期偏移量，0为本周，-1为上周，1为下周，如此类推
     * @return Date[]
     * @Title: getWeekTimesBE
     * @Description: 获取一周的起止时间
     */
    public static Date[] getWeekTimesBE(int offset) {
        try {
            Date[] dates = new Date[2];
            SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 得到当前日期
            Calendar cal = Calendar.getInstance();

            // 得到本周第一天日期
            int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DATE, -day_of_week + 1 + (7 * offset));
            Date begin = cal.getTime();
            String weekFirstStr = f1.format(begin);
            begin = f2.parse(weekFirstStr + " 00:00:00");

            // 得到本周最后一天
            cal.add(Calendar.DATE, 6);
            Date end = cal.getTime();
            String weekLastStr = f1.format(end);
            end = f2.parse(weekLastStr + " 23:59:59");
            dates[0] = begin;
            dates[1] = end;
            return dates;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param @param  offset 月份偏移量，0为本月，-1为上月，1为下月，如此类推
     * @param @return date[0]：开始时间，格式2012-03-01
     *                00:00:00；date[1]：结束时间，格式2012-03-31 23:59:59；异常为null
     * @return Date[]
     * @Title: getMonthTimesBE
     * @Description: 获取一月的起止时间
     */
    public static Date[] getMonthTimesBE(int offset) {
        try {
            Date[] dates = new Date[2];
            // 得到当前日期
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, offset);

            int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            // 按你的要求设置时间
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay,
                    23, 59, 59);
            Date end = cal.getTime();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 00, 00,
                    00);
            Date begin = cal.getTime();
            dates[0] = begin;
            dates[1] = end;
            return dates;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param @param  date
     * @param @return
     * @return Date[]
     * @Title: getDayTimesBE
     * @Description: 获取一天的开始结束时间
     */
    public static Date[] getDayTimesBE(Date date) {
        Date[] dates = new Date[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // 按你的要求设置时间
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date end = cal.getTime();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
                .get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        Date begin = cal.getTime();
        dates[0] = begin;
        dates[1] = end;
        return dates;
    }


    /**
     * 解析输入流成byte数组
     *
     * @param inputstream
     * @param length
     * @return
     * @throws Exception
     */
    public static byte[] recvMsg(InputStream inputstream, int length)
            throws Exception {
        try {
            byte content[] = new byte[length];
            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount < length) {
                int size = (length - readCount) > 1024 ? 1024
                        : (length - readCount);
                readCount += inputstream.read(content, readCount, size);
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        if (null == phone || "".equals(phone)) {
            return false;
        }
        String regExp = "^[1][3,5,8][0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    /**
     * 判断邮箱号是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regExp = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.find();
    }

    /**
     * @param list  要转换的对象
     * @param split 分隔符
     * @param
     * @return String
     * @Title: parseListToString
     * @Description: 列表转换为字符串
     */
    public static String parseListToString(List<?> list, String split) {
        if (list != null && list.size() > 0) {
            String str = "";
            int len = list.size();
            for (int i = 0; i < len; i++) {
                if (i != (len - 1)) {
                    str += (list.get(i) + split);
                } else {
                    str += list.get(i);
                }
            }
            return str;
        }
        return null;
    }

    /**
     * @param arr
     * @param split
     * @return String
     * @Title: pasreArrayToString
     * @Description: 数组转字符串
     */
    public static String pasreArrayToString(Object[] arr, String split) {
        if (arr != null && arr.length > 0) {
            String str = "";
            for (int i = 0; i < arr.length; i++) {
                if (i != (arr.length - 1)) {
                    str += (arr[i] + split);
                } else {
                    str += arr[i];
                }
            }
            return str;
        }
        return null;
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
     * @Title: getTimeFileName
     * @Description: 获取默认的以是时间命名的文件名
     */
    public static String getTimeFileName(String suffix) {
        return format(new Date(), "yyyyMMddHHmmssSSS") + suffix;
    }

    /**
     * @param @param birthday
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
     * @param realPath
     * @return void
     * @Title: deleteFile
     * @Description: 删除文件或文件夹
     */
    public static void deleteFile(String realPath) {
        File file = new File(realPath);
        if (file.isFile() && file.exists()) {
            file.delete();
        } else {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            file.delete();
        }
    }

    /**
     * @param path
     * @return long
     * @Title: getFileSize
     * @Description: 根据路径获取文件大小
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            return file.length();
        }
        return 0;
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

    /**
     * 读取图片
     *
     * @param imgPath
     * @return
     */
    public static byte[] getImageToBytes(String imgPath) {
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            //创建URL
            URL url = new URL(imgPath);
            //得到连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            //得到连接地址的输入流
            InputStream in = urlConn.getInputStream();

            int size;
            //缓冲值
            bytes = new byte[1024];
            if (in != null) {
                //循环读输入流至read返回-1为止，并写到缓存中
                while ((size = in.read(bytes)) != -1) {
                    out.write(bytes, 0, size);
                }
            }
            out.close();//关闭输出流
            in.close();//关闭输入流
            urlConn.disconnect();//断开连接

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    /**
     * 保存图片
     *
     * @param bytes
     * @param imgFile
     * @throws Exception
     */
    public static void bytesToImgSave(byte[] bytes, String imgFile) throws Exception {
        //UUID序列号作为保存图片的名称

        File f = new File(imgFile);

        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
            for (int i = 0; i < bytes.length; i++) {
                out.write(bytes[i]);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * basePath路径
     *
     * @param request
     * @return
     */
    public static String getServerPath(HttpServletRequest request) {
        String basePath = request.getSession().getServletContext().getRealPath("/");
        return basePath;
    }

    /**
     * 保存远程图片
     *
     * @param urlStr
     * @return
     */
    public String putUrlPNG(String urlStr) {

        String imgUrl = null;
        String name = format(new Date(), "yyyyMMddHHmmssSSS");
        String path =/**Constants.getFileBasePath()+*/"opt/appleprofession/";

        @SuppressWarnings("unused")
        String path2 = "C://test//";    //本机测试路径
        try {
            URL url = new URL(urlStr);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            byte[] bytes = new byte[100];
            imgUrl = path + name + ".png";
            OutputStream bos = new FileOutputStream(new File(imgUrl));

            int len;
            while ((len = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            bis.close();
            bos.flush();
            bos.close();
        } catch (MalformedURLException e) {
            log.error(ExceptionMessage.MALFORMED_URL_EXCEPTION.getExceptionMsg());
            return ExceptionMessage.MALFORMED_URL_EXCEPTION.getExceptionCode().toString();
        } catch (IOException e) {
            log.error(ExceptionMessage.IO_EXCEPTION.getExceptionMsg());
            return ExceptionMessage.IO_EXCEPTION.getExceptionCode().toString();
        }

        return imgUrl;
    }

    /**
     * 隐藏号码
     *
     * @param param
     * @return
     */
    public static String putConcealParam(String param) {

        String str = param;
        str = str.substring(0, 3) + "****" + str.substring(str.length() - 4, str.length());
        return str;
    }

    /**
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
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }


    /**
     * 拆分字符串 以"|"分割
     *
     * @param str
     * @return String[]
     */
    public static String[] stringSplit(String str) {

        if (isEmpty(str)) return null;
        String[] strings = null;
        try {
            strings = str.split("\\|");
        } catch (Exception e) {
            return null;
        }
        if (isEmpty(strings)) return null;
        return strings;
    }

    /**
     * 拆分字符串 以"|"分割
     *
     * @param str
     * @return Integer[]
     */
    public static Integer[] IntegerSplit(String str) {

        if (isEmpty(str)) return null;
        String[] strings = null;

        try {
            strings = str.split("\\|");
        } catch (Exception e) {
            return null;
        }

        Integer[] integers = new Integer[strings.length];

        for (int i = 0; i < integers.length; i++) {
            integers[i] = parseInteger(strings[i].trim());
        }

        if (isEmpty(integers)) return null;
        return integers;
    }
}
