package com.churchmutual.rest.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Date;

/**
 * @author Kayleen Lim
 */
public class CMICPolicyDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public Date getCancelDate() {
		return _cancelDate;
	}

	public String getCompanyNumber() {
		if (Validator.isNull(_companyNumber)) {
			_log.error(
				"CMICPolicyDTO with policy number=" + _policyNumber + " and accountNumber=" + _accountNumber +
					" has no companyNumber, using default companyNumber");

			return DEFAULT_COMPANY_NUMBER;
		}

		return _companyNumber;
	}

	public int getCurrentSequenceNumber() {
		return _currentSequenceNumber;
	}

	public boolean getHasHiredAndNonOwnedAutoLimits() {
		return _hasHiredAndNonOwnedAutoLimits;
	}

	public boolean getHasSexualMisconductLimits() {
		return _hasSexualMisconductLimits;
	}

	public String getPolicyEffectiveDate() {
		return _policyEffectiveDate;
	}

	public String getPolicyExpirationDate() {
		return _policyExpirationDate;
	}

	public String getPolicyNumber() {
		return _policyNumber;
	}

	public String getPolicyType() {
		return _policyType;
	}

	public String getProduct() {
		return _product;
	}

	public BigDecimal getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public boolean isExpiredPolicy() {
		return _expiredPolicy;
	}

	public boolean isFuturePolicy() {
		return _futurePolicy;
	}

	public boolean isInForcePolicy() {
		return _inForcePolicy;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		_companyNumber = companyNumber;
	}

	public void setCancelDate(Date cancelDate) {
		_cancelDate = cancelDate;
	}

	public void setCurrentSequenceNumber(int currentSequenceNumber) {
		_currentSequenceNumber = currentSequenceNumber;
	}

	public void setExpiredPolicy(boolean expiredPolicy) {
		_expiredPolicy = expiredPolicy;
	}

	public void setFuturePolicy(boolean futurePolicy) {
		_futurePolicy = futurePolicy;
	}

	public void setHasHiredAndNonOwnedAutoLimits(boolean hasHiredAndNonOwnedAutoLimits) {
		_hasHiredAndNonOwnedAutoLimits = hasHiredAndNonOwnedAutoLimits;
	}

	public void setHasSexualMisconductLimits(boolean hasSexualMisconductLimits) {
		_hasSexualMisconductLimits = hasSexualMisconductLimits;
	}

	public void setInForcePolicy(boolean inForcePolicy) {
		_inForcePolicy = inForcePolicy;
	}

	public void setPolicyEffectiveDate(String policyEffectiveDate) {
		_policyEffectiveDate = policyEffectiveDate;
	}

	public void setPolicyExpirationDate(String policyExpirationDate) {
		_policyExpirationDate = policyExpirationDate;
	}

	public void setPolicyNumber(String policyNumber) {
		_policyNumber = policyNumber;
	}

	public void setPolicyType(String policyType) {
		_policyType = policyType;
	}

	public void setProduct(String product) {
		_product = product;
	}

	public void setTotalBilledPremium(BigDecimal totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICPolicyDTO.class);

	private String _accountNumber;
	private Date _cancelDate;
	private String _companyNumber;
	private int _currentSequenceNumber;
	private boolean _expiredPolicy;
	private boolean _futurePolicy;
	private boolean _hasHiredAndNonOwnedAutoLimits;
	private boolean _hasSexualMisconductLimits;
	private boolean _inForcePolicy;
	private String _policyEffectiveDate;
	private String _policyExpirationDate;
	private String _policyNumber;
	private String _policyType;
	private String _product;
	private BigDecimal _totalBilledPremium;

}