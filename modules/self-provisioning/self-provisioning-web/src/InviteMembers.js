import React from 'react';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Input} from 'com.churchmutual.commons.web';

export default class extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      emails: '',
      loading: false,
      showErrors: false
    };
    this.emailsInputRef = React.createRef();
  }

  cancelSubmission() {
    if (this.state.loading) {
      return;
    }

    this.clearMemberEmails();
  }

  clearMemberEmails() {
    this.setState({
      emails: '',
      showInviteMembers: false
    });
    this.emailsInputRef.current.value = '';
    this.setShowErrors(false);
    this.props.onClickCancel();
  }

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  setIsLoading(value) {
    this.setState({loading: value});
  }

  setShowErrors(value) {
    this.setState({
      showErrors: value
    });
  }

  updateAccountUserEntries() {
    this.props.updateUserList();
  }

  updateInvitations(value) {
    if (this.state.showErrors) {
      this.setShowErrors(false);
    }

    this.setState({
      emails: value
    })
  }

  submit() {
    this.setIsLoading(true);

    let callback = (data) => {
      this.setIsLoading(false);

      if (data == 'PROVISIONING_DUPLICATE_USER_ERROR') {
        let errorMsg = 'error.one-or-more-email-addresses-are-already-registered-for-x-please-use-a-different-email-address';

        this.props.displayErrorMessage(Liferay.Util.sub(Liferay.Language.get(errorMsg), this.props.currentBusinessName));
      }
      else if (data == 'PROVISIONING_USER_TYPE_ERROR') {
        let errorMsg = 'error.one-or-more-email-addresses-are-registered-for-the-producer-portal-please-use-a-different-email-address';

        if (this.props.isProducer) {
          errorMsg = 'error.one-or-more-email-addresses-are-registered-for-the-insured-portal-please-use-a-different-email-address';
        }

        this.props.displayErrorMessage(Liferay.Language.get(errorMsg));
      }
      else if (data == 'PROVISIONING_SUCCESS') {
        this.setShowErrors(false);
        this.updateAccountUserEntries();
        this.clearMemberEmails();
        this.props.displaySuccessMessage('your-request-completed-successfully');
      }
      else {
        this.props.displayErrorMessage('your-request-failed-to-complete');
      }
    }

    let errCallback = () => {this.setIsLoading(false); this.props.displayErrorMessage('your-request-failed-to-complete')};

    Liferay.Service(
      '/cmic.cmicuser/invite-business-members',
      {
        cmicBusinessKey: JSON.stringify({
          producerId: this.props.producerId,
          accountNumber: this.props.accountNumber,
          companyNumber: this.props.companyNumber,
          producer: this.props.isProducer
        }),
        emailAddresses: this.state.emails
      },
      callback,
      errCallback
    );
  }

  validateAndSubmit() {
    let emails = this.state.emails.split(',');

    for (const email of emails) {
      let index = email.indexOf('@');

      if (index < 0 || email.indexOf('.', index) < 0) {
        this.setShowErrors(true);
        return;
      }

      // check if multiple emails exist without a separator, denoted by special character @

      const re = new RegExp('@.*@');

      if (re.test(email)) {
        this.setShowErrors(true);
        return;
      }
    }

    this.submit();
  }

  render () {
    return this.props.visible && (
      <div className="row">
        <div className="col data-hj-suppress">
          <Input
            label={Liferay.Language.get('email-s')}
            fieldName="invitationEmailsInput"
            handleFieldChange={(fieldName, fieldValue) => this.updateInvitations(fieldValue)}
            showErrors={this.state.showErrors}
            errorMsg={Liferay.Language.get('please-enter-a-valid-email-address')}
            labelHint={Liferay.Util.sub(Liferay.Language.get('add-email-to-invite-user-to-x'), this.props.currentBusinessName)}
            inputRef={this.emailsInputRef}
          />
        </div>
        <div className="col-md-auto">
          <ClayButton.Group spaced>
            <ClayButton displayType="outline-secondary" className={this.state.loading ? "btn-lg disabled" : "btn-lg"} onClick={() => this.cancelSubmission()}>
              {Liferay.Language.get('cancel')}
            </ClayButton>
            <ClayButton displayType="primary" className={this.state.loading ? "d-none" : "btn-lg"} onClick={() => this.validateAndSubmit()}>
              {Liferay.Language.get('invite')}
            </ClayButton>
          </ClayButton.Group>
          <span className={this.state.loading ? "inline-item loading-icon loading-icon-invite" : "d-none"}>
            <ClayLoadingIndicator />
          </span>
        </div>
      </div>
    );
  }
}