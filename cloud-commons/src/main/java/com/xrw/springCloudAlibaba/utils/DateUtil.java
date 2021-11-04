package com.xrw.springCloudAlibaba.utils;

import com.alibaba.fastjson.JSONObject;
import com.xrw.springCloudAlibaba.enums.FileConstant;
import com.xrw.springCloudAlibaba.exception.ApiError;
import com.xrw.springCloudAlibaba.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author unknow
 */
@Slf4j
public class DateUtil {

    /** 默认日期时间格式 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static String formatTimestamp(Timestamp timestamp, String format) {
        if (format == null) {
            format = "yyyyMMddHHmmss";
        }
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            tsStr = sdf.format(timestamp);
            return tsStr;
        } catch (Exception e) {
            log.error("", e);
        }
        return tsStr;
    }

    public static String timeStampToString(Timestamp timestamp) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            tsStr = sdf.format(timestamp);
            //System.out.println(tsStr);
            return tsStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    public static String timeStampToString(String format,Timestamp timestamp) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            tsStr = sdf.format(timestamp);
            //System.out.println(tsStr);
            return tsStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 功能描述: 日期转换成字符串
     * @param dateDate 转换的日期
     * @param pattern 转换格式
     * @Return: java.lang.String
     * @Author: xjx
     * @Date: 2019/11/18 11:13
     */
    public static String dateToStrWithPattern(Date dateDate, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static Date beginningOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date endOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date parseDate(String str) {
        if (str == null) {
            return null;
        }
        try {
            Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDateWithException(String str) throws ParseException {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw e;
        }
    }

    public static Timestamp parseTimestamp(String str) {
        if (str == null) {
            return null;
        }
        try {
            Date utilDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
            return new Timestamp(utilDate.getTime());
        } catch (ParseException e) {
            try {
                Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                return new Timestamp(utilDate.getTime());
            } catch (ParseException e1) {
                return null;
            }
        }
    }

    public static Timestamp parseTimestampWithException(String str) throws ParseException {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            Date utilDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
            return new Timestamp(utilDate.getTime());
        } catch (ParseException e) {
            try {
                Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                return new Timestamp(utilDate.getTime());
            } catch (ParseException e1) {
                throw e1;
            }
        }
    }

    public static List<Map<String, String>> getDateOfWeek(String startTime, String endTime) {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar SC = Calendar.getInstance();
            Calendar EC = Calendar.getInstance();
            Date sTime = sdf.parse(startTime);
            Date eTime = sdf.parse(endTime);
            //取得开始日期指定日期所在周的第一天
            String first_week = getFirstDayOfWeek(startTime);
            //取得结束日期指定日期所在周的最后一天
            String last_week = getLastDayOfWeek(endTime);

            SC.setTime(sTime);
            EC.setTime(eTime);
            int year = SC.get(Calendar.YEAR);

            int currentWeekOfYear = SC.get(Calendar.WEEK_OF_YEAR);
            int currentWeekOfYear_e = EC.get(Calendar.WEEK_OF_YEAR);

            if (currentWeekOfYear_e == 1) {
                currentWeekOfYear_e = 53;
            }

            int j = 12;
            for (int i = 0; i < currentWeekOfYear_e; i++) {
                int dayOfWeek = EC.get(Calendar.DAY_OF_WEEK) - 2;
                //得到本周的第一天
                EC.add(Calendar.DATE, -dayOfWeek);

                String sDate = sdf.format(EC.getTime());
                //得到本周的最后一天
                EC.add(Calendar.DATE, 6);

                String eDate = sdf.format(EC.getTime());
                //减去增加的日期
                EC.add(Calendar.DATE, -j);

                //只取两个日期之间的周
                if (currentWeekOfYear == currentWeekOfYear_e - i + 2) {
                    break;
                }

                //只取两个日期之间的周
                if (compareDate(first_week, sDate) && compareDate(sDate, last_week)
                        && compareDate(first_week, eDate) && compareDate(eDate, last_week)) {
                    int count = 0;
                    //超过选择的日期，按选择日期来算
                    if (!compareDate(startTime, sDate)) {
                        sDate = startTime;
                    }
                    if (!compareDate(eDate, endTime)) {
                        eDate = endTime;
                    }
                    Map<String, String> map = new HashMap<>(16);
                    map.put("year", String.valueOf(year));
                    map.put("week", String.valueOf((currentWeekOfYear + i)));
                    map.put("startTime", String.valueOf(sDate));
                    map.put("endTime", String.valueOf(eDate));
                    list.add(count, map);
                    count++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 取得指定日期所在周的第一天
     */
    public static String getFirstDayOfWeek(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date time = sdf.parse(date);
            Calendar c = new GregorianCalendar();
            c.setFirstDayOfWeek(Calendar.MONDAY);
            c.setTime(time);
            // Monday
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
            return sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }


    }

    /**
     * 取得指定日期所在周的最后一天
     */
    public static String getLastDayOfWeek(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date time = sdf.parse(date);
            Calendar c = new GregorianCalendar();
            c.setFirstDayOfWeek(Calendar.MONDAY);
            c.setTime(time);
            // Sunday
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
            return sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * compareDate方法
     * <p>方法说明：
     * 比较endDate是否是晚于startDate；
     * 如果是，返回true， 否则返回false
     * </p>
     */
    public static boolean compareDate(String startDate, String endDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = dateFormat.parse(startDate);
            Date date2 = dateFormat.parse(endDate);
            if (date1.getTime() > date2.getTime()) {
                return false;
            }
            //startDate时间上早于endDate
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * compareTime方法
     * <p>方法说明：
     * 比较endTime是否是晚于startTime；
     * 如果是，返回true， 否则返回false
     * </p>
     */
    public static boolean compareTime(String startTime, String endTime) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date1 = dateFormat.parse(startTime);
            Date date2 = dateFormat.parse(endTime);
            if (date1.getTime() > date2.getTime()) {
                return false;
            }
            //startDate时间上早于endDate
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean compareTime(String startTime, String endTime, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date1 = dateFormat.parse(startTime);
            Date date2 = dateFormat.parse(endTime);
            if (date1.getTime() > date2.getTime()) {
                return false;
            }
            //startDate时间上早于endDate
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static Date parseDateTime(String strDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
            return date;
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间断的天数
     *
     * @param cntDateBeg 开始时间
     * @param cntDateEnd 结束时间
     * @return
     */
    public static List<String> addDates(String cntDateBeg, String cntDateEnd) {
        List<String> list = new ArrayList<>();
        //拆分成数组
        String[] dateBegs = cntDateBeg.split("-");
        String[] dateEnds = cntDateEnd.split("-");
        //开始时间转换成时间戳
        Calendar start = Calendar.getInstance();
        start.set(Integer.valueOf(dateBegs[0]), Integer.valueOf(dateBegs[1]) - 1, Integer.valueOf(dateBegs[2]));
        Long startTime = start.getTimeInMillis();
        //结束时间转换成时间戳
        Calendar end = Calendar.getInstance();
        end.set(Integer.valueOf(dateEnds[0]), Integer.valueOf(dateEnds[1]) - 1, Integer.valueOf(dateEnds[2]));
        Long endTime = end.getTimeInMillis();
        //定义一个一天的时间戳时长
        Long oneDay = 1000 * 60 * 60 * 24L;
        Long time = startTime;
        //循环得出
        while (time <= endTime) {
            list.add(new SimpleDateFormat("yyyy-MM-dd").format(new Date(time)));
            time += oneDay;
        }
        return list;
    }

    /**
     * 获取时间断的月
     *
     * @param minDate 开始时间
     * @param maxDate 结束时间
     * @return
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *      * 获取任意时间的一个月
     *      * @param repeatDate
     *      * @return
     *     
     */
    public static String getPreMonth(String repeatDate) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            if (StringUtils.isNotBlank(repeatDate) && !FileConstant.NULL.equals(repeatDate)) {
                calendar.setTime(dft.parse(repeatDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dft.format(calendar.getTime());
    }

    public static Date getFirstDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }


    /**
     * 获取年份
     */
    public static List<String> getBetween(Integer minDate, Integer maxDate) {
        ArrayList<String> result = new ArrayList<>();
        try {
            while (true) {
                result.add(minDate.toString());
                if (minDate < maxDate) {
                    minDate++;
                    continue;
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *    * 获取某年第一天日期
     *    * @param year 年份
     *    * @return Date
     *    
     */
    public static String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(currYearFirst);
        return dateStr;
    }

    /**
     *    * 获取某年最后一天日期
     *    * @param year 年份
     *    * @return Date
     *    
     */
    public static String getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(currYearLast);
        return dateStr;
    }

    /**
     * 给当前日期增加日期
     *
     * @param beginDate
     * @param days
     * @return
     */
    public static Date addDay(Date beginDate, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(beginDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取这个月有多天
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(String date) throws ParseException {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = dft.parse(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个时间的时间差
     * @param endDate
     * @param beginDate
     * @return hh:mm:ss
     */
    public static String getDiffDateConversionTime(Date endDate, Date beginDate) {
        long diff = Math.abs(endDate.getTime() - beginDate.getTime());
        Long hours = (diff) / (1000 * 60 * 60);
        Long minutes = (diff - hours * (1000 * 60 * 60)) / (1000 * 60);
        Long second = (diff - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
        return numberZeroFill(hours.intValue()) + ":" + numberZeroFill(minutes.intValue()) + ":" + numberZeroFill(second.intValue());
    }

    /**
     * 获取两个时间的时间差
     * @param endDate
     * @param beginDate
     * @return hh时mm分ss秒
     */
    public static String getDiffDateConversionTimeCN(Date endDate, Date beginDate){
        String time = getDiffDateConversionTime(endDate, beginDate);
        return time.replaceFirst(":", "时").replaceFirst(":", "分") + "秒";
    }

    public static String numberZeroFill(int v1) {
        int max = 10;
        if (v1 < max && v1 >= 0) {
            return "0" + v1;
        } else if (v1 > -max && v1 < 0) {
            return "-0" + Math.abs(v1);
        } else {
            return String.valueOf(v1);
        }
    }

    /**
     * 获取当前日期
     *
     * @param wantYear  为true则获取年份
     * @param wantMonth 为true则获取月份
     * @param wantDay   为true则获取日期
     * @return
     */
    public static String getCurrentDate(boolean wantYear, boolean wantMonth, boolean wantDay) {
        Calendar c = Calendar.getInstance();
        StringBuilder stringBuilder = new StringBuilder("");
        if (wantYear) {
            stringBuilder.append(c.get(Calendar.YEAR));
        }
        if (wantMonth) {
            stringBuilder.append("-");
            String month = String.valueOf(c.get(Calendar.MONTH) + 1);
            month = month.length() == 2 ? month : "0" + month;
            stringBuilder.append(month);
        }
        if (wantDay) {
            stringBuilder.append("-");
            String day = String.valueOf(c.get(Calendar.DATE));
            day = day.length() == 2 ? day : "0" + day;
            stringBuilder.append(day);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取当前时间
     *
     * @param wantHour   为true则获取小时
     * @param wantMinute 为true则获取分钟
     * @param wantSecond 为true则获取分秒
     * @return
     */
    public static String getCurrentTime(boolean wantHour, boolean wantMinute, boolean wantSecond) {
        Calendar c = Calendar.getInstance();
        StringBuilder stringBuilder = new StringBuilder("");
        if (wantHour) {
            String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
            hour = hour.length() == 2 ? hour : "0" + hour;
            stringBuilder.append(hour);
        }
        if (wantMinute) {
            stringBuilder.append(":");
            String minute = String.valueOf(c.get(Calendar.MINUTE));
            minute = minute.length() == 2 ? minute : "0" + minute;
            stringBuilder.append(minute);
        }
        if (wantSecond) {
            stringBuilder.append(":");
            String second = String.valueOf(c.get(Calendar.SECOND));
            second = second.length() == 2 ? second : "0" + second;
            stringBuilder.append(second);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据年份与月份获取月份的每一天日期
     *
     * @param yearParam
     * @param monthParam
     * @return
     */
    public static List<String> getDayByMonth(int yearParam, int monthParam) {
        List list = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearParam, monthParam - 1, 1);
        //年份
        int year = calendar.get(Calendar.YEAR);
        //月份
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = null;
            if (month < 10 && i < 10) {
                aDate = String.valueOf(year) + "-0" + month + "-0" + i;
            }
            if (month < 10 && i >= 10) {
                aDate = String.valueOf(year) + "-0" + month + "-" + i;
            }
            if (month >= 10 && i < 10) {
                aDate = String.valueOf(year) + "-" + month + "-0" + i;
            }
            if (month >= 10 && i >= 10) {
                aDate = String.valueOf(year) + "-" + month + "-" + i;
            }

            list.add(aDate);
        }
        return list;
    }

    /**
     * 根据格式校验日期
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isValidDate(String str, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date date = format.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断middle时间是否在start时间与end时间之间
     *
     * @param start
     * @param end
     * @param middle
     * @return
     */
    public static Boolean isBetweenTime(String start, String end, String middle) {
        try {
            Date dateStart = new SimpleDateFormat("HH:mm:ss").parse(start);
            Date dateEnd = new SimpleDateFormat("HH:mm:ss").parse(end);
            Date dateMiddle = new SimpleDateFormat("HH:mm:ss").parse(middle);
            return dateMiddle.getTime() >= dateStart.getTime() && dateMiddle.getTime() <= dateEnd.getTime();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 增加天数
     *
     * @param s
     * @param n
     * @return
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(dateFormat.parse(s));
            //增加一天
            c.add(Calendar.DATE, n);
            //增加一个月
            //cd.add(Calendar.MONTH, n);
            return dateFormat.format(c.getTime());

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断middleTime时间是否在startTime时间与endTime时间之间
     * @param startTime
     * @param endTime
     * @param middleTime
     * @return
     * @throws Exception
     */
    public static Boolean isBetweenDate(String startTime, String endTime, String middleTime) throws Exception{
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
        Date middleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(middleTime);
        if (middleDate.getTime() >= startDate.getTime() && middleDate.getTime() <= endDate.getTime()) {
            return true;
        } else {
            return false;
        }
    }
    public static Boolean isBetweenDate(Date startTime, Date endTime, Date middleTime) {
        if (middleTime.getTime() >= startTime.getTime() && middleTime.getTime() <= endTime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    public static String CustomFormatPast2Day(Date date,Boolean formatMinute){
        Date current = new Date();
        SimpleDateFormat simpleDateFormat = null;
        Date beginOfToday = beginningOfDate(current);
        Date endOfToday = endOfDate(current);
        String result = "";
        Date beginOfYesterday = addDay(beginOfToday, -1);
        Date endOfYesterday = addDay(endOfToday, -1);
        if(isBetweenDate(beginOfToday,endOfToday,date)){
            result += "今天 ";
            if(formatMinute){
                simpleDateFormat = new SimpleDateFormat("HH:mm");
            }else{
                simpleDateFormat = new SimpleDateFormat("HH:00");
            }
        }else if(isBetweenDate(beginOfYesterday,endOfYesterday,date)){
            result += "昨天 ";
            if(formatMinute){
                simpleDateFormat = new SimpleDateFormat("HH:mm");
            }else{
                simpleDateFormat = new SimpleDateFormat("HH:00");
            }
        }else{
            if(formatMinute){
                simpleDateFormat = new SimpleDateFormat("M月d日 HH:mm");
            }else{
                simpleDateFormat = new SimpleDateFormat("M月d日 HH:00");
            }
        }
        result += simpleDateFormat.format(date);
        return result;
    }

    public static String dateFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static Date beginOfQuarter(int year, int quarter){
        if(quarter < 1 || quarter > 4){
            throw new  ApiException(ApiError.NUMBER_FORMAT_EXCEPTION);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, (quarter - 1) * 3, 1);
        return beginningOfDate(calendar.getTime());

    }

    public static  Date endOfQuarter(int year, int quarter){
        if(quarter < 1 || quarter > 4){
            throw new ApiException(ApiError.NUMBER_FORMAT_EXCEPTION);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, (quarter - 1) * 3 + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endOfDate(calendar.getTime());
    }

    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy-MM-dd HH:mm:ss
     * @param sourceDate
     * @return
     */
    public static Date dealDateFormat(String sourceDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date source;
        Date target = null;
        try {
            source = df.parse(sourceDate);
            target = df2.parse(df2.format(source));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 获取环比的时间段 （开始时间和结束时间的格式必须与时间格式一致）
     *
     * @param startTime 开始时间 yyyy-MM-dd HH:mm:ss / yyyy-MM-dd
     * @param endTime 结束时间 yyyy-MM-dd HH:mm:ss / yyyy-MM-dd
     * @param dateFormat 日期格式 yyyy-MM-dd HH:mm:ss /  yyyy-MM-dd
     * @return
     */
    public static JSONObject getChainComparisonTimeSlot(String startTime, String endTime, String dateFormat) {
        JSONObject result = new JSONObject();
        if (DEFAULT_DATE_TIME_FORMAT.equals(dateFormat)) {
            // 将时间格式的字符串转成 LocalDateTime ，便于处理
            LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(dateFormat));
            LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(dateFormat));
            // 计算结束时间与开始时间的 月份 的差值
            int differenceMonths = (end.getYear() - start.getYear()) * 12 + (end.getMonthValue() - start.getMonthValue());
            // 得出环比的时间
            LocalDateTime newStart = start.minusMonths(differenceMonths + 1);
            LocalDateTime newEnd = end.minusMonths(differenceMonths + 1);
            // 格式化环比的时间
            String resultStart = newStart.format(DateTimeFormatter.ofPattern(dateFormat));
            String resultEnd = newEnd.format(DateTimeFormatter.ofPattern(dateFormat));
            result.put("start", resultStart);
            result.put("end", resultEnd);
        } else if (DEFAULT_DATE_FORMAT.equals(dateFormat)) {
            LocalDate start = LocalDate.parse(startTime, DateTimeFormatter.ofPattern(dateFormat));
            LocalDate end = LocalDate.parse(endTime, DateTimeFormatter.ofPattern(dateFormat));
            int differenceMonths = (end.getYear() - start.getYear()) * 12 + (end.getMonthValue() - start.getMonthValue());
            LocalDate newStart = start.minusMonths(differenceMonths + 1);
            LocalDate newEnd = end.minusMonths(differenceMonths + 1);
            String resultStart = newStart.format(DateTimeFormatter.ofPattern(dateFormat));
            String resultEnd = newEnd.format(DateTimeFormatter.ofPattern(dateFormat));
            result.put("start", resultStart);
            result.put("end", resultEnd);
        }
        return result;
    }

    /**
     * 获取同比的时间段 （开始时间和结束时间的格式必须与时间格式一致）
     *
     * @param startTime 开始时间 yyyy-MM-dd HH:mm:ss / yyyy-MM-dd
     * @param endTime 结束时间 yyyy-MM-dd HH:mm:ss / yyyy-MM-dd
     * @param dateFormat 日期格式 yyyy-MM-dd HH:mm:ss /  yyyy-MM-dd
     * @return
     */
    public static JSONObject getYearOnYearTimeSlot(String startTime, String endTime, String dateFormat) {
        JSONObject result = new JSONObject();
        if (DEFAULT_DATE_TIME_FORMAT.equals(dateFormat)) {
            // 将时间格式的字符串转成 LocalDateTime ，便于处理
            LocalDateTime start = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(dateFormat));
            LocalDateTime end = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(dateFormat));
            // 计算结束时间与开始时间的 年份 的差值
            int differenceYears = end.getYear() - start.getYear();
            // 得出同比的时间
            LocalDateTime newStart = start.minusYears(differenceYears + 1);
            LocalDateTime newEnd = end.minusYears(differenceYears + 1);
            // 格式化同比的时间
            String resultStart = newStart.format(DateTimeFormatter.ofPattern(dateFormat));
            String resultEnd = newEnd.format(DateTimeFormatter.ofPattern(dateFormat));
            result.put("start", resultStart);
            result.put("end", resultEnd);
        } else if (DEFAULT_DATE_FORMAT.equals(dateFormat)) {
            LocalDate start = LocalDate.parse(startTime, DateTimeFormatter.ofPattern(dateFormat));
            LocalDate end = LocalDate.parse(endTime, DateTimeFormatter.ofPattern(dateFormat));
            int differenceYears = end.getYear() - start.getYear();
            LocalDate newStart = start.minusYears(differenceYears + 1);
            LocalDate newEnd = end.minusYears(differenceYears + 1);
            String resultStart = newStart.format(DateTimeFormatter.ofPattern(dateFormat));
            String resultEnd = newEnd.format(DateTimeFormatter.ofPattern(dateFormat));
            result.put("start", resultStart);
            result.put("end", resultEnd);
        }
        return result;
    }


}
