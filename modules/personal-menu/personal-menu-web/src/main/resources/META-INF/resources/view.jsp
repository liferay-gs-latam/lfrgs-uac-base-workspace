<%--
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
--%>

<%@ include file="init.jsp" %>

<c:choose>
	<c:when test="${themeDisplay.isSignedIn()}">
		<liferay-util:buffer var="userAvatar">
			<span class="layout-icon">
				<c:choose>
					<c:when test="${portraitImageURL.length() eq 0}">
						<liferay-ui:user-portrait
								cssClass="nav-item-icon"
								user="<%= user %>"
						/>
					</c:when>
					<c:otherwise>
						<span class="nav-item-icon sticker sticker-circle sticker-lg sticker-light">
							<span class="sticker-overlay">
								<img class="sticker-img" src="${portraitImageURL}" />
							</span>
						</span>
					</c:otherwise>
				</c:choose>
			</span>
			<span class="layout-name">${themeDisplay.user.firstName}</span>
		</liferay-util:buffer>
		<nav class="sort-pages modify-pages js-navigation" id="navigation">
			<ul class="nav">
				<li class="nav-item ${themeDisplay.layout.friendlyURL.toString()=='/profile'?'selected':''}">
					<clay:link
							contributorKey="PersonalMenuKey"
							href="${profilePageRedirect}"
							label="<%= userAvatar %>"
							elementClasses="<%= themeDisplay.getLayout().getFriendlyURL().toString().trim().equals("/profile")?"nav-link active":"nav-link" %>"
					/>
				</li>
			</ul>
		</nav>
	</c:when>
	<c:otherwise>

		<%
			Map<String, Object> anchorData = new HashMap<String, Object>();
			anchorData.put("redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request)));
		%>

		<span class="sign-in text-default" role="presentation">
			<a class="align-items-center d-flex sign-in text-default" data-redirect="false" href="${signInRedirect}">
				<clay:icon symbol="user" />

				<span class="taglib-icon-label"><liferay-ui:message key="sign-in" /></span>
			</a>
		</span>
	</c:otherwise>
</c:choose>