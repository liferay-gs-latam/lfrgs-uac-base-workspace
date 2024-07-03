import React from 'react';

/*
 * This function is a workaround to navigation errors when links are using relative URLs and the current URL is something like ‘/group/producer’.
 * If the current URL includes ‘/group/name’, it will navigate to the full path, but if the current URL is using friendly URLs like '/group/producer/accounts', then navigate to the relative URL.
 */

export function navigate(path) {
  Liferay.Util.navigate(getFriendlyURL(path));
}

export function getFriendlyURL(path) {
  return Liferay.ThemeDisplay.getLayoutRelativeURL().split('/').slice(0, 3).join('/') + '/' + path;
}