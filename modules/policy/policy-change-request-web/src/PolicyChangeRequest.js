import React from 'react';
import ClayIcon from '@clayui/icon';
import {PolicyChangeRequestModal} from './PolicyChangeRequestModal';

const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg';

class PolicyChangeRequest extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      contactEmail: "",
      modalVisible: false
    }
  }

  componentDidMount() {
    this.getContactEmail();
  }

  getContactEmail() {
    let callback = (data) => this.setState({contactEmail: data});
    let errCallback = () => this.displayErrorMessage('your-request-failed-to-complete');

    Liferay.Service(
      '/cmic.cmicuser/get-insured-theme-setting',
      {
        themeSettingKey: 'contact-email'
      },
      callback,
      errCallback
    );
  }

  onClickContinue() {
    const link = document.createElement('a');

    link.href = `mailto:${this.state.contactEmail}?subject=Policy Change Request via Portal`;
    link.rel = 'noopener noreferrer';
    link.target = '_blank';

    let footer = document.getElementsByClassName('modal-footer');

    if (footer.length > 0) {
      footer[0].appendChild(link);
      link.click();
      footer[0].removeChild(link);
    } else {
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }

  render() {
    return (
      <div className="policy-change-request">

        <div className="my-3 py-3">
          <div className="align-items-center flex-container">
            <ClayIcon symbol={"edit"} spritemap={spritemap} className="lexicon-icon-cmic mr-2 neutral-3" />

            <h5 className="font-weight-bold m-0">{Liferay.Language.get('policy-change-request')}</h5>
          </div>

          <div className="py-2">{Liferay.Language.get('updating-your-policy-is-now-easier-than-ever')}</div>

          <div className="pt-2">
            <button
              className="btn cursor-pointer link-action p-0"
              onClick={() => this.setState({modalVisible: true})}>
              {Liferay.Language.get('contact-us')}
            </button>
          </div>
        </div>

        <PolicyChangeRequestModal
          onClickCancel={() => this.setState({modalVisible: false})}
          onClickContinue={() => this.onClickContinue()}
          visible={this.state.modalVisible}
          setVisible={(visible) => this.setState({modalVisible: visible})}
        />
      </div>
    );
  }
}

export default PolicyChangeRequest;