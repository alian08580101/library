package com.jd.a6i.common;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static java.lang.Float.isNaN;
import static java.lang.String.format;

public final class StringUtils {

    private static final Pattern whiteSpacePattern = Pattern.compile("\\s+");
    private static final Pattern numberPattern = Pattern.compile("[0-9]*");
    public static final String EMPTY = "";

    public static boolean isNotBlank(String text) {
        return text != null && !"".equalsIgnoreCase(text.trim());
    }

    public static boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    public static boolean isNumber(String text) {
        return text != null && numberPattern.matcher(text).matches();
    }

    public static String removeAllWhiteSpace(String text) {
        return text == null ? null : whiteSpacePattern.matcher(text).replaceAll("");
    }

    public static String formatTimeToHHMMString(Date time) {
        return time == null ? null : new SimpleDateFormat("HH:mm").format(time);
    }

    public static String formatTemplateString(Context context, int templateId, Object... params) {
        String template = context.getResources().getText(templateId).toString();
        return format(template, params);
    }

    public static boolean isEqual(String text, String target) {
        return !isBlank(text) && text.equals(target);
    }
}
