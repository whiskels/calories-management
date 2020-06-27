package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.LocalMealMapStorage;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MapStorage<Meal> localMealMapStorage;

    @Override
    public void init() throws ServletException {
        super.init();
        localMealMapStorage = new LocalMealMapStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        final String action = req.getParameter("action");
        log.debug("POST request. Action: {}", action);

        switch (action != null ? action : "") {
            case "update": // drop through to create
            case "create":
                final Long id = req.getParameter("id") == null ? null : Long.parseLong(req.getParameter("id"));
                final String description = req.getParameter("description");
                final int calories = Integer.parseInt(req.getParameter("calories"));
                final LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));

                Meal meal = new Meal(id, dateTime, description, calories);
                log.debug(meal.toString());

                localMealMapStorage.save(meal);
                log.debug("Saved meal to {}", localMealMapStorage.getClass().toString());
                break;
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final String action = req.getParameter("action");
        log.debug("GET request. Action: {}", action);
        long id;

        switch (action != null ? action : "") {
            case "delete":
                id = Long.parseLong(req.getParameter("id"));
                localMealMapStorage.delete(id);
                log.debug("Deleted id {}", id);
                resp.sendRedirect("meals");
                log.debug("Redirected to meals");
                break;
            case "edit":
                id = Long.parseLong(req.getParameter("id"));
                Meal meal = localMealMapStorage.get(id);
                log.debug("Redirected to update form for id {}",id);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/meal-edit-form.jsp").forward(req, resp);
                break;
            default:
                req.setAttribute("mealList", MealsUtil.getAllWithExceeded((List<Meal>)localMealMapStorage.getAll(), MealsUtil.DAILY_CALORIES_LIMIT));
                log.debug("Showing all meals");
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }
}
