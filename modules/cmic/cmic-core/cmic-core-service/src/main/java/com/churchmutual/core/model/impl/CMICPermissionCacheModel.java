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

package com.churchmutual.core.model.impl;

import com.churchmutual.core.model.CMICPermission;
import com.churchmutual.core.service.persistence.CMICPermissionPK;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CMICPermission in entity cache.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICPermissionCacheModel
	implements CacheModel<CMICPermission>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CMICPermissionCacheModel)) {
			return false;
		}

		CMICPermissionCacheModel cmicPermissionCacheModel =
			(CMICPermissionCacheModel)obj;

		if (cmicPermissionPK.equals(
				cmicPermissionCacheModel.cmicPermissionPK)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, cmicPermissionPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{userId=");
		sb.append(userId);
		sb.append(", cmicBusinessKey=");
		sb.append(cmicBusinessKey);
		sb.append(", actionId=");
		sb.append(actionId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CMICPermission toEntityModel() {
		CMICPermissionImpl cmicPermissionImpl = new CMICPermissionImpl();

		cmicPermissionImpl.setUserId(userId);

		if (cmicBusinessKey == null) {
			cmicPermissionImpl.setCmicBusinessKey("");
		}
		else {
			cmicPermissionImpl.setCmicBusinessKey(cmicBusinessKey);
		}

		if (actionId == null) {
			cmicPermissionImpl.setActionId("");
		}
		else {
			cmicPermissionImpl.setActionId(actionId);
		}

		cmicPermissionImpl.resetOriginalValues();

		return cmicPermissionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		userId = objectInput.readLong();
		cmicBusinessKey = objectInput.readUTF();
		actionId = objectInput.readUTF();

		cmicPermissionPK = new CMICPermissionPK(
			userId, cmicBusinessKey, actionId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(userId);

		if (cmicBusinessKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cmicBusinessKey);
		}

		if (actionId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(actionId);
		}
	}

	public long userId;
	public String cmicBusinessKey;
	public String actionId;
	public transient CMICPermissionPK cmicPermissionPK;

}