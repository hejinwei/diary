package com.hejinwei.diary.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hejinwei.diary.dao.mybatis.model.Diary;
import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.enums.DiaryTypeEnum;
import com.hejinwei.diary.enums.PrivateTypeEnum;
import com.hejinwei.diary.enums.WeatherEnum;
import com.hejinwei.diary.helpmodel.Page;
import com.hejinwei.diary.service.DiaryService;
import com.hejinwei.diary.service.MemcachedService;
import com.hejinwei.diary.util.Constants;

@Controller
public class ProfileController {

	@Autowired
	private DiaryService diaryService;
	
	@Autowired
	private MemcachedService memcachedService;

	@RequestMapping("/profile/list/{userId}")
	public ModelAndView profile(HttpServletRequest request, 
			@PathVariable("userId") Long userId,
			@RequestParam(value = "type", defaultValue = "0") Byte type,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		ModelAndView mav = new ModelAndView("template/profile/list");

		if ((byte) 0 == type) {
			type = null;
		}
		
		User user = (User) memcachedService.getUser(userId + "");

		// 分页查询日记列表
		int count = diaryService.findDiaryCountForProfile(userId, type);
		if (count == 0) {
			mav.addObject("diaries", null);
		} else {
			List<Diary> diaries = diaryService.findDiaryPageForProfile(userId, type, pageNum, Constants.DEFAULT_PAGE_SIZE);
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
		mav.addObject("type", type == null ? (byte) 0 : type);
		mav.addObject("owner", user);

		return mav;
	}

}
