package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class DateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(@Nullable String text, Locale locale) throws ParseException {
        return text.isEmpty() ? null : LocalDate.parse(text);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(ISO_LOCAL_DATE);
    }
}
