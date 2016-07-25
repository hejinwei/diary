package com.hejinwei.diary.service;

import java.util.List;

import com.hejinwei.diary.dao.mybatis.model.Diary;

public interface DiaryService {

	int findDiaryCount(Long userId, Byte type);
	
	List<Diary> findDiaryPage(Long userId, Byte type, Integer pageNum, Integer pageSize);
	
	int findDiaryCountForProfile(Long userId, Byte type);
	
	List<Diary> findDiaryPageForProfile(Long userId, Byte type, Integer pageNum, Integer pageSize);
	
}
