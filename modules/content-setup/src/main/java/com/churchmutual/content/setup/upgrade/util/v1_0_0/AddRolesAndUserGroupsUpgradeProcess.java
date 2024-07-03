/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.churchmutual.content.setup.upgrade.util.v1_0_0;

import com.churchmutual.commons.enums.CMICRole;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.commons.enums.CMICUserGroup;
import com.churchmutual.commons.util.RoleHelper;
import com.churchmutual.content.setup.upgrade.util.common.BaseAdminUpgradeProcess;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Chan
 */
public class AddRolesAndUserGroupsUpgradeProcess extends BaseAdminUpgradeProcess {

	public AddRolesAndUserGroupsUpgradeProcess(
		GroupLocalService groupLocalService, PermissionCheckerFactory permissionCheckerFactory, Portal portal,
		RoleLocalService roleLocalService, UserGroupLocalService userGroupLocalService,
		UserLocalService userLocalService) {

		super(permissionCheckerFactory, portal, roleLocalService, userLocalService);

		this.groupLocalService = groupLocalService;
		this.userGroupLocalService = userGroupLocalService;
	}

	protected void addRoles() throws Exception {
		List<Role> roles = new ArrayList<>();

		Role administratorReadOnlyRole = RoleHelper.addRole(
			CMICRole.ADMINISTRATOR_READ_ONLY.getRoleName(), StringPool.BLANK, RoleConstants.TYPE_REGULAR);
		Role contentManagerRole = RoleHelper.addRole(
			CMICRole.CONTENT_MANAGER.getRoleName(), StringPool.BLANK, RoleConstants.TYPE_REGULAR);

		roles.add(administratorReadOnlyRole);
		roles.add(contentManagerRole);

		Map<String, String[]> administratorReadOnlyPermissions = _getAdministratorReadOnlyPermissions();
		Map<String, String[]> contentManagerPermissions = _getContentManagerPermissions();

		for (Map.Entry<String, String[]> resourcePermission : administratorReadOnlyPermissions.entrySet()) {
			RoleHelper.addResourcePermissionWithCompanyScope(
				new Role[] {administratorReadOnlyRole}, resourcePermission.getValue(), resourcePermission.getKey());
		}

		for (Map.Entry<String, String[]> resourcePermission : contentManagerPermissions.entrySet()) {
			RoleHelper.addResourcePermissionWithCompanyScope(
				new Role[] {contentManagerRole}, resourcePermission.getValue(), resourcePermission.getKey());
		}
	}

	/**
	 * Add User groups, associate user groups with Role and Producer site
	 */
	protected void addUserGroups() throws PortalException {
		long userId = PrincipalThreadLocal.getUserId();

		long companyId = portal.getDefaultCompanyId();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		for (CMICUserGroup portalUserGroups : CMICUserGroup.values()) {
			String name = portalUserGroups.getName();

			UserGroup userGroup = userGroupLocalService.addUserGroup(
				userId, companyId, name, StringPool.BLANK, serviceContext);

			if (_log.isInfoEnabled()) {
				_log.info("Added User Group with name: " + name);
			}

			Role role = roleLocalService.getRole(companyId, portalUserGroups.getAssociatedRoleName());

			roleLocalService.addGroupRole(userGroup.getGroupId(), role);

			if (_log.isInfoEnabled()) {
				_log.info(String.format("Added User Group, %s, to Role, %s.", name, role.getName()));
			}

			Group producerGroup = groupLocalService.getFriendlyURLGroup(companyId, CMICSite.PRODUCER.getFriendlyURL());

			userGroupLocalService.addGroupUserGroup(producerGroup.getGroupId(), userGroup);
		}
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		addRoles();

		addUserGroups();
	}

	protected GroupLocalService groupLocalService;
	protected UserGroupLocalService userGroupLocalService;

