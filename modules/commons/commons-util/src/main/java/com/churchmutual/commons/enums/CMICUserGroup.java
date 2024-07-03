package com.churchmutual.commons.enums;

import com.liferay.portal.kernel.model.role.RoleConstants;

/**
 * @author Kayleen Lim
 */
public enum CMICUserGroup {

	ADMIN_NON_PROD("LiferayAdminNonProd", RoleConstants.ADMINISTRATOR),
	ADMIN_PROD("LiferayAdminProd", RoleConstants.ADMINISTRATOR),
	ADMIN_READ_ONLY("LiferayAdminReadOnly", CMICRole.ADMINISTRATOR_READ_ONLY.getRoleName()),
	CONTENT_MANAGER("LiferayContentManager", CMICRole.CONTENT_MANAGER.getRoleName());

	public String getAssociatedRoleName() {
		return _associatedRoleName;
	}

	public String getName() {
		return _name;
	}

	private CMICUserGroup(String name, String associatedRoleName) {
		_associatedRoleName = associatedRoleName;
		_name = name;
	}

	private String _associatedRoleName;
	private String _name;

}