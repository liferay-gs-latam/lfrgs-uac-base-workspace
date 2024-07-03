package com.churchmutual.core.model;

import com.liferay.portal.kernel.util.Validator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CMICPDFDocumentDisplay {

	public CMICPDFDocumentDisplay(String lossRunsReport) {
		if (Validator.isBlank(lossRunsReport)) {
			return;
		}

		int contentDispositionEnd = lossRunsReport.indexOf(':');

		String data = lossRunsReport.substring(contentDispositionEnd + 1);
		_fileName = lossRunsReport.substring(0, contentDispositionEnd).split("filename=")[1];

		_bytes = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.ISO_8859_1));
	}

	public String getBytes() {
		return _bytes;
	}

	public String getFileName() {
		return _fileName;
	}

	private String _bytes;
	private String _fileName;

}
