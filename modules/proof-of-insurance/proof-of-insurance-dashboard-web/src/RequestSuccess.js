import React from 'react';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';

function RequestSuccess({isLoading, onClickDownload, text}) {

	return (
		<div className="align-items-center d-flex flex-column loss-run-response">
			<div className="font-weight-bold pb-4">{text}</div>

			<div className="pb-4">{Liferay.Language.get('if-it-doesnt-click-here-to-start-the-download')}</div>

			<ClayButton displayType="outline-primary" small onClick={onClickDownload} className={`${isLoading ? "d-none " : ""}download-btn-manual`}>
				{Liferay.Language.get('download')}
			</ClayButton>

			<span className={`${isLoading ? "" : "d-none "}inline-item loading-icon py-2`}>
				<ClayLoadingIndicator />
			</span>
		</div>
	);

}

export default RequestSuccess;