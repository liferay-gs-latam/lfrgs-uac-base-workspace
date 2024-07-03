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
 * This class is a wrapper for {@link CMICUser}.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICUser
 * @generated
 */
public class CMICUserWrapper
	extends BaseModelWrapper<CMICUser>
	implements CMICUser, ModelWrapper<CMICUser> {

	public CMICUserWrapper(CMICUser cmicUser) {
		super(cmicUser);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cmicUserId", getCmicUserId());
		attributes.put("userId", getUserId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cmicUserId = (Long)attributes.get("cmicUserId");

		if (cmicUserId != null) {
			setCmicUserId(cmicUserId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}
	}

	/**
	 * Returns the cmic user ID of this cmic user.
	 *
	 * @return the cmic user ID of this cmic user
	 */
	@Override
	public long getCmicUserId() {
		return model.getCmicUserId();
	}

	/**
	 * Returns the cmic user uuid of this cmic user.
	 *
	 * @return the cmic user uuid of this cmic user
	 */
	@Override
	public String getCmicUserUuid() {
		return model.getCmicUserUuid();
	}

	/**
	 * Returns the primary key of this cmic user.
	 *
	 * @return the primary key of this cmic user
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cmic user.
	 *
	 * @return the user ID of this cmic user
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this cmic user.
	 *
	 * @return the user uuid of this cmic user
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cmic user model instance should use the <code>CMICUser</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the cmic user ID of this cmic user.
	 *
	 * @param cmicUserId the cmic user ID of this cmic user
	 */
	@Override
	public void setCmicUserId(long cmicUserId) {
		model.setCmicUserId(cmicUserId);
	}

	/**
	 * Sets the cmic user uuid of this cmic user.
	 *
	 * @param cmicUserUuid the cmic user uuid of this cmic user
	 */
	@Override
	public void setCmicUserUuid(String cmicUserUuid) {
		model.setCmicUserUuid(cmicUserUuid);
	}

	/**
	 * Sets the primary key of this cmic user.
	 *
	 * @param primaryKey the primary key of this cmic user
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cmic user.
	 *
	 * @param userId the user ID of this cmic user
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this cmic user.
	 *
	 * @param userUuid the user uuid of this cmic user
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CMICUserWrapper wrap(CMICUser cmicUser) {
		return new CMICUserWrapper(cmicUser);
	}

}