	private Map<String, String[]> _getAdministratorReadOnlyPermissions() {
		Map<String, String[]> resourcePermissions = new HashMap<>();

		resourcePermissions.put(PortletKeys.PORTAL, new String[] {ActionKeys.VIEW_CONTROL_PANEL});
		resourcePermissions.put(
			PortletKeys.USERS_ADMIN, new String[] {ActionKeys.VIEW, ActionKeys.ACCESS_IN_CONTROL_PANEL});
		resourcePermissions.put(
			PortletKeys.USER_GROUPS_ADMIN, new String[] {ActionKeys.VIEW, ActionKeys.ACCESS_IN_CONTROL_PANEL});
		resourcePermissions.put(
			PortletKeys.ROLES_ADMIN, new String[] {ActionKeys.VIEW, ActionKeys.ACCESS_IN_CONTROL_PANEL});
		resourcePermissions.put(
			PortletKeys.SITE_ADMIN, new String[] {ActionKeys.VIEW, ActionKeys.ACCESS_IN_CONTROL_PANEL});
		resourcePermissions.put(User.class.getName(), new String[] {ActionKeys.VIEW});
		resourcePermissions.put(Organization.class.getName(), new String[] {ActionKeys.VIEW, ActionKeys.VIEW_MEMBERS});
		resourcePermissions.put(Group.class.getName(), new String[] {ActionKeys.VIEW, ActionKeys.VIEW_MEMBERS});
		resourcePermissions.put(UserGroup.class.getName(), new String[] {ActionKeys.VIEW, ActionKeys.VIEW_MEMBERS});

		return resourcePermissions;
	}

	private Map<String, String[]> _getContentManagerPermissions() {
		Map<String, String[]> resourcePermissions = new HashMap<>();

		resourcePermissions.put(
			ResourceActionsUtil.getCompositeModelName(DDMStructure.class.getName(), JournalArticle.class.getName()),
			new String[] {ActionKeys.VIEW, ActionKeys.UPDATE, ActionKeys.DELETE});
		resourcePermissions.put(
			ResourceActionsUtil.getCompositeModelName(DDMTemplate.class.getName(), JournalArticle.class.getName()),
			new String[] {ActionKeys.VIEW, ActionKeys.UPDATE, ActionKeys.DELETE});
		resourcePermissions.put(
			JournalConstants.RESOURCE_NAME,
			new String[] {
				ActionKeys.VIEW, ActionKeys.ADD_FEED, ActionKeys.ADD_FOLDER, DDMActionKeys.ADD_STRUCTURE,
				DDMActionKeys.ADD_TEMPLATE, ActionKeys.ADD_ARTICLE, ActionKeys.SUBSCRIBE, ActionKeys.UPDATE
			});
		resourcePermissions.put(
			JournalArticle.class.getName(),
			new String[] {
				ActionKeys.VIEW, ActionKeys.ADD_DISCUSSION, ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION,
				ActionKeys.EXPIRE, ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION
			});
		resourcePermissions.put(
			JournalFeed.class.getName(), new String[] {ActionKeys.VIEW, ActionKeys.UPDATE, ActionKeys.DELETE});
		resourcePermissions.put(
			JournalFolder.class.getName(),
			new String[] {
				ActionKeys.VIEW, ActionKeys.ACCESS, ActionKeys.ADD_SUBFOLDER, ActionKeys.ADD_ARTICLE, ActionKeys.DELETE,
				ActionKeys.SUBSCRIBE, ActionKeys.UPDATE
			});
		resourcePermissions.put(
			Group.class.getName(),
			new String[] {
				ActionKeys.VIEW, ActionKeys.UPDATE, ActionKeys.MANAGE_LAYOUTS, ActionKeys.VIEW_SITE_ADMINISTRATION
			});
		resourcePermissions.put(
			JournalPortletKeys.JOURNAL, new String[] {ActionKeys.VIEW, ActionKeys.ACCESS_IN_CONTROL_PANEL});
		resourcePermissions.put(
			PortletKeys.GROUP_PAGES, new String[] {ActionKeys.VIEW, ActionKeys.ACCESS_IN_CONTROL_PANEL});

		return resourcePermissions;
	}

	private static final Log _log = LogFactoryUtil.getLog(AddRolesAndUserGroupsUpgradeProcess.class);

}