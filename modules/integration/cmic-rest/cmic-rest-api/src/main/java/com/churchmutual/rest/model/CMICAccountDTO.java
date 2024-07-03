package com.churchmutual.rest.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Kayleen Lim
 */
public class CMICAccountDTO extends CMICObjectDTO {

	public String getAccountName() {
		return _accountName;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getCompanyNumber() {
		if (Validator.isNull(_companyNumber)) {
			_log.error(
				"CMICAccountDTO with accountNumber=" + _accountNumber +
					" has no companyNumber, using default companyNumber");

			return DEFAULT_COMPANY_NUMBER;
		}

		return _companyNumber;
	}

	public long getId() {
		return _id;
	}

	public String getProducerCode() {
		return _producerCode;
	}

	public void setAccountName(String accountName) {
		_accountName = accountName;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		_companyNumber = companyNumber;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setProducerCode(String producerCode) {
		_producerCode = producerCode;
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICAccountDTO.class);

	private String _accountName;
	private String _accountNumber;
	private String _companyNumber;
	private long _id;
	private String _producerCode;

}