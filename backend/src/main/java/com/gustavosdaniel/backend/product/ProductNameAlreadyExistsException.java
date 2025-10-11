package com.gustavosdaniel.backend.product;

import com.gustavosdaniel.backend.exception.BaseExceptionBusiness;

public class ProductNameAlreadyExistsException extends BaseExceptionBusiness {
    public ProductNameAlreadyExistsException() {
    }

    public ProductNameAlreadyExistsException(String message) {
        super(message);
    }

    public ProductNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNameAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ProductNameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
