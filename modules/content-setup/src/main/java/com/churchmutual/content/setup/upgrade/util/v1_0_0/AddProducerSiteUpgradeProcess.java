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

import com.churchmutual.commons.constants.ExpandoConstants;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.content.setup.upgrade.util.common.BaseSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.common.ProducerUpgradeConstants;
import com.churchmutual.content.setup.upgrade.util.producer.ProducerAccountDetailsPage;
import com.churchmutual.content.setup.upgrade.util.producer.ProducerAccountsPage;
import com.churchmutual.content.setup.upgrade.util.producer.ProducerContactPage;
import com.churchmutual.content.setup.upgrade.util.producer.ProducerDashboardPage;
import com.churchmutual.content.setup.upgrade.util.producer.ProducerPolicyDetailsPage;
import com.churchmutual.content.setup.upgrade.util.producer.ProfileLinks;
import com.churchmutual.content.setup.upgrade.util.producer.ProducerProfilePage;
import com.churchmutual.content.setup.upgrade.util.producer.ProducerResourcesPage;
import com.churchmutual.core.service.CMICUserLocalService;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

/**
 * @author Matthew Chan
 */
public class AddProducerSiteUpgradeProcess extends BaseSiteUpgradeProcess {

	public AddProducerSiteUpgradeProcess(
			AssetEntryLocalService assetEntryLocalService, CMICUserLocalService cmicUserLocalService,
			CompanyLocalService companyLocalService, DDM ddm, DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService, DLAppService dlAppService, DLFileEntryLocalService dlFileEntryLocalService,
			DLFileEntryTypeLocalService dlFileEntryTypeLocalService, ExpandoColumnLocalService expandoColumnLocalService,
			ExpandoTableLocalService expandoTableLocalService, GroupLocalService groupLocalService,
			JournalArticleLocalService journalArticleLocalService, LayoutSetLocalService layoutSetLocalService,
			PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService,
			StorageEngineManager storageEngineManager, UserLocalService userLocalService,
			VirtualHostLocalService virtualHostLocalService) {

		super(
			assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
			ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService,
			expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService,
			layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager,
			userLocalService, virtualHostLocalService);

		this.portal = portal;
		this.userLocalService = userLocalService;
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long producerPortalGroupId = addOrUpdatePortalSite(
			companyId, CMICSite.PRODUCER.getName(), CMICSite.PRODUCER.getFriendlyURL(),
			LayoutURLKeyConstants.THEME_ID_CMIC_PRODUCER);

		long userId = userLocalService.getDefaultUserId(companyId);

		addJournalArticle(producerPortalGroupId, ProducerUpgradeConstants.PRODUCER_CONTACT_US_WEB_CONTENT_TITLE);

		Company company = companyLocalService.getCompany(companyId);

		long globalGroupId = company.getGroupId();

		addDLFileEntryType(
			globalGroupId, ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT,
			ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT_FILE_TYPE);

		addDLFileEntries(
			producerPortalGroupId, ProducerUpgradeConstants.WIDEN_FILE_ENTRIES, globalGroupId,
			ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT_FILE_TYPE);

		addDDMTemplate(
			globalGroupId, ProducerUpgradeConstants.CMIC_RESOURCE_FILES, portal.getClassNameId(AssetEntry.class),
			portal.getClassNameId(PortletDisplayTemplate.class),
			ProducerUpgradeConstants.CMIC_RESOURCE_FILES_TEMPLATE_KEY);

		UnicodeProperties properties = new UnicodeProperties();

		properties.put(ExpandoColumnConstants.PROPERTY_HIDDEN, "true");

		addExpandoColumn(
			companyId, User.class.getName(), ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNTS,
			ExpandoColumnConstants.STRING, properties, null);

		addExpandoColumn(
			companyId, User.class.getName(), ExpandoConstants.CMIC_EMPLOYEE, ExpandoColumnConstants.BOOLEAN, properties,
			Boolean.FALSE);

		_addPrivatePages(companyId, userId, producerPortalGroupId);
	}

	protected Portal portal;
	protected UserLocalService userLocalService;

	private void _addPrivatePages(long companyId, long userId, long groupId) throws Exception {
		ProducerDashboardPage.addPage(companyId, userId, groupId);
		ProducerAccountsPage.addPage(companyId, userId, groupId);
		ProducerAccountDetailsPage.addPage(companyId, userId, groupId);
		ProducerPolicyDetailsPage.addPage(companyId, userId, groupId);
		ProducerResourcesPage.addPage(companyId, userId, groupId);
		ProducerContactPage.addPage(userId, groupId);
		ProducerProfilePage.addPage(companyId, userId, groupId);
		ProfileLinks.addLinks(companyId, userId, groupId);
	}

}