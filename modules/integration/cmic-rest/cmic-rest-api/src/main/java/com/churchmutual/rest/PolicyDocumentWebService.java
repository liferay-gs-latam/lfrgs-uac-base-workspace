package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICPolicyDocumentDTO;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Kayleen Lim
 */
public interface PolicyDocumentWebService {

	public CMICPolicyDocumentDTO downloadTransaction(
			String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum)
		throws PortalException;

}