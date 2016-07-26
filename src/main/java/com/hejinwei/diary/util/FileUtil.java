package com.hejinwei.diary.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	
	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}
	
	public static String getUeditorConfigJsonPath() {
		String projectPath = getProjectPath();
		return projectPath + File.separator + "src" + File.separator + "main"
				+ File.separator + "webapp" + File.separator + "static"
				+ File.separator + "ueditor" + File.separator + "jsp" 
				+ File.separator + "config.json";
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
		String configPath = getUeditorConfigJsonPath();
		String content = readFile(configPath);
		
		content = content.replaceAll("/\\*.*\\*/", "");
		System.out.println(content);
	}

}
