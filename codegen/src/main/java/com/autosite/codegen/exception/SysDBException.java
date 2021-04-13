package com.autosite.codegen.exception;


/**
 * <p>Description: 数据处理异常类</p>
 * @author hsh
 * @date 2017年10月29日
 */
public class SysDBException extends SysBaseException {

    private static final long serialVersionUID = 1L;

    public SysDBException(SysErrorCode code, Object... fill) {
        super(code, fill);
    }

    public SysDBException(SysErrorCode code) {
        super(code);
    }
}
