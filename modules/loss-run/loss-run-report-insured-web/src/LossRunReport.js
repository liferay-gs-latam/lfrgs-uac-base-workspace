import React, {useEffect, useState} from 'react';
import ClayIcon from '@clayui/icon';
import RequestFormModal from './RequestFormModal';

const spritemap = themeDisplay.getPathThemeImages() + '/clay/icons.svg';

function LossRunReport(props) {

	const [showModal, setShowModal] = useState(false);

	useEffect(() => {
		let callback = (hasRequestLossRuns) => {
			if (hasRequestLossRuns) {
				document.getElementById('p_p_id' + props.portletNamespace).classList.add('d-block');
			}
		}

		Liferay.Service(
			'/cmic.cmicpermission/has-permission',
			{
				actionId: 'REQUEST_LOSS_RUNS'
			},
			callback
		);
	}, []);

	function renderLossRunsPortlet() {
		return (
			<React.Fragment>
				<div className="my-3 py-3">
					<div className="align-items-center flex-container">
						<ClayIcon className="mr-2 neutral-3" spritemap={spritemap} symbol="document"/>

						<h5 className="font-weight-bold m-0">{Liferay.Language.get('loss-runs-report')}</h5>
					</div>

					<div className="py-2">{Liferay.Language.get('get-a-report-of-your-loss-runs-instantly')}</div>

					<div className="pt-2">
						<button
							className="btn cursor-pointer link-action p-0"
							onClick={() => setShowModal(true)}>{Liferay.Language.get('request')}</button>
					</div>
				</div>

				<RequestFormModal
					visible={showModal}
					setVisible={(value) => setShowModal(value)} />
			</React.Fragment>
		);
	}

	return (
		<div className="loss-run-report-insured-portlet">
			{renderLossRunsPortlet()}
		</div>
	);

}

export default LossRunReport;