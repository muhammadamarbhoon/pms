package com.product.pms.core;

import com.product.pms.data.dto.ErrorInfo;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ApplicationException.class)
    public HttpEntity<ErrorInfo> handleApplicationException(ApplicationException exception) {

        ApplicationError error = exception.getError();

        if (error.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(error.getCode());
        errorInfo.setMessage(exception.getMessage());

        return ResponseEntity.status(error.getHttpStatus()).body(errorInfo);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public HttpEntity<ErrorInfo> handleException(ConstraintViolationException exception) {

        if (ApplicationError.INVALID_PARAMETER.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        String errorMessage =
                exception.getConstraintViolations().stream()
                        .map(violation -> "Parameter '"
                                + ((PathImpl) violation.getPropertyPath()).getLeafNode().getName() + "' "
                                + violation.getMessage())
                        .collect(Collectors.toList()).get(0);

        ErrorInfo error = new ErrorInfo();
        error.setCode(ApplicationError.INVALID_PARAMETER.getCode());
        error.setMessage(errorMessage);

        return ResponseEntity.status(ApplicationError.INVALID_PARAMETER.getHttpStatus()).body(error);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HttpEntity<ErrorInfo>
            handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {

        if (ApplicationError.MISSING_PARAMETER.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo error = new ErrorInfo();
        error.setCode(ApplicationError.MISSING_PARAMETER.getCode());
        error.setMessage("Parameter '" + exception.getParameterName() + "' is missing");

        return ResponseEntity.status(ApplicationError.MISSING_PARAMETER.getHttpStatus()).body(error);

    }

    @ExceptionHandler(MissingPathVariableException.class)
    public HttpEntity<ErrorInfo> handleMissingPathVariableException(MissingPathVariableException exception) {

        if (ApplicationError.MISSING_PARAMETER.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo error = new ErrorInfo();
        error.setCode(ApplicationError.MISSING_PARAMETER.getCode());
        error.setMessage("Parameter '" + exception.getVariableName() + "' is missing");

        return ResponseEntity.status(ApplicationError.MISSING_PARAMETER.getHttpStatus()).body(error);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<ErrorInfo> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        if (ApplicationError.INVALID_PARAMETER.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> "Parameter '" + error.getField() + "' " + error.getDefaultMessage())
                .collect(Collectors.toList()).get(0);

        ErrorInfo error = new ErrorInfo();
        error.setCode(ApplicationError.INVALID_PARAMETER.getCode());
        error.setMessage(errorMessage);

        return ResponseEntity.status(ApplicationError.INVALID_PARAMETER.getHttpStatus()).body(error);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpEntity<ErrorInfo>
            handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {

        if (ApplicationError.INVALID_PARAMETER.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo error = new ErrorInfo();
        error.setCode(ApplicationError.INVALID_PARAMETER.getCode());
        error.setMessage("Parameter '" + exception.getName() + "' " + exception.getCause().getMessage());

        return ResponseEntity.status(ApplicationError.INVALID_PARAMETER.getHttpStatus()).body(error);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HttpEntity<ErrorInfo> handleException(HttpMessageNotReadableException exception) {

        if (ApplicationError.MISSING_REQUEST_BODY.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo error = new ErrorInfo();
        error.setCode(ApplicationError.MISSING_REQUEST_BODY.getCode());
        error.setMessage(ApplicationError.MISSING_REQUEST_BODY.getMessage());

        return ResponseEntity.status(ApplicationError.MISSING_REQUEST_BODY.getHttpStatus()).body(error);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpEntity<ErrorInfo> handleException(HttpRequestMethodNotSupportedException exception) {

        if (ApplicationError.HTTP_METHOD_NOT_SUPPORTED.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo error = new ErrorInfo();

        error.setCode(ApplicationError.HTTP_METHOD_NOT_SUPPORTED.getCode());
        error.setMessage(ApplicationError.HTTP_METHOD_NOT_SUPPORTED.getMessage());

        return ResponseEntity.status(ApplicationError.HTTP_METHOD_NOT_SUPPORTED.getHttpStatus()).body(error);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public HttpEntity<ErrorInfo> handleException(AccessDeniedException exception) {

        if (ApplicationError.FORBIDDEN.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo error = new ErrorInfo();

        error.setCode(ApplicationError.FORBIDDEN.getCode());
        error.setMessage(ApplicationError.FORBIDDEN.getMessage());

        return ResponseEntity.status(ApplicationError.FORBIDDEN.getHttpStatus()).body(error);

    }

    @ExceptionHandler(Exception.class)
    public HttpEntity<ErrorInfo> handleException(Exception exception) {

        if (ApplicationError.SERVER_ERROR.getLogLevel() == Level.ERROR) {
            log.error("Exception: {}, ", exception.getMessage(), exception);
        } else {
            log.warn("Exception: {} ", exception.getMessage());
        }

        ErrorInfo error = new ErrorInfo();

        error.setCode(ApplicationError.SERVER_ERROR.getCode());
        error.setMessage(ApplicationError.SERVER_ERROR.getMessage());

        return ResponseEntity.status(ApplicationError.SERVER_ERROR.getHttpStatus()).body(error);

    }

}
