package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.exception.BaseExceptionRunTime;

public class ProductNameNotFoundException extends BaseExceptionRunTime {

    public ProductNameNotFoundException() {
    }

    public ProductNameNotFoundException(String message) {
        super(message);
    }

    public ProductNameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNameNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductNameNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
