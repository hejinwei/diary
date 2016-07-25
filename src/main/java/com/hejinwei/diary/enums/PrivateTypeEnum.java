package com.hejinwei.diary.enums;

import java.util.HashMap;
import java.util.Map;

public enum PrivateTypeEnum {
	
	PUBLIC("PUBLIC", "所有人可见"),
	
	PROTECTED("PROTECTED", "密码可见"),
	
	PRIVATE("PRIVATE", "仅自己可见");
	
	private String code;
	
	private String desc;
	
	private PrivateTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<>();
		for (PrivateTypeEnum privateTypeEnum : values()) {
			map.put(privateTypeEnum.code, privateTypeEnum.desc);
		}
		
		return map;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
