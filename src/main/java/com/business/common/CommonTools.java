package com.business.common;

import com.business.common.json.JsonUtil;
import com.business.common.message.ExceptionMessage;
import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.CollectionUtils;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by yuton on 2016/8/2.
 */
@Slf4j
public class CommonTools {
    private static final String PATTERN_HAVE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_DAY = "yyyy-MM-dd";
    private static final String PATTERN_NOT_HAVE_TIME = "yyyy-MM-dd 00:00:00";

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
        if (CollectionUtils.isEmpty(list)) return true;
        return false;
    }

    /**
     * Map是否为空
     *
     * @param map {@link Map}
     * @return boolean
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        if (CollectionUtils.isEmpty(map)) return true;
        return false;
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
     * 判断是否为正确日期
     *
     * @param str，格式为：yyyy-MM-dd HH:mm:ss {@Link String}
     * @return
     */
    public static boolean isDate(String str) {

        try {
            DateFormat f = new SimpleDateFormat(PATTERN_HAVE_TIME);
            Date d = f.parse(str);
            String s = f.format(d);
            return s.equals(str);
        } catch (Exception e) {
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
     * @param intstr
     * @return int
     */
    public static int parseInt(String intstr) {
        return isEmpty(intstr) ? 0 : Integer.parseInt(intstr.trim());
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
     * @param intstr
     * @return double
     */
    public static double parseDouble(String intstr) {
        return isEmpty(intstr) ? 0.0D : Double.parseDouble(intstr.trim());
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
    public static void bytesToImgSave(byte[] bytes, String imgFile) throws IOException {
        //UUID序列号作为保存图片的名称

        File f = new File(imgFile);
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new FileOutputStream(f));
            for (int i = 0; i < bytes.length; i++) {
                out.write(bytes[i]);
            }
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally {
            out.close();
        }
    }

    /**
     * basePath路径
     *
     * @param request
     * @return
     */
    public static String getServerPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 保存远程图片
     *
     * @param urlStr
     * @return
     */
    public String putUrlPNG(String urlStr) throws IOException {

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
            log.error(e.getMessage());
            throw new MalformedURLException(ExceptionMessage.MALFORMED_URL_EXCEPTION.getExceptionMsg());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IOException(ExceptionMessage.IO_EXCEPTION.getExceptionMsg());
        }

        return imgUrl;
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
     * @param resultMessage
     * @return {@link IResult}
     */
    public static IResult successResult(ResultMessage resultMessage) {
        return new Result(resultMessage);
    }

    /***
     * 成功提示 有返回
     * @param resultMessage
     * @param result
     * @param <T>
     * @return {@link IResult}
     */
    public static <T> IResult<T> successResult(ResultMessage resultMessage, T result) {
        return new Result<>(resultMessage, result);
    }

    /***
     * 错误提示 有返回
     * @param resultMessage
     * @param result
     * @param <T>
     * @return {@link IResult}
     */
    public static <T> IResult<T> errrorResult(ResultMessage resultMessage, T result) {
        return new Result<>(resultMessage, result);
    }

    /***
     * 错误提示 无返回
     * @param resultMessage
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
     * @param code
     * @param msg
     * @return {@link IResult}
     */
    public static IResult errorResult(int code, String msg, String specificMsg) {
        IResult result = new Result();
        result.setCode(code);
        result.setMsg(msg.concat(specificMsg));
        return result;
    }
}
