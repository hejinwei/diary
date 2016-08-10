package com.hejinwei.diary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hejinwei.diary.dao.mybatis.mapper.CommentMapper;
import com.hejinwei.diary.dao.mybatis.model.Comment;
import com.hejinwei.diary.service.CommentService;
import com.hejinwei.diary.util.Constants;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public void addComment(Comment comment) {
		commentMapper.insertSelective(comment);
	}

	@Override
	public List<Comment> findByDiaryId(Long diaryId, int pageNum, int pageSize) {
		int startIndex = (pageNum - 1) * pageSize;
		return commentMapper.selectPageByDiaryId(diaryId, startIndex, pageSize);
	}

	@Override
	public void remove(Long commentId) {
		commentMapper.updateStatus(commentId, Constants.DELETE_STATUS);
	}

	@Override
	public int findCountByDiaryId(Long diaryId) {
		return commentMapper.selectCountByDiaryId(diaryId);
	}

}
