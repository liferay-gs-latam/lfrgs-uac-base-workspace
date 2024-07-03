import React from 'react';
import ClayLayout from '@clayui/layout';
import ClayButton from '@clayui/button';
import InputMask from 'react-input-mask';
import {Input} from 'com.churchmutual.commons.web';

class ConfirmYourIdentityInsured extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			accountNumber: '',
			zipCode: '',
			formErrors: {
				accountNumber: null,
				zipCode: null,
			}
		}

		this.formIdentity = React.createRef();
		this.cursor = 0;
	}

	isAccountNumberValid() {
		return this.state.accountNumber != '' && !this.state.formErrors.accountNumber;
	}

	isZipCodeValid() {
		return this.state.zipCode != '' && !this.state.formErrors.zipCode;
	}

	isFormIdentityValid() {
		return this.isAccountNumberValid() && this.isZipCodeValid();
	}

	onFormIdentitySubmit(e) {
		e.preventDefault();

		let formErrors = {};

		if (!this.isZipCodeValid()) {
			formErrors.zipCode = true;
		}

		if (!this.isAccountNumberValid()) {
			formErrors.accountNumber = true;
		}

		if (Object.keys(formErrors).length != 0) {
			this.setState({formErrors: formErrors});

			return;
		}

		this.submitFormForInsured();
	}

	setAccountNumber(fieldValue) {
		this.setState(previousState => ({
			accountNumber: fieldValue,
			formErrors: {
				...previousState.formErrors,
				accountNumber: null
			}
		}));
	}

	setZipCode(fieldValue) {
		this.setState(previousState => ({
			zipCode: fieldValue,
			formErrors: {
				...previousState.formErrors,
				zipCode: null
			}
		}));
	}

	submitFormForInsured() {
		let callback = (isUserValid) => {
			if ("valid" === isUserValid) {
				window.location.href = Liferay.ThemeDisplay.getPortalURL() + "/group/insured";
			} else if ("accountNumberError" === isUserValid) {
				this.setState(previousState => ({
					formErrors: {
						...previousState.formErrors,
						accountNumber: true
					}
				}));
			} else if ("zipCodeError" === isUserValid) {
				this.setState(previousState => ({
					formErrors: {
						...previousState.formErrors,
						zipCode: true
					}
				}));
			}
		}

		let errCallback = () =>
			this.setState(previousState => ({
				formErrors: {
					...previousState.formErrors,
					accountNumber: true,
					zipCode: true
				}
			}));

		Liferay.Service(
			'/cmic.cmicuser/validate-insured-user-registration',
			{
				accountNumber: this.state.accountNumber,
				cmicUserId: this.props.cmicUserId,
				registrationCode: this.props.registrationCode,
				zipCode: this.state.zipCode
			},
			callback,
			errCallback
		);
	}

	render() {
		return (
			<React.Fragment>
				<ClayLayout.SheetHeader>
					<h1 className="sheet-title">{Liferay.Language.get("confirm-your-identity")}</h1>
				</ClayLayout.SheetHeader>

				<ClayLayout.SheetSection>
					<form ref={this.formIdentity} onSubmit={(e) => this.onFormIdentitySubmit(e)}>
						<Input
							fieldName="accountNumber"
							handleFieldChange={(fieldName, fieldValue) => {this.setAccountNumber(fieldValue)}}
							maxLength="7"
							label={Liferay.Language.get("account-number")}
							required={true}
							value={this.state.accountNumber}
							showErrors={this.state.formErrors.accountNumber}
							errorMsg={Liferay.Language.get("error.account-number")}
						/>

						<Input
							fieldName="zipCode"
							handleFieldChange={(fieldName, fieldValue) => this.setZipCode(fieldValue)}
							label={Liferay.Language.get("mailing-address-zip-code")}
							maxLength="5"
							required={true}
							value={this.state.zipCode}
							showErrors={this.state.formErrors.zipCode}
							errorMsg={Liferay.Language.get("error.zip-code")}
						/>

						<div className="ml-3 mb-4 mt-n3 small">
							{Liferay.Language.get("zip-code-help-text")}
						</div>

						<ClayButton disabled={!this.isFormIdentityValid()} displayType="primary" type="submit">
							{Liferay.Language.get("finish")}
						</ClayButton>
					</form>
				</ClayLayout.SheetSection>
			</React.Fragment>
		);
	}
}

export default ConfirmYourIdentityInsured;