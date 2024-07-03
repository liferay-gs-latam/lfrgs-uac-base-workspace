import React from 'react';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import {ContactCards, getFriendlyURL, Toast} from 'com.churchmutual.commons.web';

class Contacts extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			contactsList: [],
			contactEmail: '',
			contactPhoneNumber: '',
			isLoading: true,
			firstName: '',
			greeting: '',
			greetingMessage: '',
			producerTypes: [],
			toast: {
				displayType: '',
				message: '',
				title: '',
			}
		}
	}

	componentDidMount() {
		this.getFirstName();
		this.getInsuredThemeText();
		this.getContacts();
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

	displayErrorMessage(msg) {
		this.setState({
			toast: {
				displayType: 'danger',
				message: msg,
				title: Liferay.Language.get('error-colon')
			}
		});
	}

	getContacts() {
		let callback = (data) => {
			this.setState({
				producerTypes: data
			});
		}

		let contactCallback = (data) => {
			if (data && data.role) {
				data.title = data.role;

				this.setState({
					contactsList: [data]
				});
			}
		}

		let errCallback = () => {
			this.displayErrorMessage(Liferay.Language.get('error.unable-to-retrieve-contact'))
		};

		Liferay.Service(
			'/cmic.cmicuser/get-producer-types-by-user-id',
			callback,
			errCallback
		);

		Liferay.Service(
			'/cmic.cmiccontact/fetch-direct-sales-representative',
			contactCallback,
			errCallback
		);
	}

	getFirstName() {
		let callback = (user) => this.setState({firstName: user.firstName})

		Liferay.Service(
			'/user/get-user-by-id',
			{
				userId: Liferay.ThemeDisplay.getUserId()
			},
			callback
		);
	}

	getInsuredThemeText() {
		let greetingCallback = (data) => this.setState({greeting: data});
		let errCallback = () => this.displayErrorMessage('your-request-failed-to-complete');

		Liferay.Service(
			'/cmic.cmicuser/get-insured-theme-setting',
			{
				themeSettingKey: 'greeting'
			},
			greetingCallback,
			errCallback
		);

		let greetingMessageCallback = (data) => this.setState({greetingMessage: data});

		Liferay.Service(
			'/cmic.cmicuser/get-insured-theme-setting',
			{
				themeSettingKey: 'greeting-message'
			},
			greetingMessageCallback,
			errCallback
		);

		let contactEmailCallback = (data) => this.setState({contactEmail: data});

		Liferay.Service(
			'/cmic.cmicuser/get-insured-theme-setting',
			{
				themeSettingKey: 'contact-email'
			},
			contactEmailCallback,
			errCallback
		);

		let contactPhoneNumberCallback = (data) => this.setState({contactPhoneNumber: data});

		Liferay.Service(
			'/cmic.cmicuser/get-insured-theme-setting',
			{
				themeSettingKey: 'contact-phone-number'
			},
			contactPhoneNumberCallback,
			errCallback
		);
	}

	getSpriteMap() {
		return Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
	}

	render() {
		return (
			<div className="contact-insured-dashboard-portlet">
				<div className={!this.state.producerTypes.includes('AGENCY') && !this.state.producerTypes.includes('DIRECT') ? "d-none d-lg-block well well-lg" : "well well-lg"}>
					<ClayCard className={!this.state.producerTypes.includes('AGENCY') && !this.state.producerTypes.includes('DIRECT') ? "card-insured d-none d-lg-block mb-0" : "card-insured d-none d-lg-block"}>
						<h2 className="text-capitalize m-0 data-hj-suppress">{this.state.greeting.replaceAll('[FIRST_NAME]', this.state.firstName)}</h2>
						<div className="text-muted">{this.state.greetingMessage}</div>
					</ClayCard>

					<div className="contact-information-container">
						{this.state.producerTypes.includes('AGENCY') || this.state.producerTypes.includes('DIRECT')
							? (
								<div className="contact-support-information">
									<h2 className="mb-3">{Liferay.Language.get('contact-support')}</h2>

									<div>
										<ClayIcon symbol={"phone"} spritemap={this.getSpriteMap()} className="mr-2 neutral-4"/>
										<a href={"tel:" + this.state.contactPhoneNumber}>{this.state.contactPhoneNumber}</a>
									</div>
									<div>
										<ClayIcon symbol={"envelope-closed"} spritemap={this.getSpriteMap()} className="mr-2 neutral-4"/>
										<a href={"mailto:" + this.state.contactEmail}>{this.state.contactEmail}</a>
									</div>
								</div>
							)
							: ''
						}

						{this.state.producerTypes.length == 1 && this.state.producerTypes[0] === 'DIRECT' && this.state.contactsList.length > 0
							? (
								<div className="my-contacts-information">
									<h2 className="mb-3">{Liferay.Language.get('my-contacts')}</h2>
									<ContactCards
										list={this.state.contactsList}
										isLoading={this.state.isLoading}
										limit={1}
										md={6}
										lg={12}
										showAllContactsLink={getFriendlyURL('contact')}
									/>
								</div>
							) : ''
						}
					</div>

					<Toast
						displayType={this.state.toast.displayType}
						message={this.state.toast.message}
						onClose={() => this.onToastClosed()}
						title={this.state.toast.title}/>
				</div>
			</div>
		);
	}
}

export default Contacts;