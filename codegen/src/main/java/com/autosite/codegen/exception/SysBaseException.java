package com.autosite.codegen.exception;

import java.text.MessageFormat;
import java.util.Map;


/**
 * <p>Description: 异常基类</p>
 * @author hsh
 * @date 2017年10月29日
 */
public class SysBaseException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
    private String errorCode;
    private String errorMsg;
    private String msgDetail;
    private Map<String,Object> data;

    public String getMsgDetail() {
        return msgDetail;
    }

    public void setMsgDetail(String msgDetail) {
        this.msgDetail = msgDetail;
    }

    public SysBaseException(SysErrorCode code, Object... fillStr) {
        this.errorCode = code.getErrorCode();
        this.errorMsg = MessageFormat.format(code.getErrorMsg(), fillStr);
        this.msgDetail = code.getMsgDetail();
    }

    public SysBaseException(int isReturnData, SysErrorCode code, String errMsg, Map<String,Object> data) {
    	if(isReturnData == 0){
    		this.errorCode = code.getErrorCode();
            this.errorMsg = MessageFormat.format(code.getErrorMsg(), data);
            this.msgDetail = code.getMsgDetail();
    	}else{
    		this.errorCode = code.getErrorCode();
            this.errorMsg = errMsg;
            this.msgDetail = code.getMsgDetail();
            this.data = data;
    	}
    }
    
    public SysBaseException(SysErrorCode code) {
        this.errorCode = code.getErrorCode();
        this.errorMsg = code.getErrorMsg();
        this.msgDetail = code.getMsgDetail();
    }

    public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}

	public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return getErrorMsg();
    }

    @Override
    public String toString() {
        return "错误码:[ " + this.getErrorCode() + " ] ,错误信息:[ " + this.getErrorMsg() + " ] ," + "解决办法:[ "
                + this.getMsgDetail() + " ]";
    }
}
