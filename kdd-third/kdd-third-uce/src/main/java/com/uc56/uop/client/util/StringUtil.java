package com.uc56.uop.client.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �ַ���������
 * @author liuhaibo
 * @since 2013-12-18
 */
public class StringUtil {

	/**
	 * ����ַ����Ƿ�Ϊnull���ֵ
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(null == str || "".equals(str) || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * ����ַ����Ƿ�Ϊ�����ʽ
	 * @param email
	 * @author liuhaibo
	 * @return
	 */
	public static boolean isEmail(String email){
		if(isEmpty(email))return false;
		boolean bool = true;
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()){
			bool = false;
		}
		return bool;
	}
	
	/**
	 * ����ַ����Ƿ�Ϊ��ֵ����
	 * @param number
	 * @author liuhaibo 
	 * @return
	 */
	public static boolean isNumeric(String number){
		if(number.matches("//d*")){
			return true;
		}else{
			return false;
		}
	}
	
}
