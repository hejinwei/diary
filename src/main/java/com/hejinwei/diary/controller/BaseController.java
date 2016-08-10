package com.hejinwei.diary.controller;

import com.alibaba.fastjson.JSONObject;

public class BaseController {
	
	public String operateResult(int code, String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		return jsonObject.toString();
	}

}
