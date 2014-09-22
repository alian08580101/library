package com.jd.a6i.view;

import android.text.method.ReplacementTransformationMethod;

public class CharLowerToCapital extends ReplacementTransformationMethod {
	@Override
	protected char[] getOriginal() {
		char[] lowerChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
		return lowerChar;
	}

	@Override
	protected char[] getReplacement() {
		char[] capitalChar = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
		return capitalChar;
	}
}
