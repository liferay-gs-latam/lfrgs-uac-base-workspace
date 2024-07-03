import React from 'react';
import ClayCard from '@clayui/card';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Toast, CardPageHeader, hasAccountPermission} from 'com.churchmutual.commons.web';
import AccountInfo from './AccountInfo';
import PolicyList from './PolicyList';

class Policies extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      account: {
        accountName: '',
        accountNumber: '',
        companyNumber: '',
        producerEntity: '',
        producerEntityCode: ''
      },
      isLoading: true,
      filterBy: '',
      producerId: 0,
      searchTerm: '',
      toast:  {
        displayType: '',
        message: '',
        title: '',
      },
      user: {
        isProducer: ''
      },
      hasViewPolicyDocuments: false
    };
  }

  componentDidMount() {
    this.getUser();
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

  displaySuccessMessage(msg) {
    this.setState({
      toast: {
        displayType: 'success',
        message: msg
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

  getAccount() {
    let accountNumber = this.getCMICAccountNumber();
    let companyNumber = this.getCMICCompanyNumber();

    if (accountNumber == null || companyNumber == null) {
      this.getPolicies();

      return;
    }


    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-policies')

    let accountCallback = (account) => {
      this.setState({
        account: {
          accountName: account.accountName,
          accountNumber: account.accountNumber,
          companyNumber: account.companyNumber,
          producerEntity: account.producerName,
          producerEntityCode: account.producerCodeFormatted
        }
      });

      this.getPolicies();
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

  getHasViewPolicyDocuments() {
    let callback = (hasViewPolicyDocuments) => this.setState({hasViewPolicyDocuments: hasViewPolicyDocuments});

    let accountNumber = this.getCMICAccountNumber();
    let companyNumber = this.getCMICCompanyNumber();

    if (accountNumber && companyNumber) {
      Liferay.Service(
        '/cmic.cmicpermission/has-permission',
        {
          actionId: 'VIEW_POLICY_DOCUMENTS',
          cmicBusinessKey: JSON.stringify({
            producerId: '0',
            accountNumber: accountNumber,
            companyNumber: companyNumber,
            producer: false
          })
        },
        callback
      );
    }
    else {
      Liferay.Service(
        '/cmic.cmicpermission/has-permission',
        {
          actionId: 'VIEW_POLICY_DOCUMENTS'
        },
        callback
      );
    }
  }

  getPolicies() {
    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-list-of-policies')

    let policyListCallback = (policies) => {
      this.setState((prevState) => ({
        account: {
          ...prevState.account,
          policyList: policies
        },
        isLoading: false
      }));

      this.addRecentlyViewedCMICAccountNumber();
      this.getHasViewPolicyDocuments();
    }

    if (this.getCMICAccountNumber() != null) {
      Liferay.Service(
        '/cmic.cmicqspolicy/get-policy-displays',
        {
          cmicBusinessKey: JSON.stringify({
            producerId: '0',
            accountNumber: this.getCMICAccountNumber(),
            companyNumber: this.getCMICCompanyNumber(),
            producer: false
          })
        },
        policyListCallback,
        errCallback
      );
    }
    else {
      Liferay.Service(
        '/cmic.cmicqspolicy/get-policy-displays-by-user-id',
        policyListCallback,
        errCallback
      );
    }
  }

  getUser() {
    let callback = (user) => {
      this.setState(
        {
          user: {
            isProducer: user.producer
          }
        }
      );
    }

    let errCallback = () => this.displayErrorMessage(Liferay.Language.get('error.unable-to-retrieve-user-information'));

    Liferay.Service(
      '/cmic.cmicuser/get-user-details',
      {
        userId: Liferay.ThemeDisplay.getUserId()
      },
      callback,
      errCallback
    );
  }

  addRecentlyViewedCMICAccountNumber() {
    if (this.state.user.isProducer) {
      Liferay.Service(
        '/cmic.cmicuser/add-recently-viewed-cmic-account',
        {
          cmicBusinessKey: JSON.stringify({
            producerId: '0',
            accountNumber: this.getCMICAccountNumber(),
            companyNumber: this.getCMICCompanyNumber(),
            producer: false
          })
        }
      );
    }
  }

  render() {
    return (
      <div className="policy-list-portlet">
        {this.state.user.isProducer ? (
          <CardPageHeader>
            <AccountInfo
              account={this.state.account}
              isLoading={this.state.isLoading}
            />
          </CardPageHeader>
        ) : ''}
        <ClayCard>
          {this.state.isLoading ?
            <ClayLoadingIndicator /> :
            this.state.account.policyList.length == 0 ? (
              <ClayCard.Body className="mobile-table-padding">
                <div>{Liferay.Language.get('no-policy-information-is-available')}</div>
              </ClayCard.Body>) :
              <PolicyList
                policyList={this.state.account.policyList}
                accountNumber={this.getCMICAccountNumber()}
                displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
                displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
                userIsProducer={this.state.user.isProducer}
                hasViewPolicyDocuments={this.state.hasViewPolicyDocuments}
              />
          }
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


export default Policies;