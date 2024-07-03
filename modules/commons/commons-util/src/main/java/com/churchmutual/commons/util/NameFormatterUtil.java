package com.churchmutual.commons.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameFormatterUtil {

	public static String format(String s) {
		if (Validator.isNull(s)) {
			return s;
		}

		String pattern = "(\\S+)";

		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(s);

		StringBuilder result = new StringBuilder();

		while(m.find()){
			String match = m.group().toLowerCase();

			result.append(StringUtil.upperCaseFirstLetter(match));
			result.append(CharPool.SPACE);
		}

		return result.toString();
	}

}