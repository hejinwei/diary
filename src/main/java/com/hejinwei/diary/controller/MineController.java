package com.hejinwei.diary.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hejinwei.diary.dao.mybatis.model.Diary;
import com.hejinwei.diary.enums.DiaryTypeEnum;
import com.hejinwei.diary.enums.PrivateTypeEnum;
import com.hejinwei.diary.enums.WeatherEnum;
import com.hejinwei.diary.helpmodel.Page;
import com.hejinwei.diary.service.DiaryService;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;

/**
 * Created by jinweihe on 16/7/24.
 */
@Controller
public class MineController {

	@Autowired
	private DiaryService diaryService;

	@RequestMapping("/mine")
	public ModelAndView mine(HttpServletRequest request, @RequestParam(value = "type", defaultValue = "0") Byte type,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

		
		if ((byte) 0 == type) {
			type = null;
		}
		
		ModelAndView mav = new ModelAndView("template/mine/list");

		Cookie userIdCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
		if (userIdCookie == null || userIdCookie.getValue() == null) {
			mav = new ModelAndView("login");
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

		return mav;
	}

	@RequestMapping("/mine/addDiary")
	public ModelAndView addDiary() {
		ModelAndView mav = new ModelAndView("template/diary/addOrEdit");

		mav.addObject("diaryTypeEnums", DiaryTypeEnum.values());
		mav.addObject("weatherEnums", WeatherEnum.values());
		mav.addObject("privateTypeEnums", PrivateTypeEnum.values());

		return mav;
	}

}
