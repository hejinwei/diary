package com.hejinwei.diary.controller;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.dao.mybatis.model.Diary;
import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.enums.DiaryTypeEnum;
import com.hejinwei.diary.enums.PrivateTypeEnum;
import com.hejinwei.diary.enums.WeatherEnum;
import com.hejinwei.diary.service.DiaryService;
import com.hejinwei.diary.service.MemcachedService;
import com.hejinwei.diary.service.StatisticService;
import com.hejinwei.diary.service.UserService;
import com.hejinwei.diary.util.Constants;
import com.hejinwei.diary.util.CookieHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jinweihe on 16/7/30.
 */
@Controller
public class ViewDiaryController {

    @Autowired
    private DiaryService diaryService;
    
    @Autowired
    private MemcachedService memcachedService;

    @Autowired
    private StatisticService statisticService;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/viewDiary/{diaryId}")
    public ModelAndView viewDiary(HttpServletRequest request, @PathVariable("diaryId") Long diaryId) {
        ModelAndView mav = new ModelAndView("template/diary/view");
        
        Diary diary = diaryService.findDiary(diaryId);
        
        User owner = (User) memcachedService.getUser(diary.getUserId() + "");
        
        Cookie curUserCookie = CookieHelper.getCookieByName(request, Constants.COOKIE_NAME_USERID);
        
        if (PrivateTypeEnum.PRIVATE.getCode().equals(diary.getPrivateType())) {
        	if (curUserCookie == null || curUserCookie.getValue() == null) {
        		throw new RuntimeException("No Login");
        	} else if (!curUserCookie.getValue().equals(diary.getUserId() + "")) {
        		throw new RuntimeException("Not CurrentUser");
        	}
        } else if (PrivateTypeEnum.PROTECTED.getCode().equals(diary.getPrivateType())) {
        	
        	if (curUserCookie != null && curUserCookie.getValue() != null && 
        			curUserCookie.getValue().equals(diary.getUserId() + "")) {
        		// 自己不需要密码
        	} else {
        		String password = request.getParameter("password");
            	if (password == null) {
            		throw new RuntimeException("No Password");
            	} else {
            		String realPassword = diaryService.findPassword(diaryId);
            		if (!password.equals(realPassword)) {
            			throw new RuntimeException("Password Error");
            		}
            	}
        	}
        }
        
        statisticService.addRecordOrAddViewNumber(diaryId, diary.getUserId());
        
        mav.addObject("diary", diary);
        mav.addObject("owner", owner);
        
        mav.addObject("diaryTypeEnums", DiaryTypeEnum.values());
		mav.addObject("diaryTypeMap", DiaryTypeEnum.getMap());
		mav.addObject("privateTypeEnums", PrivateTypeEnum.values());
		mav.addObject("privateTypeMap", PrivateTypeEnum.getMap());
		mav.addObject("weatherMap", WeatherEnum.getMap());
		mav.addObject("weatherEnums", WeatherEnum.values());

        return mav;
    }

    @RequestMapping("/checkPassword/{diaryId}")
    @ResponseBody
    public String checkPassword(HttpServletRequest request, @PathVariable("diaryId") Long diaryId) {

        JSONObject jsonObject = new JSONObject();

        Diary diary = diaryService.findDiary(diaryId);

        String privateType = diary.getPrivateType();

        if (PrivateTypeEnum.PUBLIC.getCode().equals(privateType)) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "Need not password");
            return jsonObject.toString();
        } else if (PrivateTypeEnum.PRIVATE.getCode().equals(privateType)) {
            jsonObject.put("code", 1);
            jsonObject.put("message", "Private Diary");
            return jsonObject.toString();
        } else if (PrivateTypeEnum.PROTECTED.getCode().equals(privateType)) {
            String password = request.getParameter("password");
            String realPassword = diaryService.findPassword(diaryId);
            if (realPassword.equals(password)) {
                jsonObject.put("code", 0);
                jsonObject.put("message", "OK");
                return jsonObject.toString();
            } else {
                jsonObject.put("code", 2);
                jsonObject.put("message", "Password Error");
                return jsonObject.toString();
            }
        }

        return null;
    }

    @RequestMapping("/getTop5/{userId}")
    @ResponseBody
    public String getTop5(@PathVariable("userId") Long userId) {
    	
    	JSONObject jsonObject = new JSONObject();
    	
    	try {
    		List<Diary> hotDiaries = diaryService.findTopN(userId, Constants.TOP_NUMBER);
        	
        	jsonObject.put("data", hotDiaries);
        	
        	int size = hotDiaries == null ? 0 : hotDiaries.size();
        	jsonObject.put("size", size);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("size", 0);
		}
    	
        return jsonObject.toString();
    }
    
    @RequestMapping("/getSign/{userId}")
    @ResponseBody
    public String getSign(@PathVariable("userId") Long userId) {
    	String sign = userService.findSign(userId);
    	return sign;
    }


}
