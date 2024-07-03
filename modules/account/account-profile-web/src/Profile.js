import React from 'react';
import ClayCard from '@clayui/card';
import {Toast} from 'com.churchmutual.commons.web';
import InsuredAccountsList from './InsuredAccountsList';

class Profile extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			insuredAccountsList: [],
			isLoading: true,
			toast:  {
				displayType: '',
				message: '',
				title: '',
			}
		};
	}

	componentDidMount() {
		this.getInsuredAccountsList();
	}

	displayErrorMessage(msg) {
		this.setState({
			toast: {
				displayType: 'danger',
				message: msg,
				title: Liferay.Language.get('error-colon')
			}
		});
	}

	onToastClosed() {
		this.setState({
			toast: {
				displayType: '',
				message: '',
				title: ''
			}
		});
	}

	getInsuredAccountsList() {
		let callback = (data) => this.setState({insuredAccountsList: data, isLoading: false});

		let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts');

		Liferay.Service(
			'/cmic.cmicaccountentry/get-cmic-account-entry-displays-with-addresses',
			callback,
			errCallback
		);
	}

	render() {
		return (
			<div className="account-profile-portlet">
				<ClayCard>
					<div className="card-header">
						<ClayCard.Description displayType="title">{Liferay.Language.get('insured-accounts')}</ClayCard.Description>
					</div>
					<ClayCard.Body>
						<InsuredAccountsList
							insuredAccountsList={this.state.insuredAccountsList}
							isLoading={this.state.isLoading}
						/>
					</ClayCard.Body>
				</ClayCard>

				<Toast
					displayType={this.state.toast.displayType}
					message={this.state.toast.message}
					onClose={() => this.onToastClosed()}
					title={this.state.toast.title} />
			</div>
		);
	}
};


export default Profile;
