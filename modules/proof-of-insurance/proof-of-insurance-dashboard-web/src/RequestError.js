import React from 'react';
import ClayIcon from '@clayui/icon';

function RequestError() {

	return (
		<div className="align-items-center d-flex flex-column loss-run-response">
			<div className="color-danger pb-4"><ClayIcon className="mr-2" spritemap={Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg'} symbol="exclamation-full" />{Liferay.Language.get('error.there-was-an-issue-downloading-the-pdf-please-try-again-later')}</div>

			<div className="color-danger">{Liferay.Language.get('if-you-still-need-assistance-please-contact-a-specialist')}</div>
		</div>
	);

}

export default RequestError;