import React, {useState} from 'react';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import {RequestFormModal} from 'com.churchmutual.loss.run.report.insured.web';

const spritemap = themeDisplay.getPathThemeImages() + '/clay/icons.svg';

function LossRunReport() {

	const [showModal, setShowModal] = useState(false);

	return (
		<div className="loss-run-report-portlet">

			<ClayCard className="p-1">
				<ClayCard.Body>
					<div className="align-items-center flex-container flex-nowrap text-nowrap">
						<ClayIcon className="loss-runs-icon mr-2" spritemap={spritemap} symbol="document" />

						<h5 className="font-weight-bold m-0">{Liferay.Language.get('loss-runs-report')}</h5>
					</div>

					<div className="py-2">{Liferay.Language.get('get-a-report-of-your-loss-runs-instantly')}</div>

					<div>
						<button
							className="btn cursor-pointer link-action p-0"
							onClick={() => setShowModal(true)}>{Liferay.Language.get('request')}</button>
					</div>
				</ClayCard.Body>
			</ClayCard>

			<RequestFormModal
				visible={showModal}
				setVisible={(value) => setShowModal(value)} />
		</div>
	);

}

export default LossRunReport;