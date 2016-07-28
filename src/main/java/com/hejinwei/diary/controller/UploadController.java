package com.hejinwei.diary.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.hejinwei.diary.util.FileUtil;
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

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping("/upload/image/1")
	public String uploadImageQiniu(@RequestParam("imageType") int imageType,
            @RequestParam("file") MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) {
		
		System.out.println("上传图片：" + file.getOriginalFilename());
		
		String filename = imageType + "/" + sdf.format(new Date()) + "/" + UUID.randomUUID() 
			+ FileUtil.getFilenameSuffix(file.getOriginalFilename());
		
		try {
			Response qiniuResponse = uploadManager.put(file.getBytes(), filename, qiniuToken);
			System.out.println(qiniuResponse.bodyString());
			
			// 返回url
			response.getWriter().print(QINIU_PIC_URL_PREFIX + filename);
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
	
	@RequestMapping("/ueditor/dispatch")
	public void ueditorDispatch(HttpServletRequest request, HttpServletResponse response,
            //@RequestParam(value = "upfile", required=false) MultipartFile upfile,
            @RequestParam(value = "action") String action) {
    	
    	response.setHeader("Content-Type" , "text/html");
        
    	try {
    		switch (action) {
    		case "config":
    			String configPath = FileUtil.getUeditorConfigJsonPath(request);
    			String content = FileUtil.readFile(configPath);
    			
    			content = content.replaceAll("/\\*.*\\*/", "");
    			response.getWriter().print(content);
    			break;

    		default:
    			MultipartFile file = ((MultipartHttpServletRequest)request).getFile("upfile");
    			String filename = "200/" + sdf.format(new Date()) + "/" + UUID.randomUUID() 
    				+ FileUtil.getFilenameSuffix(file.getOriginalFilename());
    			Response qiniuResponse = uploadManager.put(file.getBytes(), filename, qiniuToken);
    			System.out.println(qiniuResponse.bodyString());
    			
    			String url = QINIU_PIC_URL_PREFIX + filename;
    			JSONObject jsonObject = constructUeditorReturnJson(url, filename, file.getSize());
    			response.getWriter().print(jsonObject.toString());
    			break;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    }
	
	private JSONObject constructUeditorReturnJson(String url, String filename, long fileSize) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", "SUCCESS");
		jsonObject.put("url", url);
		jsonObject.put("title", filename);
		jsonObject.put("original", filename);
		jsonObject.put("type", FileUtil.getFilenameSuffix(filename));
		jsonObject.put("size", fileSize);
		return jsonObject;
	}

}
