package com.sogetirockstars.sogetipaintinglotteryserver.exception;

/**
 * PhotoMissingException
 */
public class PhotoWriteException extends Exception {
    private static final long serialVersionUID = 1L;

    public PhotoWriteException() {
    }

    public PhotoWriteException(String str) {
        super(str);
    }
}
