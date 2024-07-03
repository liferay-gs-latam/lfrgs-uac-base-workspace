package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICInsuredUserDTO;
import com.churchmutual.rest.model.CMICProducerUserDTO;
import com.churchmutual.rest.model.CMICUserDTO;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface PortalUserWebService {

	public CMICUserDTO deleteInsuredEntityUser(
			long callerCMICUserId, long cmicUserId, String accountNumber, String companyNumber)
		throws PortalException;

	public CMICUserDTO deleteProducerEntityUser(long callerCMICUserId, long cmicUserId, long producerId)
		throws PortalException;

	public String generateLossRunReport(
			String accountNumber, long cmicUserId, String requesterFirstName, String requesterLastName,
			String claimHistoryEndDate, String claimHistoryStartDate, String requestReason,
			boolean showClaimantInformation, boolean showReserveInformation)
		throws PortalException;

	public List<CMICUserDTO> getAccountEntityUsers(String accountNumber) throws PortalException;

	public List<CMICUserDTO> getProducerEntityUsers(long producerId) throws PortalException;

	public CMICUserDTO getUserDetails(long cmicUserId) throws PortalException;

	public boolean isUserRegistered(long cmicUserId) throws PortalException;

	public boolean isServiceHealthy();
	
	public CMICInsuredUserDTO provisionExistingInsuredUser(
			long callerCMICUserId, long cmicUserId, String accountNumber, String companyNumber, long producerId,
			String email)
		throws PortalException;

	public CMICProducerUserDTO provisionExistingProducerUser(
			long callerCMICUserId, long cmicUserId, long producerId, String email)
		throws PortalException;

	public List<CMICUserDTO> provisionNewInsuredUsers(
			long callerCMICUserId, String accountNumber, String companyNumber, long producerId, List<String> emails)
		throws PortalException;

	public List<CMICUserDTO> provisionNewProducerUsers(long callerCMICUserId, long producerId, List<String> emails)
		throws PortalException;

	public CMICUserDTO updateInsuredUserRole(
			long callerCMICUserId, long cmicUserId, String accountNumber, String companyNumber, String role)
		throws PortalException;

	public CMICUserDTO updateProducerUserRole(long callerCMICUserId, long cmicUserId, long producerId, String role)
		throws PortalException;

	public CMICUserDTO validateInsuredUserRegistration(
			long cmicUserId, String accountNumber, String companyNumber, String registrationCode, String cmicUUID,
			String zipCode)
		throws PortalException;

	public CMICUserDTO validateProducerUserRegistration(
			long cmicUserId, String agentNumber, String divisionNumber, String registrationCode, String cmicUUID,
			String zipCode)
		throws PortalException;

	public CMICUserDTO validateUserRegistrationCode(String registrationCode) throws PortalException;

}