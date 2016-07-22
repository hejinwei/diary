package com.hejinwei.diary.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;


@Controller
public class UploadController {
	
	
	private final static String QINIU_ACCESS_KEY = "78b7U280BHUAOFCNLpDUeA2SUShOhCpfIYhn2NYa";
	private final static String QINIU_SECRET_KEY = "1OI2waYFu2WvArkvvqCgESil24P086rMsu1GVqeP";
	private final static String QINIU_BUCKET_NAME = "test";
	
	private static Auth auth = Auth.create(QINIU_ACCESS_KEY, QINIU_SECRET_KEY);
	private static UploadManager uploadManager = new UploadManager();
	private static String qiniuToken = auth.uploadToken(QINIU_BUCKET_NAME);
	
	private static final String QINIU_PIC_URL_PREFIX = "http://o9vm2dp5m.bkt.clouddn.com/";
	
	@RequestMapping("/upload/image/1")
	public String uploadImageQiniu(@RequestParam("imageType") int imageType,
            @RequestParam("file") MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) {
		
		System.out.println("上传图片：" + file.getOriginalFilename());
		
		String fileName = UUID.randomUUID() + ".jpg";
		
		try {
			Response qiniuResponse = uploadManager.put(file.getBytes(), fileName, qiniuToken);
			System.out.println(qiniuResponse.bodyString());
			
			// TODO 返回url
			response.getWriter().print(QINIU_PIC_URL_PREFIX + fileName);
		} catch (QiniuException e) {
			System.out.println(e.response.toString());
			try {
				System.out.println(e.response.bodyString());
			} catch (QiniuException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
