package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICAdditionalInterestDTO;
import com.churchmutual.rest.model.CMICPolicyDTO;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Matthew Chan
 */
public interface CertificateOfInsuranceWebService {

	public String downloadCOIDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			boolean showSMLimits, boolean showHiredAndNonOwnedAutoLimits, boolean rentalAutoCertificate,
			boolean editable, boolean performUpload, String[] policyNumbers)
		throws PortalException;
	
	public String downloadNYWCDocument(
			String city, String fullName, String address, String address2, String postalCode, String state,
			 boolean performUpload, String policyNumber, String telephoneNumber)
		throws PortalException;

	public String downloadEOPDocument(
			CMICAdditionalInterestDTO cmicAdditionalInterestDTO, String policyNumber, boolean editable,
			boolean performUpload, String buildingNumber, String locationPremisesNumber)
		throws PortalException;

	public List<CMICPolicyDTO> getCOIEligiblePolicies(String[] policyNumbers) throws PortalException;
	
	public List<String> getNYWCEligiblePolicies(String accountNumber) throws PortalException;
	
	public List<String> getNYWCDisplay(String accountNumber) throws PortalException;

	public List<CMICPolicyDTO> getEOPEligiblePolicies(String[] policyNumbers) throws PortalException;

}