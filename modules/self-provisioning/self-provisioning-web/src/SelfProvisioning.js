import React from 'react';
import ClayCard from '@clayui/card';
import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTabs from '@clayui/tabs';
import {Toast, ChangesFeedback, ChangesTrackerContext, hasPermission} from 'com.churchmutual.commons.web';
import UserList from './UserList';
import InviteMembers from './InviteMembers';
import PermissionList from "./PermissionList";

class SelfProvisioning extends React.Component {

  static contextType = ChangesTrackerContext;

  permissionListRef;
  userListRef;

  constructor(props) {
    super(props);

    this.state = {
      activeTabKeyValue: 0,
      accountNumber: '',
      businessesList: [],
      companyNumber: '',
      hasManagePermissions: false,
      hasManageUsers: false,
      isEditingPermissions: false,
      isEditingUsers: false,
      isProducer: false,
      inviteMembersVisible: false,
      loading: false,
      membersToBeRemoved: [],
      producerId: 0,
      roleTypes: [],
      toast:  {
        displayType: '',
        message: '',
        title: '',
      },
      updatedUserPermissionList: [],
      updatedUserRoles: []
    };

    this.setHasManagePermissions = this.setHasManagePermissions.bind(this);
  }

  componentDidMount() {
    this.getBusinesses();
  }

  setHasManagePermissions(producerId, accountNumber, companyNumber, isProducer) {
    let cmicBusinessKey = JSON.stringify({
      producerId: producerId,
      accountNumber: accountNumber,
      companyNumber: companyNumber,
      producer: isProducer
    });

    hasPermission(cmicBusinessKey, 'MANAGE_PERMISSIONS', (data) => this.setState({hasManagePermissions: data}));
  }

  setHasManageUsers(producerId, accountNumber, companyNumber, isProducer) {
    let cmicBusinessKey = JSON.stringify({
      producerId: producerId,
      accountNumber: accountNumber,
      companyNumber: companyNumber,
      producer: isProducer
    });

    hasPermission(cmicBusinessKey, 'MANAGE_USERS', (data) => this.setState({hasManageUsers: data}));
  }

  updateBusinessMembers() {
    this.setState({loading: true});

    let callback = () => {
      this.userListRef.updateUserList();

      if (this.permissionListRef) {
        this.permissionListRef.updateUserList();
      }

      this.context.clearChanges();

      this.setState({
        isEditingUsers: false,
        loading: false,
        updatedUserRoles: [],
        membersToBeRemoved: [],
      });

      this.displaySuccessMessage('your-request-completed-successfully');
    }

    let errCallback = () => {this.setState({loading: false}); this.displayErrorMessage('your-request-failed-to-complete')};

    let updateUserRolesJSONString = JSON.stringify(this.state.updatedUserRoles);

    let removeUsersJSONString = JSON.stringify(this.state.membersToBeRemoved);

    Liferay.Service(
      '/cmic.cmicuser/update-business-members',
      {
        cmicBusinessKey: JSON.stringify({
          producerId: this.state.producerId,
          accountNumber: this.state.accountNumber,
          companyNumber: this.state.companyNumber,
          producer: this.state.isProducer
        }),
        updateUserRolesJSONString: updateUserRolesJSONString,
        removeUsersJSONString: removeUsersJSONString
      },
      callback,
      errCallback
    )
  }

  updateBusinessMemberPermissions() {
    this.setState({loading: true});

    let callback = () => {
      this.userListRef.updateUserList();

      if (this.permissionListRef) {
        this.permissionListRef.updateUserList();
      }

      this.context.clearChanges();

      this.setState({
        isEditingPermissions: false,
        loading: false,
        updatedUserPermissionList: []
      });

      this.displaySuccessMessage('your-request-completed-successfully');
    }

    let errCallback = () => {this.setState({loading: false}); this.displayErrorMessage('your-request-failed-to-complete')};

    let updateUserPermissionsJSONString = JSON.stringify(this.state.updatedUserPermissionList);

    Liferay.Service(
      '/cmic.cmicuser/update-business-member-permissions',
      {
        cmicBusinessKey: JSON.stringify({
          producerId: this.state.producerId,
          accountNumber: this.state.accountNumber,
          companyNumber: this.state.companyNumber,
          producer: this.state.isProducer
        }),
        updateUserPermissionsJSONString: updateUserPermissionsJSONString
      },
      callback,
      errCallback
    )
  }

