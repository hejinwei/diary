package com.hejinwei.diary.enums;

import java.util.HashMap;
import java.util.Map;

public enum DiaryTypeEnum {
	
	LIUSHUIZHANG( (byte) 1, "流水账"),
	
	MOOD_RELEASE( (byte) 2, "心情发泄"),
	
	BOOK_COMMENT( (byte) 3, "书评"),
	
	FILM_COMMENT( (byte) 4, "影评" );

	private Byte type;
	
	private String desc;
	
	private DiaryTypeEnum(Byte type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public static Map<Byte, String> getMap() {
		Map<Byte, String> map = new HashMap<>();
		for (DiaryTypeEnum diaryTypeEnum : values()) {
			map.put(diaryTypeEnum.type, diaryTypeEnum.desc);
		}
		
		return map;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
