package com.hejinwei.diary.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.helpmodel.Page;
import com.hejinwei.diary.service.MailService;
import com.hejinwei.diary.service.UserService;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;
import com.hejinwei.diary.util.MD5Util;
import com.hejinwei.diary.util.SystemUtil;

@Controller
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
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
		if ( !SystemUtil.isLogined(request) ) {
			return operateResult(1, "未登录");
		}
		
		try {
			Long userId = Long.parseLong(CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID).getValue());
			
			String oldPassword = request.getParameter("oldPassword");
			oldPassword = MD5Util.getMD5WithSalt(oldPassword);
			User user = userService.findById(userId);
			if ( !user.getPassword().equals(oldPassword) ) {
				return operateResult(2, "旧密码错误");
			}
			
			String newPassword = request.getParameter("newPassword");
			newPassword = MD5Util.getMD5WithSalt(newPassword);
			userService.editPassword(userId, newPassword);
			
			return operateResult(0, "修改成功，请重新登录");
		} catch (Exception e) {
			e.printStackTrace();
			return operateResult(99, "系统异常");
		}
		
	}
	
	@RequestMapping("/forgetPassword")
	public ModelAndView forgetPassword(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("template/forgetPassword");
		
		return mav;
	}

	@RequestMapping(value = "/doForgetPassword", method = RequestMethod.POST)
	@ResponseBody
	public String doForgetPassword(HttpServletRequest request) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email)) {
			throw new RuntimeException("用户名或邮箱为空");
		}
		
		User user = userService.findByName(name);
		if (user == null) {
			return operateResult(1, "用户不存在");
		}
		
		if ( !email.equalsIgnoreCase(user.getEmail()) ) {
			return operateResult(2, "用户名、邮箱不一致");
		}
		
		String newPassword = RandomStringUtils.randomAlphanumeric(8);
		String message = "你在" + Constants.webName + "的密码已被改为:" + newPassword;
		try {
			mailService.apacheSimpleMail(email, Constants.webName + "的新密码", message);
		} catch (EmailException e) {
			e.printStackTrace();
			return operateResult(3, "给" + email + "发送邮件失败，请联系网站管理员");
		}
		
		userService.editPassword(user.getUserId(), MD5Util.getMD5WithSalt(newPassword));
		
		return operateResult(0, "密码重置成功，请去邮箱查收");
	}
	
	@RequestMapping("/doSearchPeople")
	public ModelAndView findPeople(@RequestParam(value = "nickname" ) String nickname,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("template/searchResult");
		
		if (StringUtils.isEmpty(nickname)) {
			throw new RuntimeException("搜索内容为空");
		}
		
		nickname = URLDecoder.decode(nickname, "UTF-8");
		
		int count = userService.searchCountByNickname(nickname);
		Page page = new Page();
		page.setCurrentPageNum(pageNum);
		page.setTotalCount(count);
		mav.addObject("page", page);
		
		if (count == 0) {
			mav.addObject("userList", null);
		} else {
			List<User> userList = userService.searchPageUsersByNickname(nickname, pageNum, 10);
			mav.addObject("userList", userList);
		}
		
		mav.addObject("nickname", nickname);
		
		return mav;
	}
	
}
