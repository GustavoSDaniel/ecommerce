package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.exception.BaseExceptionRunTime;

public class ProductIdNotFoundException extends BaseExceptionRunTime {
    public ProductIdNotFoundException() {
    }

    public ProductIdNotFoundException(String message) {
        super(message);
    }

    public ProductIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIdNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductIdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
