package com.hejinwei.diary.controller;

import java.util.Date;
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
import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.service.CommentService;
import com.hejinwei.diary.service.UserService;

@Controller
public class CommentController extends BaseController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/comment/add", method = RequestMethod.POST)
	@ResponseBody
	public String addComment(HttpServletRequest request) {

		String userIdStr = request.getParameter("userId");
		String content = request.getParameter("content");
		String diaryIdStr = request.getParameter("diaryId");

		if (StringUtils.isEmpty(userIdStr) || StringUtils.isEmpty(content) || StringUtils.isEmpty(diaryIdStr)) {
			throw new RuntimeException("参数为空");
		}
		
		User user = userService.findById(Long.parseLong(userIdStr));
		Comment comment = new Comment();
		comment.setAddTime(new Date().getTime());
		comment.setContent(content);
		comment.setDiaryId(Long.parseLong(diaryIdStr));
		comment.setNickname(user.getNickname());
		comment.setUserId(user.getUserId());
		comment.setHeadImage(user.getHeadImage());

		try {
			if (!isLogined(request)) {
				return operateResult(1, "未登录");
			}

			commentService.addComment(comment);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 0);
			jsonObject.put("message", "评论成功");
			jsonObject.put("comment", comment);

			return jsonObject.toString();
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
			List<Comment> commentList = commentService.findByDiaryId(diaryId, pageNum, 500);
			jsonObject.put("commentList", commentList);
		}

		return jsonObject.toString();
	}

}
