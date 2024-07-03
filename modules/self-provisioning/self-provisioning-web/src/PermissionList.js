import React from 'react';
import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import {Popover, UserAvatar} from 'com.churchmutual.commons.web';

export default class extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			currentUser: {
				fullName: '',
				email: '',
				role: '',
				status: ''
			},
			permissionList: [],
			userList: [],
			originalCurrentUser: {},
			originalUserPermissionList: [],
			updateUserAction: {
				userToUpdate: {}
			}
		}
	}

	updateUserList(businessProducerId, businessAccountNumber, businessCompanyNumber, businessIsProducer) {
		let producerId = businessProducerId ? businessProducerId : this.props.producerId;
		let accountNumber = businessAccountNumber ? businessAccountNumber : this.props.accountNumber;
		let companyNumber = businessCompanyNumber ? businessCompanyNumber : this.props.companyNumber;
		let isProducer = businessIsProducer ? businessIsProducer : this.props.isProducer;

		this.getBusinessMembers(producerId, accountNumber, companyNumber, isProducer);
		this.getPermissionList(producerId, accountNumber, companyNumber, isProducer);
	}

	getBusinessMembers(producerId, accountNumber, companyNumber, isProducer) {
		let callback = (data) => {
			this.props.setHasManagePermissions(producerId, accountNumber, companyNumber, isProducer);

			let currentUserEmail = Liferay.ThemeDisplay.getUserEmailAddress();

			let index = data.findIndex((user) => user.email === currentUserEmail);

			if (index > -1) {
				let currentUser = data.splice(index, 1)[0];

				this.setState({
					userList: data,
					originalUserPermissionList: JSON.parse(JSON.stringify(data))
				});

				this.updateCurrentUser(currentUser);
			}
			else {
				this.props.displayErrorMessage('error.unable-to-retrieve-current-business-user');
			}
		}

		let errCallback = () => {
			this.props.displayErrorMessage('error.unable-to-retrieve-list-of-business-users');

			this.setState({
				currentUser: {
					fullName: '',
					email: '',
					role: '',
					status: ''
				},
				userList: [],
				originalUserPermissionList: []
			});
		}

		Liferay.Service(
			'/cmic.cmicuser/get-business-members',
			{
				cmicBusinessKey: JSON.stringify({
					producerId: producerId,
					accountNumber: accountNumber,
					companyNumber: companyNumber,
					producer: isProducer
				}),
			},
			callback,
			errCallback
		);
	}

	getPermissionList(producerId, accountNumber, companyNumber, isProducer) {
		let callback = (data) => {
			this.setState({permissionList: data});
		}

		let errCallback = () => {
			this.props.displayErrorMessage('error.unable-to-retrieve-list-of-permissions');
		}

		Liferay.Service(
			'/cmic.cmicuser/get-business-user-permissions',
			{
				cmicBusinessKey: JSON.stringify({
					producerId: producerId,
					accountNumber: accountNumber,
					companyNumber: companyNumber,
					producer: isProducer
				})
			},
			callback,
			errCallback
		);
	}

	updateCurrentUser(user) {
		let userCopy = JSON.parse(JSON.stringify(user));

		this.setState({currentUser: userCopy, originalCurrentUser: user});
	}

	permissionCheckbox(user, permission) {
		let hasPermission = false;

		if (user.permissions) {
			const userPermissions = user.permissions.split(",");
			hasPermission = userPermissions.includes(permission);
		}

		if (!this.props.isEditingPermissions || user.role === 'owner') {
			return (
				<ClayCheckbox
					checked={hasPermission}
					disabled
				/>
			);
		}

		return (
			<ClayCheckbox
				checked={hasPermission}
				onChange={() => this.props.updateUserPermission(user, permission, hasPermission)}
			/>
		);
	}

	resetChanges() {
		this.setState({userList: this.state.originalUserPermissionList});

		this.updateCurrentUser(this.state.originalCurrentUser);
	}

	render() {
		return (
			<React.Fragment>
				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell expanded headingCell>{Liferay.Language.get('name')}</ClayTable.Cell>
							{this.state.permissionList.map((permission, index) => (
								<React.Fragment key={index}>
									<ClayTable.Cell key={index} headingCell>
										{Liferay.Language.get('permission.' + permission)}
										{permission == 'MANAGE_PERMISSIONS' ? <Popover message={Liferay.Language.get('grants-users-the-ability-to-add-or-remove-any-permissions-of-other-users')} />: ''}
									</ClayTable.Cell>
								</React.Fragment>
							))}
							{this.props.isEditingPermissions ? <ClayTable.Cell headingCell /> : null}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						<ClayTable.Row className={this.state.currentUser.changed ? "unsaved-changes" : ""}>
							<ClayTable.Cell className="h4">
								<div className="flex-container align-items-center flex-nowrap text-nowrap">
									<UserAvatar
										index="0"
										image={this.state.currentUser.portraitImageUrl}
										firstName={this.state.currentUser.firstName}
										lastName={this.state.currentUser.lastName}
										className="mr-3 data-hj-suppress"
									/>
									<div className="text-nowrap data-hj-suppress">
										{this.state.currentUser.fullName}
										<small className="font-weight-normal">{" (" + Liferay.Language.get('me').toLowerCase() + ")"}</small>
										<div>
											<small className="font-weight-normal data-hj-suppress">{this.state.currentUser.email}</small>
										</div>
									</div>
								</div>
							</ClayTable.Cell>
							{this.state.permissionList.map((permission, index) => (
								<ClayTable.Cell key={index}>{this.permissionCheckbox(this.state.currentUser, permission)}</ClayTable.Cell>
							))}
						</ClayTable.Row>

						{this.state.userList.map((user, index) => (
							<ClayTable.Row key={index} className={(user.changed) ? "unsaved-changes" : ""}>
								<ClayTable.Cell className="h4">
									<div className="flex-container align-items-center flex-nowrap text-nowrap">
										<UserAvatar
											index={index}
											image={user.portraitImageUrl}
											firstName={user.firstName}
											lastName={user.lastName}
											className="mr-3 data-hj-suppress"
										/>
										<div className="text-nowrap data-hj-suppress">
											{user.fullName}
											<div>
												<small className={"data-hj-suppress"}>{user.email}</small>
											</div>
										</div>
									</div>
								</ClayTable.Cell>
								{this.state.permissionList.map((permission, index) => (
									<ClayTable.Cell key={index}>{this.permissionCheckbox(user, permission)}</ClayTable.Cell>
								))}
							</ClayTable.Row>
						))}

					</ClayTable.Body>
				</ClayTable>
			</React.Fragment>
		);
	}
}