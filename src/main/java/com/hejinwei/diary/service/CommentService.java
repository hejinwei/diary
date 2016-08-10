package com.hejinwei.diary.service;

import java.util.List;

import com.hejinwei.diary.dao.mybatis.model.Comment;

public interface CommentService {
	
	void addComment(Comment comment);
	
	List<Comment> findByDiaryId(Long diaryId, int pageNum, int pageSize);
	
	void remove(Long commentId);
	
	int findCountByDiaryId(Long diaryId);

}
