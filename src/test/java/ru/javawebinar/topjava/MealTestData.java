package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int MEAL_START_ID = START_SEQ + 2;


    public static final Meal USER_MEAL_MORNING = new Meal(MEAL_START_ID, LocalDateTime.of(2020, 7, 11, 10, 0), "Pastrami sandwich", 500);
    public static final Meal USER_MEAL_LUNCH = new Meal(MEAL_START_ID + 1, LocalDateTime.of(2020, 7, 11, 12, 0), "Orange juice", 100);
    public static final Meal USER_MEAL_DINNER = new Meal(MEAL_START_ID + 2, LocalDateTime.of(2020, 7, 11, 14, 0), "Vegetable salad", 500);
    public static final Meal USER_MEAL_SUPPER = new Meal(MEAL_START_ID + 3, LocalDateTime.of(2020, 7, 11, 20, 0), "Ice cream", 500);
    public static final Meal ADMIN_MEAL = new Meal(MEAL_START_ID + 4, LocalDateTime.of(2020, 7, 11, 22, 0), "Admin super meal", 9999);

    public static Meal getNewUserMeal() {
        return new Meal(null, LocalDateTime.now(), "newMeal", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL_MORNING);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(9999);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
