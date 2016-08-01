package com.hejinwei.diary.service;


import com.hejinwei.diary.dao.mybatis.model.User;

public interface UserService {
	
	User findByName(String name);
	
	User findById(Long id);
	
	void add(User user);
	
	void edit(User user);
	
	void remove(Long id);

	String findSign(Long userId);
}
