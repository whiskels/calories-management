package ru.javawebinar.topjava;

import java.util.concurrent.TimeUnit;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitStopWatch extends Stopwatch {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        log.info(String.format("Test %s %s, spent %d microseconds",
                testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }
}
