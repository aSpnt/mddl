package ru.softmachine.odyssey.backend.cms.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Slf4j
@Component
public class DateTimeUtils {

    private static final String[] FORMATS = new String[]{
            "yyyy-MM-dd",
            "HH:ss",
            "yyyy-MM-dd'T'HH:mm:ssZ"
    };

    /**
     * Нужно аккуратно с дефолтной таймзоной
     */
    public LocalDate getLocalDateByString(String value) {
        try {
            return DateUtils.parseDate(value, FORMATS).toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDate();
        } catch (ParseException e) {
            log.warn("Error parsing date: {}", value);
            return null;
        }
    }

    /**
     * Нужно аккуратно с дефолтной таймзоной
     */
    public LocalTime getLocalTimeByString(String value) {
        try {
            return DateUtils.parseDate(value, FORMATS).toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalTime();
        } catch (ParseException e) {
            log.warn("Error parsing time: {}", value);
            return null;
        }
    }

    /**
     * Нужно аккуратно с дефолтной таймзоной
     */
    public LocalDateTime getLocalDateTimeByString(String value) {
        try {
            return DateUtils.parseDate(value, FORMATS).toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime();
        } catch (ParseException e) {
            log.warn("Error parsing date time: {}", value);
            return null;
        }
    }

    /**
     * Нужно аккуратно с дефолтной таймзоной
     */
    public ZonedDateTime getZonedDateTimeByString(String value) {
        try {
            return ZonedDateTime.ofInstant(DateUtils.parseDate(value, FORMATS).toInstant(), TimeZone.getDefault().toZoneId());
        } catch (ParseException e) {
            log.warn("Error parsing date time to zone date time: {}", value);
            return null;
        }
    }
}
