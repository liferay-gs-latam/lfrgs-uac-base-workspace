<#-- Theme settings -->
<#assign greeting = getterUtil.getString(themeDisplay.getThemeSetting("greeting")) />
<#assign greeting = greeting?replace("[FIRST_NAME]", "${user_first_name}") />
<#assign greeting_message = getterUtil.getString(themeDisplay.getThemeSetting("greeting-message")) />

<#-- Nav icons -->
<#assign nav_item_icon_hash =
	{
		"home": "home",
		"accounts": "briefcase",
		"resources": "document",
		"contact us": "question-mark"
	}
/>