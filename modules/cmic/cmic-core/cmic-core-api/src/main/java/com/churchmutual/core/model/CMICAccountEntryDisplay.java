package com.churchmutual.core.model;

import com.churchmutual.commons.util.NameFormatterUtil;
import com.churchmutual.core.constants.CMICCoreConstants;
import com.churchmutual.core.service.CMICAccountEntryLocalServiceUtil;
import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICAddressDTO;
import com.churchmutual.rest.model.CMICPolicyAccountSummaryDTO;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

public class CMICAccountEntryDisplay extends CMICBusinessDisplay {

	public CMICAccountEntryDisplay(CMICAccountDTO cmicAccountDTO, CMICAddressDTO cmicAddressDTO, CMICPolicyAccountSummaryDTO cmicPolicyAccountSummaryDTO, String accountNumber) {
		this(cmicAccountDTO, cmicAddressDTO, cmicPolicyAccountSummaryDTO);

		if (Validator.isNull(_accountNumber)) {
			_accountNumber = accountNumber;
		}

		if (Validator.isNull(_companyNumber)) {
			_companyNumber = CMICCoreConstants.DEFAULT_COMPANY_NUMBER;
		}
	}

	public CMICAccountEntryDisplay(
			CMICAccountDTO cmicAccountDTO, CMICAddressDTO cmicAddressDTO, CMICPolicyAccountSummaryDTO cmicPolicyAccountSummaryDTO) {

		if (cmicPolicyAccountSummaryDTO != null) {
			_numExpiredPolicies = cmicPolicyAccountSummaryDTO.getNumExpiredPolicies();
			_numFuturePolicies = cmicPolicyAccountSummaryDTO.getNumFuturePolicies();
			_numInForcePolicies = cmicPolicyAccountSummaryDTO.getNumInForcePolicies();

			BigDecimal totalBilledPremium = cmicPolicyAccountSummaryDTO.getTotalBilledPremium();

			_totalBilledPremium = totalBilledPremium.toPlainString();

			if (BigDecimal.ZERO.equals(totalBilledPremium)) {
				_totalBilledPremiumSortable = 0;
			}
			else {
				_totalBilledPremiumSortable = totalBilledPremium.doubleValue();
			}
		}

		if (cmicAddressDTO != null) {
			_addressLine1 = NameFormatterUtil.format(cmicAddressDTO.getAddressLine1());
			_addressLine2 = NameFormatterUtil.format(cmicAddressDTO.getAddressLine2());
			_addressLine3 = NameFormatterUtil.format(cmicAddressDTO.getAddressLine3());
			_city = NameFormatterUtil.format(cmicAddressDTO.getCity());
			_state = cmicAddressDTO.getState();
			_zipCode = cmicAddressDTO.getZipCode();
		}

		if (cmicAccountDTO != null) {
			_accountId = cmicAccountDTO.getId();
			_accountNumber = cmicAccountDTO.getAccountNumber();
			_companyNumber = cmicAccountDTO.getCompanyNumber();

			try {
				_accountName = cmicAccountDTO.getAccountName();

				_policyNumbers = StringPool.BLANK;

				_producerCode = cmicAccountDTO.getProducerCode();
				_producerName = CMICAccountEntryLocalServiceUtil.getProducerName(cmicAccountDTO.getProducerCode());
			}
			catch (PortalException pe) {
				_log.error(pe);
			}
		}

		if (Validator.isBlank(_accountName)) {
			_accountName = LanguageUtil.get(LocaleUtil.getDefault(), "no-account-name");
		}
	}

	public long getAccountId() {
		return _accountId;
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getAddressLine3() {
		return _addressLine3;
	}

	public String getCity() {
		return _city;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public String getName() {
		return _accountName;
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

	public String getPolicyNumbers() {
		return _policyNumbers;
	}

	public String getProducerCode() {
		return _producerCode;
	}

	public String getProducerCodeFormatted() {
		if (Validator.isBlank(_producerCode)) {
			return StringPool.BLANK;
		}

		return _producerCode.substring(0, 2) + StringPool.DASH + _producerCode.substring(2);
	}

	public String getProducerName() {
		return _producerName;
	}

	public String getState() {
		return _state;
	}

	public String getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public double getTotalBilledPremiumSortable() {
		return _totalBilledPremiumSortable;
	}

	public String getZipCode() {
		return _zipCode;
	}

	private static Log _log = LogFactoryUtil.getLog(CMICAccountEntryDisplay.class);

	private long _accountId;
	private String _accountName;
	private String _accountNumber;
	private String _addressLine1;
	private String _addressLine2;
	private String _addressLine3;
	private String _city;
	private String _companyNumber;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private String _policyNumbers;
	private String _producerCode;
	private String _producerName;
	private String _state;
	private String _totalBilledPremium;
	private double _totalBilledPremiumSortable;
	private String _zipCode;

}