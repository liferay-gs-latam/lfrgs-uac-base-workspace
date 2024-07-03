import React from 'react';
import ReactDOM from 'react-dom';

const content = document.getElementById('content');

class CardPageHeader extends React.Component {
	constructor(props) {
		super(props);

		this.el = document.createElement('div');
		this.el.classList.add('card-page-header');
	}

	componentDidMount() {
		content.insertBefore(this.el, content.childNodes[0]);
	}

	componentWillUnmount() {
		content.removeChild(this.el);
	}

	render() {
		return ReactDOM.createPortal(
			this.props.children,
			this.el
		);
	}
}

export default CardPageHeader;