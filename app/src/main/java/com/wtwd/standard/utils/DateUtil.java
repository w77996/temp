package com.wtwd.standard.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期工具类
 *
 * @author huangziwei
 * @date 2016.1.13
 */
public class DateUtil {

    public final static int DAY = 86400000; //１天＝24*60*60*1000ms
    public final static int HOUR = 3600000;
    public final static int MIN = 60000;

    /**
     * 获取某个月份的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if (isLeapYear(year)) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    /**
     * 是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }


    /**
     * 根据年份和月份获取日期数组，1、2、3...
     *
     * @param year
     * @param month
     * @return
     */
    public static List<String> getMonthDaysArray(int year, int month) {
        List<String> dayList = new ArrayList<String>();
        int days = DateUtil.getMonthDays(year, month);
        for (int i = 1; i <= days; i++) {

            if ((12 == month && 31 == i) || (1 == month && 1 == i)) {
                dayList.add(year + "/" + month + "/" + i);
            } else {
                dayList.add(month + "/" + i);
            }
//            dayList.add(i + "");
        }
        return dayList;
    }

    /**
     * 获取当前系统时间的年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前系统时间的月份
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前系统时间的月份的第几天
     *
     * @return
     */
    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前系统时间的小时数
     *
     * @return
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前系统时间的分钟数
     *
     * @return
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 获取当前系统时间的秒数
     *
     * @return
     */
    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 获取当前系统时间的毫秒数
     *
     * @return
     */
    public static int getMillSecond() {
        return Calendar.getInstance().get(Calendar.MILLISECOND);
    }


    /**
     * 根据系统默认时区，获取当前时间与time的天数差
     *
     * @param time 相差的天数
     * @return　等于０表示今天，大于０表示今天之前
     */
    public static long getDaySpan(long time) {
        return getTimeSpan(time, DAY);
    }

    public static long getHourSpan(long time) {
        return getTimeSpan(time, HOUR);
    }

    public static long getMinSpan(long time) {
        return getTimeSpan(time, MIN);
    }

    public static long getTimeSpan(long time, long span) {
        // 系统默认时区，ms
        int tiemzone = TimeZone.getDefault().getRawOffset();
        return (System.currentTimeMillis() + tiemzone) / span
                - (time + tiemzone) / span;
    }

    public static boolean isToday(long time) {
        return getDaySpan(time) == 0;
    }

    public static boolean isYestoday(long time) {
        return getDaySpan(time) == 1;
    }

    public static boolean isTomorrow(long time) {
        return getDaySpan(time) == -1;
    }

