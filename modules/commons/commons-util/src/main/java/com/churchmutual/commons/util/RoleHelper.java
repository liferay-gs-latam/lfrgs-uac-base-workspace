package com.churchmutual.commons.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Helper methods to work with roles
 */
public class RoleHelper {

	/**
	* Adds an array of permissions to roles for a resource, scope, and prim key.
	*
	* @param role
	* @param actionIds
	* @param resourceName (for a portlet-resource use the portlet name, for a model-resource use the model-name)
	* @param scope
	* @param primKey
	* @throws Exception
	*/
	public static void addResourcePermission(
			Role[] roles, String[] actionIds, String resourceName, int scope, long primKey)
		throws Exception {

		long companyId = PortalUtil.getDefaultCompanyId();

		if (ArrayUtil.isNotEmpty(roles)) {
			for (Role role : roles) {
				if (role != null) {
					for (String actionId : actionIds) {
						ResourcePermissionLocalServiceUtil.addResourcePermission(
							companyId, resourceName, scope, String.valueOf(primKey), role.getRoleId(), actionId);

						if (_log.isInfoEnabled()) {
							_log.info(
									"Added Permission: " + primKey + " to Role: " + role.getName() + " for Resource: " +
										resourceName + " and Action: " + actionId);
						}
					}
				}
			}
		}
	}

	/**
	 * addResourcePermission with scope fields prepopulated for company scope.
	 * @param actionIds Use values such as those found in ActionKeys.java
	 * @param resourceName This has 3 possible types of values.  For portlet resource, use the portlet name such as
	 * those found in PortletKeys.java.  For top-level model-resource permissions, use the package path for the
	 * service.xml such as "com.scottlee.folder".  For entity-level resource permissions, use the enties's class name
	 * such as "Folder.class.getName()"
	 */
	public static void addResourcePermissionWithCompanyScope(Role[] roles, String[] actionIds, String resourceName)
		throws Exception {

		// Company scope is the broadest, and grants a user with the role permissions for  every resource of the type
		// within the company.

		// The value of primKey is the primary key of the company.

		addResourcePermission(
			roles, actionIds, resourceName, ResourceConstants.SCOPE_COMPANY, PortalUtil.getDefaultCompanyId());
	}

	/**
	 * addResourcePermission with scope fields prepopulated for group template scope
	 * @param actionIds Use values such as those found in ActionKeys.java
	 * @param resourceName This has 3 possible types of values.  For portlet resource, use the portlet name such as
	 * those found in PortletKeys.java.  For top-level model-resource permissions, use the package path for the
	 * service.xml such as "com.scottlee.folder".  For entity-level resource permissions, use the enties's class name
	 * such as "Folder.class.getName()"
	 * @param groupId The groupId to apply the permission to.
	 */
	public static void addResourcePermissionWithGroupScope(
			Role[] roles, String[] actionIds, String resourceName, long groupId)
		throws Exception {

		// Group scope gives users with the role permissions for every resource within the specified group

		// The value of primKey is the primary key of the group for which permission is defined.

		addResourcePermission(roles, actionIds, resourceName, ResourceConstants.SCOPE_GROUP_TEMPLATE, groupId);
	}

	/**
	 * addResourcePermission with scope fields prepopulated for group template scope
	 * @param actionIds Use values such as those found in ActionKeys.java
	 * @param resourceName This has 3 possible types of values.  For portlet resource, use the portlet name such as
	 * those found in PortletKeys.java.  For top-level model-resource permissions, use the package path for the
	 * service.xml such as "com.scottlee.folder".  For entity-level resource permissions, use the enties's class name
	 * such as "Folder.class.getName()"
	 */
	public static void addResourcePermissionWithGroupTemplateScope(
			Role[] roles, String[] actionIds, String resourceName)
		throws Exception {

		// Group-template scope is similar to group scope, except that it does not automatically apply to a specific
		// group. A user must be a member of a group (generally either a site or an organization), and they must have
		// been given the role within that group before they are granted its permissions.

		// The value of primKey is 0.

		addResourcePermission(
			roles, actionIds, resourceName, ResourceConstants.SCOPE_GROUP_TEMPLATE,
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
	}

	/**
	 * Adds a new User or UserGroup Role based on the name, description and role type passed in.
	 * The title used for the new Role will be the the same as the name.
	 *
	 * @param roleName Used for the Name and Title of the new Role.
	 * @param description The high-level description for the Role
	 * @param roleType i.e., "TYPE_REGULAR", "TYPE_SITE", or "TYPE_ORGANIZATION"
	 */
	public static Role addRole(String roleName, String description, int roleType) throws Exception {
		return addRole(roleName, description, roleType, null);
	}

	/**
	 * Adds a new Role based on the name, description, role type and class passed in.
	 * The title used for the new Role will be the the same as the name.
	 *
	 * @param roleName Used for the Name and Title of the new Role.
	 * @param description The high-level description for the Role
	 * @param roleType i.e., "TYPE_REGULAR", "TYPE_SITE", or "TYPE_ORGANIZATION"
	 * @param clazz i.e., Role.cass, etc.
	 */
	public static Role addRole(String roleName, String description, int roleType, Class<?> clazz) throws Exception {
		long companyId = PortalUtil.getDefaultCompanyId();

		User user = UserLocalServiceUtil.getDefaultUser(companyId);

		ServiceContext serviceContext = new ServiceContext();

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.getDefault(), roleName);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		String className = clazz != null ? clazz.getName() : null;
		String subtype = null; // subtype is optional, and unused
		long classPK = 0; // Use default class private key
		long userId = user.getUserId();

		Role role = RoleLocalServiceUtil.fetchRole(companyId, roleName);

		if (Validator.isNull(role)) {
			role = RoleLocalServiceUtil.addRole(
				userId, className, classPK, roleName, titleMap, descriptionMap, roleType, subtype, serviceContext);

			if (_log.isInfoEnabled()) {
				_log.info("Added role : " + role.getName());
			}
		}

		return role;
	}

	private static Log _log = LogFactoryUtil.getLog(RoleHelper.class);

}