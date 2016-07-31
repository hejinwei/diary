package com.hejinwei.diary.service;

import java.util.List;

import com.hejinwei.diary.dao.mybatis.model.Diary;
import com.hejinwei.diary.dao.mybatis.model.DiaryPassword;

public interface DiaryService {

	int findDiaryCount(Long userId, Byte type);
	
	List<Diary> findDiaryPage(Long userId, Byte type, Integer pageNum, Integer pageSize);
	
	Diary findDiary(Long diaryId);
	
	int findDiaryCountForProfile(Long userId, Byte type);
	
	List<Diary> findDiaryPageForProfile(Long userId, Byte type, Integer pageNum, Integer pageSize);
	
	int addDiary(Diary diary);
	
	void deleteDiary(Long diaryId);
	
	void editDiary(Diary diary);
	
	void addOrEditPassword(DiaryPassword diaryPassword);

	void editPasswordStatus(Long diaryId, Byte status);

	Long findUserIdByDiaryId(Long diaryId);

	String findPassword(Long diaryId);

	List<Diary> findTop5(Long userId);
	
}
