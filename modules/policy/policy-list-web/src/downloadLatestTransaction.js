import {base64ToBlob, isAndroid} from 'com.churchmutual.commons.web';

export function downloadLatestTransaction(e, policy, accountNumber, displayErrorMessage, displaySuccessMessage) {
	if (policy != null) {
		let downloadIcon = e.currentTarget.querySelector(".download-icon");
		let loadingIcon = e.currentTarget.querySelector(".loading-icon");

		if (downloadIcon.classList.contains("d-none")) {
			return;
		}

		downloadIcon.classList.add("d-none");
		loadingIcon.classList.remove("d-none");

		let callback = (data) => {
			const blob = base64ToBlob(data.bytes, data.mimeType);

			if (window.navigator && window.navigator.msSaveOrOpenBlob) {
				window.navigator.msSaveOrOpenBlob(blob, data.name);

				downloadIcon.classList.remove("d-none");
				loadingIcon.classList.add("d-none");

				return;
			}

			const blobUrl = URL.createObjectURL(blob);

			const link = document.createElement('a');

			link.href = blobUrl;
			link.download = data.name;
			link.target = '_blank';

			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);

			if (Liferay.Browser.isMobile() && isAndroid()) {
				displaySuccessMessage('file-was-successfully-downloaded-to-your-files-app');
			}

			downloadIcon.classList.remove("d-none");
			loadingIcon.classList.add("d-none");
		}

		let errCallback = () => {
			downloadIcon.classList.remove("d-none");
			loadingIcon.classList.add("d-none");
			displayErrorMessage('error.a-policy-document-is-not-available-at-this-time');
		}

		Liferay.Service(
			'/cmic.cmicqspolicy/download-policy-transaction',
			{
				accountNumber: accountNumber,
				policyNumber: policy.policyNumber,
				policyType: policy.policyType,
				sequenceNumber: policy.currentSequenceNumber
			},
			callback,
			errCallback
		);
	}
}