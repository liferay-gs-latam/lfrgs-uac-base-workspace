import React from 'react';
import {AsteriskIcon, base64ToBlob, Dialog, isAndroid, Select, Toast} from 'com.churchmutual.commons.web';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import RequestError from './RequestError';
import RequestSuccess from './RequestSuccess';

class RequestFormModal extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			accountsList: [],
			customErrorMessage: '',
			isLoading: false,
			otherReasonValue: '',
			selectedAccount: '',
			selectedReasonForRequest: '',
			showRequestError: false,
			showRequestSuccess: false,
			toast:  {
				displayType: '',
				message: '',
				title: '',
			}
		}
	}

	componentDidMount() {
		this.getAccountsList();
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

	displaySuccessMessage(msg) {
		this.setState({
			toast: {
				displayType: 'success',
				message: msg
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

	downloadLossRunsReport(data, fileName) {
		let blob = base64ToBlob(data, 'application/pdf');

		if (window.navigator && window.navigator.msSaveOrOpenBlob) {
			window.navigator.msSaveOrOpenBlob(blob, fileName);

			return;
		}

		const blobUrl = URL.createObjectURL(blob);

		const link = document.createElement('a');

		link.href = blobUrl;
		link.download = fileName;
		link.target = '_blank';
		link.className = 'd-none';

		let footer = document.getElementsByClassName('modal-footer');

		if (footer.length > 0) {
			footer[0].appendChild(link);
			link.click();
			footer[0].removeChild(link);
		} else {
			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);
		}

		if (Liferay.Browser.isMobile() && isAndroid()) {
			this.displaySuccessMessage('file-was-successfully-downloaded-to-your-files-app');
		}
	}

	formInputIsInvalid() {
		return this.state.selectedAccount === '' || this.getRequestReason() === '';
	}

	getAccountsList() {
		let callback = (data) => this.setState({accountsList: data.sort((a, b) => {return a.accountNumber.localeCompare(b.accountNumber)})});

		let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts');

		Liferay.Service(
			'/cmic.cmicaccountentry/get-cmic-account-entry-displays-with-permission',
			{
				actionId: 'REQUEST_LOSS_RUNS'
			},
			callback,
			errCallback
		);
	}

	getRequestReason() {
		return this.state.selectedReasonForRequest === 'other' ? this.state.otherReasonValue : this.state.selectedReasonForRequest;
	}

	hasSubmittedForm() {
		return this.state.showRequestSuccess || this.state.showRequestError;
	}

	onClickDone() {
		this.setState({
			isLoading: false,
			otherReasonValue: '',
			selectedAccount: '',
			selectedReasonForRequest: '',
			showRequestError: false,
			showRequestSuccess: false
		});
	}

	onClickDownload() {
		if (this.formInputIsInvalid()) {
			return;
		}

		this.setState({isLoading: true});

		let callback = (data) => {
			if (data.bytes) {
				this.setState({
					showRequestSuccess: true,
					isLoading: false
				});

				this.downloadLossRunsReport(data.bytes, data.fileName);
			}
			else {
				this.setState({
					customErrorMessage: 'error.there-is-no-recent-claim-information-to-generate-your-report',
					showRequestError: true,
					isLoading: false
				});
			}
		}

		let errCallback = () => {
			this.setState({
				customErrorMessage: '',
				showRequestError: true,
				isLoading: false
			})
		};

		let index = this.state.accountsList.findIndex(account => this.state.selectedAccount === account.accountNumber);

		Liferay.Service(
			'/cmic.cmiclossrun/generate-loss-runs-report',
			{
				cmicBusinessKey: JSON.stringify({
					producerId: '0',
					accountNumber: this.state.selectedAccount,
					companyNumber: this.state.accountsList[index].companyNumber,
					producer: false
				}),
				requestReason: this.getRequestReason()
			},
			callback,
			errCallback
		);
	}

	render() {
		let hasSubmittedForm = this.hasSubmittedForm();

		return (
			<Dialog
				size="lg"
				className={`form-modal-dialog ${this.state.showRequestError || this.state.showRequestSuccess ? 'modal-shrink' : ''}`}
				title={Liferay.Language.get('request-a-loss-runs-report')}
				confirmButtonText={!hasSubmittedForm ? Liferay.Language.get('download') : Liferay.Language.get('done')}
				disableConfirm={this.formInputIsInvalid()}
				hideCancel={hasSubmittedForm}
				onClickConfirm={!hasSubmittedForm ? () => this.onClickDownload() : () => this.onClickDone()}
				onClickCancel={() => this.onClickDone()}
				closeOnConfirm={hasSubmittedForm}
				loading={!hasSubmittedForm ? this.state.isLoading : false}
				setVisible={this.props.setVisible}
				visible={this.props.visible}
			>
				<div className="loss-run-modal">
					{this.state.showRequestError
						? <RequestError customErrorMessage={this.state.customErrorMessage} />
						: this.state.showRequestSuccess
						? <RequestSuccess
								startDownload={() => this.onClickDownload()}
								isLoading={this.state.isLoading} />
						: <React.Fragment>
								<div className="font-weight-bold text-wrap-indent">{Liferay.Language.get('select-an-account')}</div>

								<div className="account-number-select pb-2">
									<Select
										handleFieldChange={(fieldName, fieldValue) => this.setState({selectedAccount: fieldValue})}
										fieldName={Liferay.Language.get('account-number')}
										label={Liferay.Language.get('account-number')}
										options={[{label: '', value: ''}].concat([...this.state.accountsList].sort((a, b) => a.accountNumber - b.accountNumber).map((item) => ({label: item.accountNumber, value: item.accountNumber})))}
										required={true}
										containsSensitiveInformation={true}
									/>
								</div>

								<div className="font-weight-bold my-4 text-wrap-indent">{Liferay.Language.get('reason-for-request-of-a-loss-run-report')}<AsteriskIcon /></div>

								<ClayRadioGroup
									className="font-weight-bold"
									onSelectedValueChange={value => this.setState({selectedReasonForRequest: value})}
									selectedValue={this.state.selectedReasonForRequest}
								>
									<ClayRadio
										label={Liferay.Language.get('i-am-shopping-for-new-or-additional-insurance-coverage')}
										value={Liferay.Language.get('i-am-shopping-for-new-or-additional-insurance-coverage')}
										containerProps={{className: 'mb-4 ml-0 ml-sm-3 radio-wrap-indent'}} />

									<ClayRadio
										label={Liferay.Language.get('i-have-switched-to-a-new-insurance-company')}
										value={Liferay.Language.get('i-have-switched-to-a-new-insurance-company')}
										containerProps={{className: 'mb-4 ml-0 ml-sm-3 radio-wrap-indent'}} />

									<ClayRadio
										label={
											<span className="align-items-center d-flex-inline text-nowrap">
												{Liferay.Language.get('other')}
												<input className="d-inline form-control ml-3" disabled={this.state.selectedReasonForRequest != 'other'} id="otherReasonInput" placeholder="Reason description" type="text" onChange={e => this.setState({otherReasonValue: e.target.value})}/>
											</span>
										}
										value="other"
										containerProps={{className: 'mb-4 ml-0 ml-sm-3 radio-with-input text-nowrap'}} />

								</ClayRadioGroup>
							</React.Fragment>
					}
				</div>

				<Toast
					displayType={this.state.toast.displayType}
					message={this.state.toast.message}
					onClose={() => this.onToastClosed()}
					title={this.state.toast.title} />
			</Dialog>
		);
	}
}

export default RequestFormModal;