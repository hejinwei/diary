package com.hejinwei.diary.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;

public class BaseController {
	
	public String operateResult(int code, String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		return jsonObject.toString();
	}
	
	public boolean isLogined(HttpServletRequest request) {
		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		
		if (userIdCookie == null || userIdCookie.getValue() == null) {
			return false;
		}
		
		return true;
	}

}
