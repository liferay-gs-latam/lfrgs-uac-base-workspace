import {base64ToBlob, isAndroid} from 'com.churchmutual.commons.web';

function createBlob(base64String, contentType) {
	const byteCharacters = atob(base64String);

	const bytes = new Array(byteCharacters.length);

	for (let i = 0; i < byteCharacters.length; i++) {
		bytes[i] = byteCharacters.charCodeAt(i);
	}

	const byteArray = new Uint8Array(bytes);

	return new Blob([byteArray], {type: contentType});
}

export function download(data, fileName, displaySuccessMessage) {
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
		displaySuccessMessage('file-was-successfully-downloaded-to-your-files-app');
	}
}