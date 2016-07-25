package com.hejinwei.diary.enums;

import java.util.HashMap;
import java.util.Map;

public enum WeatherEnum {
	
	SUNNY("SUNNY", "晴"),
	CLOUDY("CLOUDY", "阴天"),
	SMALL_RAIN("SMALL_RAIN", "小雨"),
	BIG_RAIN("BIG_RAIN", "大雨"),
	TYPHOON("TYPHOON", "台风");

	private String code;
	
	private String desc;
	
	private WeatherEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<>();
		for (WeatherEnum weatherEnum : values()) {
			map.put(weatherEnum.code, weatherEnum.desc);
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
