package com.churchmutual.rest.service;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kayleen Lim
 */
public class MockResponseReaderUtil {

	public static String readFile(String fileName) {
		try (InputStream stream = MockResponseReaderUtil.class.getResourceAsStream(_MOCK_RESPONSE_DIR + fileName)) {
			return StringUtil.read(stream);
		}
		catch (IOException ioe) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to read from file: " + fileName, ioe);
			}

			return StringPool.BLANK;
		}
	}

	private static final String _MOCK_RESPONSE_DIR = "/mock-response/";

	private static final Log _log = LogFactoryUtil.getLog(MockResponseReaderUtil.class);

}