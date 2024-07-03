import React from 'react';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import ClayButton from '@clayui/button';
import {Dialog, parseAsHTML, UserAvatar} from 'com.churchmutual.commons.web';
import {RoleSelect} from './RoleSelect';

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
      userList: [],
      originalCurrentUser: {},
      originalUserList: [],
      removeUserAction: {
        modalVisible: false,
        userToRemove: {}
      },
      updateUserAction: {
        modalVisible: false,
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
  }

  getBusinessMembers(producerId, accountNumber, companyNumber, isProducer) {
    let callback = (data) => {
      this.props.setHasManageUsers(producerId, accountNumber, companyNumber, isProducer);

      let currentUserEmail = Liferay.ThemeDisplay.getUserEmailAddress();

      let index = data.findIndex((user) => user.email === currentUserEmail);

      if (index > -1) {
        let currentUser = data.splice(index, 1)[0];

        this.setState({
          userList: data,
          originalUserList: data
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
        originalUserList: []
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

  updateCurrentUser(user) {
    let userCopy = JSON.parse(JSON.stringify(user));

    this.setState({currentUser: userCopy, originalCurrentUser: user});
  }

  setNewRole(user, newRole) {
    let updatedUser = {...user};
    updatedUser.role = newRole;

    this.setState({updateUserAction: {modalVisible: true, userToUpdate: updatedUser}});
  }

  roleSelect(user) {
    if (!this.props.isEditingUsers || this.state.currentUser.role !== 'owner' || user.removed) {
      return this.getLocalization(user.role);
    }

    return (
      <RoleSelect
        value={user.role}
        user={user}
        currentUser={this.state.currentUser}
        roleTypes={this.props.roleTypes}
        handleFieldChange={(user, fieldValue) =>
          this.setNewRole(user, fieldValue)}
      />
    );
  }

  removeUserButton(user) {
    if (this.props.isEditingUsers && this.props.hasManageUsers && this.state.currentUser.email != user.email && user.role != 'owner') {
      return (
        <ClayTable.Cell>
          {!user.removed && (
            <ClayButton monospaced="true" displayType="unstyled" small="true" className="text-danger"
                onClick={() => this.setState({removeUserAction: {modalVisible: true, userToRemove: user}})}>
              <ClayIcon symbol={"trash"} spritemap={this.getSpritemap()}/>
            </ClayButton>
          )}
        </ClayTable.Cell>
      );
    }
    else if (this.props.isEditingUsers) {
      return (<ClayTable.Cell />);
    }
  }

  confirmRemoveUser() {
    let user = this.state.removeUserAction.userToRemove;

    this.props.addMemberToBeRemoved(user);

    this.indicateRemovedUser(user);

    this.setState({
      removeUserAction: {
        modalVisible: false,
        userToRemove: {}
      }
    });
  }

  indicateRemovedUser(user) {
    this.setState((previousState) => {
      let newUserList = previousState.userList.map(u => {
        if (u.email === user.email) {
          return {
            ...u,
            removed: true,
            status: 'removed'
          }
        }
        return u;
      });
      return {
        userList: newUserList
      }
    });
  }

  confirmUpdateUser() {
    let userToUpdate = this.state.updateUserAction.userToUpdate;

    this.setState((previousState) => {
      let userList = [...previousState.userList];
      let index = userList.findIndex((user) => user.email == userToUpdate.email);

      userList[index] = userToUpdate;

      return {userList: userList};
    });

    if (userToUpdate.role === 'owner') {
      let currentOwner = this.getCurrentOwner();

      if (currentOwner != null) {
        currentOwner.role = 'member';

        this.props.addUpdatedUserRole(currentOwner);
      }
      else {
        this.props.displayErrorMessage('your-request-failed-to-complete');

        return;
      }
    }

    this.props.addUpdatedUserRole(userToUpdate);

    this.setState({
      updateUserAction: {
        modalVisible: false,
        userToUpdate: {}
      }
    });
  }

  getCurrentOwner() {
    let index = this.state.userList.findIndex((user) => user.role == 'owner');

    if (index != -1) {
      return this.state.userList(index);
    }

    if (this.state.currentUser.role == 'owner') {
      return this.state.currentUser;
    }

    return null;
  }

  getUpdateUserModalTitle() {
    let userToUpdate = this.state.updateUserAction.userToUpdate;
    let newRole = userToUpdate.role;

    if (newRole == "owner") {
      return Liferay.Language.get('change-owner');
    }

    return Liferay.Util.sub(Liferay.Language.get('change-role-to-x'), Liferay.Language.get(this.state.updateUserAction.userToUpdate.role));
  }

  getUpdateUserModalText() {
    let userToUpdate = this.state.updateUserAction.userToUpdate;
    let newRole = userToUpdate.role;
    let userDisplay = this.getUserFullNameOrEmail(userToUpdate);
    let message;

    if (newRole == "member") {
      message = Liferay.Language.get('are-you-sure-you-want-to-change-x-role-from-admin-to-member');
    }
    else if (newRole == "owner") {
      message = Liferay.Language.get('are-you-sure-you-want-to-make-x-the-owner-for-x');

      return Liferay.Util.sub(message, userDisplay, this.props.currentBusinessName);
    }
    else {
      return '';
    }

    return Liferay.Util.sub(message, userDisplay);
  }

  getUserFullNameOrEmail(user) {
    let userDisplay = user.fullName;

    if (userDisplay == '----') {
      userDisplay = user.email;
    }

    return userDisplay;
  }

  resetChanges() {
    this.setState({userList: this.state.originalUserList});

    this.updateCurrentUser(this.state.originalCurrentUser);
  }

  getSpritemap() {
    return Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';
  }

  getLocalization(key) {
    return key && key != '' ? Liferay.Language.get(key) : key;
  }

  render() {
    return (
      <React.Fragment>
        <ClayTable>
          <ClayTable.Head>
            <ClayTable.Row>
              <ClayTable.Cell expanded headingCell>{Liferay.Language.get('name')}</ClayTable.Cell>
              <ClayTable.Cell expanded headingCell>{Liferay.Language.get('email')}</ClayTable.Cell>
              <ClayTable.Cell headingCell>{Liferay.Language.get('role')}</ClayTable.Cell>
              <ClayTable.Cell headingCell>{Liferay.Language.get('status')}</ClayTable.Cell>
              {this.props.isEditingUsers && this.props.hasManageUsers ? <ClayTable.Cell headingCell /> : null}
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
                    className="mr-3"
                  />
                  <div className="text-nowrap data-hj-suppress">
                    {this.state.currentUser.fullName}
                    <small className="font-weight-normal">{" (" + Liferay.Language.get('me').toLowerCase() + ")"}</small>
                  </div>
                </div>
              </ClayTable.Cell>
              <ClayTable.Cell className={"data-hj-suppress"}>{this.state.currentUser.email}</ClayTable.Cell>
              <ClayTable.Cell>{this.roleSelect(this.state.currentUser)}</ClayTable.Cell>
              <ClayTable.Cell>{this.getLocalization(this.state.currentUser.status)}</ClayTable.Cell>
              {this.removeUserButton(this.state.currentUser)}
            </ClayTable.Row>

            {this.state.userList.map((user, index) => (
              <ClayTable.Row key={index} className={(user.changed || user.removed) ? "unsaved-changes" : ""}>
                <ClayTable.Cell className="h4">
                  <div className="flex-container align-items-center flex-nowrap text-nowrap">
                    <UserAvatar
                      index={index}
                      image={user.portraitImageUrl}
                      firstName={user.firstName}
                      lastName={user.lastName}
                      className="mr-3"
                    />
                    <div className="text-nowrap data-hj-suppress">{user.fullName}</div>
                  </div>
                </ClayTable.Cell>
                <ClayTable.Cell className={"data-hj-suppress"}>{user.email}</ClayTable.Cell>
                <ClayTable.Cell>{this.roleSelect(user)}</ClayTable.Cell>
                <ClayTable.Cell>{Liferay.Language.get(user.status)}</ClayTable.Cell>
                {this.removeUserButton(user)}
              </ClayTable.Row>
            ))}

          </ClayTable.Body>
        </ClayTable>
        <Dialog
          title={Liferay.Language.get('remove-user')}
          confirmButtonText={Liferay.Language.get('continue')}
          onClickConfirm={() => this.confirmRemoveUser()}
          visible={this.state.removeUserAction.modalVisible}
          setVisible={(show) => this.setState({removeUserAction: {modalVisible: show}})}
          status="warning"
        >
          <div className="lead-text text-center">
            {this.state.removeUserAction.modalVisible &&
              parseAsHTML(Liferay.Util.sub(Liferay.Language.get('are-you-sure-you-want-to-remove-x-from-x'),
                this.getUserFullNameOrEmail(this.state.removeUserAction.userToRemove), this.props.currentBusinessName))
            }
          </div>
        </Dialog>
        <Dialog
          title={this.state.updateUserAction.modalVisible && this.getUpdateUserModalTitle()}
          confirmButtonText={Liferay.Language.get('continue')}
          onClickConfirm={() => this.confirmUpdateUser()}
          visible={this.state.updateUserAction.modalVisible}
          setVisible={(show) => this.setState({updateUserAction: {modalVisible: show}})}
          status="warning"
        >
          <div className="lead-text text-center">
            {this.state.updateUserAction.modalVisible && parseAsHTML(this.getUpdateUserModalText())}
          </div>
        </Dialog>
      </React.Fragment>
    );
  }
}