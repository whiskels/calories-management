package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static ru.javawebinar.topjava.util.ValidationUtil.getLocalizedMessageIfExist;
import static ru.javawebinar.topjava.util.ValidationUtil.getStringErrorDetails;
import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    public static final String EXCEPTION_USER_DUPLICATE_MAIL = "exception.user.duplicateMail";
    public static final String EXCEPTION_MEAL_DUPLICATE_DATETIME = "exception.meal.duplicateDateTime";

    private static final Map<String, String> DATABASE_CONSTRAINTS = Map.of(
            "meals_unique_user_datetime_idx", EXCEPTION_MEAL_DUPLICATE_DATETIME,
            "users_unique_email_idx", EXCEPTION_USER_DUPLICATE_MAIL
    );


    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private final MessageSourceAccessor messageSourceAccessor;

    public ExceptionInfoHandler(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo validationError(HttpServletRequest req, Exception e) {
        BindingResult bindingResult = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String errorDetails = getStringErrorDetails(bindingResult);
        log.warn("{} at request  {}: {}", VALIDATION_ERROR, req.getRequestURL(), errorDetails);
        return new ErrorInfo(req.getRequestURL(), VALIDATION_ERROR, errorDetails);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String message = ValidationUtil.getRootCause(e).getMessage();
        if (message != null) {
            message = message.toLowerCase();
            for (Map.Entry<String, String> entry : DATABASE_CONSTRAINTS.entrySet()) {
                if (message.contains(entry.getKey())) {
                    return logAndGetErrorInfo(req,
                            new DataIntegrityViolationException(
                                    messageSourceAccessor.getMessage(entry.getValue())),
                            true,
                            VALIDATION_ERROR);
                }
            }
        }
        return logAndGetErrorInfo(req,
                e,
                true,
                DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, getLocalizedMessageIfExist(rootCause));
    }
}