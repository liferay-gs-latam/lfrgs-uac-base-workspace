import React from 'react';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {parseAsHTML} from 'com.churchmutual.commons.web';

const spritemap = themeDisplay.getPathThemeImages() + '/clay/icons.svg';

class NotificationList extends React.Component {
	constructor(props) {
		super(props);
	}

	markNotificationAsRead(notificationToMark) {
		this.props.markNotificationAsRead(notificationToMark);

		if (notificationToMark.URL) {
			Liferay.Util.navigate(notificationToMark.URL);
		}
	}

	removeNotification(notificationToRemove) {
		this.props.removeNotification(notificationToRemove);
	}

	render() {
		if (this.props.isLoading) {
			return (<ClayLoadingIndicator />);
		}
		else {
			return (
				<div className="notification-list">
					{this.props.notifications.map((notification, index) => (
						<div className="notification" key={index}>
							<a
								className="cursor-pointer notification-remove"
								onClick={this.removeNotification.bind(this, notification)}>
								<ClayIcon className="lexicon-icon-times" symbol="times" spritemap={spritemap} />
							</a>
							<div className="cursor-pointer" onClick={this.markNotificationAsRead.bind(this, notification)}>
								<div className="notification-header">
									{notification.isImportant ?
										<div>
											<ClayLabel
												className="font-weight-bold"
												displayType="warning"
												large={true}
											>
												{Liferay.Language.get('important')}
											</ClayLabel>
										</div>
										: ''
									}
									<span className="notification-date text-muted">{notification.date}</span>
									<span className="notification-unread">{notification.isRead ? '' : Liferay.Language.get('unread')}</span>
								</div>
								<div className="notification-title data-hj-suppress">
									{notification.title}
								</div>
								<div className="notification-body data-hj-suppress">
									{parseAsHTML(notification.message)}
								</div>
							</div>
						</div>
					))}
				</div>
			);
		}
	}
};

export default NotificationList;