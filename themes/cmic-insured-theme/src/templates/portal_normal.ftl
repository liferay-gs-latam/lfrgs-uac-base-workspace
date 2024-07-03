<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<!-- Google Tag Manager -->
	<script>
		(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
					new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
				j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
				'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-MKLB35R');
	</script>
	<!-- End Google Tag Manager -->
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	<@liferay_util["include"] page=top_head_include />
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<@liferay.control_menu />

<div id="wrapper">
	<#include "${full_templates_path}/header.ftl" />

	<section id="content">
		<h1 class="current-layout-${layout.getNameCurrentValue()} hide-accessible" role="heading" aria-level="1">${the_title}</h1>

        <#if is_first_parent && is_signed_in && group?? && "/registration" != group.getFriendlyURL()>
			<div class="insured-greeting-banner page-header">
				<h2 class="card card-insured text-left">${greeting} <span class="font-weight-normal greeting-message">${greeting_message}</span></h2>
			</div>
        </#if>

		<#if selectable>
			<@liferay_util["include"] page=content_include />
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			<@liferay_theme["wrap-portlet"] page="portlet.ftl">
				<@liferay_util["include"] page=content_include />
			</@>
		</#if>
	</section>

	<footer>
		<div class="label-lg neutral-4 text-center">
			<div><@liferay.language_format arguments="${.now?string('yyyy')}" key="footer-copyright-text-x" /></div>


			<div><@liferay.language key="footer-disclaimer-text" /></div>
		</div>
	</footer>
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />

<!-- inject:js -->
<!-- endinject -->

</body>

</html>