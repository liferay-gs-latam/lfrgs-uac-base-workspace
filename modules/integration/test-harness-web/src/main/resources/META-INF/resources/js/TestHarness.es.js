import Component from 'metal-component';

class TestHarness extends Component {

	constructor(selector) {
		super();

		this._attachListeners(selector);
		this._attachRepeatableFieldListeners(selector);
	}

	_attachListeners(selector) {
		let container = document.querySelector(selector);
		let buttons = container.querySelectorAll('.api-method');

		for (var i = 0; i < buttons.length; i++) {
			var button = buttons[i];

			button.addEventListener('click', event => {
				let currentTarget = event.currentTarget;
				let resourceURL = currentTarget.dataset.url;
				let data = new FormData(event.currentTarget.form);

				currentTarget.innerText = 'Loading...';

				fetch(resourceURL, {
					method: 'post',
					headers: new Headers(),
					body: data
				}).then(response => {
					return response.json();
				}).then(data => {
					let responseContainer = container.querySelector('#response');

					if (responseContainer) {
						responseContainer.innerHTML = JSON.stringify(data, undefined, 4);
					}
				}).then(() => {
					currentTarget.innerText = 'Invoke';
				});
			});
		}
	}

	_attachRepeatableFieldListeners(selector) {
		let container = document.querySelector(selector);

		let removeButtons = container.querySelectorAll('.remove-item');

		for (let i = 0; i < removeButtons.length; i++) {
			let removeButton = removeButtons[i];

			removeButton.addEventListener('click', event => {
				let currentTarget = event.currentTarget;

				removeButton.closest(".row").remove();
			});
		}

		let addButtons = container.querySelectorAll('.add-item');

		for (let i = 0; i < addButtons.length; i++) {
			let addButton = addButtons[i];

			addButton.addEventListener('click', event => {
				let currentTarget = event.currentTarget;
				let arrayField = currentTarget.parentElement;
				let templateRow = arrayField.querySelector('.template-row');

				let newRow = templateRow.cloneNode(true);
				newRow.classList.remove(...["d-none", "template-row"]);
				let newInput = newRow.querySelector('input');
				newInput.disabled = false;
				newInput.classList.remove("disabled");

				let removeButton = newRow.querySelector('.remove-item');

				removeButton.addEventListener('click', event => {
					let currentTarget = event.currentTarget;

					removeButton.closest(".row").remove();
				});

				arrayField.insertBefore(newRow, currentTarget);
			});
		}
	}

}

export default TestHarness;