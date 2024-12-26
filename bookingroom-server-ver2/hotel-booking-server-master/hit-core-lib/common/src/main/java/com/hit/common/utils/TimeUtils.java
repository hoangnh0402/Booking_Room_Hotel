package com.hit.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Log4j2
@UtilityClass
public class TimeUtils {

    public static final String VIETNAM_DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";
    public static final String ISO_DATE_TIME_UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String ISO_DATE_TIME_UTC_2_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String ISO_OFFSET_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_DATE_PATTERN = "HH:mm:ss yyyy-MM-dd";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_ID_PATTERN = "yyyyMMddHHmmss";

    public static String formatLocalDate(LocalDate date, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return formatter.format(date);
        } catch (Exception e) {
            log.error("formatLocalDate ERROR", e);
            return null;
        }
    }

    public static LocalDate parseToLocalDate(String dateStr, String pattern) {
        if (StringUtils.isEmptyOrBlank(dateStr)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            log.error("parseToLocalDate ERROR", e);
            return null;
        }
    }

    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return formatter.format(datetime);
        } catch (Exception e) {
            log.error("formatLocalDateTime ERROR", e);
            return null;
        }
    }

    public static LocalDateTime parseToLocalDateTime(String datetimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (StringUtils.isEmptyOrBlank(datetimeStr)) {
            return null;
        } else if (datetimeStr.contains(".")) {
            datetimeStr = datetimeStr.substring(0, datetimeStr.indexOf('.'));
        }
        try {
            return LocalDateTime.parse(datetimeStr, formatter);
        } catch (Exception e) {
            log.error("parseToLocalDateTime ERROR", e);
            return null;
        }
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            log.error("dateToLocalDateTime ERROR", e);
            return null;
        }
    }

    public static LocalDate dateToLocalDate(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            log.error("dateToLocalDate ERROR", e);
            return null;
        }
    }


    public static String dateToString(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            log.error("dateToString ERROR", e);
            return null;
        }
    }

    public static Date stringToDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            log.error("stringToDate ERROR", e);
            return null;
        }
    }


    /*
     *
     * Methods calculate date
     *
     * */

    public static Integer getDaysBetween(LocalDateTime start, LocalDateTime end) {
        return Math.toIntExact(Math.round(ChronoUnit.HOURS.between(start, end) / 24d));
    }
}
