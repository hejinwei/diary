package com.hejinwei.diary.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SystemUtil {
	
	public static boolean isLogined(HttpServletRequest request) {
		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		
		if (userIdCookie == null || userIdCookie.getValue() == null) {
			return false;
		}
		
		return true;
	}

}
