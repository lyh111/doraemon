package com.autosite.codegen.exception;

import java.util.Map;

/**
 * <p>Description: 业务逻辑异常类</p>
 * @author hsh
 * @date 2017年10月29日
 */
public class SysBZException extends SysBaseException {
	
	private static final long serialVersionUID = 1L;
	public static final int RETURNDATA = 1;
    public static final int NOTRETURNDATA = 0;
	
    public SysBZException(SysErrorCode code) {
        super(code);
    }

    public SysBZException(SysErrorCode code, Object... fillStr) {
        super(code, fillStr);
    }
    
    public SysBZException(int isReturnData, SysErrorCode code, String errMsg, Map<String,Object> data) {
    	super(isReturnData,code,errMsg,data);
    }

}
