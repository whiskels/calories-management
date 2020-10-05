package ru.javawebinar.topjava.web.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.validation.Valid;

import static ru.javawebinar.topjava.web.ExceptionInfoHandler.EXCEPTION_USER_DUPLICATE_MAIL;

@Controller
@RequestMapping("/profile")
public class ProfileUIController extends AbstractUserController {

    @GetMapping
    public String profile() {
        return "profile";
    }

    @PostMapping
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        try {
            if (result.hasErrors()) {
                return "profile";
            } else {
                super.update(userTo, SecurityUtil.authUserId());
                SecurityUtil.get().update(userTo);
                status.setComplete();
                return "redirect:/meals";
            }
        } catch (DataIntegrityViolationException e) {
            return handleDuplicateMail(result);
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("register", true);
                return "profile";
            } else {
                super.create(userTo);
                status.setComplete();
                return "redirect:/login?message=app.registered&username=" + userTo.getEmail();
            }
        } catch (DataIntegrityViolationException e) {
            return handleDuplicateMail(result);
        }
    }

    private String handleDuplicateMail(BindingResult result) {
        // https://mkyong.com/spring-mvc/spring-mvc-form-handling-example/  (4 Form Validator)
        result.rejectValue("email", EXCEPTION_USER_DUPLICATE_MAIL);
        return "profile";
    }
}