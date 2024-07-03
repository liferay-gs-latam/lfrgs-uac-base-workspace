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

package com.churchmutual.core.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Kayleen Lim
 * @generated
 */
public class CMICPermissionPK
	implements Comparable<CMICPermissionPK>, Serializable {

	public long userId;
	public String cmicBusinessKey;
	public String actionId;

	public CMICPermissionPK() {
	}

	public CMICPermissionPK(
		long userId, String cmicBusinessKey, String actionId) {

		this.userId = userId;
		this.cmicBusinessKey = cmicBusinessKey;
		this.actionId = actionId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCmicBusinessKey() {
		return cmicBusinessKey;
	}

	public void setCmicBusinessKey(String cmicBusinessKey) {
		this.cmicBusinessKey = cmicBusinessKey;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	@Override
	public int compareTo(CMICPermissionPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (userId < pk.userId) {
			value = -1;
		}
		else if (userId > pk.userId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = cmicBusinessKey.compareTo(pk.cmicBusinessKey);

		if (value != 0) {
			return value;
		}

		value = actionId.compareTo(pk.actionId);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CMICPermissionPK)) {
			return false;
		}

		CMICPermissionPK pk = (CMICPermissionPK)obj;

		if ((userId == pk.userId) &&
			cmicBusinessKey.equals(pk.cmicBusinessKey) &&
			actionId.equals(pk.actionId)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, userId);
		hashCode = HashUtil.hash(hashCode, cmicBusinessKey);
		hashCode = HashUtil.hash(hashCode, actionId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("{");

		sb.append("userId=");

		sb.append(userId);
		sb.append(", cmicBusinessKey=");

		sb.append(cmicBusinessKey);
		sb.append(", actionId=");

		sb.append(actionId);

		sb.append("}");

		return sb.toString();
	}

}