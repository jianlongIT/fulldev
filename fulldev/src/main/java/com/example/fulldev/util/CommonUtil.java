package com.example.fulldev.util;

import com.example.fulldev.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;
        PageCounter pageCounter = new PageCounter(pageNum, count);
        return pageCounter;
    }

    public static Boolean isInTimeLine(Date date, Date start, Date end) {
        Long time = date.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        if (time > startTime && time < endTime) {
            return true;
        }
        return false;
    }

    public static Calendar addSomeSeconds(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    //period 单位：秒
    public static Boolean isOutOfDate(Date startTime, Long period) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long startTimeStamp = startTime.getTime();
        Long periodMillSecond = period * 1000;
        if (now > (startTimeStamp + periodMillSecond)) {
            return true;
        }
        return false;
    }

    public static String timeStamp10() {
        Long timeStamp = Calendar.getInstance().getTimeInMillis();
        String timeStampStr = timeStamp.toString();
        return timeStampStr.substring(0, timeStampStr.length() - 3);
    }

    public static String yuanToFenPlainString(BigDecimal p) {
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    public static String toPlain(BigDecimal p) {
        return p.stripTrailingZeros().toPlainString();
    }

}
