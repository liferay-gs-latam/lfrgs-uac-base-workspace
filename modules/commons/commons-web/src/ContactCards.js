import React from 'react';
import ClayLayout from '@clayui/layout';
import UserCard from './UserCard';

const ContactCards = ({list, isLoading, limit, showAllContactsLink, ...otherProps}) => {
	return (
		<React.Fragment>
			<ClayLayout.Row>
				{list.slice(0, limit).map((contact, index) => (
					<ClayLayout.Col {...otherProps} key={index}>
						<UserCard
							index={index}
							firstName={contact.firstName}
							lastName={contact.lastName}
							fullName={contact.fullName}
							title={contact.title}
							email={contact.email}
							phoneNumber={contact.phoneNumber}
							phoneNumberURL={contact.phoneNumberURL}
						/>
					</ClayLayout.Col>
				))}
			</ClayLayout.Row>
			{(limit && list.length > limit) &&
				<a href={showAllContactsLink} className="link-action">{Liferay.Language.get('see-all-contacts')}</a>
			}
		</React.Fragment>
	);
};

export default ContactCards;
