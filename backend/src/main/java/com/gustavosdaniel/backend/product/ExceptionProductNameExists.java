package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.exception.BaseExceptionBusiness;

public class ExceptionProductNameExists extends BaseExceptionBusiness {

    public ExceptionProductNameExists() {
    }

    public ExceptionProductNameExists(String message) {
        super(message);
    }

    public ExceptionProductNameExists(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionProductNameExists(Throwable cause) {
        super(cause);
    }

    public ExceptionProductNameExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
