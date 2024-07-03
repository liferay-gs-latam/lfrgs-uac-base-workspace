import React from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Dialog, Input, Toast} from "com.churchmutual.commons.web";

class SelfProvisioningInviteInsured extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      account: {
        accountName: '',
        accountNumber: ''
      },
      dialogModal: {
        visible: ''
      },
      emails: '',
      formErrors: {
        emails: null
      },
      invitationsSent: false,
      toast:  {
        displayType: '',
        message: '',
        title: '',
      },
      isLoading: false
    };
  }

  cancelInvitations() {
    this.setState({
      emails: '',
      formErrors: {
        emails: null
      }
    });
  }

  componentDidMount() {
    this.getAccount();
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

  emailsAreValid() {
    let emails = this.state.emails.split(',');

    for (const email of emails) {
      let index = email.indexOf('@');

      if (index < 0 || email.indexOf('.', index) < 0) {
        return false;
      }

      // check if multiple emails exist without a separator, denoted by special character @

      const re = new RegExp('@.*@');

      if (re.test(email)) {
        return false;
      }
    }

    return true;
  }

  getSpriteMap() {
    return Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
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

  getAccount() {
    let accountNumber = this.getCMICAccountNumber();
    let companyNumber = this.getCMICCompanyNumber();

    if (accountNumber == null || companyNumber == null) {
      return;
    }

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-current-account')

    let accountCallback = (account) => {
      this.setState({
        account: {
          accountName: account.accountName,
          accountNumber: accountNumber,
          companyNumber: account.companyNumber
        }
      });
    }

    Liferay.Service(
      '/cmic.cmicaccountentry/get-cmic-account-entry-display',
      {
        cmicBusinessKey: JSON.stringify({
          producerId: '0',
          accountNumber: accountNumber,
          companyNumber: companyNumber,
          producer: false
        })
      },
      accountCallback,
      errCallback
    );
  }

  getCMICAccountNumber() {
    let urlSearchParameters = new URLSearchParams(window.location.search);

    return urlSearchParameters.get('accountNumber');
  }

  getCMICCompanyNumber() {
    let urlSearchParameters = new URLSearchParams(window.location.search);

    return urlSearchParameters.get('companyNumber');
  }

  sendInsuredInvitations() {
    if (!this.emailsAreValid()) {
      this.setState({
        formErrors: {
          emails: true
        },
      });

      return;
    }

    let callback = (data) => {
      this.setState({
        isLoading: false
      });

      if (data == 'PROVISIONING_DUPLICATE_USER_ERROR') {
        let errorMsg = 'error.one-or-more-email-addresses-are-already-registered-for-x-please-use-a-different-email-address';

        this.displayErrorMessage(Liferay.Util.sub(Liferay.Language.get(errorMsg), this.state.account.accountName));
      }
      else if (data == 'PROVISIONING_USER_TYPE_ERROR') {
        let errorMsg = 'error.one-or-more-email-addresses-are-registered-for-the-producer-portal-please-use-a-different-email-address';

        this.displayErrorMessage(Liferay.Language.get(errorMsg));
      }
      else if (data == 'PROVISIONING_SUCCESS') {
        this.setState({
          invitationsSent: true
        });
      }
      else {
        this.displayErrorMessage('your-request-failed-to-complete');
      }
    }

    let errCallback = () => {this.setState({isLoading: false}); this.displayErrorMessage('your-request-failed-to-complete')};

    this.setState({isLoading: true});

    Liferay.Service(
      '/cmic.cmicuser/invite-business-members',
      {
        cmicBusinessKey: JSON.stringify({
          producerId: '0',
          accountNumber: this.state.account.accountNumber,
          companyNumber: this.state.account.companyNumber,
          producer: false
        }),
        emailAddresses: this.state.emails
      },
      callback,
      errCallback
    );
  }

  showInviteDialog(show) {
    this.setState({
      dialogModal: {
        visible: show
      }
    });

    if (!show) {
      this.setState({
        invitationsSent: false,
        emails: '',
        formErrors: {
          emails: null
        }
      });
    }
  }

  render() {
    return(
      <div className="self-provisioning-invite-insured-portlet elevation-2 p-4 sheet">
        <div className="align-items-center flex-container mb-2">
          <ClayIcon className="invite-member-icon mr-2" spritemap={this.getSpriteMap()} symbol="user-plus" />
          <h4 className="font-weight-bold m-0">
            {Liferay.Language.get('invite-insured-users')}
          </h4>
        </div>

        <div>
          {Liferay.Language.get('give-insureds-access-to-this-account-by-inviting-them-with-an-email')}
        </div>
        <ClayButton
          borderless="true"
          className="link-action m-0 p-0 text-uppercase"
          onClick={() => this.showInviteDialog(true)}>
          <h5 className="pt-3 text-left">
            {Liferay.Language.get('invite')}
          </h5>
        </ClayButton>

        <Dialog
          title={Liferay.Util.sub(Liferay.Language.get('invite-insureds-to-x'), this.state.account.accountName)}
          confirmButtonText={this.state.invitationsSent ? Liferay.Language.get('done') : Liferay.Language.get('invite')}
          onClickConfirm={this.state.invitationsSent ? () => this.showInviteDialog(false) : () => this.sendInsuredInvitations()}
          onClickCancel={() => this.cancelInvitations()}
          loading={this.state.isLoading}
          closeOnConfirm={this.state.invitationsSent}
          hideCancel={this.state.invitationsSent}
          disableCancel={this.state.isLoading}
          visible={this.state.dialogModal.visible}
          setVisible={(show) => this.showInviteDialog(show)}
        >
          {this.state.invitationsSent ?
            <div className="my-3 py-4">
              <div className="font-weight-bolder py-2 text-success text-center">
                {Liferay.Language.get('invitations-sent')}
              </div>
            </div> :
            <div>
              <Input
                fieldName="emails"
                label={Liferay.Language.get('emails')}
                labelHint={!this.state.formErrors.emails ? Liferay.Language.get('separate-emails-by-commas') : null}
                handleFieldChange={(fieldName, fieldValue) => this.setState({formErrors: {emails: null}, emails: fieldValue})}
                value={this.state.emails}
                required={true}
                showRequired={false}
                showErrors={this.state.formErrors.emails}
                errorMsg={Liferay.Language.get('error.emails')}
              />
            </div>
          }
        </Dialog>

        <Toast
          displayType={this.state.toast.displayType}
          message={this.state.toast.message}
          onClose={() => this.onToastClosed()}
          title={this.state.toast.title} />
      </div>
    );
  }
};

export default SelfProvisioningInviteInsured;