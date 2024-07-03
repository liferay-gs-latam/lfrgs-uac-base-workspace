import React from 'react';
import ClayCard from '@clayui/card';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Toast} from 'com.churchmutual.commons.web';
import AccountList from './AccountList';

class Accounts extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      accountsList: [],
      isLoading: true,
      filterBy: '',
      producerId: 0,
      searchTerm: '',
      toast:  {
        displayType: '',
        message: '',
        title: '',
      }
    };
  }

  componentDidMount() {
    this.loadAccounts();
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

  displayInfoMessage(msg) {
    this.setState({
      toast: {
        displayType: 'info',
        message: msg,
        title: ''
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

  loadAccounts() {
    let callback = (data) => {
      this.refreshAccountsList();

      if (data == "IN_OLD_CACHE") {
        this.refreshAccountsCache();
        this.refreshAccountsListOnceCacheHasBeenUpdated();
      }
    }

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts');

    Liferay.Service(
        '/cmic.cmicaccountentry/get-cmic-account-entry-displays-cache-status',
        callback,
        errCallback
    );
  }

  refreshAccountsCache() {
    Liferay.Service('/cmic.cmicaccountentry/refresh-cmic-account-entry-displays-cache', {}, {});
  }

  refreshAccountsListOnceCacheHasBeenUpdated() {
    let callback = (data) => {
      if (data == "IN_YOUNG_CACHE") {
        this.displayInfoMessage('your-accounts-data-has-been-updated');
        this.refreshAccountsList();
      }
      else if (data == "IN_OLD_CACHE") {
        setTimeout(() => { this.refreshAccountsListOnceCacheHasBeenUpdated() }, 5000);
      }
    }

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts');

    Liferay.Service(
        '/cmic.cmicaccountentry/get-cmic-account-entry-displays-cache-status',
        callback,
        errCallback
    );
  }

  refreshAccountsList() {
    let callback = (data) => {
      this.setState({accountsList: [], isLoading: true });
      this.setState({accountsList: data, isLoading: false});
    }

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts');

    Liferay.Service(
        '/cmic.cmicaccountentry/get-cmic-account-entry-displays',
        callback,
        errCallback
    );
  }

  render() {
    return (
        <div className="account-list-portlet">
          <ClayCard>
            <ClayCard.Body className="mobile-table-padding">
              {this.state.isLoading ?
                  <ClayLoadingIndicator /> :
                  this.state.accountsList.length == 0 ?
                      <div>{Liferay.Language.get('no-account-information-is-available')}</div> :
                      <AccountList accountsList={this.state.accountsList} />
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
