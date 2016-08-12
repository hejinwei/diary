package com.hejinwei.diary.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.enums.LoginStatusEnum;
import com.hejinwei.diary.service.MemcachedService;
import com.hejinwei.diary.service.UserService;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;
import com.hejinwei.diary.util.MD5Util;

@Controller
public class LoginController {

	@Autowired
	private MemcachedService memcachedService;

	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String index() {
		return "redirect:/login";
	}
	

	@RequestMapping("/login")
	public String login() {
		return "template/login";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public String doLogin(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String password = request.getParameter("password");
		JSONObject jsonObject = new JSONObject();

		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
			jsonObject.put("code", LoginStatusEnum.INPUT_EMPTY.getCode());
			jsonObject.put("desc", LoginStatusEnum.INPUT_EMPTY.getDesc());
			return jsonObject.toString();
		}

		User user = userService.findByName(name);
		if (user == null) {
			jsonObject.put("code", LoginStatusEnum.USER_NOT_EXIST.getCode());
			jsonObject.put("desc", LoginStatusEnum.USER_NOT_EXIST.getDesc());
			return jsonObject.toString();
		}

		password = MD5Util.getMD5WithSalt(password);
		if (!password.equals(user.getPassword())) {
			jsonObject.put("code", LoginStatusEnum.PASSWORD_WRONG.getCode());
			jsonObject.put("desc", LoginStatusEnum.PASSWORD_WRONG.getDesc());
			return jsonObject.toString();
		}

		memcachedService.setWithType(user.getUserId() + "", user, User.class);
		CookieHelper.addCookie(response, Constants.COOKIE_NAME_USERID, user.getUserId() + "", Constants.COOKIE_MAX_AGE);
		CookieHelper.addCookie(response, Constants.COOKIE_NAME_PASSWORD, user.getPassword(), Constants.COOKIE_MAX_AGE);

		jsonObject.put("code", LoginStatusEnum.SUCCESS.getCode());
		jsonObject.put("desc", LoginStatusEnum.SUCCESS.getDesc());
		return jsonObject.toString();
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		if (userIdCookie != null && userIdCookie.getValue() != null) {
			memcachedService.deleteWithType(userIdCookie.getValue(), User.class);
			CookieHelper.deleteCookie(request, response, Constants.COOKIE_NAME_USERID);
			CookieHelper.deleteCookie(request, response, Constants.COOKIE_NAME_PASSWORD);
		}

		return "redirect:/login";
	}
	
	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView("template/register");
		mav.addObject("user", null);
		return mav;
	}
	
	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public String doRegister(User user) {
		if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
			throw new RuntimeException("用户信息缺失");
		}
		
		user.setPassword(MD5Util.getMD5WithSalt(user.getPassword()));
		userService.add(user);
		return "redirect:/mine";
	}

}
