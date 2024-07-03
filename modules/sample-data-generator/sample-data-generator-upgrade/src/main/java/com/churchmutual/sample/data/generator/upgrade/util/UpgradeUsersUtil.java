package com.churchmutual.sample.data.generator.upgrade.util;

import com.churchmutual.commons.constants.ExpandoConstants;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.commons.enums.CMICUserGroup;
import com.churchmutual.core.service.CMICUserLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

public class UpgradeUsersUtil {

	public UpgradeUsersUtil(
			CMICUserLocalService cmicUserLocalService, GroupLocalService groupLocalService, Props props,
			UserGroupLocalService userGroupLocalService, UserLocalService userLocalService) {
		this.cmicUserLocalService = cmicUserLocalService;
		this.groupLocalService = groupLocalService;
		this.props = props;
		this.userGroupLocalService = userGroupLocalService;
		this.userLocalService = userLocalService;
	}

	public void addSampleContentManagerUser(long companyId, long defaultUserId) throws Exception {
		String emailAddress = props.get(_PROP_KEY_CONTENT_MANAGER_EMAIL_ADDRESS);

		User user = userLocalService.fetchUserByEmailAddress(companyId, emailAddress);

		if (user != null) {
			return;
		}

		String password = props.get(_PROP_KEY_PASSWORD);

		user = _addUser(companyId, defaultUserId, 0, emailAddress, password, null, null);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		expandoBridge.setAttribute(ExpandoConstants.CMIC_EMPLOYEE, Boolean.TRUE);

		UserGroup liferayContentManagerUserGroup = userGroupLocalService.getUserGroup(companyId, CMICUserGroup.CONTENT_MANAGER.getName());

		userLocalService.addUserGroupUser(liferayContentManagerUserGroup.getUserGroupId(), user.getUserId());
	}

	public void addTestUsers(long companyId, long defaultUserId) throws Exception {
		List<TestUserDTO> testUsers = getTestUsers();

		Group producerGroup = groupLocalService.getFriendlyURLGroup(companyId, CMICSite.PRODUCER.getFriendlyURL());

		Group insuredGroup = groupLocalService.getFriendlyURLGroup(companyId, CMICSite.INSURED.getFriendlyURL());

		for (TestUserDTO testUserDTO : testUsers) {
			String emailAddress = testUserDTO.getEmailAddress();

			User user = userLocalService.fetchUserByEmailAddress(companyId, emailAddress);

			if (user != null) {
				continue;
			}

			long groupId = producerGroup.getGroupId();

			if (StringUtil.containsIgnoreCase(emailAddress, CMICSite.INSURED.getName(), StringPool.BLANK)) {
				groupId = insuredGroup.getGroupId();
			}

			_addUser(
				companyId, defaultUserId, groupId, emailAddress, testUserDTO.getPassword(), testUserDTO.getCmicUserId(),
				testUserDTO.getCmicUUID());
		}
	}

	public void setDefaultAdminAsCMICEmployee(long companyId) throws Exception {
		String defaultAdminScreenName = props.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME);
		String defaultAdminEmailAddress = props.get(_PROP_KEY_DEFAULT_ADMIN_EMAIL_ADDRESS);

		User user = userLocalService.fetchUserByScreenName(companyId, defaultAdminScreenName);

		if (user == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
						String.format(
								"Cannot set default admin to CMIC employee. User could not be found with username %s.",
								defaultAdminScreenName));
			}

			return;
		}

		userLocalService.updateEmailAddress(
				user.getUserId(), user.getPassword(), defaultAdminEmailAddress, defaultAdminEmailAddress);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		expandoBridge.setAttribute(ExpandoConstants.CMIC_EMPLOYEE, Boolean.TRUE);
	}

	protected List<TestUserDTO> getTestUsers() {
		List<TestUserDTO> testUsers = new ArrayList<>();
		String emailAddress;
		String cmicUserId;
		int testUserIndex = 1;

		String password = props.get(_PROP_KEY_PASSWORD);

		do {
			String emailAddressKey = StringUtil.replace(
				_PROP_KEY_EMAIL_ADDRESS, "{INDEX}", String.valueOf(testUserIndex));
			String cmicUserIdKey = StringUtil.replace(_PROP_KEY_CMIC_USER_ID, "{INDEX}", String.valueOf(testUserIndex));
			String cmicUUIDKey = StringUtil.replace(_PROP_KEY_CMIC_UUID, "{INDEX}", String.valueOf(testUserIndex));

			emailAddress = props.get(emailAddressKey);
			cmicUserId = props.get(cmicUserIdKey);
			String cmicUUID = props.get(cmicUUIDKey);

			if (Validator.isNotNull(emailAddress) && Validator.isNotNull(cmicUserId)) {
				testUsers.add(new TestUserDTO(emailAddress, password, cmicUserId, cmicUUID));
			}

			testUserIndex += 1;
		}
		while (Validator.isNotNull(emailAddress) && Validator.isNotNull(cmicUserId));

		return testUsers;
	}

	protected CMICUserLocalService cmicUserLocalService;
	protected GroupLocalService groupLocalService;
	protected Props props;
	protected UserGroupLocalService userGroupLocalService;
	protected UserLocalService userLocalService;

	private User _addUser(
			long companyId, long defaultUserId, long groupId, String emailAddress, String password, String cmicUserId,
			String externalReferenceCode)
			throws PortalException {

		String[] emailSplitStrings = emailAddress.split("@|\\.");

		String firstName = StringUtil.upperCaseFirstLetter(emailSplitStrings[0]);
		String lastName = StringUtil.upperCaseFirstLetter(emailSplitStrings[1]);

		long[] groupIds = null;

		if (groupId != 0) {
			groupIds = new long[]{groupId};
		}

		User user = userLocalService.addUser(
			defaultUserId, companyId, false, password, password, true, null, emailAddress, 0, null,
			LocaleUtil.getDefault(), firstName, null, lastName, -1, -1, true, 1, 1, 1977, null, groupIds, null, null,
			null, false, null);

		if (Validator.isNotNull(externalReferenceCode)) {
			user.setExternalReferenceCode(externalReferenceCode);

			userLocalService.updateUser(user);
		}

		if (Validator.isNotNull(cmicUserId)) {
			cmicUserLocalService.addCMICUser(Long.valueOf(cmicUserId), user.getUserId());
		}

		return user;
	}

	private static final String _PROP_KEY_CMIC_USER_ID = "test.cmic.user.id[{INDEX}]";

	private static final String _PROP_KEY_CMIC_UUID = "test.cmic.uuid[{INDEX}]";

	private static final String _PROP_KEY_CONTENT_MANAGER_EMAIL_ADDRESS = "test.content.manager.email.address";

	private static final String _PROP_KEY_DEFAULT_ADMIN_EMAIL_ADDRESS = "default.admin.email.address";

	private static final String _PROP_KEY_EMAIL_ADDRESS = "test.email.address[{INDEX}]";

	private static final String _PROP_KEY_PASSWORD = "test.password";

	private static final Log _log = LogFactoryUtil.getLog(UpgradeUsersUtil.class);

}
