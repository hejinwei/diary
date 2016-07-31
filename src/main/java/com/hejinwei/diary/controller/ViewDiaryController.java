package com.hejinwei.diary.controller;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.dao.mybatis.model.Diary;
import com.hejinwei.diary.dao.mybatis.model.DiaryPassword;
import com.hejinwei.diary.enums.PrivateTypeEnum;
import com.hejinwei.diary.service.DiaryService;
import com.hejinwei.diary.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jinweihe on 16/7/30.
 */
@Controller
public class ViewDiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private StatisticService statisticService;

    @RequestMapping("/viewDiary/{diaryId}")
    public ModelAndView viewDiary(@PathVariable("diaryId") Long diaryId) {
        ModelAndView mav = new ModelAndView("template/diary/view");

        Long userId = diaryService.findUserIdByDiaryId(diaryId);

        statisticService.addRecordOrAddViewNumber(diaryId, userId);

        Diary diary = diaryService.findDiary(diaryId);
        mav.addObject("diary", diary);

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

        return null;
    }


}
