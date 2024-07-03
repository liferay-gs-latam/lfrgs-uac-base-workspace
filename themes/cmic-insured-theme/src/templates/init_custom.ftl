<#assign registrationHeader = "Portal Sign Up" />

<#assign groupLocalService = serviceLocator.findService("com.liferay.portal.kernel.service.GroupLocalService") />
<#assign group = groupLocalService.getGroup(themeDisplay.getScopeGroupId()) />

<#assign layout = themeDisplay.getLayout() />

<#-- Theme settings -->
<#assign greeting = getterUtil.getString(themeDisplay.getThemeSetting("greeting")) />
<#assign greeting = greeting?replace("[FIRST_NAME]", "${user_first_name}") />
<#assign greeting_message = getterUtil.getString(themeDisplay.getThemeSetting("greeting-message")) />