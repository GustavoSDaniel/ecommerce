package com.gustavosdaniel.backend.image;

import com.gustavosdaniel.backend.exception.BaseExceptionBusiness;

public class ErrorValidateImage extends BaseExceptionBusiness {

    public ErrorValidateImage() {
    }

    public ErrorValidateImage(String message) {
        super(message);
    }

    public ErrorValidateImage(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorValidateImage(Throwable cause) {
        super(cause);
    }

    public ErrorValidateImage(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
