package com.product.pms.core;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ApplicationError error;

    public ApplicationException(ApplicationError error) {
        super(error.getMessage());
        this.error = error;
    }

    public ApplicationError getError() {
        return error;
    }

}
