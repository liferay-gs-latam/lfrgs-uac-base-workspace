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

package com.churchmutual.core.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CMICUserLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICUserLocalService
 * @generated
 */
public class CMICUserLocalServiceWrapper
	implements CMICUserLocalService, ServiceWrapper<CMICUserLocalService> {

	public CMICUserLocalServiceWrapper(
		CMICUserLocalService cmicUserLocalService) {

		_cmicUserLocalService = cmicUserLocalService;
	}

	/**
	 * Adds the cmic user to the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUser the cmic user
	 * @return the cmic user that was added
	 */
	@Override
	public com.churchmutual.core.model.CMICUser addCMICUser(
		com.churchmutual.core.model.CMICUser cmicUser) {

		return _cmicUserLocalService.addCMICUser(cmicUser);
	}

	@Override
	public void addCMICUser(long cmicUserId, long userId) {
		_cmicUserLocalService.addCMICUser(cmicUserId, userId);
	}

	@Override
	public void addRecentlyViewedCMICAccount(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.addRecentlyViewedCMICAccount(
			userId, cmicBusinessKey);
	}

	/**
	 * Creates a new cmic user with the primary key. Does not add the cmic user to the database.
	 *
	 * @param cmicUserId the primary key for the new cmic user
	 * @return the new cmic user
	 */
	@Override
	public com.churchmutual.core.model.CMICUser createCMICUser(
		long cmicUserId) {

		return _cmicUserLocalService.createCMICUser(cmicUserId);
	}

	/**
	 * Deletes the cmic user from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUser the cmic user
	 * @return the cmic user that was removed
	 */
	@Override
	public com.churchmutual.core.model.CMICUser deleteCMICUser(
		com.churchmutual.core.model.CMICUser cmicUser) {

		return _cmicUserLocalService.deleteCMICUser(cmicUser);
	}

	/**
	 * Deletes the cmic user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user that was removed
	 * @throws PortalException if a cmic user with the primary key could not be found
	 */
	@Override
	public com.churchmutual.core.model.CMICUser deleteCMICUser(long cmicUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.deleteCMICUser(cmicUserId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deletePortraitImage(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.deletePortraitImage(userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cmicUserLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _cmicUserLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _cmicUserLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _cmicUserLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _cmicUserLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _cmicUserLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.churchmutual.core.model.CMICUser fetchCMICUser(long cmicUserId) {
		return _cmicUserLocalService.fetchCMICUser(cmicUserId);
	}

	@Override
	public com.churchmutual.core.model.CMICUser fetchCMICUserByUserId(
		long userId) {

		return _cmicUserLocalService.fetchCMICUserByUserId(userId);
	}

	@Override
	public com.liferay.portal.kernel.model.User fetchUserByCmicUUID(
			long companyId, String cmicUUID)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.fetchUserByCmicUUID(companyId, cmicUUID);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cmicUserLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICBusinessDisplay>
			getBusinessesWithPermission(long userId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getBusinessesWithPermission(
			userId, actionId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICUserDisplay>
			getBusinessMembers(
				long userId,
				com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getBusinessMembers(
			userId, cmicBusinessKey);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getBusinessRoles(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getBusinessRoles(userId);
	}

	@Override
	public com.churchmutual.core.model.CMICUserDisplay getBusinessUserDetails(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getBusinessUserDetails(
			userId, cmicBusinessKey);
	}

	@Override
	public String[] getBusinessUserPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getBusinessUserPermissions(
			userId, cmicBusinessKey);
	}

	/**
	 * Returns the cmic user with the primary key.
	 *
	 * @param cmicUserId the primary key of the cmic user
	 * @return the cmic user
	 * @throws PortalException if a cmic user with the primary key could not be found
	 */
	@Override
	public com.churchmutual.core.model.CMICUser getCMICUser(long cmicUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getCMICUser(cmicUserId);
	}

	/**
	 * Returns a range of all the cmic users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.churchmutual.core.model.impl.CMICUserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic users
	 * @param end the upper bound of the range of cmic users (not inclusive)
	 * @return the range of cmic users
	 */
	@Override
	public java.util.List<com.churchmutual.core.model.CMICUser> getCMICUsers(
		int start, int end) {

		return _cmicUserLocalService.getCMICUsers(start, end);
	}

	/**
	 * Returns the number of cmic users.
	 *
	 * @return the number of cmic users
	 */
	@Override
	public int getCMICUsersCount() {
		return _cmicUserLocalService.getCMICUsersCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cmicUserLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public String getInsuredThemeSetting(long userId, String themeSettingKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getInsuredThemeSetting(
			userId, themeSettingKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public String getPortraitImageURL(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getPortraitImageURL(userId);
	}

	@Override
	public com.churchmutual.core.constants.ProducerType[]
			getProducerTypesByUserId(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getProducerTypesByUserId(userId);
	}

	@Override
	public java.util.List<com.churchmutual.core.model.CMICAccountEntryDisplay>
			getRecentlyViewedCMICAccountEntryDisplays(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getRecentlyViewedCMICAccountEntryDisplays(
			userId);
	}

	@Override
	public com.churchmutual.core.model.CMICUserDisplay getUserDetails(
			long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getUserDetails(userId);
	}

	@Override
	public String inviteBusinessMembers(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String emailAddresses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.inviteBusinessMembers(
			userId, cmicBusinessKey, emailAddresses);
	}

	@Override
	public boolean isDeactivated(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.isDeactivated(userId);
	}

	@Override
	public boolean isUserRegistered(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.isUserRegistered(userId);
	}

	@Override
	public void updateBusinessMemberPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserPermissionsJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.updateBusinessMemberPermissions(
			userId, cmicBusinessKey, updateUserPermissionsJSONString);
	}

	@Override
	public void updateBusinessMembers(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey,
			String updateUserRolesJSONString, String removeUsersJSONString)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.updateBusinessMembers(
			userId, cmicBusinessKey, updateUserRolesJSONString,
			removeUsersJSONString);
	}

	@Override
	public void updateBusinessPermissions(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.updateBusinessPermissions(userId);
	}

	@Override
	public void updateBusinessPermissions(
			long userId,
			com.churchmutual.core.model.CMICBusinessKey cmicBusinessKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.updateBusinessPermissions(
			userId, cmicBusinessKey);
	}

	/**
	 * Updates the cmic user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param cmicUser the cmic user
	 * @return the cmic user that was updated
	 */
	@Override
	public com.churchmutual.core.model.CMICUser updateCMICUser(
		com.churchmutual.core.model.CMICUser cmicUser) {

		return _cmicUserLocalService.updateCMICUser(cmicUser);
	}

	@Override
	public String updatePortraitImage(long userId, String imageFileString)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.updatePortraitImage(
			userId, imageFileString);
	}

	@Override
	public String validateInsuredUserRegistration(
			long userId, long cmicUserId, String accountNumber,
			String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.validateInsuredUserRegistration(
			userId, cmicUserId, accountNumber, registrationCode, zipCode);
	}

	@Override
	public String validateProducerUserRegistration(
			long userId, long cmicUserId, String agentNumber,
			String divisionNumber, String registrationCode, String zipCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.validateProducerUserRegistration(
			userId, cmicUserId, agentNumber, divisionNumber, registrationCode,
			zipCode);
	}

	@Override
	public com.churchmutual.core.model.CMICUserDisplay
			validateUserRegistrationCode(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.validateUserRegistrationCode(
			registrationCode);
	}

	@Override
	public CMICUserLocalService getWrappedService() {
		return _cmicUserLocalService;
	}

	@Override
	public void setWrappedService(CMICUserLocalService cmicUserLocalService) {
		_cmicUserLocalService = cmicUserLocalService;
	}

	private CMICUserLocalService _cmicUserLocalService;

}