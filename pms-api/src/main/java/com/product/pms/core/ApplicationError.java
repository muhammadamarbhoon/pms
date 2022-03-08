package com.product.pms.core;

import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

public enum ApplicationError {

    FORBIDDEN(HttpStatus.FORBIDDEN, "PMS-00", "Action Foridden", Level.ERROR),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "PMS-01", "Bad Request", Level.ERROR),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PMS-02", "Server Error", Level.ERROR),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "PMS-03", "Missing request parameter", Level.ERROR),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "PMS-04", "Invalid value for one or more parameters in request",
            Level.WARN),
    HTTP_METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "PMS-05", "HTTP Method not supported", Level.ERROR),
    MISSING_REQUEST_BODY(HttpStatus.BAD_REQUEST, "PMS-06", "Required request body is missing", Level.ERROR),
    NO_PRODUCTS_FOUND(HttpStatus.NOT_FOUND, "PMS-07", "No Products found", Level.ERROR),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PMS-08", "Product not found", Level.ERROR);

    private HttpStatus httpStatus;
    private String code;
    private String message;
    private Level logLevel;

    ApplicationError(HttpStatus httpStatus, String code, String message, Level logLevel) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Level getLogLevel() {
        return logLevel;
    }
}
