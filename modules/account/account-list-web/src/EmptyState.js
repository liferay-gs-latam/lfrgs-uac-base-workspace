import React from 'react';

const EmptyState = () => {
	return (
		<div className="empty-state">
			<h3 className="font-weight-bold">{Liferay.Language.get('no-accounts-match-this-criteria')}</h3>
			<h3 className="font-weight-bold">{Liferay.Language.get('try-adjusting-your-search-text-or-filters')}</h3>
			<div className="well well-lg">
				<h4 className="font-weight-bold">{Liferay.Language.get('you-can-search-by')}</h4>
				<ul>
					<li>{Liferay.Language.get('account-name')}</li>
					<li>{Liferay.Language.get('account-number')}</li>
					<li>{Liferay.Language.get('producer-code')}</li>
				</ul>
			</div>
		</div>
	);
}

export default EmptyState;