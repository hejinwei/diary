package com.hejinwei.diary.service.impl;

import com.hejinwei.diary.dao.mybatis.mapper.StatisticMapper;
import com.hejinwei.diary.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinweihe on 16/7/30.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public void addRecordOrAddViewNumber(Long diaryId, Long userId) {
        statisticMapper.insertOrAddViewNumber(diaryId, userId);
    }
}
