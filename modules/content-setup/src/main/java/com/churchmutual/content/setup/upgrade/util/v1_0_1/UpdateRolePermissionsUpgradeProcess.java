package com.churchmutual.content.setup.upgrade.util.v1_0_1;

import com.churchmutual.commons.enums.CMICRole;
import com.churchmutual.commons.util.RoleHelper;
import com.churchmutual.content.setup.upgrade.util.common.BaseAdminUpgradeProcess;

import com.liferay.announcements.constants.AnnouncementsPortletKeys;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.HashMap;
import java.util.Map;

public class UpdateRolePermissionsUpgradeProcess extends BaseAdminUpgradeProcess {

	public UpdateRolePermissionsUpgradeProcess(
		PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService,
		UserLocalService userLocalService) {

		super(permissionCheckerFactory, portal, roleLocalService, userLocalService);

		this.portal = portal;
		this.roleLocalService = roleLocalService;
	}

	public void doUpgradeAsAdmin() throws Exception {
		updateRoles();
	}

	protected void updateRoles() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		Role contentManagerRole = roleLocalService.getRole(companyId, CMICRole.CONTENT_MANAGER.getRoleName());

		Map<String, String[]> resourcePermissions = new HashMap<>();

		resourcePermissions.put(Group.class.getName(), new String[] {ActionKeys.MANAGE_ANNOUNCEMENTS});
		resourcePermissions.put(
			PortletKeys.PORTAL, new String[] {ActionKeys.ADD_GENERAL_ANNOUNCEMENTS, ActionKeys.VIEW_CONTROL_PANEL});
		resourcePermissions.put(
			AnnouncementsPortletKeys.ANNOUNCEMENTS,
			new String[] {
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW
			});
		resourcePermissions.put(
			AnnouncementsEntry.class.getName(),
			new String[] {ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW});
		resourcePermissions.put(
			AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN,
			new String[] {
				ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
				ActionKeys.PREFERENCES, ActionKeys.VIEW
			});

		for (Map.Entry<String, String[]> resourcePermission : resourcePermissions.entrySet()) {
			RoleHelper.addResourcePermissionWithCompanyScope(
				new Role[] {contentManagerRole}, resourcePermission.getValue(), resourcePermission.getKey());
		}
	}

	protected Portal portal;
	protected RoleLocalService roleLocalService;

}