package com.lawfirm.utils;

public class TextUtils {


	public static String getData(String data) {
		return data.substring(0, data.indexOf("?"));
	}
}
