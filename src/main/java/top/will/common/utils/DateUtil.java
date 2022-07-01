package top.will.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Desc: 日期时间相关的工具类
 * @author panhao
 */
public class DateUtil {
    private DateUtil(){}

    /**
     * Date增加一天
     * @param date 被增加的日期
     * @return 增加一天的日期
     * @author panhao
     */
    public static Calendar dateOfNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    /**
     * 判断日期是否超过指定日期当天
     * @param specifyDate 超期日期 deadline
     * @return  true-超期 false-没超期
     * @author panhao
     */
    public static Boolean isOverdue(Date specifyDate) {
        return dateOfNextDay(specifyDate).getTime().compareTo(Calendar.getInstance().getTime()) < 0;
    }


    /**
     * 获取某年某月最开始时间点
     * @author panhao
     */
    public static Calendar getYearFirstTime(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取某年某月最后的时间点
     * @author panhao
     */
    public static Calendar getYearLastTime(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

}
