package com.churchmutual.core.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CMICPermissionAction {

	MANAGE_PERMISSIONS("MANAGE_PERMISSIONS", false, false),
	MANAGE_USERS("MANAGE_USERS", true, false),
	REQUEST_COI_EOP("REQUEST_COI_EOP", false, false),
	REQUEST_LOSS_RUNS("REQUEST_LOSS_RUNS", false, false),
	VIEW("VIEW", true, true),
	VIEW_COMMISSION_STATEMENTS("VIEW_COMMISSION_STATEMENTS", false, false),
	VIEW_POLICY_DOCUMENTS("VIEW_POLICY_DOCUMENTS", false, false);

	public static List<CMICPermissionAction> getInsuredPermissionActions(
			boolean includeHidden, boolean includeOwnerOnlyViewPermissions) {
		List<CMICPermissionAction> permissions = new ArrayList<>(Arrays.asList(
			MANAGE_PERMISSIONS, MANAGE_USERS, REQUEST_COI_EOP, REQUEST_LOSS_RUNS, VIEW, VIEW_POLICY_DOCUMENTS));

		if (!includeOwnerOnlyViewPermissions) {
			permissions.remove(MANAGE_PERMISSIONS);
		}

		return _filterHidden(permissions, includeHidden);
	}

	public static CMICPermissionAction getPermissionActionFromActionId(String actionId) {
		for (CMICPermissionAction permissionAction : values()) {
			if (permissionAction._actionId.equals(actionId)) {
				return permissionAction;
			}
		}

		return null;
	}

	public static List<CMICPermissionAction> getProducerPermissionActions(
			boolean includeHidden, boolean includeOwnerOnlyViewPermissions) {
		List<CMICPermissionAction> permissions = new ArrayList<>(Arrays.asList(
			MANAGE_PERMISSIONS, MANAGE_USERS, VIEW, VIEW_COMMISSION_STATEMENTS));

		if (!includeOwnerOnlyViewPermissions) {
			permissions.remove(MANAGE_PERMISSIONS);
		}

		return _filterHidden(permissions, includeHidden);
	}

	public static List<CMICPermissionAction> getDefaultInsuredPermissionActions() {
		List<CMICPermissionAction> permissions = getInsuredPermissionActions(true, true);

		return _filterDefault(permissions);
	}

	public static List<CMICPermissionAction> getDefaultProducerPermissionActions() {
		List<CMICPermissionAction> permissions = getProducerPermissionActions(true, true);

		return _filterDefault(permissions);
	}

	public String getActionId() {
		return _actionId;
	}

	public boolean isDefault() {
		return  _default;
	}

	private CMICPermissionAction(String actionId, boolean isDefault, boolean hidden) {
		_actionId = actionId;
		_default = isDefault;
		_hidden = hidden;
	}

	private static List<CMICPermissionAction> _filterDefault(List<CMICPermissionAction> permissions) {
		return permissions.stream().filter(
			permission -> permission.isDefault()
		).collect(
			Collectors.toList()
		);
	}

	private static List<CMICPermissionAction> _filterHidden(List<CMICPermissionAction> permissions, boolean includeHidden) {
		if (includeHidden) {
			return permissions;
		}

		return permissions.stream().filter(
			permission -> !permission._hidden
		).collect(
			Collectors.toList()
		);
	}

	private String _actionId;
	private boolean _default;
	private boolean _hidden;

}