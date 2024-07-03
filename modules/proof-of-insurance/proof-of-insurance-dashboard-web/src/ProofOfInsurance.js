import React from 'react';
import ClayButton from '@clayui/button';
import ProofOfInsuranceTypeSelect from './ProofOfInsuranceTypeSelect';

class ProofOfInsurance extends React.Component {

	constructor(props) {
		super(props);

		this.showRequestDialog = this.showRequestDialog.bind(this);

		this.state = {
			dialogModal: {
				visible: ''
			},
			hasRequestCOIEOPPermission: false
		}
	}

	componentDidMount() {
		let callback = (data) => this.setState({hasRequestCOIEOPPermission: data});

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
		if (this.state.hasRequestCOIEOPPermission) {
			return (
				<div className="mb-5 mt-lg-5 proof-of-insurance-portlet">
					<h1>{Liferay.Language.get('proof-of-insurance-forms')}</h1>
					<div>{Liferay.Language.get('request-a-certificate-of-insurance-or-evidence-of-property-insurance-form')}</div>

					<div className="mt-4">
						<ClayButton
							className="btn btn-sm request-a-certificate-btn"
							onClick={() => this.showRequestDialog(true)}>
							<div>
								{Liferay.Language.get('request-a-certificate')}
							</div>
						</ClayButton>
					</div>

					<ProofOfInsuranceTypeSelect
						showRequestDialog={this.showRequestDialog}
						visible={this.state.dialogModal.visible}
					/>
				</div>
			);
		}
		else {
			return (
				<div className="proof-of-insurance-portlet" />
			);
		}
	}

}

export default ProofOfInsurance;