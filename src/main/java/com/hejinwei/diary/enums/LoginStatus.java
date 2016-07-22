package com.hejinwei.diary.enums;

public enum LoginStatus {
	
	SUCCESS(0, "成功"),
	
	INPUT_EMPTY(1, "用户名或密码为空"),
	
	USER_NOT_EXIST(2, "用户不存在"),
	
	PASSWORD_WRONG(3, "密码错误");
	
	
	private Integer code;
	
	private String desc;

	private LoginStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
