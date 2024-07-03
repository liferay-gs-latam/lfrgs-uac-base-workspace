import React from 'react';
import {Toast} from 'com.churchmutual.commons.web';
import NotificationList from "./NotificationList";

class Notification extends React.Component {

	constructor(props) {
		super(props);

		this.removeNotification = this.removeNotification.bind(this);
		this.markNotificationAsRead = this.markNotificationAsRead.bind(this);

		this.state = {
			announcementsList: [],
			isAnnouncementsLoading: true,
			isNotificationsLoading: true,
			notificationsList: [],
			toast:  {
				displayType: '',
				message: '',
				title: '',
			}
		}
	}

	componentDidMount() {
		this.getAnnouncements();
		this.getNotifications();
	}

	decrementNotificationsCount() {
		let nodeList = document.getElementsByClassName("notification-count");

		for (let i = 0; i < nodeList.length; i++) {
			let node = nodeList[i];
			let newCount = parseInt(node.innerHTML) - 1;

			if (newCount > 0) {
				node.innerHTML = newCount.toString();
			}

			if (newCount === 0) {
				node.parentNode.classList.add('hide');
			}
		}
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

	getAnnouncements() {
		let callback = (data) => this.setState({
			announcementsList: data,
			isAnnouncementsLoading: false
		});

		let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-announcements');

		Liferay.Service(
			'/cmic.cmicannouncementandnotification/get-announcement-displays',
			callback,
			errCallback
		);
	}

	getNotifications() {
		let callback = (data) => this.setState({
			notificationsList: data,
			isNotificationsLoading: false
		});

		let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-notifications');

		Liferay.Service(
			'/cmic.cmicannouncementandnotification/get-notification-displays',
			callback,
			errCallback
		);
	}

	markNotificationAsRead(notificationToMark) {
		if (!notificationToMark.isRead) {
			if (notificationToMark.announcementId) {
				let errCallback = () => this.displayErrorMessage('error.unable-to-update-list-of-announcements');

				Liferay.Service(
					'/cmic.cmicannouncementandnotification/mark-as-read-announcement',
					{
						announcementEntryId: notificationToMark.announcementId
					},
					() => this.decrementNotificationsCount(),
					errCallback
				);

				let announcementsList = this.state.announcementsList;

				let index = announcementsList.findIndex((announcement) => announcement.announcementId === notificationToMark.announcementId);

				if (index > -1) {
					announcementsList[index].isRead = true;

					this.setState({
						announcementsList: announcementsList
					});
				}
			}
			else if (notificationToMark.notificationId) {
				let errCallback = () => this.displayErrorMessage('error.unable-to-update-list-of-notifications');

				Liferay.Service(
					'/cmic.cmicannouncementandnotification/mark-as-read-notification',
					{
						userNotificationEventId: notificationToMark.notificationId
					},
					() => this.decrementNotificationsCount(),
					errCallback
				);

				let notificationsList = this.state.notificationsList;

				let index = notificationsList.findIndex((notification) => notification.notificationId === notificationToMark.notificationId);

				if (index > -1) {
					notificationsList[index].isRead = true;

					this.setState({
						notificationsList: notificationsList
					});
				}
			}
		}
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

	removeNotification(notificationToRemove){
		let callback = () => {
			if (!notificationToRemove.isRead) {
				this.decrementNotificationsCount();
			}
		};

		if (notificationToRemove.announcementId) {
			let errCallback = () => this.displayErrorMessage('error.unable-to-update-list-of-announcements');

			Liferay.Service(
				'/cmic.cmicannouncementandnotification/dismiss-announcement',
				{
					announcementEntryId: notificationToRemove.announcementId
				},
				callback,
				errCallback
			);

			this.setState(prevState => ({
				announcementsList: prevState.announcementsList.filter(announcement => announcement.announcementId !== notificationToRemove.announcementId)
			}));
		}
		else if (notificationToRemove.notificationId) {
			let errCallback = () => this.displayErrorMessage('error.unable-to-update-list-of-notifications');

			Liferay.Service(
				'/cmic.cmicannouncementandnotification/delete-notification',
				{
					userNotificationEventId: notificationToRemove.notificationId
				},
				callback,
				errCallback
			);

			this.setState(prevState => ({
				notificationsList: prevState.notificationsList.filter(notification => notification.notificationId !== notificationToRemove.notificationId)
			}));
		}
	}

	render() {
		return (
			<div className="notification-portlet">
				<div className="notification-section">
					<div className="notification-section-header">
						<p className="notification-section-title">
							{Liferay.Language.get('announcements')} ({this.state.announcementsList.length})
						</p>
					</div>
					<NotificationList
						markNotificationAsRead={this.markNotificationAsRead}
						removeNotification={this.removeNotification}
						isLoading={this.state.isAnnouncementsLoading}
						notifications={this.state.announcementsList}
					/>
				</div>
				<div className="notification-section">
					<div className="notification-section-header">
						<p className="notification-section-title">
							{Liferay.Language.get('notifications')} ({this.state.notificationsList.length})
						</p>
					</div>
					<NotificationList
						markNotificationAsRead={this.markNotificationAsRead}
						removeNotification={this.removeNotification}
						isLoading={this.state.isNotificationsLoading}
						notifications={this.state.notificationsList}
					/>
				</div>
				<Toast
					displayType={this.state.toast.displayType}
					message={this.state.toast.message}
					onClose={() => this.onToastClosed()}
					title={this.state.toast.title} />
			</div>
		);
	}
}

export default Notification;