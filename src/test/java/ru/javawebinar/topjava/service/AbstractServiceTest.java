package ru.javawebinar.topjava.service;

import org.assertj.core.annotations.Beta;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.JUnitStopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AbstractServiceTest {
    protected final static Logger log = LoggerFactory.getLogger(AbstractServiceTest.class);
    private static Map<String, Long> testResults;

    @BeforeClass
        public static void beforeClass() {
        testResults = new HashMap<>();
    }

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
