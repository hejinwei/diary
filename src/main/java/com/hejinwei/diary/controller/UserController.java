package com.hejinwei.diary.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.service.UserService;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;
import com.hejinwei.diary.util.MD5Util;
import com.hejinwei.diary.util.SystemUtil;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/mine/editPersonalInfo")
	public ModelAndView editPersonalInfo(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		if ( !SystemUtil.isLogined(request) ) {
			mav.setViewName("template/login");
			return mav;
		}
		
		Long userId = Long.parseLong(CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID).getValue());
		User user = userService.findById(userId);
		
		if (user == null) {
			mav.setViewName("template/login");
			return mav;
		}
		
		mav.addObject("user", user);
		mav.setViewName("template/register");
		return mav;
	}
	
	@RequestMapping(value = "/mine/doEditPersonalInfo", method = RequestMethod.POST)
	public String doEditPersonalInfo(User user) {
		
		userService.edit(user);
		
		return "redirect:/mine";
	}
	
	@RequestMapping("/mine/editPassword")
	public String editPassword(HttpServletRequest request) {
		
		if ( !SystemUtil.isLogined(request) ) {
			return "template/login";
		}
		
		return "template/mine/editPassword";
	}
	
	@RequestMapping(value="/mine/doEditPassword", method = RequestMethod.POST)
	@ResponseBody
	public String doEditPassword(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		if ( !SystemUtil.isLogined(request) ) {
			jsonObject.put("code", 1);
			jsonObject.put("message", "未登录");
			return jsonObject.toString();
		}
		
		try {
			Long userId = Long.parseLong(CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID).getValue());
			
			String oldPassword = request.getParameter("oldPassword");
			oldPassword = MD5Util.getMD5WithSalt(oldPassword);
			User user = userService.findById(userId);
			if ( !user.getPassword().equals(oldPassword) ) {
				jsonObject.put("code", 2);
				jsonObject.put("message", "旧密码错误");
				return jsonObject.toString();
			}
			
			String newPassword = request.getParameter("newPassword");
			newPassword = MD5Util.getMD5WithSalt(newPassword);
			userService.editPassword(userId, newPassword);
			
			jsonObject.put("code", 0);
			jsonObject.put("message", "修改成功，请重新登录");
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("code", 99);
			jsonObject.put("message", "系统异常");
			return jsonObject.toString();
		}
		
	}

}
