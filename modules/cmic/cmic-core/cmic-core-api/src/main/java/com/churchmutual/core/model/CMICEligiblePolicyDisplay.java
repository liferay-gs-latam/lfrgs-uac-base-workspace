package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICPolicyDTO;

public class CMICEligiblePolicyDisplay {

	public CMICEligiblePolicyDisplay(CMICPolicyDTO cmicPolicyDTO, String productName) {
		_hasHiredAndNonOwnedAutoLimits = cmicPolicyDTO.getHasHiredAndNonOwnedAutoLimits();
		_hasSexualMisconductLimits = cmicPolicyDTO.getHasSexualMisconductLimits();
		_policyNumber = cmicPolicyDTO.getPolicyNumber();
		_productName = productName;
	}

	public boolean getHasHiredAndNonOwnedAutoLimits() {
		return _hasHiredAndNonOwnedAutoLimits;
	}

	public boolean getHasSexualMisconductLimits() {
		return _hasSexualMisconductLimits;
	}

	public String getPolicyNumber() {
		return _policyNumber;
	}

	public String getProductName() {
		return _productName;
	}

	private boolean _hasHiredAndNonOwnedAutoLimits;
	private boolean _hasSexualMisconductLimits;
	private String _policyNumber;
	private String _productName;

}
