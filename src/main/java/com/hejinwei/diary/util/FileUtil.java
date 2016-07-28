package com.hejinwei.diary.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	
	public static String getFilePath(HttpServletRequest request, String path) {
		return request.getSession().getServletContext().getRealPath(path);
	}
	
	public static String getUeditorConfigJsonPath(HttpServletRequest request) {
		return getFilePath(request, "/static/ueditor/jsp/config.json");
	}
	
	/**
	 * 如"aaa.jpg"返回".jpg"
	 * @param filename
	 * @return
	 */
	public static String getFilenameSuffix(String filename) {
		return filename.substring(filename.lastIndexOf(".")).toLowerCase();
	}
	
	public static String readFile(String path) {
		File file = new File(path);
		
		try {
			String content = FileUtils.readFileToString(file);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		String filename = "aaa.jpg";
		System.out.println(getFilenameSuffix(filename));
	}

}
