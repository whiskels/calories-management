package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class LocalMealMapStorage implements MapStorage<Meal> {
    private final Map<Long, Meal> database = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    public LocalMealMapStorage() {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Кофе с маффином", 600));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Пастрами сэндвич", 500));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Капрезе бургер", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Полуночный банан", 200));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Чай с печеньем", 200));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Паста с курицей", 400));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Фрукты", 200));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal != null) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
            }
            database.put(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public void delete(long id) {
        database.remove(id);
    }

    @Override
    public Meal get(long id) {
        return database.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(database.values());
    }
}
