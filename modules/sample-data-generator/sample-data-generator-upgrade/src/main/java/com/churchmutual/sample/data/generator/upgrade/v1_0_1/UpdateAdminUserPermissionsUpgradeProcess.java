package com.churchmutual.sample.data.generator.upgrade.v1_0_1;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.content.setup.upgrade.util.common.BaseAdminUpgradeProcess;
import com.churchmutual.core.model.CMICBusinessKey;
import com.churchmutual.core.model.CMICUserDisplay;
import com.churchmutual.core.model.CMICUserRelationDisplay;
import com.churchmutual.core.service.CMICPermissionLocalService;
import com.churchmutual.core.service.CMICUserLocalService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

public class UpdateAdminUserPermissionsUpgradeProcess extends BaseAdminUpgradeProcess {

	public UpdateAdminUserPermissionsUpgradeProcess(
			CMICPermissionLocalService cmicPermissionLocalService, CMICUserLocalService cmicUserLocalService,
			GroupLocalService groupLocalService, PermissionCheckerFactory permissionCheckerFactory, Portal portal,
			RoleLocalService roleLocalService, UserLocalService userLocalService) {
		super(permissionCheckerFactory, portal, roleLocalService, userLocalService);

		this.cmicPermissionLocalService = cmicPermissionLocalService;
		this.cmicUserLocalService = cmicUserLocalService;
		this.groupLocalService = groupLocalService;
		this.userLocalService = userLocalService;
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		updateUserPermissions();
	}

	/**
	 * Add default permissions for all existing Admin users
	 */
	protected void updateUserPermissions() throws PortalException {
		long companyId = portal.getDefaultCompanyId();

		List<User> users = new ArrayList<>();

		Group producerGroup = groupLocalService.getFriendlyURLGroup(companyId, CMICSite.PRODUCER.getFriendlyURL());

		users.addAll(userLocalService.getGroupUsers(producerGroup.getGroupId()));

		Group insuredGroup = groupLocalService.getFriendlyURLGroup(companyId, CMICSite.INSURED.getFriendlyURL());

		users.addAll(userLocalService.getGroupUsers(insuredGroup.getGroupId()));

		for (User user : users) {
			try {
				long userId = user.getUserId();

				CMICUserDisplay cmicUserDisplay = cmicUserLocalService.getUserDetails(userId);

				_addDefaultUserPermissions(cmicUserDisplay);
			}
			catch (PortalException pe) {
				_log.error(
					String.format(
						"Could not update permissions for user with userId, %s. %s", user.getUserId(), pe.getMessage()));
			}
		}
	}

	private void _addDefaultUserPermissions(CMICUserDisplay cmicUserDisplay) throws PortalException {
		long userId = cmicUserDisplay.getUserId();

		if (cmicUserDisplay.isProducer()) {
			List<CMICUserRelationDisplay> cmicUserRelationDisplays = cmicUserDisplay.getOrganizationList();

			for (CMICUserRelationDisplay cmicUserRelationDisplay : cmicUserRelationDisplays) {
				BusinessRole role = BusinessRole.fromShortenedNameKey(
					cmicUserRelationDisplay.getRole(), BusinessPortalType.PRODUCER);

				if (BusinessRole.ORGANIZATION_ADMINISTRATOR.equals(role)) {
					cmicPermissionLocalService.addDefaultPermissions(
						userId, new CMICBusinessKey(cmicUserRelationDisplay.getProducerId()));
				}
			}
		}
		else {
			List<CMICUserRelationDisplay> cmicUserRelationDisplays = cmicUserDisplay.getAccountList();

			for (CMICUserRelationDisplay cmicUserRelationDisplay : cmicUserRelationDisplays) {
				BusinessRole role = BusinessRole.fromShortenedNameKey(
					cmicUserRelationDisplay.getRole(), BusinessPortalType.INSURED);

				if (BusinessRole.ACCOUNT_ADMINISTRATOR.equals(role)) {
					cmicPermissionLocalService.addDefaultPermissions(
						userId, new CMICBusinessKey(
							cmicUserRelationDisplay.getAccountNumber(), cmicUserRelationDisplay.getCompanyNumber()));
				}
			}
		}
	}

	protected CMICPermissionLocalService cmicPermissionLocalService;
	protected CMICUserLocalService cmicUserLocalService;
	protected GroupLocalService groupLocalService;
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(UpdateAdminUserPermissionsUpgradeProcess.class);
}
