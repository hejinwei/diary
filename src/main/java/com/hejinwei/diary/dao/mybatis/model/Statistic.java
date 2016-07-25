package com.hejinwei.diary.dao.mybatis.model;

public class Statistic {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column statistics.diary_id
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    private Long diaryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column statistics.user_id
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column statistics.view_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    private Integer viewNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column statistics.comment_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    private Integer commentNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column statistics.like_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    private Integer likeNumber;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column statistics.diary_id
     *
     * @return the value of statistics.diary_id
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public Long getDiaryId() {
        return diaryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column statistics.diary_id
     *
     * @param diaryId the value for statistics.diary_id
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public void setDiaryId(Long diaryId) {
        this.diaryId = diaryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column statistics.user_id
     *
     * @return the value of statistics.user_id
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column statistics.user_id
     *
     * @param userId the value for statistics.user_id
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column statistics.view_number
     *
     * @return the value of statistics.view_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public Integer getViewNumber() {
        return viewNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column statistics.view_number
     *
     * @param viewNumber the value for statistics.view_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public void setViewNumber(Integer viewNumber) {
        this.viewNumber = viewNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column statistics.comment_number
     *
     * @return the value of statistics.comment_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public Integer getCommentNumber() {
        return commentNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column statistics.comment_number
     *
     * @param commentNumber the value for statistics.comment_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column statistics.like_number
     *
     * @return the value of statistics.like_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public Integer getLikeNumber() {
        return likeNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column statistics.like_number
     *
     * @param likeNumber the value for statistics.like_number
     *
     * @mbggenerated Mon Jul 25 09:57:21 CST 2016
     */
    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }
}