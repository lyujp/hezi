package com.lyujp.heziservice.util;
import org.apache.commons.text.StringEscapeUtils;

public class FilterUtils {
    public static String escapeHtml(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }
}