    /**
     * @return 返回当前时间，yyyy-MM-dd HH-mm-ss
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd HH-mm-ss");
    }

    public static String getDate(String format) {
        return getDate(new java.util.Date().getTime(), format);
    }

    public static String getDate(long time, String format) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        String date = sDateFormat.format(time);
        return date;
    }

    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    /**
     * 计算两个日期之间的日期
     *
     * @param startTime
     * @param endTime
     */
    public static List<String> betweenDays(String startTime, String endTime) {
        Date date_start = new Date(convert2long(startTime, "yyyy/MM/dd"));
        Date date_end = new Date(convert2long(endTime, "yyyy/MM/dd"));
        List<String> mDateList = new ArrayList<String>();


        //计算日期从开始时间于结束时间的0时计算
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        int s = (int) ((toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (ONE_DAY_MS));
        if (s > 0) {
            for (int i = 0; i <= s; i++) {
                long todayDate = fromCalendar.getTimeInMillis() + i * ONE_DAY_MS;
                Log.i("打印日期", getCustonFormatTime(todayDate, "yyyy/MM/dd"));
//                System.out.println(getCustonFormatTime(todayDate, "yyyy/MM/dd"));
                mDateList.add(i, getCustonFormatTime(todayDate, "yyyy/MM/dd"));
            }

        } else {//此时在同一天之内
//            Log.i("打印日期", getCustonFormatTime(startTime, "yyyy-MM-dd"));
//            System.out.println(getCustonFormatTime(startTime, "yyyy/MM/dd"));
//            System.out.println(startTime);
            mDateList.add(startTime);
        }

        return mDateList;
    }

    /**
     * 格式化传入的时间
     *
     * @param time      需要格式化的时间
     * @param formatStr 格式化的格式
     * @return
     */
    public static String getCustonFormatTime(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date d1 = new Date(time);
        return format.format(d1);
    }

    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param date
     * @param format
     * @return
     */
    public static long convert2long(String date, String format) {
        try {
            if (!TextUtils.isEmpty(date)) {
                if (TextUtils.isEmpty(format))
                    format = "yyyy/MM/dd";
                SimpleDateFormat sf = new SimpleDateFormat(format);
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }


    public static List<String> betweenDaysForWeek(String mstart, String mend) {

        List<String> mWeekList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        long CONST_WEEK = 1000 * 3600 * 24 * 7;//一周毫秒数
        try {
            Date start = sdf.parse(mstart);
            Date end = sdf.parse(mend);

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(start);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(end);

            Date now = new Date();
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(now);

            //查找开始日期的那个星期的第一天
            int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK);
            startCal.add(Calendar.DATE, -(dayOfWeek - 1));//周日是第一天 所以-1
            //查找结束日期的那个星期的第一天
            dayOfWeek = endCal.get(Calendar.DAY_OF_WEEK);
            endCal.add(Calendar.DATE, 7 - (dayOfWeek - 1));

            //计算总共多少周
            int total = (int) ((endCal.getTimeInMillis() - startCal.getTimeInMillis()) / CONST_WEEK);

            for (int i = 0; i < total; i++) {
                HashMap<String, String> week = new HashMap<String, String>();
                week.put("index", String.valueOf(i + 1));
                week.put("title", "第" + (i + 1) + "周");
                startCal.add(Calendar.DATE, 1);
                String time = sdf.format(startCal.getTime());//第一天
                startCal.add(Calendar.DATE, 6);
                time += "-" + sdf.format(startCal.getTime());//最后一天
                week.put("time", time);
//                        weeks.add(week);
                System.out.println("第" + (i + 1) + "周 : " + time);

                mWeekList.add(i, time);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mWeekList;
    }

    /**
     * 时间段内有几个月
     *
     * @param mStartDate
     * @param mEndDate
     * @return
     */
    public static List<String> betweenDaysForMonth(String mStartDate, String mEndDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        List<String> mDateList = new ArrayList<>();
        try {
            Date d1 = format.parse(mStartDate);
            Date d2 = format.parse(mEndDate);//定义结束日期

            Calendar dd = Calendar.getInstance();//定义日期实例

            dd.setTime(d1);//设置日期起始时间

            while (dd.getTime().before(d2)) {//判断是否到结束日期

                String str = format.format(dd.getTime());

                System.out.println(str);//输出日期结果
                mDateList.add(str);
                dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
            }
            mDateList.add(mEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateList;
    }


    public static List<String> betweenYears(String mStartDate, String mEndDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        List<String> mYears = new ArrayList<>();

        try {
            Date d1 = format.parse(mStartDate);
            Date d2 = format.parse(mEndDate);

            Calendar mStart = Calendar.getInstance();
            mStart.setTime(d1);

            Calendar mEnd = Calendar.getInstance();
            mEnd.setTime(d2);

            if (mEnd.getTime().before(mStart.getTime())) {
                throw new IllegalArgumentException("the end time must after start time");
            }

            int mStartYear = mStart.get(Calendar.YEAR);
            int mEndYear = mEnd.get(Calendar.YEAR);

            for (int i = 0; i <= (mEndYear - mStartYear); i++) {
                int year = mStartYear + i;
                mYears.add(i, year + "");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mYears;

    }

    /**
     * 获取某个月的天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date mDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

}