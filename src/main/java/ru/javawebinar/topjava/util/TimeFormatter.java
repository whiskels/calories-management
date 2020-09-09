package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

public class TimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(@Nullable String text, Locale locale) throws ParseException {
        return text.isEmpty() ? null : LocalTime.parse(text);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.format(ISO_LOCAL_TIME);
    }
}
