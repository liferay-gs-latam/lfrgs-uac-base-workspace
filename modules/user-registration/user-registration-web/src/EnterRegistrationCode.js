import React from 'react';
import ClayLayout from '@clayui/layout';
import ClayButton from '@clayui/button';
import {Input} from 'com.churchmutual.commons.web';

class EnterRegistrationCode extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			isFormRegistrationCodeSubmitted: false,
			registrationCode: '',
			formErrors: {
				registrationCode: null
			}
		}

		this.formRegistrationCode = React.createRef();
	}

	isFormRegistrationCodeValid() {
		return this.state.registrationCode != '' && !this.state.formErrors.registrationCode && !this.state.registrationCode.hasOwnProperty("CMICUserId");
	}

	onFormRegistrationCodeSubmit(e) {
		e.preventDefault();

		if (!this.isFormRegistrationCodeValid()) {
			this.setState({formErrors: {registrationCode: true}});

			return;
		}

		this.submitFormRegistrationCode();
	}

	setRegistrationCode(fieldValue) {
		this.setState({registrationCode: fieldValue, formErrors: {registrationCode: null}});
	};

	submitFormRegistrationCode() {
		let callback = (data) => {
			console.log("callback");
			if (!data.hasOwnProperty("CMICUserId")) {
				this.setState({formErrors: {registrationCode: true}});
			} else {
				this.props.setRegistrationFormValues(this.state.registrationCode, data.CMICUserId, data.producer);
			}
		}

		let errCallback = () => {
			console.log("errCallback");
			this.setState({formErrors: {registrationCode: true}});
		}

		Liferay.Service(
			'/cmic.cmicuser/validate-user-registration-code',
			{
				registrationCode: this.state.registrationCode
			},
			callback,
			errCallback
		);
	}

	render() {
		return (
			<React.Fragment>
				<ClayLayout.SheetHeader>
					<h1 className="sheet-title">{Liferay.Language.get("enter-registration-code")}</h1>
				</ClayLayout.SheetHeader>

				<ClayLayout.SheetSection>
					<form ref={this.formRegistrationCode} onSubmit={(e) => this.onFormRegistrationCodeSubmit(e)}>

						<Input
							fieldName="registrationCode"
							handleFieldChange={(fieldName, fieldValue) => this.setRegistrationCode(fieldValue)}
							label={Liferay.Language.get("registration-code")}
							maxLength="150"
							required={true}
							value={this.state.registrationCode}
							showErrors={this.state.formErrors.registrationCode}
							errorMsg={Liferay.Language.get("error.registration-code")}
						/>

						<div className="ml-3 mb-4 mt-n3 small">
							<a data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample"
								 className="collapsed text-muted" href="#helpText">
								{Liferay.Language.get("cant-find-your-registration-code")}
							</a>

							<div className="collapse" id="helpText">
								{Liferay.Language.get("registration-help-text")}
							</div>
						</div>
						<div>
							<ClayButton disabled={!this.isFormRegistrationCodeValid()} displayType="primary" type="submit">
								{Liferay.Language.get("continue")}
							</ClayButton>
						</div>
					</form>
				</ClayLayout.SheetSection>
			</React.Fragment>
		);
	}
}

export default EnterRegistrationCode;