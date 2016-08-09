package com.hejinwei.diary.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.dao.mybatis.model.Diary;
import com.hejinwei.diary.dao.mybatis.model.DiaryPassword;
import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.enums.DiaryTypeEnum;
import com.hejinwei.diary.enums.PrivateTypeEnum;
import com.hejinwei.diary.enums.WeatherEnum;
import com.hejinwei.diary.helpmodel.Page;
import com.hejinwei.diary.service.DiaryService;
import com.hejinwei.diary.service.MemcachedService;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;

/**
 * Created by jinweihe on 16/7/24.
 */
@Controller
public class MineController {

	@Autowired
	private DiaryService diaryService;
	
	@Autowired
	private MemcachedService memcachedService;

	@RequestMapping("/mine")
	public ModelAndView mine(HttpServletRequest request, @RequestParam(value = "type", defaultValue = "0") Byte type,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

		
		if ((byte) 0 == type) {
			type = null;
		}
		
		ModelAndView mav = new ModelAndView("template/mine/list");

		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		if (userIdCookie == null || userIdCookie.getValue() == null) {
			mav = new ModelAndView("template/login");
			return mav;
		}

		// 分页查询日记列表
		Long userId = Long.parseLong(userIdCookie.getValue());
		int count = diaryService.findDiaryCount(userId, type);
		if (count == 0) {
			mav.addObject("diaries", null);
		} else {
			List<Diary> diaries = diaryService.findDiaryPage(userId, type, pageNum, Constants.DEFAULT_PAGE_SIZE);
			mav.addObject("diaries", diaries);
		}

		Page page = new Page();
		page.setCurrentPageNum(pageNum);
		page.setTotalCount(count);
		mav.addObject("page", page);

		// 数据字典：日记类型、天气、私密类型
		mav.addObject("diaryTypeEnums", DiaryTypeEnum.values());
		mav.addObject("diaryTypeMap", DiaryTypeEnum.getMap());
		mav.addObject("privateTypeMap", PrivateTypeEnum.getMap());
		mav.addObject("weatherMap", WeatherEnum.getMap());

		mav.addObject("type", type == null ? (byte)0 : type);

		return mav;
	}

	@RequestMapping("/mine/addDiary")
	public ModelAndView addDiary(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("template/diary/addOrEdit");
		
		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		User user = (User) memcachedService.getWithType(userIdCookie.getValue(), User.class);

		mav.addObject("diaryTypeEnums", DiaryTypeEnum.values());
		mav.addObject("weatherEnums", WeatherEnum.values());
		mav.addObject("privateTypeEnums", PrivateTypeEnum.values());
		mav.addObject("diary", null);
		mav.addObject("owner", user);

		return mav;
	}
	
	@RequestMapping("/mine/editDiary/{diaryId}")
	public ModelAndView editDiary(HttpServletRequest request, @PathVariable("diaryId") Long diaryId) {
		ModelAndView mav = new ModelAndView("template/diary/addOrEdit");
		
		Diary diary = diaryService.findDiary(diaryId);

		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		if (userIdCookie == null || userIdCookie.getValue() == null) {
			mav = new ModelAndView("template/login");
			return mav;
		}

		if (diary.getUserId() != Long.parseLong(userIdCookie.getValue())) {
			throw new RuntimeException("当前日记不属于你");
		}
		
		User user = (User) memcachedService.getWithType(userIdCookie.getValue(), User.class);

		mav.addObject("diary", diary);

		mav.addObject("diaryTypeEnums", DiaryTypeEnum.values());
		mav.addObject("weatherEnums", WeatherEnum.values());
		mav.addObject("privateTypeEnums", PrivateTypeEnum.values());
		mav.addObject("owner", user);

		return mav;
	}
	
	@RequestMapping(value = "/mine/doDeleteDiary/{diaryId}", method = RequestMethod.POST)
	@ResponseBody
	public String doDelete(HttpServletRequest request, @PathVariable("diaryId") Long diaryId) {

		JSONObject jsonObject = new JSONObject();

		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		if (userIdCookie == null || userIdCookie.getValue() == null) {
			jsonObject.put("code", 1);
			jsonObject.put("message", "请重新登录");
			return jsonObject.toString();
		}

		Diary diary = diaryService.findDiary(diaryId);
		if (diary.getUserId() != Long.parseLong(userIdCookie.getValue())) {
			throw new RuntimeException("当前日记不属于你");
		}

		diaryService.deleteDiary(diaryId);

		jsonObject.put("code", 0);
		jsonObject.put("message", "删除成功");
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/mine/doEditDiary", method = RequestMethod.POST)
	@ResponseBody
	public String doEditDiary(HttpServletRequest request, Diary diary) {

		JSONObject jsonObject = new JSONObject();

		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		if (userIdCookie == null || userIdCookie.getValue() == null) {
			jsonObject.put("code", 1);
			jsonObject.put("message", "请重新登录");
			return jsonObject.toString();
		}

		try {
			if ( PrivateTypeEnum.PROTECTED.getCode().equals(diary.getPrivateType()) ) {
				DiaryPassword diaryPassword = new DiaryPassword();
				diaryPassword.setDiaryId(diary.getDiaryId());
				diaryPassword.setPassword(request.getParameter("password"));
				diaryService.addOrEditPassword(diaryPassword);
			} else {
				Diary oldDiary = diaryService.findDiary(diary.getDiaryId());
				if ( PrivateTypeEnum.PROTECTED.getCode().equals(oldDiary.getPrivateType()) ) {
					diaryService.editPasswordStatus(diary.getDiaryId(), Constants.DELETE_STATUS);
				}
			}

			diaryService.editDiary(diary);
			
			jsonObject.put("code", 0);
			jsonObject.put("message", "保存成功");
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("code", 99);
			jsonObject.put("message", "系统异常");
			return jsonObject.toString();
		}

	}
	
	@RequestMapping(value = "/mine/doAddDiary", method = RequestMethod.POST)
	@ResponseBody
	public String doAddDiary(HttpServletRequest request, Diary diary) {
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
			if (userIdCookie == null || userIdCookie.getValue() == null) {
				jsonObject.put("code", 1);
				jsonObject.put("message", "未登录");
				return jsonObject.toString();
			}
			
			diary.setDate(new Date().getTime());
			diary.setUserId(Long.parseLong(userIdCookie.getValue()));
			
			diaryService.addDiary(diary);
			
			if (PrivateTypeEnum.PROTECTED.getCode().equals(diary.getPrivateType())) {
				DiaryPassword diaryPassword = new DiaryPassword();
				diaryPassword.setDiaryId(diary.getDiaryId());
				diaryPassword.setPassword(request.getParameter("password"));
				diaryService.addOrEditPassword(diaryPassword);
			}
			
			jsonObject.put("code", 0);
			jsonObject.put("message", "保存成功");
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("code", 99);
			jsonObject.put("message", "系统异常");
			return jsonObject.toString();
		}
		
	}

}
