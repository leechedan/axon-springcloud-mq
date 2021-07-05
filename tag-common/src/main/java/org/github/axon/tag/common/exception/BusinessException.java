package org.github.axon.tag.common.exception;

import java.io.Serializable;

/**
 * @author lee
 * @date 2018/7/25 15:59
 */
public class BusinessException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    public BusinessException(BusinessErrorCode businessErrorCode) {
        super(businessErrorCode.getStatus() + "#" + businessErrorCode.getMsg());
    }

    public BusinessException(BusinessErrorCode businessErrorCode, String additionalString) {
        super(businessErrorCode.getStatus() + "#" + businessErrorCode.getMsg() + ":" + additionalString);
    }

    public BusinessException(String code, String message) {
        super(code + "#" + message);
    }

    public BusinessException(String message) {
        super(500 + "#" + message);
    }

    // 重写此方法返回this,业务异常不打印堆栈信息提高速度
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}