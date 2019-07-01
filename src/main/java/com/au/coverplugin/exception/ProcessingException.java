package com.au.coverplugin.exception;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 2:11 PM 2019/1/22
 */
public class ProcessingException extends Exception{

    public ProcessingException() {
        super();
    }

    public ProcessingException(final String message) {
        super(message);
    }

    public ProcessingException(final Throwable cause) {
        super(cause);
    }

    public ProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
