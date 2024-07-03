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

package com.churchmutual.content.setup.upgrade.util.common;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.content.setup.upgrade.util.v1_0_0.AddProducerSiteUpgradeProcess;
import com.churchmutual.core.service.CMICUserLocalService;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
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
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Luiz Marins
 */
public abstract class BaseSiteUpgradeProcess extends BaseAdminUpgradeProcess {

	public BaseSiteUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService, CMICUserLocalService cmicUserLocalService,
		CompanyLocalService companyLocalService, DDM ddm, DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService, DLAppService dlAppService,
		DLFileEntryLocalService dlFileEntryLocalService, DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		ExpandoColumnLocalService expandoColumnLocalService,
		ExpandoTableLocalService expandoTableLocalService, GroupLocalService groupLocalService,
		JournalArticleLocalService journalArticleLocalService, LayoutSetLocalService layoutSetLocalService,
		PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService,
		StorageEngineManager storageEngineManager, UserLocalService userLocalService,
		VirtualHostLocalService virtualHostLocalService) {

		super(permissionCheckerFactory, portal, roleLocalService, userLocalService);

		this.assetEntryLocalService = assetEntryLocalService;
		this.cmicUserLocalService = cmicUserLocalService;
		this.companyLocalService = companyLocalService;
		this.ddm = ddm;
		this.ddmStructureLocalService = ddmStructureLocalService;
		this.ddmTemplateLocalService = ddmTemplateLocalService;
		this.dlAppService = dlAppService;
		this.dlFileEntryLocalService = dlFileEntryLocalService;
		this.dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		this.expandoColumnLocalService = expandoColumnLocalService;
		this.expandoTableLocalService = expandoTableLocalService;
		this.groupLocalService = groupLocalService;
		this.journalArticleLocalService = journalArticleLocalService;
		this.layoutSetLocalService = layoutSetLocalService;
		this.storageEngineManager = storageEngineManager;
		this.userLocalService = userLocalService;
		this.virtualHostLocalService = virtualHostLocalService;
	}

	protected void addDDMTemplate(
			long groupId, String title, long classNameId, long resourceClassNameId, String ddmTemplateKey)
		throws Exception {

		long companyId = portal.getDefaultCompanyId();

		long userId = userLocalService.getDefaultUserId(companyId);

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setAddGroupPermissions(true);

		// Add asset publisher display template

		Map<Locale, String> titleMap = _getTitleMap(groupId, title);
		Map<Locale, String> descriptionMap = new HashMap<>();

		String language = TemplateConstants.LANG_TYPE_FTL;

		String fileName = String.format("%s/%s.%s", _DDM_TEMPLATE_DIR, title, TemplateConstants.LANG_TYPE_FTL);

		InputStream stream = BaseSiteUpgradeProcess.class.getResourceAsStream(fileName);

		String script = StringUtil.read(stream);

		DDMTemplate ddmTemplate = ddmTemplateLocalService.addTemplate(
			userId, groupId, classNameId, 0, resourceClassNameId, titleMap, descriptionMap,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK, language, script, serviceContext);

		ddmTemplate.setTemplateKey(ddmTemplateKey);

		ddmTemplateLocalService.updateDDMTemplate(ddmTemplate);
	}

	protected void addDLFileEntries(long groupId, String title, long fileEntryTypeGroupId, String fileEntryTypeKey)
		throws Exception {

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			fileEntryTypeGroupId, portal.getClassNameId(DLFileEntryMetadata.class), fileEntryTypeKey);

		DLFileEntryType dlFileEntryType = dlFileEntryTypeLocalService.getFileEntryType(
			fileEntryTypeGroupId, fileEntryTypeKey);

		long companyId = portal.getDefaultCompanyId();

		long userId = userLocalService.getDefaultUserId(companyId);

		// Add empty files with populated metadata fields

		String fileName = String.format("%s/%s.%s", DL_FILE_ENTRY_DIR, title, TemplateConstants.LANG_TYPE_JSON);

		InputStream stream = BaseSiteUpgradeProcess.class.getResourceAsStream(fileName);

		String fileMetadataString = StringUtil.read(stream);

		JSONArray fileMetadataJSON = JSONFactoryUtil.createJSONArray(fileMetadataString);

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setAddGroupPermissions(true);

		for (int i = 0; i < fileMetadataJSON.length(); i++) {
			JSONObject fileMetadata = fileMetadataJSON.getJSONObject(i);

			Map<String, DDMFormValues> ddmFormValuesMap = createDDMFormValuesMap(
				groupId, ddmStructure.getStructureId(), ddmStructure.getStructureKey(), fileMetadata, serviceContext);

			File file = new File(TEMP);

			file.createNewFile();

			DLFileEntry dlFileEntry = dlFileEntryLocalService.addFileEntry(
				userId, groupId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				ContentTypes.APPLICATION_PDF, fileMetadata.getString(TITLE), null, null,
				dlFileEntryType.getFileEntryTypeId(), ddmFormValuesMap, file, null, 0, serviceContext);

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			dlFileEntryLocalService.updateStatus(
				userId, dlFileVersion.getFileVersionId(), WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<>());

			assetEntryLocalService.updateEntry(
				dlFileEntry.getUserId(), dlFileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
				dlFileEntry.getPrimaryKey(), null, null);
		}
	}

	protected void addDLFileEntryType(long groupId, String title, String fileEntryTypeKey) throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long userId = userLocalService.getDefaultUserId(companyId);

		// Add template structure for file type metadata

		Map<Locale, String> titleMap = _getTitleMap(groupId, title);
		Map<Locale, String> descriptionMap = new HashMap<>();

		String fileName = String.format("%s/%s.%s", _DDM_STRUCTURE_DIR, title, TemplateConstants.LANG_TYPE_JSON);

		InputStream stream = BaseSiteUpgradeProcess.class.getResourceAsStream(fileName);

		String fileTypeDefinition = StringUtil.read(stream);

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setAddGroupPermissions(true);

		DDMStructure ddmStructure = ddmStructureLocalService.addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			portal.getClassNameId(DLFileEntryMetadata.class), fileEntryTypeKey, titleMap, descriptionMap,
			fileTypeDefinition, TemplateConstants.LANG_TYPE_XML, serviceContext);

		dlFileEntryTypeLocalService.addFileEntryType(
			userId, groupId, fileEntryTypeKey, titleMap, descriptionMap, new long[] {ddmStructure.getStructureId()},
			serviceContext);
	}

	protected Folder addDLFolder(long groupId, long parentFolderId, String name) throws PortalException {
		long companyId = portal.getDefaultCompanyId();

		try {
			return dlAppService.getFolder(groupId, parentFolderId, name);
		}
		catch (PortalException pe) {
			ServiceContext serviceContext = getDefaultServiceContext(companyId);

			serviceContext.setScopeGroupId(groupId);
			serviceContext.setAddGroupPermissions(true);

			return dlAppService.addFolder(groupId, parentFolderId, name, StringPool.BLANK, serviceContext);
		}
	}

	protected void addExpandoColumn(
			long companyId, String className, String columnName, int dataType, UnicodeProperties properties,
			Object defaultData)
		throws PortalException {

		ExpandoTable expandoTable = expandoTableLocalService.fetchTable(
			companyId, portal.getClassNameId(className), ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (expandoTable == null) {
			expandoTable = expandoTableLocalService.addTable(
				companyId, className, ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		ExpandoColumn expandoColumn = expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), columnName, dataType, defaultData);

		expandoColumn.setTypeSettingsProperties(properties);

		expandoColumnLocalService.updateExpandoColumn(expandoColumn);

		if (_log.isInfoEnabled()) {
			_log.info(String.format("Added an expando column for className, %s, with name %s", className, columnName));
		}
	}

	protected Group addOrFetchGroup(long companyId, String name, String description, int type, String friendlyURL)
		throws PortalException {

		Group group = groupLocalService.fetchFriendlyURLGroup(companyId, friendlyURL);

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		if (group == null) {
			return groupLocalService.addGroup(
				userLocalService.getDefaultUserId(companyId), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0, 0, nameMap,
				descriptionMap, type, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, true, true,
				serviceContext);
		}

		return group;
	}

	protected void addJournalArticle(long groupId, String title) throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long userId = userLocalService.getDefaultUserId(companyId);

		// Add template for basic web content structure

		String ddmStructureKey = "BASIC-WEB-CONTENT";

		long journalArticleClassNameId = portal.getClassNameId(JournalArticle.class);

		Company company = companyLocalService.getCompanyByMx(PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group globalGroup = company.getGroup();

		long globalGroupId = globalGroup.getGroupId();

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			globalGroupId, journalArticleClassNameId, ddmStructureKey);

		long classPK = ddmStructure.getStructureId();

		Map<Locale, String> titleMap = _getTitleMap(groupId, title);
		Map<Locale, String> descriptionMap = new HashMap<>();
		String language = TemplateConstants.LANG_TYPE_FTL;

		String fileName = String.format("%s/%s.%s", _JOURNAL_CONTENT_DIR, title, TemplateConstants.LANG_TYPE_FTL);

		InputStream stream = AddProducerSiteUpgradeProcess.class.getResourceAsStream(fileName);

		String script = StringUtil.read(stream);

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setAddGroupPermissions(true);

		DDMTemplate ddmTemplate = ddmTemplateLocalService.addTemplate(
			userId, groupId, portal.getClassNameId(DDMStructure.class), classPK,
			portal.getClassNameId(JournalArticle.class), titleMap, descriptionMap,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK, language, script, serviceContext);

		// Add empty web content article with template

		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		String content = _getDefaultContent(groupId);

		journalArticleLocalService.addArticle(
			userId, groupId, folderId, titleMap, descriptionMap, content, ddmStructureKey, ddmTemplate.getTemplateKey(),
			serviceContext);

		if (_log.isInfoEnabled()) {
			_log.info(String.format("Added a journal article with title %s for group with groupId %s", title, groupId));
		}
	}

	protected Map<String, DDMFormValues> createDDMFormValuesMap(
			long groupId, long ddmStructureId, String ddmStructureKey, JSONObject fileMetadata,
			ServiceContext serviceContext)
		throws PortalException {

		DDMFormValues ddmFormValues = storageEngineManager.getDDMFormValues(
			ddmStructureId, String.valueOf(ddmStructureId), serviceContext);

		Locale locale = portal.getSiteDefaultLocale(groupId);

		ddmFormValues.addAvailableLocale(locale);
		ddmFormValues.setDefaultLocale(locale);

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(ddmFormField.getName());

			Value value = new LocalizedValue(locale);

			value.addString(locale, fileMetadata.getString(ddmFormField.getName()));

			ddmFormFieldValue.setValue(value);

			ddmFormFieldValues.add(ddmFormFieldValue);
		}

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		Map<String, DDMFormValues> ddmFormValuesMap = new HashMap<>();

		ddmFormValuesMap.put(ddmStructureKey, ddmFormValues);

		return ddmFormValuesMap;
	}

	protected void setLayoutScript(long groupId, boolean privateLayout, String friendlyURL, String fileName) throws PortalException {
		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, privateLayout, friendlyURL);

		UnicodeProperties layoutTypeSettings = layout.getTypeSettingsProperties();

		layoutTypeSettings.setProperty("javascript", _readFile(fileName));

		LayoutLocalServiceUtil.updateLayout(layout);
	}

	protected long addOrUpdatePortalSite(long companyId, String name, String friendlyURL, String themeId)
			throws PortalException {
		return addOrUpdatePortalSite(companyId, name, friendlyURL, GroupConstants.TYPE_SITE_PRIVATE, themeId);
	}

	protected long addOrUpdatePortalSite(long companyId, String name, String friendlyURL, int siteType, String themeId)
			throws PortalException {
		Group group = addOrFetchGroup(companyId, name, StringPool.BLANK, siteType, friendlyURL);

		long groupId = group.getGroupId();

		// Update site theme

		if (!Validator.isBlank(themeId)) {
			layoutSetLocalService.updateLookAndFeel(groupId, true, themeId, StringPool.BLANK, StringPool.BLANK);

			layoutSetLocalService.updateLookAndFeel(groupId, false, themeId, StringPool.BLANK, StringPool.BLANK);
		}
		else {
			String producerThemeId = LayoutURLKeyConstants.THEME_ID_CMIC_PRODUCER;

			String insuredThemeId = LayoutURLKeyConstants.THEME_ID_CMIC_INSURED;

			layoutSetLocalService.updateLookAndFeel(groupId, true, producerThemeId, StringPool.BLANK, StringPool.BLANK);

			layoutSetLocalService.updateLookAndFeel(groupId, false, insuredThemeId, StringPool.BLANK, StringPool.BLANK);
		}

		return groupId;
	}

	protected ServiceContext getDefaultServiceContext(long companyId) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userLocalService.getDefaultUserId(companyId));

		return serviceContext;
	}

	protected void updateDDMTemplate(
			long groupId, String title, long classNameId, String ddmTemplateKey)
		throws Exception {

		String fileName = String.format("%s/%s.%s", _DDM_TEMPLATE_DIR, title, TemplateConstants.LANG_TYPE_FTL);

		InputStream stream = BaseSiteUpgradeProcess.class.getResourceAsStream(fileName);

		String script = StringUtil.read(stream);

		DDMTemplate ddmTemplate = ddmTemplateLocalService.getTemplate(groupId, classNameId, ddmTemplateKey);

		ddmTemplate.setScript(script);

		ddmTemplateLocalService.updateDDMTemplate(ddmTemplate);
	}

	protected long updateDLFileEntryType(long groupId, String title, String fileEntryTypeKey) throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long userId = userLocalService.getDefaultUserId(companyId);

		Map<Locale, String> titleMap = _getTitleMap(groupId, title);
		Map<Locale, String> descriptionMap = new HashMap<>();

		String fileName = String.format("%s/%s.%s", _DDM_STRUCTURE_DIR, title, TemplateConstants.LANG_TYPE_JSON);

		InputStream stream = BaseSiteUpgradeProcess.class.getResourceAsStream(fileName);

		String fileTypeDefinition = StringUtil.read(stream);

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setAddGroupPermissions(true);

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			groupId, portal.getClassNameId(DLFileEntryMetadata.class), fileEntryTypeKey);

		ddmStructure.setStorageType(TemplateConstants.LANG_TYPE_JSON);

		ddmStructure = ddmStructureLocalService.updateDDMStructure(ddmStructure);

		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm = ddm.getDDMForm(fileTypeDefinition);

		DDMFormLayout ddmFormLayout = ddm.getDefaultDDMFormLayout(ddmForm);

		ddmStructure = ddmStructureLocalService.updateStructure(
			userId, ddmStructure.getStructureId(), ddmForm, ddmFormLayout, serviceContext);

		ddmStructureLocalService.addStructureResources(ddmStructure, true, false);

		DLFileEntryType dlFileEntryType = dlFileEntryTypeLocalService.getFileEntryType(groupId, fileEntryTypeKey);

		dlFileEntryTypeLocalService.updateFileEntryType(
			userId, dlFileEntryType.getFileEntryTypeId(), titleMap, descriptionMap,
			new long[] {ddmStructure.getStructureId()}, serviceContext);

		// Structure key will be changed from "WIDEN-FILE-SHORTCUT" to "AUTO_ + fileTypeUUID"

		ddmStructure.setStructureKey(DLUtil.getDDMStructureKey(dlFileEntryType.getUuid()));

		ddmStructureLocalService.updateDDMStructure(ddmStructure);

		return dlFileEntryType.getFileEntryTypeId();
	}

	protected void updateVirtualHost(long companyId, long groupId, String hostname, boolean privateLayout)
		throws PortalException {

		if (Validator.isNotNull(virtualHostLocalService.fetchVirtualHost(hostname))) {
			return;
		}

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(groupId, privateLayout);

		virtualHostLocalService.updateVirtualHosts(
			companyId, layoutSet.getLayoutSetId(),
			TreeMapBuilder.put(
				hostname, StringPool.BLANK
			).build());
	}

	protected AssetEntryLocalService assetEntryLocalService;
	protected CMICUserLocalService cmicUserLocalService;
	protected CompanyLocalService companyLocalService;
	protected DDM ddm;
	protected DDMStructureLocalService ddmStructureLocalService;
	protected DDMTemplateLocalService ddmTemplateLocalService;
	protected DLAppService dlAppService;
	protected DLFileEntryLocalService dlFileEntryLocalService;
	protected DLFileEntryTypeLocalService dlFileEntryTypeLocalService;
	protected ExpandoColumnLocalService expandoColumnLocalService;
	protected ExpandoTableLocalService expandoTableLocalService;
	protected GroupLocalService groupLocalService;
	protected JournalArticleLocalService journalArticleLocalService;
	protected LayoutSetLocalService layoutSetLocalService;
	protected StorageEngineManager storageEngineManager;
	protected UserLocalService userLocalService;
	protected VirtualHostLocalService virtualHostLocalService;

	protected static final String DL_FILE_ENTRY_DIR = "/dl-file-entry";

	protected static final String TEMP = "temp";

	protected static final String TITLE = "Title";

	private String _getDefaultContent(long groupId) throws PortalException {
		Locale locale = portal.getSiteDefaultLocale(groupId);

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", locale.toString());
		rootElement.addAttribute("default-locale", locale.toString());

		Element dynamicElementElement = rootElement.addElement("dynamic-element");

		dynamicElementElement.addAttribute("index-type", "text");
		dynamicElementElement.addAttribute("name", "content");
		dynamicElementElement.addAttribute("type", "text_area");
		dynamicElementElement.addAttribute("instance-id", StringUtil.randomId());

		Element element = dynamicElementElement.addElement("dynamic-content");

		element.addAttribute("language-id", LocaleUtil.toLanguageId(locale));
		element.addCDATA(StringPool.BLANK);

		return document.asXML();
	}

	private Map<Locale, String> _getTitleMap(long groupId, String title) throws PortalException {
		Locale defaultLocale = portal.getSiteDefaultLocale(groupId);

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(defaultLocale, title);

		return titleMap;
	}

	private static String _readFile(String fileName) {
		try (InputStream stream = BaseSitePage.class.getResourceAsStream( _LAYOUT_SCRIPT_DIR + fileName + _JAVASCRIPT_EXTENSION)) {
			return StringUtil.read(stream);
		}
		catch (IOException ioe) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to read from file: " + fileName, ioe);
			}

			return StringPool.BLANK;
		}
	}

	private static final String _DDM_STRUCTURE_DIR = "/ddm-structure";

	private static final String _DDM_TEMPLATE_DIR = "/ddm-template";

	private static final String _JAVASCRIPT_EXTENSION = ".js";

	private static final String _JOURNAL_CONTENT_DIR = "/journal-content";

	private static final String _LAYOUT_SCRIPT_DIR = "/layout-script/";

	private static final Log _log = LogFactoryUtil.getLog(BaseSiteUpgradeProcess.class);

}