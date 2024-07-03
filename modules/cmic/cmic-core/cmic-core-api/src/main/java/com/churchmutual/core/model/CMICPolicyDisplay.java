package com.churchmutual.core.model;

import com.churchmutual.commons.util.NameFormatterUtil;
import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICPolicyDTO;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;

public class CMICPolicyDisplay {

	public CMICPolicyDisplay(CMICPolicyDTO cmicPolicyDTO, CMICAccountDTO cmicAccountDTO, String productName) {
		_accountNumber = cmicPolicyDTO.getAccountNumber();
		_companyNumber = cmicPolicyDTO.getCompanyNumber();
		_currentSequenceNumber = cmicPolicyDTO.getCurrentSequenceNumber();
		_effectiveDate = _getFormattedPolicyDate(cmicPolicyDTO.getPolicyEffectiveDate());
		_expirationDate = _getFormattedPolicyDate(cmicPolicyDTO.getPolicyExpirationDate());
		_hasViewPolicyDocument = false;
		_isExpired = _isExpired(cmicPolicyDTO.getPolicyExpirationDate());
		_numTransactions = cmicPolicyDTO.getCurrentSequenceNumber();
		_policyNumber = cmicPolicyDTO.getPolicyNumber();
		_policyType = cmicPolicyDTO.getProduct();
		_productName = productName;

		if (cmicAccountDTO != null) {
			_accountName = cmicAccountDTO.getAccountName();
		}

		if (Validator.isBlank(_accountName)) {
			_accountName = LanguageUtil.get(LocaleUtil.getDefault(), "no-account-name");
		}

		SimpleDateFormat format = new SimpleDateFormat(_FORMAT_YYYY_MM_DD);

		try {
			_effectiveDateSortable = format.parse(cmicPolicyDTO.getPolicyEffectiveDate());
		}
		catch (Exception e) {
			_log.warn(e);
		}

		BigDecimal premium = cmicPolicyDTO.getTotalBilledPremium();

		if (Validator.isNull(premium)) {
			_totalBilledPremium = 0.0;
		}
		else {
			_totalBilledPremium = premium.doubleValue();
		}
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public int getCurrentSequenceNumber() {
		return _currentSequenceNumber;
	}

	public String getEffectiveDate() {
		return _effectiveDate;
	}

	public Date getEffectiveDateSortable() {
		return _effectiveDateSortable;
	}

	public String getExpirationDate() {
		return _expirationDate;
	}

	public boolean getHasViewPolicyDocument() {
		return _hasViewPolicyDocument;
	}

	public boolean getIsExpired() {
		return _isExpired;
	}

	public int getNumTransactions() {
		return _numTransactions;
	}

	public String getPolicyNumber() {
		return _policyNumber;
	}

	public String getPolicyType() {
		return _policyType;
	}

	public String getProductName() {
		return NameFormatterUtil.format(_productName);
	}

	public double getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public void setHasViewPolicyDocument(boolean hasViewPolicyDocument) {
		_hasViewPolicyDocument = hasViewPolicyDocument;
	}

	private String _getFormattedPolicyDate(String date) {
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

	private boolean _isExpired(String expirationDate) {
		SimpleDateFormat format = new SimpleDateFormat(_FORMAT_YYYY_MM_DD);

		try {
			Date statementDate = format.parse(expirationDate);

			return statementDate.before(new Date());
		}
		catch (Exception e) {
			_log.warn(e);

			return false;
		}
	}

	private static final String _FORMAT_MM_DD_YYYY = "MM/dd/yy";

	private static final String _FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	private static final Log _log = LogFactoryUtil.getLog(CMICPolicyDisplay.class);

	private String _accountName;
	private String _accountNumber;
	private String _companyNumber;
	private int _currentSequenceNumber;
	private String _effectiveDate;
	private Date _effectiveDateSortable;
	private String _expirationDate;
	private boolean _hasViewPolicyDocument;
	private boolean _isExpired;
	private int _numTransactions;
	private String _policyNumber;
	private String _policyType;
	private String _productName;
	private double _totalBilledPremium;

}