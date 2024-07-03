import React from 'react';
import ClayLayout from '@clayui/layout';
import ConfirmYourIdentityInsured from './ConfirmYourIdentityInsured';
import ConfirmYourIdentityProducer from './ConfirmYourIdentityProducer';
import EnterRegistrationCode from './EnterRegistrationCode';

class UserRegistration extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      cmicUserId: '',
      isFormRegistrationCodeSubmitted: false,
      isProducerUser: null,
      registrationCode: ''
    }
  }

  setRegistrationFormValues(registrationCode, cmicUserId, isProducerUser) {
    this.setState({
      cmicUserId: cmicUserId,
      isFormRegistrationCodeSubmitted: true,
      isProducerUser: isProducerUser,
      registrationCode: registrationCode,
    })
  }

  render() {
    return (
      <div className="user-registration-portlet">
        <ClayLayout.Sheet>
          {!this.state.isFormRegistrationCodeSubmitted
            ? <EnterRegistrationCode setRegistrationFormValues={(registrationCode, cmicUserId, isProducerUser) => this.setRegistrationFormValues(registrationCode, cmicUserId, isProducerUser)} />
            : this.state.isProducerUser
            ? <ConfirmYourIdentityProducer
                cmicUserId={this.state.cmicUserId}
                registrationCode={this.state.registrationCode}
              />
            : <ConfirmYourIdentityInsured
                cmicUserId={this.state.cmicUserId}
                registrationCode={this.state.registrationCode}
              />
          }
        </ClayLayout.Sheet>

        <div className="label-lg mt-3 neutral-3 text-center">{Liferay.Language.get("need-assistance")}</div>
      </div>
    );
  }
}

export default UserRegistration;