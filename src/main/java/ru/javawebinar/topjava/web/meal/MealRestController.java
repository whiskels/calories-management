package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        final int userId = SecurityUtil.authUserId();
        log.info("create meal {} for {}", meal, userId);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void update(Meal meal) {
        final int userId = SecurityUtil.authUserId();
        log.info("update meal with id={}", meal.getId());
        assureIdConsistent(meal, meal.getId());
        service.update(userId, meal);
    }

    public void delete(int id) {
        final int userId = SecurityUtil.authUserId();
        log.info("delete id {} for user {}", id, userId);
        service.delete(userId, id);
    }

    public Meal get(int id) {
        final int userId = SecurityUtil.authUserId();
        log.info("get id {} for user {}", id, userId);
        return service.get(userId, id);
    }

    public List<MealTo> getAll() {
        final int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        final int userId = SecurityUtil.authUserId();
        final List<Meal> mealsByDate = service.getByDate(userId, startDate, endDate);

        return MealsUtil.getFilteredTos(mealsByDate,
                SecurityUtil.authUserCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime);
    }
}