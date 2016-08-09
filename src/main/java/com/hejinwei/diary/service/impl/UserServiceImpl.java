package com.hejinwei.diary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hejinwei.diary.dao.mybatis.mapper.UserMapper;
import com.hejinwei.diary.dao.mybatis.model.User;
import com.hejinwei.diary.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findByName(String name) {
		return userMapper.selectByName(name);
	}

	@Override
	public User findById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void add(User user) {
		userMapper.insertSelective(user);
	}

	@Override
	public void edit(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void remove(Long id) {
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public String findSign(Long userId) {
		return userMapper.selectSign(userId);
	}

	@Override
	public void editPassword(Long userId, String password) {
		userMapper.updatePassword(userId, password);
	}

}
