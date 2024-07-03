package com.churchmutual.core.model;

import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.enums.BusinessUserStatus;
import com.churchmutual.core.constants.SelfProvisioningConstants;
import com.churchmutual.core.service.CMICOrganizationLocalServiceUtil;
import com.churchmutual.core.service.CMICUserLocalServiceUtil;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.ArrayList;
import java.util.List;

public class CMICUserDisplaySoap {

	public static CMICUserDisplaySoap[] toSoapModels(List<CMICUserDisplay> models) {
		return new CMICUserDisplaySoap[0];
	}

	public CMICUserDisplaySoap(User user, String portraitImageURL) throws PortalException {
		if (user == null) {
			throw new PortalException("User cannot be null");
		}

		_email = user.getEmailAddress();

		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		if (SelfProvisioningConstants.NAME.equalsIgnoreCase(firstName)) {
			firstName = StringPool.DOUBLE_DASH;
		}

		if (SelfProvisioningConstants.NAME.equalsIgnoreCase(lastName)) {
			lastName = StringPool.DOUBLE_DASH;
		}

		_firstName = firstName;
		_fullName = getFullName(user);
		_lastName = lastName;
		_portraitImageUrl = portraitImageURL;

		long userId = user.getUserId();

		_userId = userId;

		CMICUser cmicUser = CMICUserLocalServiceUtil.fetchCMICUserByUserId(userId);

		if (cmicUser != null) {
			_organizationList = CMICOrganizationLocalServiceUtil.getCMICOrganizationDisplaysByUserId(userId);
		}

		_uuid = user.getExternalReferenceCode();
	}

	public String getEmail() {
		return _email;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getFullName() {
		return _fullName;
	}

	public String getLastName() {
		return _lastName;
	}

	public List<CMICOrganizationDisplay> getOrganizationList() {
		return _organizationList;
	}

	public String getPermissions() {
		return _permissions;
	}

	public String getPortraitImageUrl() {
		return _portraitImageUrl;
	}

	public String getRole() {
		return _role;
	}

	public String getStatus() {
		return _status;
	}

	public String getStatusKey() {
		return _statusKey;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setBusinessRole(BusinessRole businessRole) {
		if (businessRole != null) {
			_role = businessRole.getShortenedNameKey();
		}
	}

	public void setBusinessUserStatus(BusinessUserStatus businessUserStatus) {
		if (businessUserStatus != null) {
			_status = businessUserStatus.getUserStatusName();
			_statusKey = businessUserStatus.getMessageKey();

			if (BusinessUserStatus.INVITED.equals(businessUserStatus)) {
				_firstName = StringPool.DOUBLE_DASH;
				_lastName = StringPool.DOUBLE_DASH;
			}
		}
	}

	public void setPermissions(String permissions) {
		_permissions = permissions;
	}

	protected String getFullName(User user) {
		if (SelfProvisioningConstants.NAME.equalsIgnoreCase(user.getFirstName()) &&
			SelfProvisioningConstants.NAME.equalsIgnoreCase(user.getLastName())) {

			return StringPool.DOUBLE_DASH + StringPool.DOUBLE_DASH;
		}

		return user.getFirstName() + StringPool.SPACE + user.getLastName();
	}

	private String _email;
	private String _firstName;
	private String _fullName;
	private String _lastName;
	private List<CMICOrganizationDisplay> _organizationList = new ArrayList<>();
	private String _permissions;
	private String _portraitImageUrl;
	private String _role;
	private String _status;
	private String _statusKey;
	private long _userId;
	private String _uuid;

}