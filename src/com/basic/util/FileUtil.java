package com.basic.util;

public class FileUtil {

	public static String getBasicPath(){
		String filePath = FileUtil.class.getResource("").toString();
		int end = filePath.indexOf("com");
		filePath = filePath.substring(5, end);
		return filePath;
	}
}
