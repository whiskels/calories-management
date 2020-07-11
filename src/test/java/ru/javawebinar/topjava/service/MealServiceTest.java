package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void createUserMeal() throws Exception {
        Meal meal = getNewUserMeal();
        Meal created = service.create(meal, USER_ID);
        Integer newId = created.getId();
        meal.setId(newId);
        assertMatch(created, meal);
        assertMatch(service.get(newId, USER_ID), meal);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_START_ID, USER_ID);
        assertNull(repository.get(MEAL_START_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteOtherUserMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_START_ID + 4, USER_ID));
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_START_ID, USER_ID);
        assertMatch(meal, USER_MEAL_MORNING);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getOtherUserMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_START_ID + 4, USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        int updatedId = updated.getId();
        service.update(updated, USER_ID);
        assertMatch(service.get(updatedId, USER_ID), updated);
    }

    public void updateOtherUserMeal() throws Exception {
        Meal updated = getUpdated();
        int updatedId = updated.getId();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, USER_MEAL_SUPPER, USER_MEAL_DINNER, USER_MEAL_LUNCH, USER_MEAL_MORNING);
    }

    @Test
    public void getAllBetweenNullDates() throws Exception {
        assertEquals(service.getBetweenInclusive(null, null, ADMIN_ID).size(), 1);
    }

    @Test
    public void getAllBetweenFutureDates() throws Exception {
        assertEquals(service.getBetweenInclusive(LocalDate.now().plusYears(100), null, USER_ID).size(), 0);
    }
}