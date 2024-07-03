package com.churchmutual.rest.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Kayleen Lim
 */
public class CMICUserRelationDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getCompanyNumber() {
		if (Validator.isNull(_companyNumber)) {
			_log.error(
				"CMICUserRelationDTO with accountNumber=" + _accountNumber +
					" has no companyNumber, using default companyNumber");

			return DEFAULT_COMPANY_NUMBER;
		}

		return _companyNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public long getProducerId() {
		return _producerId;
	}

	public String getRole() {
		return _role;
	}

	public boolean isProducer() {
		if (_producerId > 0) {
			return true;
		}

		return false;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setAgentNumber(String agentNumber) {
		_agentNumber = agentNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		_companyNumber = companyNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		_divisionNumber = divisionNumber;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	public void setRole(String role) {
		_role = role;
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICUserRelationDTO.class);

	private String _accountNumber;
	private String _agentNumber;
	private String _companyNumber;
	private String _divisionNumber;
	private long _producerId;
	private String _role;

}