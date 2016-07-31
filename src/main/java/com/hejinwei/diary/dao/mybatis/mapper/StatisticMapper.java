package com.hejinwei.diary.dao.mybatis.mapper;

import com.hejinwei.diary.dao.mybatis.model.Statistic;
import org.apache.ibatis.annotations.Param;

public interface StatisticMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistics
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    int deleteByPrimaryKey(Long diaryId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistics
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    int insert(Statistic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistics
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    int insertSelective(Statistic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistics
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    Statistic selectByPrimaryKey(Long diaryId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistics
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    int updateByPrimaryKeySelective(Statistic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table statistics
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    int updateByPrimaryKey(Statistic record);

    void insertOrAddViewNumber(@Param("diaryId") Long diaryId, @Param("userId") Long userId);
}
