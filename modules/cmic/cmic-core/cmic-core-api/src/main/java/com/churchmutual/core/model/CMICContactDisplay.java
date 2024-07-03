package com.churchmutual.core.model;

import com.churchmutual.commons.util.URLBuilderUtil;
import com.churchmutual.rest.model.CMICRoleAssignmentDTO;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;
import java.util.TreeSet;

public class CMICContactDisplay {

	public CMICContactDisplay(CMICRoleAssignmentDTO cmicRoleAssignmentDTO) {
		_email =
			Validator.isNotNull(cmicRoleAssignmentDTO.getEmail()) ? cmicRoleAssignmentDTO.getEmail() : StringPool.BLANK;
		_employeeId = cmicRoleAssignmentDTO.getEmployeeId();
		_id = cmicRoleAssignmentDTO.getId();
		_jobTitle = cmicRoleAssignmentDTO.getJobTitle();
		_fullName = cmicRoleAssignmentDTO.getName();
		_phoneNumber =
			Validator.isNotNull(cmicRoleAssignmentDTO.getPhoneNumber()) ? cmicRoleAssignmentDTO.getPhoneNumber() :
			StringPool.BLANK;
		_phoneNumberURL = Validator.isNotNull(cmicRoleAssignmentDTO.getPhoneNumber()) ?
		URLBuilderUtil.formatPhoneNumberURL(cmicRoleAssignmentDTO.getPhoneNumber()) : StringPool.BLANK;
		_producerCodesSet = new TreeSet<>();
		_role = cmicRoleAssignmentDTO.getRole();
		_version = cmicRoleAssignmentDTO.getVersion();
	}

	public void addProducerCode(String producerCode) {
		_producerCodesSet.add(producerCode.substring(0, 2) + StringPool.DASH + producerCode.substring(2));
	}

	public boolean equals(CMICContactDisplay cmicContactDisplay) {
		if (cmicContactDisplay == null) {
			return false;
		}

		if (_employeeId.equals(cmicContactDisplay.getEmployeeId())) {
			return true;
		}

		return false;
	}

	public String getEmail() {
		return _email;
	}

	public String getEmployeeId() {
		return _employeeId;
	}

	public String getFirstName() {
		String[] splitStrings = StringUtil.split(_fullName, CharPool.SPACE);

		if (splitStrings.length > 0) {
			return splitStrings[0];
		}

		return _fullName;
	}

	public String getFullName() {
		return _fullName;
	}

	public long getId() {
		return _id;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getLastName() {
		String[] splitStrings = StringUtil.split(_fullName, CharPool.SPACE);

		if (splitStrings.length > 1) {
			return splitStrings[1];
		}

		return _fullName;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public String getPhoneNumberURL() {
		return _phoneNumberURL;
	}

	public String getProducerCodes() {
		return StringUtil.merge(_producerCodesSet, ", ");
	}

	public String getRole() {
		return _role;
	}

	public int getVersion() {
		return _version;
	}

	public int producerCodesLength() {
		return _producerCodesSet.size();
	}

	private String _email;
	private String _employeeId;
	private String _fullName;
	private long _id;
	private String _jobTitle;
	private String _phoneNumber;
	private String _phoneNumberURL;
	private Set<String> _producerCodesSet;
	private String _role;
	private int _version;

}