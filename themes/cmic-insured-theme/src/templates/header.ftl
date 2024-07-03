<header id="header" role="banner">

	<#if group?? && "/registration" == group.getFriendlyURL()>

		<a class="btn text-decoration-none" href="/group/registration/cancel">
            <@liferay.language key="cancel" />
		</a>

		<div class="header-text">
			${registrationHeader}</h2>
		</div>

		<a class="${logo_css_class}" href="${site_default_url}">
			<svg class="logo-icon">
				<use xlink:href="${themeDisplay.getPathThemeImages()}/cmic/icons.svg#logo" />
			</svg>
		</a>
	<#else>
		<#if has_navigation && is_setup_complete>
			<button class="btn btn-sm link-action d-lg-none js-navigation-toggler">
				<@liferay.language key="menu" />
			</button>
		</#if>

		<a class="${logo_css_class}" href="${site_default_url}">
			<svg class="logo-icon">
				<use xlink:href="${themeDisplay.getPathThemeImages()}/cmic/icons.svg#logo" />
			</svg>
		</a>

		<#if has_navigation && is_setup_complete>
			<#include "${full_templates_path}/navigation.ftl" />
		</#if>

		<div class="nav-user-bar">
			<#if is_signed_in && !deactivated>
				<div class="user-notification">
					<a class="page-header-notification-link cursor-pointer js-modal">
						<svg class="lexicon-icon lexicon-icon-bell-on navbar-toggler-icon">
							<use xlink:href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#bell-on" />
						</svg>
						<#if (notification_and_announcements_count > 0)>
							<span class="badge badge-danger">
								<span class="badge-item badge-item-expand notification-count">${notification_and_announcements_count}</span>
							</span>
						</#if>
					</a>
				</div>
				<div class="user-personal-bar data-hj-suppress">
					<@liferay.user_personal_bar />
				</div>
			</#if>
		</div>
	</#if>
	<div class="page-header-notification-panel sidebar hide">
		<div class="sidebar-header">
			<div class="autofit-row sidebar-section">
				<div class="autofit-col autofit-col-expand">
					<h1 class="sidebar-header-text">
					<span class="text-truncate-inline">
						<@liferay.language key="notifications" />
					</span>
            				<#if (notification_and_announcements_count > 0)>
						<span class="badge badge-danger">
							<span class="badge-item badge-item-expand notification-count">${notification_and_announcements_count}</span>
						</span>
					</#if>
					</h1>
				</div>
				<div class="autofit-col">
					<a aria-expanded="false" class="page-header-notification-close" href="javascript:;" role="button">
						<svg class="lexicon-icon lexicon-icon-times" focusable="false" role="presentation">
							<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#times"></use>
						</svg>
					</a>
				</div>
			</div>
		</div>
		<div class="sidebar-body sidebar-fade">
			<@liferay_portlet["runtime"]
				portletName="com_churchmutual_notification_web_portlet_NotificationWebPortlet"
			/>
		</div>
	</div>
</header>