  inviteMembers() {
    return this.state.hasManageUsers
      && this.state.inviteMembersVisible
      && !this.state.isEditingUsers
      && (
        <InviteMembers
          accountNumber={this.state.accountNumber}
          companyNumber={this.state.companyNumber}
          displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
          displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
          currentBusinessName={this.getCurrentBusinessName()}
          isProducer={this.state.isProducer}
          producerId={this.state.producerId}
          updateUserList={() => this.userListRef.updateUserList()}
          visible={this.state.inviteMembersVisible}
          onClickCancel={() => this.setState({inviteMembersVisible: false})}
        />
    );
  }

  cancelSavePermissionButtons() {
    return this.state.hasManagePermissions && this.state.isEditingPermissions && (
      <React.Fragment>
        <ClayButton.Group spaced>
          <ChangesFeedback />
          <ClayButton displayType="outline-secondary" className={this.state.loading ? "disabled" : ""} onClick={() => this.cancelUpdatePermissions()}>
            {Liferay.Language.get("cancel")}
          </ClayButton>
          <ClayButton displayType="primary" className={this.state.loading ? "d-none" : ""} onClick={() => this.updateBusinessMemberPermissions()}>
            {Liferay.Language.get("save")}
          </ClayButton>
        </ClayButton.Group>
        <span className={this.state.loading ? "inline-item loading-icon loading-icon-save" : "d-none"}>
            <ClayLoadingIndicator />
          </span>
      </React.Fragment>
    );
  }

  cancelSaveUserButtons() {
    return this.state.hasManageUsers && this.state.isEditingUsers && (
        <React.Fragment>
          <ClayButton.Group spaced>
              <ChangesFeedback />
              <ClayButton displayType="outline-secondary" className={this.state.loading ? "disabled" : ""} onClick={() => this.cancelUpdateAccountMembers()}>
                  {Liferay.Language.get("cancel")}
              </ClayButton>
              <ClayButton displayType="primary" className={this.state.loading ? "d-none" : ""} onClick={() => this.updateBusinessMembers()}>
                  {Liferay.Language.get("save")}
              </ClayButton>
          </ClayButton.Group>
          <span className={this.state.loading ? "inline-item loading-icon loading-icon-save" : "d-none"}>
            <ClayLoadingIndicator />
          </span>
        </React.Fragment>
    );
  }

  editPermissionsButton() {
    return this.state.hasManagePermissions && !this.state.isEditingPermissions
    && (
      <ClayButton displayType="link" small className="link-action"
          onClick={() => this.setState({
            isEditingPermissions: true,
            inviteMembersVisible: false
          })}>
          {Liferay.Language.get('edit-permissions')}
        </ClayButton>
      );
  }

  editUsersButton() {
    return this.state.hasManageUsers && !this.state.isEditingUsers
    && (
      <ClayButton displayType="link" small className="link-action"
          onClick={() => this.setState({
            isEditingUsers: true,
            inviteMembersVisible: false
          })}>
          {Liferay.Language.get('edit-users')}
        </ClayButton>
      );
  }

  cancelUpdateAccountMembers() {
    if (this.state.loading) {
      return;
    }

    this.context.clearChanges();
    this.userListRef.resetChanges();

    this.setState({
      isEditingUsers: false,
      updatedUserRoles: [],
      membersToBeRemoved: []
    });
  }

  cancelUpdatePermissions() {
    if (this.state.loading) {
      return;
    }

    this.context.clearChanges();

    if (this.permissionListRef) {
      this.permissionListRef.resetChanges();
    }

    this.setState({
      isEditingPermissions: false,
      updatedUserPermissionList: []
    });
  }

  addUserButton() {
    return this.state.hasManageUsers
      && !this.state.isEditingUsers
      && !this.state.inviteMembersVisible
      && (
        <ClayButton displayType="link" small className="link-action"
          onClick={() => this.setState({
            inviteMembersVisible: true
          })}>
          {Liferay.Language.get('Add users +')}
        </ClayButton>
    );
  }

  addUpdatedUserRole(user) {
    this.context.onChange();
    this.setState((previousState) => {
      let updatedUserRoles = [...previousState.updatedUserRoles]

      updatedUserRoles = updatedUserRoles.filter((e) => e.email !== user.email);

      user.changed = true;

      return {updatedUserRoles: [...updatedUserRoles, user]};
    });
  }

  addMemberToBeRemoved(user) {
    this.context.onChange();
    this.setState((previousState) => {
      return {membersToBeRemoved: [...previousState.membersToBeRemoved, user]}
    });
  }

