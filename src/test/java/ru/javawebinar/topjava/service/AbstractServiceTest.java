package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.JUnitStopWatch;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AbstractServiceTest {
    protected final static Logger log = LoggerFactory.getLogger(AbstractServiceTest.class);
    private static final Map<String, Long> testResults = new TreeMap<>();

    @Rule
    public JUnitStopWatch watch = new JUnitStopWatch() {
        @Override
        protected void finished(long nanos, Description description) {
            testResults.put(description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            super.finished(nanos, description);
        }
    };

    @AfterClass
    public static void printResults() {
        log.info(testResults.entrySet().stream()
                .map(e -> String.format("Method %s finished in %d milliseconds", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n")));
    }
}
