package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.exception.BaseExceptionBusiness;

public class ExceptionCategoryNameExists extends BaseExceptionBusiness {
    public ExceptionCategoryNameExists() {
    }

    public ExceptionCategoryNameExists(String message) {
        super(message);
    }

    public ExceptionCategoryNameExists(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionCategoryNameExists(Throwable cause) {
        super(cause);
    }

    public ExceptionCategoryNameExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
