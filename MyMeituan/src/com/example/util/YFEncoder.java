package com.example.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class YFEncoder {

	//防止中文乱码设置编码
	public static String EncodeToUTF(String str) {
		
		String s = "";
		try {
			s = URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
		
	}
}
