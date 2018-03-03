package com.business.common.date;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yuton
 * @version 1.0
 * @description 时间工具类
 * @since 2017/2/22 20:10
 */
@Slf4j
public class DateUtil {
    private static final String PATTERN_HAVE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param date 格式为：yyyy-MM-dd HH:mm:ss {@link String}
     * @return boolean
     * @description 判断是否为正确日期
     */
    public static boolean isDate(String date) {
        FastDateFormat format = FastDateFormat.getInstance(PATTERN_HAVE_TIME);
        try {
            return format.format(format.parse(date)).equals(date);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * @param pattern 日期格式
     * @param date    日期
     * @return boolean
     * @description 判断是否为正确日期
     */
    public static boolean isDate(String date, String pattern) {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(pattern);
        try {
            return fastDateFormat.format(fastDateFormat.parse(date)).equals(date);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * @param date 时间类型
     * @return yyyy-MM-dd {@link String}
     * @description 对时间格式进行格式化
     */
    public static String format(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_DATE_FORMAT.getPattern());
    }

    /***
     * 格式化时间参数
     * @param date 时间参数
     * @return HH:mm:ss {@link String}
     */
    public static String formatTime(Date date) {
        return DateFormatUtils.format(date, DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern());
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


    /**
     * @return {@link String}
     * @description 当前时间  yyyy-MM-dd HH:mm:ss
     */
    public static String getCurDatetime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_HAVE_TIME));
    }

    /**
     * @param pattern {@link String}
     * @return {@link String}
     * @description 当前时间 pattern
     */
    public static String getCurDatetime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /***
     * 格式化时间
     * @param date yyyy-MM-dd
     * @return {@link Date}
     */
    public static Date getCurDate(String date, String pattern) {
        FastDateFormat format = FastDateFormat.getInstance(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * @param date1 等待比较第一个时间
     * @param date2 等待比较第二个时间
     * @return 比较结果
     * @description 判断两个时间是否相同
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    /**
     * @param cal1 比较第一个日历类
     * @param cal2 比较第二个日历类
     * @return 比较结果
     * @description 比较两个日历类数据是否相同
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameDay(cal1, cal2);
    }

    /**
     * @param date 需要新增时间
     * @param year 增加年份
     * @return 增加后年份
     * @description 增减n年份
     */
    public static Date addYears(Date date, int year) {
        return DateUtils.addYears(date, year);
    }

    /**
     * @param date  传入时间
     * @param month 需要增加月份
     * @return 增加月份
     * @description 增加月份
     */
    public static Date addMonths(Date date, int month) {
        return DateUtils.addMonths(date, month);
    }

    /**
     * @param date   当前时间
     * @param amount 需要增加周
     * @return 增加后时间
     * @description 增加周
     */
    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    /**
     * @param date   当前时间
     * @param amount 需要增加天数
     * @return 增加后时间
     * @description 增加天
     */
    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * @param date   当前时间
     * @param amount 增加小时数
     * @return 增加后时间
     * @description 增加小时
     */
    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * @param date   当前时间
     * @param amount 增加分钟数
     * @return 增加后时间
     * @description 增加分钟
     */
    public static Date addMinutes(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    /**
     * @param date   当前时间
     * @param amount 增加秒数
     * @return 增加后时间
     * @description 增加秒
     */
    public static Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    /**
     * @param date   当前时间
     * @param amount 增加毫秒
     * @return 增加后时间
     * @description 添加毫秒
     */
    public static Date addMilliseconds(Date date, int amount) {
        return DateUtils.addMilliseconds(date, amount);
    }

    /**
     * @param date1 {@link Date}
     * @param date2 {@link Date}
     * @return {@link Integer}
     * @description 计算两个时间相差的天数 先格式化时间
     */
    public static int daysBetween(Date date1, Date date2, String pattern) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        date1 = getCurDate(format(date1, pattern), pattern);
        date2 = getCurDate(format(date2, pattern), pattern);
        Calendar cal = Calendar.getInstance();
        assert date1 != null;
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        assert date2 != null;
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /***
     * 判断是否是周末
     * @param date {@link Date}
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
     * 计算两个时间相差的天数
     * @param date1 {@link Date}
     * @param date2 {@link Date}
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

    /**
     * 获取时间差 (time1-time2 单位为ms)
     *
     * @param time1 {@link Date}
     * @param time2 {@link Date}
     * @return int
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
     * 计算年龄
     *
     * @return String
     */
    public static String getAgeByBirthday(Date birthday) {
        int days = daysBetween(birthday, new Date());
        String ageByBirthday = "{age}岁{month}个月";
        return ageByBirthday.replace("{age}", String.valueOf(days / 365))
                .replace("{month}", String.valueOf((days % 365) / 30));
    }
}
