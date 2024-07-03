import React from 'react';
import ClayCard from '@clayui/card';
import {Toast} from 'com.churchmutual.commons.web';
import AccountList from './AccountList';

class Accounts extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      accountsList: [],
      isLoading: true,
      producerId: 0,
      toast:  {
        displayType: '',
        message: '',
        title: '',
      }
    };
  }

  componentDidMount() {
    this.getAccountsList();
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

  onToastClosed() {
    this.setState({
      toast: {
        displayType: '',
        message: '',
        title: ''
      }
    });
  }

  getAccountsList() {
    let callback = (data) => {
      this.setState({accountsList: data, isLoading: false});

      // Prefetch accounts into cache

      Liferay.Service('/cmic.cmicaccountentry/get-cmic-account-entry-displays', {}, {});
      Liferay.Service('/cmic.cmicaccountentry/refresh-cmic-account-entry-displays-cache', {}, {});
    }

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts');

    Liferay.Service(
      '/cmic.cmicuser/get-recently-viewed-cmic-account-entry-displays',
      callback,
      errCallback
    );
  }

  render() {
    return (
      <div className="account-producer-dashboard-portlet">
        <ClayCard>
          <div className="card-header">
            <ClayCard.Description displayType="title">{Liferay.Language.get('accounts')}</ClayCard.Description>
          </div>
          <ClayCard.Body className="fade-table scrollable-table">
            {(this.state.accountsList.length == 0 && !this.state.isLoading) ?
              <div>{Liferay.Language.get('no-account-information-is-available')}</div> :
              <AccountList
                accountsList={this.state.accountsList}
                isLoading={this.state.isLoading}
              />
            }
          </ClayCard.Body>
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


export default Accounts;
