package com.liushunqiu.error;

import java.io.Serializable;

public class ApiResultVO implements Serializable {

	private int code = 0;
	private String message = "";
	private Object data = null;
	private boolean success = false;

	private ApiResultVO(int code, String message, Object data, boolean success) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.success = success;
	}

	public static ApiResultVO buildOKResult(Object data) {
		return new ApiResultVO(0, "OK", data, true);
	}

	public static ApiResultVO buildErrResult(String message){
		return new ApiResultVO(-1,message,null,false);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public boolean isSuccess() {
		return success;
	}
}
