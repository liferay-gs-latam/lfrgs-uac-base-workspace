package com.churchmutual.rest.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

/**
 * @author Kayleen Lim
 */
public class CMICPolicyAccountSummaryDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getCompanyNumber() {
		if (Validator.isNull(_companyNumber)) {
			_log.error(
				"CMICPolicyAccountSummaryDTO with accountNumber=" + _accountNumber +
					" has no companyNumber, using default companyNumber");

			return DEFAULT_COMPANY_NUMBER;
		}

		return _companyNumber;
	}

	public int getNumExpiredPolicies() {
		return _numExpiredPolicies;
	}

	public int getNumFuturePolicies() {
		return _numFuturePolicies;
	}

	public int getNumInForcePolicies() {
		return _numInForcePolicies;
	}

	public BigDecimal getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		_companyNumber = companyNumber;
	}

	public void setNumExpiredPolicies(int numExpiredPolicies) {
		_numExpiredPolicies = numExpiredPolicies;
	}

	public void setNumFuturePolicies(int numFuturePolicies) {
		_numFuturePolicies = numFuturePolicies;
	}

	public void setNumInForcePolicies(int numInForcePolicies) {
		_numInForcePolicies = numInForcePolicies;
	}

	public void setTotalBilledPremium(BigDecimal totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICPolicyAccountSummaryDTO.class);

	private String _accountNumber;
	private String _companyNumber;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private BigDecimal _totalBilledPremium;

}