package com.backend.api.domain.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@UtilityClass
public class DateUtil {

    public String convertLocalDateTimeToString(LocalDateTime localDateTime){
        if (Objects.nonNull(localDateTime)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DEFAULT_FORMAT_DATE_TIME);
            return localDateTime.format(formatter);
        }
        return "";
    }

    public LocalDateTime convertToDateTime(String date){
        if (Objects.isNull(date)) return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constant.DEFAULT_FORMAT_DATE_TIME, Locale.ENGLISH);
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
