package com.hejinwei.diary.service;


import java.util.List;

import com.hejinwei.diary.dao.mybatis.model.User;

public interface UserService {
	
	User findByName(String name);
	
	User findById(Long id);
	
	void add(User user);
	
	void edit(User user);
	
	void remove(Long id);

	String findSign(Long userId);
	
	void editPassword(Long userId, String password);
	
	/**
	 * 模糊查询用户
	 * @param nickname
	 * @return
	 */
	List<User> searchUsersByNickname(String nickname);
	
	List<User> searchPageUsersByNickname(String nickname, Integer pageNum, Integer pageSize);
	
	int searchCountByNickname(String nickname);
}
