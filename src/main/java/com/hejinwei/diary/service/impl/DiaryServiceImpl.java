package com.hejinwei.diary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hejinwei.diary.dao.mybatis.mapper.DiaryMapper;
import com.hejinwei.diary.dao.mybatis.mapper.DiaryPasswordMapper;
import com.hejinwei.diary.dao.mybatis.model.Diary;
import com.hejinwei.diary.dao.mybatis.model.DiaryPassword;
import com.hejinwei.diary.service.DiaryService;
import com.hejinwei.diary.util.Constants;

@Service
public class DiaryServiceImpl implements DiaryService {
	
	@Autowired
	private DiaryMapper diaryMapper;
	
	@Autowired
	private DiaryPasswordMapper diaryPasswordMapper;

	@Override
	public List<Diary> findDiaryPage(Long userId, Byte type, Integer pageNum, Integer pageSize) {
		int startIndex = (pageNum - 1) * pageSize;
		return diaryMapper.selectDiaryPage(userId, type, startIndex, pageSize);
	}

	@Override
	public List<Diary> findDiaryPageForProfile(Long userId, Byte type, Integer pageNum, Integer pageSize) {
		int startIndex = (pageNum - 1) * pageSize;
		return diaryMapper.selectDiaryPageForProfile(userId, type, startIndex, pageSize);
	}

	@Override
	public int findDiaryCount(Long userId, Byte type) {
		return diaryMapper.selectDiaryCount(userId, type);
	}

	@Override
	public int findDiaryCountForProfile(Long userId, Byte type) {
		return diaryMapper.selectDiaryCountForProfile(userId, type);
	}

	@Override
	public int addDiary(Diary diary) {
		return diaryMapper.insertSelective(diary);
	}

	@Override
	public void deleteDiary(Long diaryId) {
		diaryMapper.updateDeleteStatus(diaryId, Constants.DELETE_STATUS);
	}

	@Override
	public Diary findDiary(Long diaryId) {
		return diaryMapper.selectByPrimaryKey(diaryId);
	}

	@Override
	public void editDiary(Diary diary) {
		diaryMapper.updateByPrimaryKeySelective(diary);
	}

	@Override
	public void addOrEditPassword(DiaryPassword diaryPassword) {
		diaryPasswordMapper.insertOrUpdate(diaryPassword);
	}

	@Override
	public void editPasswordStatus(Long diaryId, Byte status) {
		diaryPasswordMapper.updatePasswordStatus(diaryId, status);
	}

	@Override
	public Long findUserIdByDiaryId(Long diaryId) {
		return diaryMapper.selectUserIdByDiaryId(diaryId);
	}

	@Override
	public String findPassword(Long diaryId) {
		return diaryPasswordMapper.selectPassword(diaryId);
	}

	@Override
	public List<Diary> findTop5(Long userId) {
		return null;
	}


}
