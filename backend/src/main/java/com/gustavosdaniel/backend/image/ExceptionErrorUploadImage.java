package com.gustavosdaniel.backend.image;

import com.gustavosdaniel.backend.exception.BaseExceptionBusiness;

public class ExceptionErrorUploadImage extends BaseExceptionBusiness {
    public ExceptionErrorUploadImage() {
    }

    public ExceptionErrorUploadImage(String message) {
        super(message);
    }

    public ExceptionErrorUploadImage(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionErrorUploadImage(Throwable cause) {
        super(cause);
    }

    public ExceptionErrorUploadImage(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
