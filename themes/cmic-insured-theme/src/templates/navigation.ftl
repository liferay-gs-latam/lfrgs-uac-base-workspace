<nav class="${nav_css_class} js-navigation" id="navigation" role="navigation">
	<ul class="nav" aria-label="<@liferay.language key="site-pages" />" role="menubar">
		<#list nav_items as nav_item>
			<#assign
				nav_item_attr_has_popup = ""
				nav_item_css_class = "nav-item"
				nav_item_layout = nav_item.getLayout()
				nav_link_css_class = "nav-link"
				nav_item_icon = "arrow-right"
			/>

			<#if nav_item.isSelected()>
				<#assign
					nav_item_attr_has_popup = "aria-haspopup='true'"
					nav_item_css_class = nav_item_css_class + " selected"
					nav_link_css_class = nav_link_css_class + " active"
				/>
			</#if>

			<li class="${nav_item_css_class}" id="layout_${nav_item.getLayoutId()}" role="presentation">
				<a class="${nav_link_css_class}" aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem">
					<span class="layout-name">${nav_item.getName()}</span>
				</a>
			</li>
		</#list>
	</ul>
</nav>