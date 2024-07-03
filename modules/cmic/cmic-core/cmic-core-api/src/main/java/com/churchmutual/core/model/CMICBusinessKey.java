package com.churchmutual.core.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Kayleen Lim
 */
public class CMICBusinessKey implements Serializable {

	public CMICBusinessKey() {
	}

	public CMICBusinessKey(boolean producer, String accountNumber, String companyNumber, long producerId) {
		_accountNumber = accountNumber;
		_companyNumber = companyNumber;
		_producer = producer;
		_producerId = producerId;
	}

	public CMICBusinessKey(long producerId) {
		_accountNumber = StringPool.BLANK;
		_companyNumber = StringPool.BLANK;
		_producer = true;
		_producerId = producerId;
	}

	public CMICBusinessKey(String accountNumber, String companyNumber) {
		_accountNumber = accountNumber;
		_companyNumber = companyNumber;
		_producer = false;
		_producerId = 0L;
	}

	@Override
	public boolean equals(Object obj) {
		CMICBusinessKey key = (CMICBusinessKey)obj;

		if (Objects.equals(key._accountNumber, _accountNumber) && Objects.equals(key._companyNumber, _companyNumber) &&
			Objects.equals(key._producer, _producer) && Objects.equals(key._producerId, _producerId)) {

			return true;
		}

		return false;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public String getFormattedString() {
		if (isProducer()) {
			return String.valueOf(getProducerId());
		}
		else {
			return String.format(
				"%s-%s", getCompanyNumber(), getAccountNumber());
		}
	}

	public long getProducerId() {
		return _producerId;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _accountNumber);

		hashCode = HashUtil.hash(hashCode, _companyNumber);
		hashCode = HashUtil.hash(hashCode, _producer);

		return HashUtil.hash(hashCode, _producerId);
	}

	public boolean isNotValid() {
		if (isProducer()) {
			return getProducerId() == 0L;
		}
		else {
			return !isValidAccountNumber();
		}
	}

	public boolean isValidAccountNumber() {
		return (!Validator.isBlank(_accountNumber) && Long.parseLong(_accountNumber) > 0) &&
			(!Validator.isBlank(_companyNumber) && Long.parseLong(_companyNumber) > 0);
	}

	public boolean isProducer() {
		return _producer;
	}

	private static final long serialVersionUID = 1L;

	private String _accountNumber;
	private String _companyNumber;
	private boolean _producer;
	private long _producerId;

}