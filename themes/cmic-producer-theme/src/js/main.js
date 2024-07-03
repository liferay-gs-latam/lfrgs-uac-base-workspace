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

/* eslint-disable prefer-arrow-callback */
AUI().ready(
	/*
	This function gets loaded when all the HTML, not including the portlets, is
	loaded.
	*/

	function(A) {
		var $ = AUI.$;
		var navigation = $('.js-navigation');
		var navigationToggler = $('.js-navigation-toggler');

		navigationToggler.on('click', function() {
			return (this.t = !this.t) ? (
				navigation.addClass('open'),
				navigationToggler.toggleClass('link-action btn-outline-primary'),
				navigationToggler.html(Liferay.Language.get('close'))
			) : (
				navigation.removeClass('open'),
				navigationToggler.toggleClass('link-action btn-outline-primary'),
				navigationToggler.html(Liferay.Language.get('menu'))
			);
		});
	}
);

Liferay.Portlet.ready(
	/*
	This function gets loaded after each and every portlet on the page.

	portletId: the current portlet's id
	node: the Alloy Node object of the current portlet
	*/

	function(_portletId, _node) {}
);

Liferay.on(
	'allPortletsReady',

	/*
	This function gets loaded when everything, including the portlets, is on
	the page.
	*/

	function() {}
);

window.onload = setTimeout(function() {

	/*
	Added table-scroll function to window.onload, since it would often not load every time the page was refreshed.  Also, $(document).ready would trigger less often.
	*/

	$('.table-responsive').on('scroll', function() {
		var cur = $(this).scrollLeft();
		var max = $(this)[0].scrollWidth - $(this).parent().width();

		if (cur == max) {
			$('.scrollable-table').removeClass('fade-table');
		} else {
			$('.scrollable-table').addClass('fade-table');
		}
	});
	$('.scrollable-table').trigger('scroll');
}, 1000);

	/*
	Toggle notifications panel
	 */

var notificationPanel = $('.page-header-notification-panel');
var notificationToggle = $('.page-header-notification-link');
var notificationClose = $('.page-header-notification-close');
var sidebarBody = $('.sidebar-body');
var userNotification = $('.user-notification');

function toggleNotificationPanel(event) {
	event.preventDefault();

	notificationPanel.toggleClass('hide');
	notificationPanel.toggleClass('open');
	userNotification.toggleClass('open');
}

notificationToggle.on(
	'click',
	function(event) {
		toggleNotificationPanel(event);
	}
);

notificationClose.on(
	'click',
	function(event) {
		event.preventDefault();

		notificationPanel.addClass('hide');
		notificationPanel.removeClass('open');
		userNotification.removeClass('open');
	}
);

sidebarBody.on(
	'scroll',
	function() {
		if (sidebarBody.scrollTop() + sidebarBody.innerHeight() >= sidebarBody[0].scrollHeight) {
			sidebarBody.removeClass('sidebar-fade');
		}
		else {
			if (!sidebarBody.hasClass('sidebar-fade')) {
				sidebarBody.addClass('sidebar-fade');
			}
		}
	}
)