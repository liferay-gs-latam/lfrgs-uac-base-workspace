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

package com.churchmutual.content.setup.upgrade.util.v2_0_0;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.content.setup.upgrade.util.common.BaseSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.common.InsuredUpgradeConstants;
import com.churchmutual.content.setup.upgrade.util.common.ProducerUpgradeConstants;
import com.churchmutual.content.setup.upgrade.util.insured.InsuredDashboardPage;
import com.churchmutual.content.setup.upgrade.util.insured.InsuredPaymentPage;
import com.churchmutual.content.setup.upgrade.util.insured.InsuredPoliciesPage;
import com.churchmutual.content.setup.upgrade.util.insured.InsuredPolicyDetailsPage;
import com.churchmutual.content.setup.upgrade.util.insured.InsuredProfilePage;
import com.churchmutual.content.setup.upgrade.util.insured.InsuredResourcesPage;
import com.churchmutual.content.setup.upgrade.util.producer.ProfileLinks;
import com.churchmutual.core.service.CMICUserLocalService;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Kayleen Lim
 */
public class UpdateInsuredSiteUpgradeProcess extends BaseSiteUpgradeProcess {

	public UpdateInsuredSiteUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService, ClassNameLocalService classNameLocalService, CMICUserLocalService cmicUserLocalService,
		CompanyLocalService companyLocalService, DDM ddm, DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService, DLAppService dlAppService,
		DLFileEntryLocalService dlFileEntryLocalService, DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		ExpandoColumnLocalService expandoColumnLocalService, ExpandoTableLocalService expandoTableLocalService,
		FragmentCollectionContributorTracker fragmentCollectionContributorTracker,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService, GroupLocalService groupLocalService,
		JournalArticleLocalService journalArticleLocalService, LayoutLocalService layoutLocalService, LayoutPageTemplateStructureLocalService layoutPageTemplateStructureLocalService,
		LayoutSetLocalService layoutSetLocalService, PermissionCheckerFactory permissionCheckerFactory, Portal portal,
		RoleLocalService roleLocalService, StorageEngineManager storageEngineManager, UserLocalService userLocalService,
		VirtualHostLocalService virtualHostLocalService) {

		super(
			assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
			ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService,
			expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService,
			layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager,
			userLocalService, virtualHostLocalService);

		this.classNameLocalService = classNameLocalService;
		this.fragmentCollectionContributorTracker = fragmentCollectionContributorTracker;
		this.fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		this.layoutLocalService = layoutLocalService;
		this.layoutPageTemplateStructureLocalService = layoutPageTemplateStructureLocalService;
		this.portal = portal;
		this.userLocalService = userLocalService;
	}

	protected void addPrivatePages(long companyId, long userId, long groupId) throws Exception {
		InsuredDashboardPage.addPage(companyId, userId, groupId);
		InsuredPoliciesPage.addPage(companyId, userId, groupId);
		InsuredPolicyDetailsPage.addPage(companyId, userId, groupId);
		InsuredResourcesPage.addPage(companyId, userId, groupId);
		InsuredPaymentPage.addPage(companyId, userId, groupId);
		InsuredProfilePage.addPage(companyId, userId, groupId);
		ProfileLinks.addLinks(companyId, userId, groupId);
	}

	protected void addResourceContent(long companyId, long groupId, long userId) throws Exception {
		Layout resourcesLayout = layoutLocalService.getFriendlyURLLayout(
			groupId, true, LayoutURLKeyConstants.LAYOUT_FURL_INSURED_RESOURCES);

		for (String[] subpage : InsuredResourcesPage.RESOURCE_SUBPAGES) {
			Layout layout = layoutLocalService.getFriendlyURLLayout(groupId, true, subpage[0]);

			String partnerCardFragmentKey = _getCMICFragmentKey(_COLLECTION_CMIC, _PARTNER_CARD);

			_addPageComponent(
				groupId, userId, resourcesLayout.getPlid(), subpage[2], _PARTNER_CARD_JSON, partnerCardFragmentKey);

			String heroFragmentKey = _getCMICFragmentKey(_COLLECTION_CMIC, _HERO);
			String benefitsFragmentKey = _getCMICFragmentKey(_COLLECTION_CMIC, _BENEFITS);
			String infoFragmentKey = _getCMICFragmentKey(_COLLECTION_CMIC, _INFO);

			_addPageSection(groupId, userId, layout.getPlid(), subpage[2], _HERO_JSON, heroFragmentKey);
			_addPageSection(groupId, userId, layout.getPlid(), subpage[2], _BENEFITS_JSON, benefitsFragmentKey);
			_addPageSection(groupId, userId, layout.getPlid(), subpage[2], _INFO_JSON, infoFragmentKey);

			_addAssetPublisher(companyId, groupId, userId, layout.getPlid(), subpage[1]);

			_publishLayout(layout);
		}

		_publishLayout(resourcesLayout);
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long insuredPortalGroupId = addOrUpdatePortalSite(
			companyId, CMICSite.INSURED.getName(), CMICSite.INSURED.getFriendlyURL(),
			LayoutURLKeyConstants.THEME_ID_CMIC_INSURED);

		long userId = userLocalService.getDefaultUserId(companyId);

		addJournalArticle(insuredPortalGroupId, InsuredUpgradeConstants.FILE_A_CLAIM_WEB_CONTENT_TITLE);

		addJournalArticle(insuredPortalGroupId, InsuredUpgradeConstants.PAYMENT_WEB_CONTENT_TITLE);

		addPrivatePages(companyId, userId, insuredPortalGroupId);

		addResourceContent(companyId, insuredPortalGroupId, userId);

		setLayoutScript(
			insuredPortalGroupId, true, LayoutURLKeyConstants.LAYOUT_FURL_INSURED_DASHBOARD,
			InsuredUpgradeConstants.SURVEY_MONKEY);

		Company company = companyLocalService.getCompany(companyId);

		long globalGroupId = company.getGroupId();

		updateDDMTemplate(
			globalGroupId, ProducerUpgradeConstants.CMIC_RESOURCE_FILES, portal.getClassNameId(AssetEntry.class),
			ProducerUpgradeConstants.CMIC_RESOURCE_FILES_TEMPLATE_KEY);

		long dlFileEntryTypeId = updateDLFileEntryType(
			globalGroupId, ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT,
			ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT_FILE_TYPE);

		addDLFolder(
			insuredPortalGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			InsuredUpgradeConstants.INSURED_PARTNER_RESOURCE_FOLDER_NAME);

		_addDLFileEntries(insuredPortalGroupId, globalGroupId, dlFileEntryTypeId);
	}

	protected ClassNameLocalService classNameLocalService;
	protected FragmentCollectionContributorTracker fragmentCollectionContributorTracker;
	protected FragmentEntryLinkLocalService fragmentEntryLinkLocalService;
	protected LayoutLocalService layoutLocalService;
	protected LayoutPageTemplateStructureLocalService layoutPageTemplateStructureLocalService;
	protected Portal portal;
	protected UserLocalService userLocalService;

	/**
	 * Adds an Asset Publisher Widget to Content Page
	 */
	private void _addAssetPublisher(long companyId, long groupId, long userId, long layoutPlid, String layoutName)
		throws Exception {

		JSONObject editableValueJSONObject = JSONFactoryUtil.createJSONObject();

		String assetPublisherPortletId = PortletIdCodec.encode(AssetPublisherPortletKeys.ASSET_PUBLISHER);

		String instanceId = PortletIdCodec.decodeInstanceId(assetPublisherPortletId);

		editableValueJSONObject.put(
			"instanceId", instanceId
		).put(
			"portletId", AssetPublisherPortletKeys.ASSET_PUBLISHER
		);

		InsuredResourcesPage.setAssetPublisher(
			companyId, layoutPlid, assetPublisherPortletId, ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT_FILE_TYPE,
			ProducerUpgradeConstants.CMIC_RESOURCE_FILES_TEMPLATE_KEY,
			InsuredUpgradeConstants.DDM_STRUCTURE_FIELD_NAME_PARTNER_RESOURCE, layoutName);

		fragmentEntryLinkLocalService.addFragmentEntryLink(
			userId, groupId, 0, 0, portal.getClassNameId(Layout.class), layoutPlid, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, editableValueJSONObject.toString(), StringPool.BLANK, 0, null,
			new ServiceContext());

		Layout draftLayout;

		// Wait for async creation of draft page then also add Asset Publisher widget to the draft page, otherwise it is only added to the published page.

		do {
			draftLayout = layoutLocalService.fetchLayout(PortalUtil.getClassNameId(Layout.class), layoutPlid);
		}
		while (Validator.isNull(draftLayout));

		InsuredResourcesPage.setAssetPublisher(
			companyId, draftLayout.getPlid(), assetPublisherPortletId,
			ProducerUpgradeConstants.WIDEN_FILE_SHORTCUT_FILE_TYPE,
			ProducerUpgradeConstants.CMIC_RESOURCE_FILES_TEMPLATE_KEY,
			InsuredUpgradeConstants.DDM_STRUCTURE_FIELD_NAME_PARTNER_RESOURCE, layoutName);

		fragmentEntryLinkLocalService.addFragmentEntryLink(
			userId, groupId, 0, 0, portal.getClassNameId(Layout.class), draftLayout.getPlid(), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, editableValueJSONObject.toString(), StringPool.BLANK,
			0, null, new ServiceContext());
	}

	private void _addDLFileEntries(long groupId, long fileEntryTypeGroupId, long dlFileEntryTypeId) throws Exception {
		final Bundle bundle = FrameworkUtil.getBundle(getClass());

		Enumeration<String> filePaths = bundle.getEntryPaths(
			DL_FILE_ENTRY_DIR + StringPool.SLASH + InsuredUpgradeConstants.INSURED_PARTNER_RESOURCE_FOLDER_NAME);

		if ((filePaths == null) || !filePaths.hasMoreElements()) {
			return;
		}

		while (filePaths.hasMoreElements()) {
			String path = filePaths.nextElement();

			DLFileEntryType dlFileEntryType = dlFileEntryTypeLocalService.getFileEntryType(dlFileEntryTypeId);

			String fileEntryTypeKey = DLUtil.getDDMStructureKey(dlFileEntryType.getUuid());

			DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
				fileEntryTypeGroupId, portal.getClassNameId(DLFileEntryMetadata.class), fileEntryTypeKey);

			long companyId = portal.getDefaultCompanyId();

			long userId = userLocalService.getDefaultUserId(companyId);

			// Add empty files with populated metadata fields

			InputStream stream = getClass().getResourceAsStream(StringPool.SLASH + path);

			String fileMetadataString = StringUtil.read(stream);

			JSONArray fileMetadataJSON = JSONFactoryUtil.createJSONArray(fileMetadataString);

			ServiceContext serviceContext = getDefaultServiceContext(companyId);

			serviceContext.setScopeGroupId(groupId);
			serviceContext.setAddGroupPermissions(true);

			Folder folder = dlAppService.getFolder(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				InsuredUpgradeConstants.INSURED_PARTNER_RESOURCE_FOLDER_NAME);

			long parentFolderId = folder.getFolderId();

			Folder childFolder = addDLFolder(groupId, parentFolderId, _extractSourceFileName(path, true));

			long folderId = childFolder.getFolderId();

			for (int i = 0; i < fileMetadataJSON.length(); i++) {
				JSONObject fileMetadata = fileMetadataJSON.getJSONObject(i);

				Map<String, DDMFormValues> ddmFormValuesMap = createDDMFormValuesMap(
					groupId, ddmStructure.getStructureId(), ddmStructure.getStructureKey(), fileMetadata,
					serviceContext);

				File file = new File(TEMP);

				file.createNewFile();

				String sourceFileName = _extractSourceFileName(path, false);

				DLFileEntry dlFileEntry = dlFileEntryLocalService.addFileEntry(
					userId, groupId, groupId, folderId, sourceFileName, ContentTypes.APPLICATION_PDF,
					fileMetadata.getString(TITLE), null, null, dlFileEntryType.getFileEntryTypeId(), ddmFormValuesMap,
					file, null, 0, serviceContext);

				DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

				dlFileEntryLocalService.updateStatus(
					userId, dlFileVersion.getFileVersionId(), WorkflowConstants.STATUS_APPROVED, serviceContext,
					new HashMap<>());

				assetEntryLocalService.updateEntry(
					dlFileEntry.getUserId(), dlFileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
					dlFileEntry.getPrimaryKey(), null, null);
			}
		}
	}

	private FragmentEntryLink _addPageFragment(
			long groupId, long userId, long layoutPlid, String partnerName, String fileName, String fragmentKey)
		throws Exception {

		FragmentEntry fragmentEntry = fragmentCollectionContributorTracker.getFragmentEntry(fragmentKey);

		if (fragmentEntry != null) {
			InputStream stream = UpdateInsuredSiteUpgradeProcess.class.getResourceAsStream(fileName);

			String fragmentString = StringUtil.read(stream);

			JSONArray fragmentJSONArray = JSONFactoryUtil.createJSONArray(fragmentString);

			String configuration = null;
			String editableValues = null;

			for (int i = 0; i < fragmentJSONArray.length(); ++i) {
				JSONObject fragmentJSON = fragmentJSONArray.getJSONObject(i);

				if (partnerName.equals(fragmentJSON.getString("partnerName"))) {
					if (fragmentJSON.has("configuration")) {
						configuration = fragmentJSON.getJSONObject(
							"configuration"
						).toString();
					}
					else {
						configuration = fragmentEntry.getConfiguration();
					}

					editableValues = fragmentJSON.getJSONObject(
						"editableValues"
					).toString();

					break;
				}
			}

			return fragmentEntryLinkLocalService.addFragmentEntryLink(
				userId, groupId, 0, 0, portal.getClassNameId(Layout.class), layoutPlid, fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(), configuration, editableValues, StringPool.BLANK, 0,
				StringPool.BLANK, new ServiceContext());
		}

		return null;
	}

	private void _addPageComponent(
			long groupId, long userId, long layoutPlid, String partnerName, String fileName, String fragmentKey)
		throws Exception {

		FragmentEntryLink fragmentEntryLink = _addPageFragment(groupId, userId, layoutPlid, partnerName, fileName, fragmentKey);

		long layoutClassNameId = classNameLocalService.getClassNameId(Layout.class.getName());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructureLocalService.fetchLayoutPageTemplateStructure(
				groupId, layoutClassNameId, layoutPlid);

		String data = _getPartnerCardLayoutJSON(layoutPageTemplateStructure, partnerName, fragmentEntryLink);

		if (layoutPageTemplateStructure == null) {
			layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
				userId, groupId, layoutClassNameId, layoutPlid, data, new ServiceContext());
		}
		else {
			layoutPageTemplateStructureLocalService.updateLayoutPageTemplateStructure(
				groupId, layoutClassNameId, layoutPlid, _SEGMENTS_EXPERIENCE_ID_DEFAULT, data);
		}

		Layout draftLayout;

		// Wait for async creation of draft page then also add fragment to the draft page, otherwise it is only added to the published page.

		do {
			draftLayout = layoutLocalService.fetchLayout(PortalUtil.getClassNameId(Layout.class), layoutPlid);
		}
		while (Validator.isNull(draftLayout));

		long draftLayoutPlid = draftLayout.getPlid();

		FragmentEntryLink draftFragmentEntryLink = _addPageFragment(groupId, userId, draftLayoutPlid, partnerName, fileName, fragmentKey);

		LayoutPageTemplateStructure draftLayoutPageTemplateStructure =
			layoutPageTemplateStructureLocalService.fetchLayoutPageTemplateStructure(
				groupId, layoutClassNameId, draftLayoutPlid);

		String draftData = _getPartnerCardLayoutJSON(
			draftLayoutPageTemplateStructure, partnerName, draftFragmentEntryLink);

		if (layoutPageTemplateStructure == null) {
			layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
				userId, groupId, layoutClassNameId, draftLayoutPlid, draftData, new ServiceContext());
		}
		else {
			layoutPageTemplateStructureLocalService.updateLayoutPageTemplateStructure(
				groupId, layoutClassNameId, draftLayoutPlid, _SEGMENTS_EXPERIENCE_ID_DEFAULT, draftData);
		}
	}

	private String _getPartnerCardLayoutJSON(
			LayoutPageTemplateStructure layoutPageTemplateStructure, String partnerName,
			FragmentEntryLink fragmentEntryLink)
		throws Exception {
		JSONObject partnerCardLayoutJSONObject = null;

		if (layoutPageTemplateStructure != null) {
			String data = layoutPageTemplateStructure.getData(_SEGMENTS_EXPERIENCE_ID_DEFAULT);

			data = StringUtil.replace(
				data, StringPool.PERCENT + partnerName + StringPool.PERCENT, String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()));

			partnerCardLayoutJSONObject = JSONFactoryUtil.createJSONObject(data);
		}
		else {
			InputStream partnerCardLayoutJSONStream = UpdateInsuredSiteUpgradeProcess.class.getResourceAsStream(_PARTNER_CARD_LAYOUT_JSON);
			String partnerCardLayoutString = StringUtil.read(partnerCardLayoutJSONStream);

			partnerCardLayoutString = StringUtil.replace(
				partnerCardLayoutString, StringPool.PERCENT + partnerName + StringPool.PERCENT, String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()));

			partnerCardLayoutJSONObject = JSONFactoryUtil.createJSONObject(partnerCardLayoutString);
		}

		return partnerCardLayoutJSONObject.toString();
	}

	private void _addPageSection(
			long groupId, long userId, long layoutPlid, String partnerName, String fileName, String fragmentKey)
		throws Exception {

		_addPageFragment(groupId, userId, layoutPlid, partnerName, fileName, fragmentKey);

		Layout draftLayout;

		// Wait for async creation of draft page then also add fragment to the draft page, otherwise it is only added to the published page.

		do {
			draftLayout = layoutLocalService.fetchLayout(PortalUtil.getClassNameId(Layout.class), layoutPlid);
		}
		while (Validator.isNull(draftLayout));

		_addPageFragment(groupId, userId, draftLayout.getPlid(), partnerName, fileName, fragmentKey);
	}

	private String _extractSourceFileName(String filePath, boolean removeFileExtension) {
		String sourceFileName = StringUtil.extractLast(filePath, StringPool.SLASH);

		if (!removeFileExtension) {
			return sourceFileName;
		}

		return StringUtil.split(sourceFileName, StringPool.PERIOD)[0];
	}

	private String _getCMICFragmentKey(String collectionName, String fragmentName) {
		return collectionName + StringPool.DASH + fragmentName;
	}

	private void _publishLayout(Layout layout) throws PortalException {
		Layout draftLayout = layoutLocalService.fetchLayout(portal.getClassNameId(Layout.class), layout.getPlid());

		UnicodeProperties typeSettingsProperties =
			draftLayout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty("published", "true");

		layoutLocalService.updateLayout(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(), typeSettingsProperties.toString());

		layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId(), new Date());
	}

	private static final String _BENEFITS = "benefits";

	private static final String _BENEFITS_JSON = "/fragment-entry-link/benefits.json";

	private static final String _COLLECTION_CMIC = "cmic";

	private static final String _HERO = "hero";

	private static final String _HERO_JSON = "/fragment-entry-link/hero.json";

	private static final String _INFO = "info";

	private static final String _INFO_JSON = "/fragment-entry-link/info.json";

	private static final String _PARTNER_CARD = "partner-card";

	private static final String _PARTNER_CARD_JSON = "/fragment-entry-link/partner-card.json";

	private static final String _PARTNER_CARD_LAYOUT_JSON = "/layout-page-template-structure/partner-card.json";

	private static final long _SEGMENTS_EXPERIENCE_ID_DEFAULT = 0;

}