import React from 'react';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import UserAvatar from './UserAvatar';

const UserCard = (props) => {
	const userCardClassName = getCN(
		'user-card',
		props.className
	);

	return (
		<div className={userCardClassName}>
			<div className="user-card-image">
				<UserAvatar index={props.index} firstName={props.firstName} lastName={props.lastName} size="xxl" elevation="6" />
			</div>
			<div className="user-card-body">
				<h4 className="user-card-title data-hj-suppress">{props.fullName}</h4>
				{props.title &&
					<div className="user-card-subtitle data-hj-suppress">{props.title}</div>
				}
				{props.email &&
					<div className="user-card-text data-hj-suppress"><a href={`mailto:${props.email}`}>{props.email}</a></div>
				}
				{props.phoneNumber && props.phoneNumberURL &&
					<div className="user-card-text data-hj-suppress"><a href={props.phoneNumberURL}>{props.phoneNumber}</a></div>
				}
			</div>
		</div>
	)
};

export default UserCard;
