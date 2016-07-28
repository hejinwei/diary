package com.hejinwei.diary.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.service.MemcachedService;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;

public class NeedLoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private MemcachedService memcachedService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie userIdCookie =
                CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
        Cookie passwordCookie =
                CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_PASSWORD);

        if (userIdCookie != null && userIdCookie.getValue() != null && passwordCookie != null
                && passwordCookie.getValue() != null) {
			User user = (User) memcachedService.getWithType(userIdCookie.getValue(), User.class);
            if (user != null && user.getPassword().equals(passwordCookie.getValue())) {
                return true;
            }
        }

        response.sendRedirect(Constants.contextPath + "/login");

        return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// modelAndView为null的时候代表ajax请求
		if (modelAndView != null) {
			Cookie userIdCookie =
					CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
			if (userIdCookie != null && userIdCookie.getValue() != null) {
				User user = (User) memcachedService.getWithType(userIdCookie.getValue(), User.class);
				modelAndView.addObject("curUser", user);
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 暂时do nothing
	}

}
