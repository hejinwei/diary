package com.hejinwei.diary.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hejinwei.diary.dao.mybatis.mapper.TestMapper;
import com.hejinwei.diary.dao.mybatis.model.Test;

@Controller
public class TestController {
	
	@Autowired
	private TestMapper testMapper;
	
	@RequestMapping("/test/hello")
	@ResponseBody
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/test/beetle")
	public ModelAndView beetle() {
		ModelAndView mav = new ModelAndView("template/beetle");
		mav.addObject("content", "helloworld");
		return mav;
	}
	
	@RequestMapping("/test/insert")
	@ResponseBody
	public String insert() {
		Test test = new Test();
		
		Random random = new Random();
		test.setAge(random.nextInt(100));
		test.setHeight(random.nextInt(100) + 100);
		test.setName("test" + random.nextInt(10000));
		testMapper.insertSelective(test);
		
		return test.getName();
	}

}
