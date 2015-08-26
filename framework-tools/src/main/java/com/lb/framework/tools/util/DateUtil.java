package com.lb.framework.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * <p>
 * 提供了除{@link org.apache.commons.lang3.time.DateUtils}工具类之外的一些对时间的操作
 * 
 * @author 464281
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /** 默认的时间样式 */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final long DAY_MILLS = 86400000L;
    
    /**
     * 根据指定时间和样式格式化时间
     * <p>
     * 如：2014-04-21 21:20:10, yyyy-MM-dd -> 2014-04-21
     * 
     * @param date 指定时间
     * @param pattern 指定样式
     * @return 格式化时间
     * @see java.text.SimpleDateFormat
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 按照默认样式的格式化指定时间
     * <p>
     * 如：2014-04-21 21:20:10 -> 2014-04-21 21:20:10
     * 
     * @param date 指定时间
     * @return 格式化时间
     * @see #format(Date, String)
     * @see #YYYY_MM_DD_HH_MM_SS
     */
    public static String format(Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 根据格式化时间和样式解析时间
     * <p>
     * 如：2014-04-21, yyyy-MM-dd -> 2014-04-21 00:00:00
     * 
     * @param source 格式化时间
     * @param pattern 样式
     * @return 时间
     * @throws DateParseException 格式化出现异常时抛出
     * @see java.text.SimpleDateFormat
     */
    public static Date parse(String source, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(source);
        } catch (ParseException e) {
            throw new DateParseException(e);
        }
    }

    /**
     * 根据格式化时间、样式和默认时间解析时间
     * <p>
     * 若解析出现异常，则返回默认时间
     * 
     * @param source 格式化时间
     * @param pattern 样式
     * @param defaultValue 默认时间
     * @return 时间
     * @see java.text.SimpleDateFormat
     */
    public static Date parse(String source, String pattern, Date defaultValue) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(source);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    /**
     * 根据格式化时间和默认样式解析时间
     * <p>
     * 如：2014-04-21 21:20:10, yyyy-MM-dd HH:mm:ss -> 2014-04-21 21:20:10
     * 
     * @param source 格式化时间
     * @return 时间
     * @throws DateParseException 格式化出现异常时抛出
     * @see #parse(String, String)
     * @see #YYYY_MM_DD_HH_MM_SS
     */
    public static Date parse(String source) {
        return parse(source, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 根据格式化时间、默认样式和默认时间解析时间
     * <p>
     * 若解析出现异常，则返回默认时间
     * 
     * @param source 格式化时间
     * @param defaultValue 默认时间
     * @return 时间
     * @see #parse(String, String, Date)
     * @see #YYYY_MM_DD_HH_MM_SS
     */
    public static Date parse(String source, Date defaultValue) {
        return parse(source, YYYY_MM_DD_HH_MM_SS, defaultValue);
    }

    /**
     * 获取指定日期的起始时间
     * <p>
     * 如：2014-04-21 21:20:10 -> 2014-04-21 00:00:00
     * 
     * @param date 指定日期
     * @return 起始时间
     * @see java.util.Calendar
     */
    public static Date getStartOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前日期的起始时间
     * <p>
     * 如：2014-04-21 21:20:10 -> 2014-04-21 00:00:00
     * 
     * @return 当前日期的起始时间
     * @see #getStartOfDate(Date)
     */
    public static Date getStartOfToDay() {
        return getStartOfDate(new Date());
    }

    /**
     * 根据指定时间和一周的第几天获取指定日期当周几的时间
     * <p>
     * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22 21:20:10
     * 
     * @param date 指定时间
     * @param dayOfWeek 一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
     * @return 指定时间当周几的时间
     * @see java.util.Calendar
     */
    public static Date getDateOfWeek(Date date, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getTime();
    }

    /**
     * 根据一周的第几天获取当前时间的周几的时间
     * <p>
     * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22 21:20:10
     * 
     * @param dayOfWeek 一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
     * @return 指定日期当周几的时间
     * @see #getDateOfWeek(Date, int)
     */
    public static Date getDateOfWeek(int dayOfWeek) {
        return getDateOfWeek(new Date(), dayOfWeek);
    }

    /**
     * 根据指定日期和一周的第几天获取指定日期当周几的起始时间
     * <p>
     * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22 00:00:00
     * 
     * @param date 指定日期
     * @param dayOfWeek 一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
     * @return 指定日期当周几的起始时间
     * @see #getDateOfWeek(Date, int)
     * @see #getStartOfDate(Date)
     */
    public static Date getStartOfWeekDate(Date date, int dayOfWeek) {
        Date dateOfWeek = getDateOfWeek(date, dayOfWeek);
        return getStartOfDate(dateOfWeek);
    }

    /**
     * 根据一周的第几天获取本周几的起始时间
     * <p>
     * 如：2014-04-21 21:20:10, {@link java.util.Calendar#TUESDAY} -> 2014-04-22 00:00:00
     * 
     * @param dayOfWeek 一周的第几天，{@link java.util.Calendar#DAY_OF_WEEK}
     * @return 指定日期当周几的起始时间
     * @see #getDateOfWeek(int)
     * @see #getStartOfDate(Date)
     */
    public static Date getStartOfWeekDate(int dayOfWeek) {
        Date dateOfWeek = getDateOfWeek(dayOfWeek);
        return getStartOfDate(dateOfWeek);
    }

    /**
     * 根据指定时间和一月的第几天获取指定时间当月的第几天的时间
     * <p>
     * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 21:20:10
     * 
     * @param date 指定时间
     * @param dayOfMonth 一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
     * @return 指定时间当月的第几天的起始时间
     * @see java.util.Calendar
     */
    public static Date getDateOfMonth(Date date, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTime();
    }

    /**
     * 根据一月的第几天获取当前时间月份的第几天的时间
     * <p>
     * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 21:20:10
     * 
     * @param dayOfMonth 一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
     * @return 指定时间当月的第几天的起始时间
     * @see #getDateOfMonth(Date, int)
     */
    public static Date getDateOfMonth(int dayOfMonth) {
        return getDateOfMonth(new Date(), dayOfMonth);
    }

    /**
     * 根据指定时间和一月的第几天获取指定时间当月的第几天的起始时间
     * <p>
     * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 00:00:00
     * 
     * @param date 指定时间
     * @param dayOfMonth 一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
     * @return 指定时间当月的第几天的起始时间
     * @see #getDateOfMonth(Date, int)
     * @see #getStartOfDate(Date)
     */
    public static Date getStartOfMonthDate(Date date, int dayOfMonth) {
        Date dateOfMonth = getDateOfMonth(date, dayOfMonth);
        return getStartOfDate(dateOfMonth);
    }

    /**
     * 根据一月的第几天获取当前时间月份的第几天的起始时间
     * <p>
     * 如：2014-04-21 21:20:10, 1 -> 2014-04-01 00:00:00
     * 
     * @param dayOfMonth 一月的第几天，{@link java.util.Calendar#DAY_OF_MONTH}
     * @return 指定时间当月的第几天的起始时间
     * @see #getDateOfMonth(int)
     * @see #getStartOfDate(Date)
     */
    public static Date getStartOfMonthDate(int dayOfMonth) {
        Date dateOfMonth = getDateOfMonth(dayOfMonth);
        return getStartOfDate(dateOfMonth);
    }

    /**
     * 获取指定时间和相隔月份的时间
     * <p>
     * 如：2014-04-21 21:20:10, -1 -> 2014-03-21 21:20:10
     * 
     * @param date 指定时间
     * @param months 相隔月份，负数表示比指定时间早，0表示当前月，整数表示比指定时间晚
     * @return 指定时间和相隔月份的时间
     * @see java.util.Calendar
     */
    public static Date getDateApartMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的相隔月份的时间
     * <p>
     * 如：2014-04-21 21:20:10, 1 -> 2014-05-21 21:20:10
     * 
     * @param months 相隔月份，负数表示比指定时间早，0表示当前月，整数表示比指定时间晚
     * @return 当前时间的相隔月份的时间
     * @see #getDateApartMonths(Date, int)
     */
    public static Date getDateApartMonths(int months) {
        return getDateApartMonths(new Date(), months);
    }

    /**
     * 获取指定时间和相隔月份的起始时间
     * <p>
     * 如：2014-04-21 21:20:10, -1 -> 2014-03-21 00:00:00
     * 
     * @param date 指定时间
     * @param months 相隔月份，负数表示比指定时间早，0表示当前月，正数表示比指定时间晚
     * @return 指定时间和相隔月份的起始时间
     * @see #getDateApartMonths(Date, int)
     * @see #getStartOfDate(Date)
     */
    public static Date getStartDateApartMonths(Date date, int months) {
        Date dateApartMonths = getDateApartMonths(date, months);
        return getStartOfDate(dateApartMonths);
    }

    /**
     * 获取当前时间的相隔月份的起始时间
     * <p>
     * 如：2014-04-21 21:20:10, 1 -> 2014-05-21 00:00:00
     * 
     * @param months 相隔月份，负数表示比指定时间早，0表示当前月，整数表示比指定时间晚
     * @return 当前时间的相隔月份的时间
     * @see #getDateApartMonths(int)
     * @see #getStartOfDate(Date)
     */
    public static Date getStartDateApartMonths(int months) {
        Date dateApartMonths = getDateApartMonths(months);
        return getStartOfDate(dateApartMonths);
    }

    /**
     * 指定时间1是否早于指定时间2指定的间隔
     * 
     * @param date1 指定时间1
     * @param date2 指定时间2
     * @param interval 指定间隔，以毫秒为单位
     * @return <code>TRUE</code>表示早于
     */
    public static boolean before(Date date1, Date date2, long interval) {
        return date2.getTime() - date1.getTime() > interval;
    }

    /**
     * 指定时间1是否早于在指定时间2
     * 
     * @param date1 指定时间1
     * @param date2 指定时间2
     * @return <code>TRUE</code>表示早于
     */
    public static boolean before(Date date1, Date date2) {
        return before(date1, date2, 0);
    }

    /**
     * 指定时间1是否晚于指定时间2指定的间隔
     * 
     * @param date1 指定时间1
     * @param date2 指定时间2
     * @param interval 指定间隔，以毫秒为单位
     * @return <code>TRUE</code>表示晚于
     */
    public static boolean after(Date date1, Date date2, long interval) {
        return date1.getTime() - date2.getTime() > interval;
    }

    /**
     * 指定时间1是否晚于在指定时间2
     * 
     * @param date1 指定时间1
     * @param date2 指定时间2
     * @return <code>TRUE</code>表示晚于
     */
    public static boolean after(Date date1, Date date2) {
        return after(date1, date2, 0);
    }
    
    /**
     * 获取时间1和时间2之间相差的天数
     * 
     * @param date1 指定时间1 
     * @param date2 指定时间2
     * @return 相差天数
     */
    public static long getDaysOfDifferDate(Date date1, Date date2) {
        return (getStartOfDate(date1).getTime() - getStartOfDate(date2).getTime()) / DAY_MILLS;
    }
    
    /**
     * 获取当前的Unix时间，单位秒
     * @return
     */
    public static int currentTimeSeconds() {
        return (int)(System.currentTimeMillis() / 1000);
    }
}