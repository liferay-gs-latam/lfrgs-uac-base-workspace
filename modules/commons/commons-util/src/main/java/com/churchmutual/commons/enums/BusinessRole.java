package com.churchmutual.commons.enums;

import com.churchmutual.commons.constants.CommonConstants;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Chan
 * @author Nelly Liu Peng
 */
public enum BusinessRole implements DisplayEnum {

	ACCOUNT_ADMINISTRATOR(
		"Account Administrator", "account-administrator", CommonConstants.BUSINESS_TYPE_ACCOUNT, "admin"),
	ACCOUNT_OWNER("Account Owner", "account-owner", CommonConstants.BUSINESS_TYPE_ACCOUNT, "owner"),
	ACCOUNT_MEMBER("Account Member", "account-user", CommonConstants.BUSINESS_TYPE_ACCOUNT, "member"),
	ORGANIZATION_ADMINISTRATOR(
		"Organization Administrator", "organization-administrator", CommonConstants.BUSINESS_TYPE_ORGANIZATION,
		"admin"),
	ORGANIZATION_OWNER("Organization Owner", "organization-owner", CommonConstants.BUSINESS_TYPE_ORGANIZATION, "owner"),
	ORGANIZATION_MEMBER("Organization Member", "organization-user", CommonConstants.BUSINESS_TYPE_ORGANIZATION, "member");

	public static BusinessRole fromRoleName(String roleName) {
		BusinessRole businessRole = ROLE_NAME_MAP.get(roleName);

		if (Validator.isNull(businessRole)) {
			throw new IllegalArgumentException("No BusinessRole with roleName: " + roleName);
		}

		return businessRole;
	}

	public static BusinessRole fromShortenedNameKey(String shortenedNameKey, BusinessPortalType businessPortalType) throws PortalException {
		BusinessRole businessRole;

		if (BusinessPortalType.PRODUCER.equals(businessPortalType)) {
			businessRole = ORGANIZATION_ROLE_KEY_MAP.get(shortenedNameKey);
		}
		else if (BusinessPortalType.INSURED.equals(businessPortalType)) {
			businessRole = ACCOUNT_ROLE_KEY_MAP.get(shortenedNameKey);
		}
		else {
			throw new PortalException("Error: " + businessPortalType.getName() + " is not a valid portal type");
		}

		if (Validator.isNull(businessRole)) {
			throw new IllegalArgumentException("No BusinessRole with shortenedNameKey: " + shortenedNameKey);
		}

		return businessRole;
	}

	public static BusinessRole getBusinessAdminRole(BusinessPortalType businessPortalType) throws PortalException {
		if (BusinessPortalType.PRODUCER.equals(businessPortalType)) {
			return ORGANIZATION_ADMINISTRATOR;
		}
		else if (BusinessPortalType.INSURED.equals(businessPortalType)) {
			return ACCOUNT_ADMINISTRATOR;
		}

		throw new PortalException("Error: " + businessPortalType.getName() + " is not a valid portal type");
	}

	public static BusinessRole getBusinessMemberRole(BusinessPortalType businessPortalType) throws PortalException {
		if (BusinessPortalType.PRODUCER.equals(businessPortalType)) {
			return ORGANIZATION_MEMBER;
		}
		else if (BusinessPortalType.INSURED.equals(businessPortalType)) {
			return ACCOUNT_MEMBER;
		}

		throw new PortalException("Error: " + businessPortalType.getName() + " is not a valid portal type");
	}

	public static BusinessRole getBusinessOwnerRole(BusinessPortalType businessPortalType) throws PortalException {
		if (BusinessPortalType.PRODUCER.equals(businessPortalType)) {
			return ORGANIZATION_OWNER;
		}
		else if (BusinessPortalType.INSURED.equals(businessPortalType)) {
			return ACCOUNT_OWNER;
		}

		throw new PortalException("Error: " + businessPortalType.getName() + " is not a valid portal type");
	}

	public static BusinessRole[] getBusinessRoles(BusinessPortalType businessPortalType) throws PortalException {
		if (BusinessPortalType.PRODUCER.equals(businessPortalType)) {
			return ORGANIZATION_ROLES;
		}
		else if (BusinessPortalType.INSURED.equals(businessPortalType)) {
			return ACCOUNT_ROLES;
		}

		throw new PortalException("Error: " + businessPortalType.getName() + " is not a valid portal type");
	}

	public String getBusinessType() {
		return _businessType;
	}

	@Override
	public String getMessageKey() {
		return _messageKey;
	}

	public String getRoleName() {
		return _roleName;
	}

	public String getShortenedNameKey() {
		return _shortenedNameKey;
	}

	private BusinessRole(String roleName, String messageKey, String businessType, String shortenedNameKey) {
		_roleName = roleName;
		_messageKey = messageKey;
		_businessType = businessType;
		_shortenedNameKey = shortenedNameKey;
	}

	private static final Map<String, BusinessRole> ACCOUNT_ROLE_KEY_MAP = new HashMap<>();

	private static final BusinessRole[] ACCOUNT_ROLES = {ACCOUNT_OWNER, ACCOUNT_MEMBER};

	private static final Map<String, BusinessRole> ORGANIZATION_ROLE_KEY_MAP = new HashMap<>();

	private static final BusinessRole[] ORGANIZATION_ROLES = {
		ORGANIZATION_OWNER, ORGANIZATION_MEMBER
	};

	private static final Map<String, BusinessRole> ROLE_NAME_MAP = new HashMap<>();

	static {
		for (BusinessRole businessRole : ACCOUNT_ROLES) {
			ACCOUNT_ROLE_KEY_MAP.put(businessRole._shortenedNameKey, businessRole);
			ROLE_NAME_MAP.put(businessRole._roleName, businessRole);
		}

		for (BusinessRole businessRole : ORGANIZATION_ROLES) {
			ORGANIZATION_ROLE_KEY_MAP.put(businessRole._shortenedNameKey, businessRole);
			ROLE_NAME_MAP.put(businessRole._roleName, businessRole);
		}
	}

	private String _businessType;
	private String _messageKey;
	private String _roleName;
	private String _shortenedNameKey;

}