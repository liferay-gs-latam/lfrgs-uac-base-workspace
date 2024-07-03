package com.churchmutual.core.model;

import com.churchmutual.core.constants.CommissionDocumentType;
import com.churchmutual.rest.model.CMICCommissionDocumentDTO;
import com.churchmutual.rest.model.CMICFileDTO;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.SimpleDateFormat;

import java.util.Date;

public class CMICCommissionDocumentDisplay {

	public CMICCommissionDocumentDisplay(CMICCommissionDocumentDTO cmicCommissionDocumentDTO) {
		String statementDate = _getFormattedStatementDate(cmicCommissionDocumentDTO.getStatementDate());

		_documentId = cmicCommissionDocumentDTO.getId();

		String documentType = cmicCommissionDocumentDTO.getDocumentType();

		CommissionDocumentType commissionDocumentType = CommissionDocumentType.fromCmicName(
			cmicCommissionDocumentDTO.getDocumentType());

		if (commissionDocumentType != null) {
			documentType = commissionDocumentType.getAbbreviation();
		}

		String statementId = cmicCommissionDocumentDTO.getStatementId();

		_name = String.format(
			"%s-%s%s-%s-%s", statementDate, cmicCommissionDocumentDTO.getDivisionNumber(),
			cmicCommissionDocumentDTO.getAgentNumber(), statementId, documentType);

		_agentNumber = cmicCommissionDocumentDTO.getAgentNumber();
		_divisionNumber = cmicCommissionDocumentDTO.getDivisionNumber();
	}

	public CMICCommissionDocumentDisplay(CMICFileDTO cmicFileDTO) {
		_bytes = cmicFileDTO.getBytes();
		_documentId = cmicFileDTO.getId();
		_mimeType = cmicFileDTO.getMimeType();

		String name = cmicFileDTO.getName();

		if (Validator.isBlank(name)) {
			String url = cmicFileDTO.getUrl();

			int fileNameStart = url.lastIndexOf(CharPool.FORWARD_SLASH) + 1;

			int fileNameEnd = url.indexOf(CharPool.QUESTION);

			name = url.substring(fileNameStart, fileNameEnd);
		}

		_name = name;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getBytes() {
		return _bytes;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public String getDocumentId() {
		return _documentId;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public String getName() {
		return _name;
	}

	private String _getFormattedStatementDate(String date) {
		SimpleDateFormat format1 = new SimpleDateFormat(_FORMAT_YYYY_MM_DD);
		SimpleDateFormat format2 = new SimpleDateFormat(_FORMAT_MM_DD_YYYY);

		try {
			Date statementDate = format1.parse(date);

			return format2.format(statementDate);
		}
		catch (Exception e) {
			_log.warn(e);

			return date;
		}
	}

	private static final String _FORMAT_MM_DD_YYYY = "MM.dd.yyyy";

	private static final String _FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	private static final Log _log = LogFactoryUtil.getLog(CMICCommissionDocumentDisplay.class);

	private String _agentNumber;
	private String _bytes;
	private String _divisionNumber;
	private String _documentId;
	private String _mimeType;
	private String _name;

}