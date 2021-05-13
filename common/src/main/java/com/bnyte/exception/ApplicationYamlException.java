package com.bnyte.exception;


/**
 * @说明
 * @创建日期 2021-05-11 星期二
 * @作者 bnyte
 * @邮箱 liugxion@163.com
 * @版本 V1.0
 */
public class ApplicationYamlException extends RuntimeException {

    public ApplicationYamlException() {
        super();
    }

    public ApplicationYamlException(String message) {
        super(message);
    }

    public ApplicationYamlException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationYamlException(Throwable cause) {
        super(cause);
    }

    protected ApplicationYamlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
