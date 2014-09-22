package com.jd.a6i.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckExpressionUtils {
	public static boolean isLegalSealCarNo(String input,String regular){
		if(isLegalInput(input)){
			return isLegalExpression(input, regular);
		}
		return false;
	}
	
	public static boolean isLegalCarNo(String input,String regular){
		if(isLegalSealCarNo(input, ConstantUtils.SEAL_CAR_NO_REGULAR_EXPRESSION)){
			return false;
		}else{
			if (isLegalInput(input)) {
				return isLegalExpression(input, regular);
			}
		}
		return false;
	}
	
	public static boolean isLegalTurnoverBoxNo(String input,String regular){
		if(!isLegalInput(input)){
			return true;
		}else{
			return isLegalExpression(input, regular);
		}
	}
	
	private static boolean isLegalInput(String input){
		if(input!=null&&!"".equals(input)){
			return true;
		}
		return false;
	}
	
	public static boolean isLegalExpression(String input,String regular){
		Pattern pattern = Pattern.compile(regular);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
}
