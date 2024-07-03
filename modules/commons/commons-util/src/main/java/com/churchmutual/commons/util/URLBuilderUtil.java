package com.churchmutual.commons.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

/**
 * @author Shane Merriss
 */
public class URLBuilderUtil {
	public static String addParameter(String baseURL, String key, String value) {
		if (Validator.isNull(baseURL)) {
			return StringPool.BLANK;
		}

		StringBundler url = new StringBundler();

		if (Validator.isNull(key)) {
			return baseURL;
		}

		url.append(baseURL);
		url.append(StringPool.AMPERSAND);
		url.append(key);
		url.append(StringPool.EQUAL);

		if (Validator.isNotNull(value)) {
			url.append(value);
		}

		return url.toString();
	}

	public static String buildURL(String baseURL, Map<String, String> parameters) {
		if (Validator.isNull(baseURL)) {
			return StringPool.BLANK;
		}

		if (Objects.isNull(parameters) || parameters.isEmpty()) {
			return baseURL;
		}

		StringBundler url = new StringBundler();

		url.append(baseURL);

		String urlSeparator = StringPool.QUESTION;

		for (String key : parameters.keySet()) {
			url.append(urlSeparator);
			url.append(key);
			url.append(StringPool.EQUAL);

			String value = parameters.get(key);

			if (Validator.isNotNull(value)) {
				url.append(value);
			}

			urlSeparator = StringPool.AMPERSAND;
		}

		return url.toString();
	}

	public static String formatPhoneNumberURL(String phoneNumber) {
		if (Validator.isBlank(phoneNumber)) {
			return phoneNumber;
		}

		String formattedPhoneNumber = phoneNumber.replace(_EXT_KEY, StringPool.COMMA);

		return _TEL_PREFIX + formattedPhoneNumber;
	}

	public static String trimPortNumber(String host) {
		String[] splitStrings = StringUtil.split(host, StringPool.COLON);

		if (Validator.isNotNull(splitStrings) && splitStrings.length > 2) {
			host = splitStrings[0] + StringPool.COLON + splitStrings[1];
		}

		return host;
	}

	private static final String _EXT_KEY = " Ext. ";

	private static final String _TEL_PREFIX = "tel:";

}