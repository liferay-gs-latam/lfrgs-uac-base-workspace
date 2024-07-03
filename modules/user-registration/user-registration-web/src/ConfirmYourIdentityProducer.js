import React from 'react';
import ClayLayout from '@clayui/layout';
import ClayButton from '@clayui/button';
import InputMask from 'react-input-mask';
import {Input} from 'com.churchmutual.commons.web';

class ConfirmYourIdentityProducer extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			businessZipCode: '',
			producerCode: '',
			formErrors: {
				businessZipCode: null,
				producerCode: null
			}
		}

		this.formIdentity = React.createRef();
		this.producerCodeRef = React.createRef();
		this.cursor = 0;
	}

	componentDidUpdate(prevProps, prevState) {
		let mask = document.getElementById("producerCodeMask");
		if (mask && mask.value !== this.state.producerCode) {
			this.setState({producerCode: mask.value});
		}

		if (this.producerCodeRef && this.producerCodeRef.current && (prevState.producerCode != this.state.producerCode)) {
			this.producerCodeRef.current.selectionStart = this.cursor;
			this.producerCodeRef.current.selectionEnd = this.cursor;
		}
	}

	isBusinessZipCodeValid() {
		return this.state.businessZipCode != '' && !this.state.formErrors.businessZipCode;
	}

	isProducerCodeValid() {
		return this.state.producerCode != '' && this.state.producerCode != '__-___' && !this.state.formErrors.producerCode;
	}

	isProducerUser() {
		return this.props.isProducerUser;
	}

	isFormIdentityValid() {
		return this.isProducerCodeValid() && this.isBusinessZipCodeValid();
	}

	onFormIdentitySubmit(e) {
		e.preventDefault();

		let formErrors = {};

		if (!this.isBusinessZipCodeValid()) {
			formErrors.businessZipCode = true;
		}

		if (this.isProducerUser() && !this.isProducerCodeValid()) {
			formErrors.producerCode = true;
		}

		if (Object.keys(formErrors).length != 0) {
			this.setState({formErrors: formErrors});

			return;
		}

		this.submitFormForProducer();
	}

	setBusinessZipCode(fieldValue) {
		this.setState(previousState => ({
			businessZipCode: fieldValue,
			formErrors: {
				...previousState.formErrors,
				businessZipCode: null
			}
		}));
	}

	setProducerCode(fieldValue) {
		this.setState(previousState => ({
			producerCode: fieldValue,
			formErrors: {
				...previousState.formErrors,
				producerCode: null
			}
		}));

		let regex = /^(\d|_)*-?(\d|_)*$/;

		if (regex.test(fieldValue)) {
			let newCursor = this.producerCodeRef.current.selectionStart;
			let prevCursor = this.cursor;
			if (newCursor == 2 && prevCursor != 3) {
				newCursor += 1;
			}
			this.cursor = newCursor;
		}
	};

	submitFormForProducer() {
		let callback = (isUserValid) => {
			if ("valid" === isUserValid) {
				window.location.href = Liferay.ThemeDisplay.getPortalURL() + "/group/producer";
			}
			else if ("producerCodeError" === isUserValid) {
				this.setState(previousState => ({
					formErrors: {
						...previousState.formErrors,
						producerCode: true
					}
				}));
			}
			else if ("zipCodeError" === isUserValid) {
				this.setState(previousState => ({
					formErrors: {
						...previousState.formErrors,
						businessZipCode: true
					}
				}));
			}
		}

		let errCallback = () =>
			this.setState(previousState => ({
				formErrors: {
					...previousState.formErrors,
					producerCode: true,
					businessZipCode: true
				}
			}));

		let splitStrings = this.state.producerCode.split('-');

		Liferay.Service(
			'/cmic.cmicuser/validate-producer-user-registration',
			{
				cmicUserId: this.props.cmicUserId,
				agentNumber: splitStrings[1],
				divisionNumber: splitStrings[0],
				registrationCode: this.props.registrationCode,
				zipCode: this.state.businessZipCode
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
							fieldName="producerCode"
							handleFieldChange={(fieldName, fieldValue) => {
								this.setProducerCode(fieldValue)
							}}
							label={Liferay.Language.get("producer-code")}
							required={true}
							value={this.state.producerCode}
							showErrors={this.state.formErrors.producerCode}
							errorMsg={Liferay.Language.get("error.producer-code")}
							inputRef={this.producerCodeRef}
						/>

						<Input
							fieldName="businessZipCode"
							handleFieldChange={(fieldName, fieldValue) => this.setBusinessZipCode(fieldValue)}
							label={Liferay.Language.get("business-zip-code")}
							maxLength="12"
							required={true}
							value={this.state.businessZipCode}
							showErrors={this.state.formErrors.businessZipCode}
							errorMsg={Liferay.Language.get("error.zip-code")}
						/>

						<div className="ml-3 mb-4 mt-n3 small">
							{Liferay.Language.get("business-zip-code-help-text")}
						</div>

						<ClayButton disabled={!this.isFormIdentityValid()} displayType="primary" type="submit">
							{Liferay.Language.get("finish")}
						</ClayButton>
					</form>
				</ClayLayout.SheetSection>

				<div className="hide">
					<InputMask id="producerCodeMask" mask="99-999" value={this.state.producerCode} />
				</div>
			</React.Fragment>
		);
	}
}

export default ConfirmYourIdentityProducer;