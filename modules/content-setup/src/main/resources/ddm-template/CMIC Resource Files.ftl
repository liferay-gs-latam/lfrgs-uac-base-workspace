<div class="resources-list-portlet files-${entries?size}">
	<div class="card">
		<div class="card-header">
			<p class="card-title" title="Files">
				<span class="text-truncate-inline">
					<span class="text-truncate">
						<@liferay.language key="files" />
					</span>
				</span>
			</p>
		</div>

		<#if !entries?has_content>
			<#if !themeDisplay.isSignedIn()>
				${renderRequest.setAttribute("PORTLET_CONFIGURATOR_VISIBILITY", true)}
			</#if>

			<div class="card-body">
				<div class="alert alert-info">
					<@liferay_ui["message"] key="there-are-no-results" />
				</div>
			</div>
		<#else>
			<div class="card-body">
				<div class="resource-file-list">
					<#list entries as entry>
						<#assign
							entry = entry

							assetRenderer = entry.getAssetRenderer()

							entryTitle = htmlUtil.escape(assetRenderer.getTitle(locale))

							assetObject = assetRenderer.getAssetObject()

							createDate = assetObject.getCreateDate()

							viewURL = assetPublisherHelper.getAssetViewURL(renderRequest, renderResponse, assetRenderer, entry, !stringUtil.equals(assetLinkBehavior, "showFullContent"))

							dateFormat = "MM/dd/yyyy"

							ddmFormValues = assetRenderer.getDDMFormValuesReader().getDDMFormValues()

							ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap()

							locale = ddmFormValues.getDefaultLocale()

							enclosingChars = ['[', '"', ']']
						/>

						<div class="resource-file-asset">
							<div class="pull-right">
								<@getPrintIcon />

								<@getFlagsIcon />

								<@getEditIcon />
							</div>

							<#assign WidenURLFieldValues = ddmFormFieldValuesMap["WidenURL"] />

							<#if WidenURLFieldValues?? && WidenURLFieldValues?size gt 0>
								<a href="${WidenURLFieldValues[0].getValue().getString(locale)}" rel="noopener noreferrer" target="_blank">
									<#assign FileTypeFieldValues = ddmFormFieldValuesMap["FileType"] />

									<#if FileTypeFieldValues?? && FileTypeFieldValues?size gt 0>
										<#assign type = stringUtil.removeChars(FileTypeFieldValues[0].getValue().getString(locale), enclosingChars) />

										<div class="document-icon-container document-type-${type} flex-container mb-3">
											<svg class="resources-file-icon">
												<use xlink:href="${themeDisplay.getPathThemeImages()}/cmic/icons.svg#icon--document-${type}" />
											</svg>
										</div>
									</#if>

									<h4 class="entry-title">${entryTitle}</h4>

									<div>${dateUtil.getDate(createDate, dateFormat, locale)}</div>
								</a>
							<#else>
								<h4 class="entry-title">${entryTitle}</h4>

								<div>${dateUtil.getDate(createDate, dateFormat, locale)}</div>
							</#if>
						</div>
					</#list>
				</div>
			</div>

			<div class="card-footer"></div>
		</#if>
	</div>
</div>

<#macro getEditIcon>
	<#if assetRenderer.hasEditPermission(themeDisplay.getPermissionChecker())>
		<#assign editPortletURL = assetRenderer.getURLEdit(renderRequest, renderResponse, windowStateFactory.getWindowState("NORMAL"), themeDisplay.getURLCurrent())!"" />

		<#if validator.isNotNull(editPortletURL)>
			<#assign title = languageUtil.format(locale, "edit-x", entryTitle, false) />

			<@liferay_ui["icon"]
				cssClass="icon-monospaced visible-interaction"
				icon="pencil"
				markupView="lexicon"
				message=title
				url=editPortletURL.toString()
			/>
		</#if>
	</#if>
</#macro>

<#macro getFlagsIcon>
	<#if getterUtil.getBoolean(enableFlags)>
		<@liferay_flags["flags"]
			className=entry.getClassName()
			classPK=entry.getClassPK()
			contentTitle=entry.getTitle(locale)
			label=false
			reportedUserId=entry.getUserId()
		/>
	</#if>
</#macro>

<#macro getPrintIcon>
	<#if getterUtil.getBoolean(enablePrint)>
		<#assign printURL = renderResponse.createRenderURL() />

		${printURL.setParameter("mvcPath", "/view_content.jsp")}
		${printURL.setParameter("assetEntryId", entry.getEntryId()?string)}
		${printURL.setParameter("viewMode", "print")}
		${printURL.setParameter("type", entry.getAssetRendererFactory().getType())}
		${printURL.setWindowState("pop_up")}

		<@liferay_ui["icon"]
			icon="print"
			markupView="lexicon"
			message="print"
			url="javascript:Liferay.Util.openWindow({id:'" + renderResponse.getNamespace() + "printAsset', title: '" + languageUtil.format(locale, "print-x-x", ["hide-accessible", entryTitle], false) + "', uri: '" + htmlUtil.escapeURL(printURL.toString()) + "'});"
		/>
	</#if>
</#macro>