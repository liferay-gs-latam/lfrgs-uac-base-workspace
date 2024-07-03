import React from 'react';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import {Toast} from 'com.churchmutual.commons.web';
import PolicyList from './PolicyList';

class RecentPolicies extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			policiesList: [],
			isLoading: true,
			toast:  {
				displayType: '',
				message: '',
				title: '',
			}
		};
	}

	componentDidMount() {
		this.getPoliciesList();
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

	getPoliciesList() {
		let callback = (data) => this.setState({policiesList: data, isLoading: false});

		let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-policies');

		Liferay.Service(
			'/cmic.cmicqspolicy/get-recent-policy-displays',
			callback,
			errCallback
		);
	}

	getSpriteMap() {
		return Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg';
	}

	render() {
		return (
			<div className="policy-insured-dashboard-portlet policy-list-portlet">
				<ClayCard>
					<div className="card-header">
						<ClayCard.Description className="position-relative" displayType="title">
							<span className="h1">{Liferay.Language.get('recent-policies')}</span>
							<span className="file-a-claim-link">
								<a href="https://integration.churchmutual.com/online-claims-reporting/report"
									 className="cursor-pointer link-action"
									 rel="noopener noreferrer"
									 target="_blank"
									 tabIndex="0">{Liferay.Language.get('file-a-claim')}<ClayIcon spritemap={this.getSpriteMap()} symbol="external-link" className="ml-2" /></a>
							</span>
						</ClayCard.Description>
					</div>
					<PolicyList
						policiesList={this.state.policiesList}
						isLoading={this.state.isLoading}
					/>
				</ClayCard>

				<Toast
					displayType={this.state.toast.displayType}
					message={this.state.toast.message}
					onClose={() => this.onToastClosed()}
					title={this.state.toast.title} />
			</div>
		);
	}

}

export default RecentPolicies;