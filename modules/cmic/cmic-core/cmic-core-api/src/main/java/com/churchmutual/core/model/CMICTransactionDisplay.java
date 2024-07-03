package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICTransactionDTO;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CMICTransactionDisplay {

	public CMICTransactionDisplay(CMICTransactionDTO cmicTransactionDTO) {
		_eventType = cmicTransactionDTO.getEventType();
		_productType = cmicTransactionDTO.getProduct();
		_sequenceNumber = cmicTransactionDTO.getSequenceNumber();
		_transactionCommitTimestamp = cmicTransactionDTO.getTransactionCommitTimestamp();
		_transactionEffectiveDate = cmicTransactionDTO.getTransactionEffectiveDate();
	}

	public String getEventType() {
		return _eventType;
	}

	public String getProductType() {
		return _productType;
	}

	public int getSequenceNumber() {
		return _sequenceNumber;
	}

	public String getTransactionCommitTimestamp() {
		return _formatDate(_transactionCommitTimestamp);
	}

	public String getTransactionEffectiveDate() {
		return _formatDate(_transactionEffectiveDate);
	}

	public void setEventType(String eventType) {
		_eventType = eventType;
	}

	private String _formatDate(String date) {
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

	private static final String _FORMAT_MM_DD_YYYY = "MM/dd/yyyy";

	private static final String _FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	private static final Log _log = LogFactoryUtil.getLog(CMICPolicyDisplay.class);

	private String _eventType;
	private String _productType;
	private int _sequenceNumber;
	private String _transactionCommitTimestamp;
	private String _transactionEffectiveDate;

}
