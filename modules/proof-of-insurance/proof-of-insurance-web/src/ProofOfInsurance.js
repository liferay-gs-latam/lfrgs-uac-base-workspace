import React from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ProofOfInsuranceTypeSelect} from 'com.churchmutual.proof.of.insurance.dashboard.web';

const spritemap = themeDisplay.getPathThemeImages() + '/cmic/icons.svg';

class ProofOfInsurance extends React.Component {

	constructor(props) {
		super(props);

		this.showRequestDialog = this.showRequestDialog.bind(this);

		this.state = {
			dialogModal: {
				visible: ''
			}
		}
	}

	componentDidMount() {
		let callback = (hasRequestCOIEOP) => {
			if (hasRequestCOIEOP) {
				document.getElementById('p_p_id' + this.props.portletNamespace).classList.add('d-block');
			}
		}

		Liferay.Service(
			'/cmic.cmicpermission/has-permission',
			{
				actionId: 'REQUEST_COI_EOP'
			},
			callback
		);
	}

	showRequestDialog(show) {
		this.setState({
			dialogModal: {
				visible: show
			}
		});
	}

	render() {
		return (
			<div className="proof-of-insurance-portlet">
				<div className="my-3 py-3">
					<div className="align-items-center flex-container">
						<ClayIcon className="mr-2 neutral-3" spritemap={spritemap} symbol="award"/>

						<h5 className="font-weight-bold m-0">
							{Liferay.Language.get('proof-of-insurance-forms')}
						</h5>
					</div>

					<div className="py-2">
						{Liferay.Language.get('request-a-certificate-of-insurance-or-evidence-of-property-insurance-form')}
					</div>

					<div>
						<ClayButton
							borderless="true"
							className="link-action m-0 p-0 text-uppercase"
							onClick={() => this.showRequestDialog(true)}>
							<div className="pt-2 text-left">
								{Liferay.Language.get('request')}
							</div>
						</ClayButton>
					</div>

					<ProofOfInsuranceTypeSelect
						showRequestDialog={this.showRequestDialog}
						visible={this.state.dialogModal.visible}
					/>
				</div>
			</div>
		);
	}
}

export default ProofOfInsurance;