package com.xrw.springCloudAlibaba.utils;


import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TimeUtil {


    /**
     * @param strDate e.g. "2012-12-12 12:12:12"
     * @return Wed Dec 12 12:12:12 CST 2012
     */
    public static Date stringToDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            try {
                // 第二种格式方案
                date = dateFormat2.parse(strDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
                return null;
            }
        }
        return date;
    }

    public static Date stringToDate(String strDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
        }
        return date;
    }


    /**
     * 中文时间转时间戳
     *
     * @param time----中文时间(2019-10-17  08:50:43)
     * @param format--时间的格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static long stringToLong2(String time, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(time);
        } catch (Exception e) {
            return 0;
        }
        return date.getTime();
    }


    //-------------------------------报表查询使用到的时间方法-------------------------

    /**
     * date转中文格式时间
     *
     * @param date           当前时间
     * @param format--中文时间格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateToString2(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 最近24小时
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getBefor24Hours(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 最近一周
     *
     * @param date
     * @return
     */
    public static String getBefore1Week(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 最近一个月
     *
     * @param date
     * @return
     */
    public static String getBefore1Month(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(date);
        return str;
    }
    /**
     * 最近一个月
     *
     * @param date
     * @return
     */
    public static String getBefore1Year(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 当天
     * @return yyyy-MM-dd
     */
    public static String getNowDay(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 明天
     * @param date
     * @return
     */
    public static String getTomorrow(Date date) {
        Calendar calendar = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,1);
        String str= dateFormat.format(calendar.getTime());
        //String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 当月
     * @return yyyy-MM-dd
     */
    public static String getNowMonth(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 前一年月份
     * @return yyyy-MM
     */
    public static String getBeforMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 前一天日期
     * @return yyyy-MM-dd
     */
    public static String getBeforDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 前一天日期
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getBeforDay2(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 秒数转为时分秒
     * @param time
     * @return
     */
    public static String transfom(final int time) {
        int hh = time / 3600;
        int mm = (time % 3600) / 60;
        int ss = (time % 3600) % 60;
        return (hh < 10 ? ("0" + hh) : hh) + ":" + (mm < 10 ? ("0" + mm) : mm) + ":" + (ss < 10 ? ("0" + ss) : ss);
    }
    /**
     * @param date 前3天日期
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getBeforDay3(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 前3天日期
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getAfterDay7(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
    }

    public static String getAfterDay7Two(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * @param date 当前时间前一小时
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getOneHoursAgoTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, -1);
        Date dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(dt);
        return str;
    }

    /**
     * @param date 当前时间前n小时
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getNHoursAgoTime(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, -n);
        Date dt = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(dt);
        return str;
    }

    /***
     * 取得本周第一天日期
     * */
    public static String getThisWeekFistDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.add(Calendar.DATE, -day_of_week);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
    }

    /***
     * 取得本周第最后一天日期
     * */
    public static String getThisWeekEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.add(Calendar.DATE, -day_of_week + 6);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
    }

    /***
     * 取得上周第一天日期
     * */
    public static String getLastWeekFistDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset1 - 7);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
    }

    /***
     * 取得上周最后一天日期
     * */
    public static String getLastWeekEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset2 = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset2 - 7);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str = dateFormat.format(date);
        return str;
    }

    /**
     * 得到本月的第一天
     *
     * @return
     */
    public static String getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static String getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 得到上月的第一天
     *
     * @return
     */
    public static String getLastMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(calendar.getTime()) + "-01";
    }

    /**
     * 得到上月的最后一天
     *
     * @return
     */
    public static String getLastMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(calendar.getTime()) + "-31";
    }


    /**
     * 得到上年的第一天
     *
     * @return
     */
    public static String getLastYearFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(calendar.getTime()) + "-01";
    }

    /**
     * 得到上年的最后一天
     *
     * @return
     */
    public static String getLastYearLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(calendar.getTime()) + "-31";
    }

    /**
     * 获取本年的第一天
     *
     * @return String
     **/
    public static String getYearStart() {
        return new SimpleDateFormat("yyyy").format(new Date()) + "-01-01";
    }

    /**
     * 获取本年的最后一天
     *
     * @return String
     **/
    public static String getYearEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date currYearLast = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(currYearLast);
    }

    /**
     * 2020-4-10 23:00:00 ----->2020-04-10 23:00:00
     */
    public static String stringToString(String timeString) {
        if (timeString != null && !timeString.equals("")) {
            return TimeUtil.dateToString2(TimeUtil.stringToDate(timeString, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        Date date = new Date();
        System.out.println("ddddd" + TimeUtil.getThisWeekFistDay(date));
        System.out.println("ddddd" + TimeUtil.getLastWeekFistDay(date));
        System.out.println("ddddd" + TimeUtil.getLastWeekEndDay(date));
        System.out.println("ddddd" + TimeUtil.getMonthFirstDay());
        System.out.println("ddddd" + TimeUtil.getMonthLastDay());
        System.out.println("ddddd" + TimeUtil.getLastMonthFirstDay());
        System.out.println("ddddd" + TimeUtil.getLastMonthLastDay());
//    	String str="1005#";
//    	System.out.print(str.split("#").length);
//    	System.out.print(getPastMonthDay());
        System.out.print(TimeUtil.getAfterDay7(new Date()));

////    	  Long  expiry=TimeUtil.stringToDate("2020-03-07","yyyy-mm-dd").getTime()-TimeUtil.stringToDate("2020-03-02","yyyy-mm-dd").getTime();
//    	  Long  expiry=TimeUtil.stringToDate("2020-12-09","yyyy-mm-dd").getTime()-TimeUtil.stringToDate(TimeUtil.getNowDay(new Date()),"yyyy-mm-dd").getTime();
//
//    	  Long day=24*60*60*1000L;
////    	  Long time=expiry/day;
////    	  System.out.print(time);
//    	  Long  expiryTimeDay=TimeUtil.stringToDate("2020-12-14","yyyy-mm-dd").getTime()-TimeUtil.stringToDate(TimeUtil.getNowDay(new Date()),"yyyy-mm-dd").getTime();
//	    	Long expiryDay=expiryTimeDay/day;
////	    	System.out.print(expiryDay);
//
//	    	System.out.print(TimeUtil.stringToDate("2020-12-14","yyyy-mm-dd"));
//	    	System.out.print(TimeUtil.dateToString2(TimeUtil.stringToDate("2020-02-14","yyyy-mm-dd"), "yyyy-m-d"));
//	    	System.out.print(TimeUtil.getAfterDay7(new Date()).substring(5).replace("-","月"));
//    	System.out.print(TimeUtil.dateToString2(TimeUtil.stringToDate("", "yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
        String nowTime = TimeUtil.getNowDay(new Date()) + " 00:00:01";
        System.out.println(nowTime);

        String nowTime2 = TimeUtil.dateToString2(new Date(), "HH:mm");
        DateFormat df = new SimpleDateFormat("HH:mm");
        System.out.print(df.parse(nowTime2).getTime() > df.parse("18:30").getTime());
//    	System.out.print(getYearEnd());

    }

    /**
     * 时间比对:对比小时跟分钟,拿当前的时间的小时跟分钟与towTime做对比,如果时间大于twoTime,返回true
     * format---------格式   -----HH:mm
     * twoTime--------时间2-----09:10
     *
     * @throws ParseException
     */

    public static boolean checkBigTime(String format, String twoTime) throws ParseException {
        String nowTime = TimeUtil.dateToString2(new Date(), "HH:mm");
        DateFormat df = new SimpleDateFormat(format);
        boolean result = (df.parse(nowTime).getTime() > df.parse(twoTime).getTime());
        return result;
    }


    public static boolean checkNowTimeBetweenStartAndEnd(String format, String startTime, String endTime) throws ParseException {
        String nowTime = TimeUtil.dateToString2(new Date(), "HH:mm");
        DateFormat df = new SimpleDateFormat(format);
        boolean result = (df.parse(nowTime).getTime() > df.parse(startTime).getTime() && df.parse(nowTime).getTime() < df.parse(endTime).getTime());
        return result;
    }

    /**
     * 获取传入年份月份的第一天时间戳
     *
     * @param sYear
     * @param sMonth
     * @return zlw
     */
    public static long getFirstDayofMonth(int sYear, int sMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, sYear);
        c.set(Calendar.MONTH, sMonth);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 获取传入年份月份的最后一天时间戳
     *
     * @param sYear
     * @param sMonth
     * @return zlw
     */
    public static long getEndDayofMonth(int sYear, int sMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, sYear);
        c.set(Calendar.MONTH, sMonth);
        //c.getActualMaximum(Calendar.DAY_OF_MONTH)
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        c.set(Calendar.MINUTE, 59);
        //将秒至59
        c.set(Calendar.SECOND, 59);
        //将毫秒至 999
        c.set(Calendar.MILLISECOND, 999);
//		tEnddate = String.valueOf(sYear)+"-"+String.valueOf(sMonth)+"-"+c.getActualMaximum(c.DAY_OF_MONTH);
        return c.getTimeInMillis();
    }

    public static String getPastYearMonth() {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String getNowYearMonth() {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String getPastMonthDay() {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);//当前时间减去一年，即一年前的时间

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String getNowMonthDay() {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String getPastQuarter() {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -3);//当前时间减去一年，即一年前的时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 比较时间获取差值
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Map getCompareTimes(Date date1, Date date2) {
        long s = (date2.getTime() - date1.getTime()) / 1000; // 相差多少秒
        long m = s / 60; // 相差多少分
        double h = m * 1.0 / 60; // 相差多少小时
        h = (double) Math.round(h * 100) / 100;
        long d = (long) (h / 24);
        Map<String, Object> map = new HashMap<>();
        map.put("second", s);
        map.put("minute", m);
        map.put("hour", h);
        map.put("day",d);
        return map;
    }

    /**
     * 法一
     * 获取近多少天的日期day是天数
     * @param day
     * @return
     */
    public static List<String> getDate(int day) {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < day; i++) {
            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf.format(date);
            dateList.add(formatDate);
        }
        return dateList;
    }

    /**
     * 法二
     * 获取近多少天的日期day是天数
     * @param
     * @return
     */
//    public static List<String> getDateList(int day) {
//        List<String> dateList = new ArrayList<>();
//        LocalDateTime time = LocalDateTime.now();
//        for (int i = 0; i < day; i++) {
//            LocalDateTime time1 = time.minus(-(i + i), ChronoUnit.DAYS);
//            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            time1.format(dt);
//            dateList.add(time1.format(dt));
//        }
//        return dateList;
//    }

    //获取最近一年的月份
    public static List<String> getThisYearMonths() {
        //建一个容器
        List<String> months = new ArrayList<>();
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //调整到12个月以前
        calendar.add(Calendar.MONTH, -12);
        //循环12次获取12个月份
        for (int i = 0; i < 12; i++) {
            //日历对象转为Date对象
            Date date = calendar.getTime();
            //将date转为字符串
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            String dateStr = sdf.format(date);
           // String dateStr1 = sdf1.format(dateStr);
            //向list集合中添加
            months.add(dateStr);
            //每次月份+1
            calendar.add(Calendar.MONTH, 1);
        }
        return months;
    }

    /**获取两个时间节点之间的月份列表**/
    public static List<String> getMonthBetween(String minDate, String maxDate){
        ArrayList<String> result = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}