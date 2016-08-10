package com.hejinwei.diary.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.dao.mybatis.model.Comment;
import com.hejinwei.diary.service.CommentService;
import com.hejinwei.diary.util.Constants;

@Controller
public class CommentController extends BaseController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "/comment/add", method = RequestMethod.POST)
	@ResponseBody
	public String addComment(HttpServletRequest request, Comment comment) {

		try {
			if (!isLogined(request)) {
				return operateResult(1, "未登录");
			}

			commentService.addComment(comment);

			return operateResult(0, "评论成功");
		} catch (Exception e) {
			e.printStackTrace();
			return operateResult(99, "系统异常");
		}

	}

	@RequestMapping("/comment/findList")
	@ResponseBody
	public String findList(@RequestParam(value = "diaryId") Long diaryId,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

		JSONObject jsonObject = new JSONObject();
		
		int count = commentService.findCountByDiaryId(diaryId);
		jsonObject.put("count", count);
		
		if (count == 0) {
			jsonObject.put("commentList", null);
		} else {
			List<Comment> commentList = commentService.findByDiaryId(diaryId, pageNum, Constants.DEFAULT_PAGE_SIZE);
			jsonObject.put("commentList", commentList);
		}

		return jsonObject.toString();
	}

}
