package com.jd.a6i.common;

import static com.jd.a6i.common.StringUtils.formatTemplateString;
import static com.jd.a6i.common.ConstantUtils.MILLISECONDS_IN_A_DAY;

import java.util.Date;

import org.joda.time.DateTime;

import android.content.Context;

import com.jd.a6i.R;

public class Clock {
    public static final long ONE_MINUTE = 1000 * 60;

    private static long offsetInMillis = 0;

    private Clock() {
    }

    public static void setReferenceTime(DateTime dateTime) {
        setReferenceTime(dateTime.getMillis());
    }

    private static void setReferenceTime(long millis) {
        offsetInMillis = internalNow().getMillis() - millis;
    }

    public static DateTime now() {
        return adjustTime(internalNow());
    }

    public static Date adjustTime(Date time) {
        return adjustTime(new DateTime(time)).toDate();
    }

    public static DateTime adjustTime(DateTime time) {
        return minusMillis(time, offsetInMillis);
    }

    // An fix to DateTime's API, allow pass argument as long
    private static DateTime minusMillis(DateTime dateTime, long millis) {
        if (millis == 0)
            return dateTime;

        long instant = dateTime.getChronology().millis().subtract(dateTime.getMillis(), millis);
        return dateTime.withMillis(instant);
    }

    private static DateTime internalNow() {
        return new DateTime();
    }

    public static void resetTime() {
        offsetInMillis = 0;
    }

    public static String formatToYMD(DateTime dateTime) {
        return dateTime.toString("YYYYMMdd");
    }

    public static DateTime today() {
        return now().withTimeAtStartOfDay();
    }

    public static DateTime yesterday() {
        return today().minusDays(1);
    }

    public static DateTime tomorrow() {
        return today().plusDays(1);
    }

    public static DateTime thisMonth() {
        return today().withDayOfMonth(1);
    }

    public static String getYmdOfDate(Date date) {
        return new DateTime(date).toString("yyyy-MM-dd");
    }

    public static String getHmOfDate(Date date) {
        return new DateTime(date).toString("HH:mm");
    }

    public static long getIntervalDays(Date firstDate, Date secondDate) {
        firstDate = new DateTime(getYmdOfDate(firstDate)).toDate();
        secondDate = new DateTime(getYmdOfDate(secondDate)).toDate();

        return (secondDate.getTime() - firstDate.getTime()) / (MILLISECONDS_IN_A_DAY);
    }

    public static String getIntervalDaysDescription(Context context, Date firstDate, Date secondDate) {
        long internalDay = getIntervalDays(firstDate, secondDate);
        if (internalDay == 0)
            return formatTemplateString(context, R.string.same_day_text, internalDay);
        else if (internalDay > 0) {
            return formatTemplateString(context, R.string.after_interval_day_text, internalDay + 1);
        } else {
            return formatTemplateString(context, R.string.before_interval_day_text, Math.abs(internalDay));
        }
    }

    public static String getYMdHmsOfDate(DateTime referenceTime) {
        return referenceTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String getMonthAndDayOfDate(DateTime referenceTime) {
        return referenceTime.toString("M.d");
    }

    public static DateTime getThreeMonthAgoOfDate() {
        return Clock.today().minusMonths(3);
    }

    public static String getHmsOfDate(DateTime dateTime) {
        return dateTime.toString("HH:mm:ss");
    }
}
