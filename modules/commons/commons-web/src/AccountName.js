import React from 'react';

/* Component that provides a default UI element for missing account names */

export function AccountName({accountName}) {

	const accountNameDefault = Liferay.Language.get('no-account-name');

	return ( accountName === accountNameDefault ? <em className="text-muted">{accountNameDefault}</em> : accountName );

}