  businessSelect() {
    return (
      <div className="autofit-row align-items-center mb-2 mb-lg-0">
        <div className="autofit-col px-2">{Liferay.Language.get('viewing')}</div>
        <div className="autofit-col flex-shrink-1 data-hj-suppress">
          {(() => {
            if (this.state.isEditingPermissions || this.state.isEditingUsers || this.state.businessesList.length <= 1) {
              return this.getCurrentBusinessName();
            }
            else {
              return (
                <ClaySelect className="form-control-sm"
                  onChange={(e) => this.updateGroupUsersList(e.target.value)}
                  value={this.state.isProducer ? this.state.producerId : this.state.companyNumber + "-" + this.state.accountNumber}>
                    {this.state.businessesList.map(business => (
                      <ClaySelect.Option
                        className={business.name === Liferay.Language.get('no-account-name') ? 'no-account-name' : null}
                        key={business.producerId > 0 ? business.producerId : business.companyNumber + "-" + business.accountNumber}
                        label={business.name}
                        value={business.producerId > 0 ? business.producerId : business.companyNumber + "-" + business.accountNumber}
                      />
                    ))}
                </ClaySelect>
              );
            }
          })()}
        </div>
      </div>
    );
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

  displaySuccessMessage(msg) {
    this.setState({
      toast: {
        displayType: 'success',
        message: msg,
        title: Liferay.Language.get('success-colon')
      }
    });
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

  getBusinesses(actionId, curAccountNumber, curCompanyNumber, curProducerId, curIsProducer) {
    let callback = (data) => {
      let businesses = data;
      this.setState({businessesList: businesses});

      if (businesses != null && businesses.length != 0) {
        let accountNumber = curAccountNumber ? curAccountNumber : businesses[0].accountNumber;
        let companyNumber = curCompanyNumber ? curCompanyNumber : businesses[0].companyNumber;
        let producerId = curProducerId ? curProducerId : businesses[0].producerId;
        let isProducer = curIsProducer ? curIsProducer : producerId > 0;

        this.setHasManagePermissions(producerId, accountNumber, companyNumber, isProducer);

        this.setState({
          accountNumber: accountNumber,
          companyNumber: companyNumber,
          isProducer: isProducer,
          producerId: producerId
        });

        this.userListRef.updateUserList(producerId, accountNumber, companyNumber, isProducer);

        if (this.permissionListRef) {
          this.permissionListRef.updateUserList(producerId, accountNumber, companyNumber, isProducer);
        }

        this.getRoleTypes();
      }
    }

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-user-businesses');

    Liferay.Service(
      '/cmic.cmicuser/get-businesses-with-permission',
      {
        actionId: actionId ? actionId : ''
      },
      callback,
      errCallback
    )
  }

  getRoleTypes() {
    let callback = (data) => this.setState({ roleTypes: data });

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-business-roles');

    Liferay.Service(
      '/cmic.cmicuser/get-business-roles',
      callback,
      errCallback
    );
  }

  getCurrentBusinessName() {
    let business;

    if (this.state.isProducer) {
      business = this.state.businessesList.find((b) => b.producerId == this.state.producerId);
    }
    else {
      business = this.state.businessesList.find((b) =>
        (b.accountNumber === this.state.accountNumber && b.companyNumber === this.state.companyNumber));
    }

    if (!business) {
      return '';
    }

    return business.name;
  }

  setActiveTabKeyValue(value) {
    this.setState({
      activeTabKeyValue: value
    });

    if (value == 1) {
      this.getBusinesses(
        'MANAGE_PERMISSIONS', this.state.accountNumber, this.state.companyNumber, this.state.producerId,
        this.state.isProducer);
    }
    else {
      this.getBusinesses(
        '', this.state.accountNumber, this.state.companyNumber, this.state.producerId, this.state.isProducer);
    }
  }

  updateGroupUsersList(businessId) {
    let accountNumber = '';
    let companyNumber = '';
    let producerId = 0;
    let isProducer = false;

    let ids = businessId.split("-");

    if (ids.length == 1) {
      producerId = ids[0];
      isProducer = true;
    }
    else {
      accountNumber = ids[1];
      companyNumber = ids[0];
    }

    this.setHasManagePermissions(producerId, accountNumber, companyNumber, isProducer);

    this.setState({
      accountNumber: accountNumber,
      companyNumber: companyNumber,
      isProducer: isProducer,
      producerId: producerId,
      isEditingUsers: false,
      inviteMembersVisible: false,
      membersToBeRemoved: [],
      toast:  {
        displayType: '',
        message: '',
        title: '',
      },
      updatedUserRoles: []
    });

    this.userListRef.updateUserList(producerId, accountNumber, companyNumber, isProducer);

    if (this.permissionListRef) {
      this.permissionListRef.updateUserList(producerId, accountNumber, companyNumber, isProducer);
    }

    this.getRoleTypes();
  }

  updateUserPermission(user, permission, hasPermission) {
    this.setState((previousState) => {
      let userList = [...previousState.updatedUserPermissionList];
      let index = userList.findIndex((curUser) => curUser.email == user.email);
      let permissions = user.permissions.length > 0 ? user.permissions.split(",") : [];

      if (!hasPermission) {
        permissions.push(permission);
      }
      else {
        permissions = permissions.filter(curPermission => curPermission != permission);
      }

      user.permissions = permissions.join();
      user.changed = true;

      if (index > -1) {
        userList[index] = user;
      }
      else {
        userList.push(user);
      }

      this.context.onChange();

      return {updatedUserPermissionList: userList};
    });
  }

  render() {
    return (
      <div className="self-provisioning-portlet">
        <ClayCard>
          <ClayTabs>
            <ClayTabs.Item
              active={this.state.activeTabKeyValue === 0}
              disabled={this.state.isEditingPermissions}
              innerProps={{
                "aria-controls": "tabpanel-1"
              }}
              onClick={() => this.setActiveTabKeyValue(0)}
            >
              {Liferay.Language.get('users')}
            </ClayTabs.Item>
            {this.state.hasManagePermissions ?
              <ClayTabs.Item
                active={this.state.activeTabKeyValue === 1}
                disabled={this.state.isEditingUsers}
                innerProps={{
                  "aria-controls": "tabpanel-2"
                }}
                onClick={() => this.setActiveTabKeyValue(1)}
              >
                {Liferay.Language.get('permissions')}
              </ClayTabs.Item>: ''}
          </ClayTabs>
          <ClayTabs.Content activeIndex={this.state.activeTabKeyValue}>
            <ClayTabs.TabPane active="true" aria-labelledby="tab-1">
              <ClayCard.Body className="fade-table scrollable-table">
                <div key={0} className="row no-gutters align-items-center mb-3">
                  <div className="col py-2">
                    {this.businessSelect()}
                  </div>
                  <div className="col-md-auto">
                    {this.editUsersButton()}
                    {this.cancelSaveUserButtons()}
                  </div>
                </div>

                <UserList
                  key={1}
                  accountNumber={this.state.accountNumber}
                  addMemberToBeRemoved={(user) => this.addMemberToBeRemoved(user)}
                  addUpdatedUserRole={(user) => this.addUpdatedUserRole(user)}
                  companyNumber={this.state.companyNumber}
                  currentBusinessName={this.getCurrentBusinessName()}
                  displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
                  displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
                  hasManageUsers={this.state.hasManageUsers}
                  isEditingUsers={this.state.isEditingUsers}
                  isProducer={this.state.isProducer}
                  producerId={this.state.producerId}
                  ref={(userListRef) => this.userListRef = userListRef}
                  roleTypes={this.state.roleTypes}
                  setHasManageUsers={(producerId, accountNumber, companyNumber, isProducer) => this.setHasManageUsers(producerId, accountNumber, companyNumber, isProducer)}
                />
              </ClayCard.Body>
              <div className="card-footer">
                {this.addUserButton()}
                {this.inviteMembers()}
              </div>
            </ClayTabs.TabPane>
            <ClayTabs.TabPane active="true" aria-labelledby="tab-2">
              <ClayCard.Body className="fade-table scrollable-table">
                <div key={0} className="row no-gutters align-items-center mb-3">
                  <div className="col py-2">
                    {this.businessSelect()}
                  </div>
                  <div className="col-md-auto">
                    {this.editPermissionsButton()}
                    {this.cancelSavePermissionButtons()}
                  </div>
                </div>

                <PermissionList
                  key={1}
                  accountNumber={this.state.accountNumber}
                  companyNumber={this.state.companyNumber}
                  displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
                  isEditingPermissions={this.state.isEditingPermissions}
                  isProducer={this.state.isProducer}
                  producerId={this.state.producerId}
                  ref={(permissionListRef) => this.permissionListRef = permissionListRef}
                  setHasManagePermissions={(producerId, accountNumber, companyNumber, isProducer) => this.setHasManagePermissions(producerId, accountNumber, companyNumber, isProducer)}
                  updateUserPermission={(user, permission, hasPermission) => this.updateUserPermission(user, permission, hasPermission)}
                />
              </ClayCard.Body>
            </ClayTabs.TabPane>
          </ClayTabs.Content>
        </ClayCard>

        <Toast
          displayType={this.state.toast.displayType}
          message={this.state.toast.message}
          onClose={() => this.onToastClosed()}
          title={this.state.toast.title} />
      </div>
    );
  }
};

SelfProvisioning.contextType = ChangesTrackerContext;

export default SelfProvisioning;