package com.churchmutual.rest.model;

import com.churchmutual.commons.enums.BusinessPortalType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kayleen Lim
 */
public class CMICUserDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public List<CMICUserRelationDTO> getAccounts() {
		return _accounts;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public String getEmailId() {
		return _emailId;
	}

	public int getId() {
		return _id;
	}

	public List<CMICUserRelationDTO> getOrganizations() {
		return _organizations;
	}

	public long getProducerId() {
		return _producerId;
	}

	public String getRegistrationCode() {
		return _registrationCode;
	}

	public String getRegistrationExpirationDate() {
		return _registrationExpirationDate;
	}

	public boolean getRegistrationExpired() {
		return _registrationExpired;
	}

	public String getRegistrationStatus() {
		return _registrationStatus;
	}

	public String getUserRole() {
		return _userRole;
	}

	public String getUserType() {
		return _userType;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean isProducer() {
		return BusinessPortalType.PRODUCER.getGroupKey(
		).equalsIgnoreCase(
			_userType
		);
	}
	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setAccounts(List<CMICUserRelationDTO> accounts) {
		_accounts = accounts;
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

	public void setEmailId(String emailId) {
		_emailId = emailId;
	}

	public void setId(int id) {
		_id = id;
	}

	public void setOrganizations(List<CMICUserRelationDTO> organizations) {
		_organizations = organizations;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	public void setRegistrationCode(String registrationCode) {
		_registrationCode = registrationCode;
	}

	public void setRegistrationExpired(boolean registrationExpired) {
		_registrationExpired = registrationExpired;
	}

	public void setRegistrationExpirationDate(String registrationExpirationDate) {
		_registrationExpirationDate = registrationExpirationDate;
	}

	public void setRegistrationStatus(String registrationStatus) {
		_registrationStatus = registrationStatus;
	}

	public void setUserRole(String userRole) {
		_userRole = userRole;
	}

	public void setUserType(String userType) {
		_userType = userType;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private String _accountNumber;
	private List<CMICUserRelationDTO> _accounts = new ArrayList<>();
	private String _agentNumber;
	private String _companyNumber;
	private String _divisionNumber;
	private String _emailId;
	private int _id;
	private List<CMICUserRelationDTO> _organizations = new ArrayList<>();
	private long _producerId;
	private String _registrationCode;
	private boolean _registrationExpired;
	private String _registrationExpirationDate;
	private String _registrationStatus;
	private String _userRole;
	private String _userType;
	private String _uuid;

}