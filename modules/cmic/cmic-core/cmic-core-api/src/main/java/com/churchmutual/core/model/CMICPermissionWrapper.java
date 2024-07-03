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

package com.churchmutual.core.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CMICPermission}.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICPermission
 * @generated
 */
public class CMICPermissionWrapper
	extends BaseModelWrapper<CMICPermission>
	implements CMICPermission, ModelWrapper<CMICPermission> {

	public CMICPermissionWrapper(CMICPermission cmicPermission) {
		super(cmicPermission);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("userId", getUserId());
		attributes.put("cmicBusinessKey", getCmicBusinessKey());
		attributes.put("actionId", getActionId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String cmicBusinessKey = (String)attributes.get("cmicBusinessKey");

		if (cmicBusinessKey != null) {
			setCmicBusinessKey(cmicBusinessKey);
		}

		String actionId = (String)attributes.get("actionId");

		if (actionId != null) {
			setActionId(actionId);
		}
	}

	/**
	 * Returns the action ID of this cmic permission.
	 *
	 * @return the action ID of this cmic permission
	 */
	@Override
	public String getActionId() {
		return model.getActionId();
	}

	/**
	 * Returns the cmic business key of this cmic permission.
	 *
	 * @return the cmic business key of this cmic permission
	 */
	@Override
	public String getCmicBusinessKey() {
		return model.getCmicBusinessKey();
	}

	/**
	 * Returns the primary key of this cmic permission.
	 *
	 * @return the primary key of this cmic permission
	 */
	@Override
	public com.churchmutual.core.service.persistence.CMICPermissionPK
		getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cmic permission.
	 *
	 * @return the user ID of this cmic permission
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this cmic permission.
	 *
	 * @return the user uuid of this cmic permission
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cmic permission model instance should use the <code>CMICPermission</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the action ID of this cmic permission.
	 *
	 * @param actionId the action ID of this cmic permission
	 */
	@Override
	public void setActionId(String actionId) {
		model.setActionId(actionId);
	}

	/**
	 * Sets the cmic business key of this cmic permission.
	 *
	 * @param cmicBusinessKey the cmic business key of this cmic permission
	 */
	@Override
	public void setCmicBusinessKey(String cmicBusinessKey) {
		model.setCmicBusinessKey(cmicBusinessKey);
	}

	/**
	 * Sets the primary key of this cmic permission.
	 *
	 * @param primaryKey the primary key of this cmic permission
	 */
	@Override
	public void setPrimaryKey(
		com.churchmutual.core.service.persistence.CMICPermissionPK primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cmic permission.
	 *
	 * @param userId the user ID of this cmic permission
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this cmic permission.
	 *
	 * @param userUuid the user uuid of this cmic permission
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CMICPermissionWrapper wrap(CMICPermission cmicPermission) {
		return new CMICPermissionWrapper(cmicPermission);
	}

}