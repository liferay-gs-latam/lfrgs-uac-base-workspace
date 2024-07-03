import React from 'react';
import ClayCard from '@clayui/card';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Toast, hasAccountPermission} from 'com.churchmutual.commons.web';
import AccountInfo from './AccountInfo';
import TransactionList from './TransactionList';

class Transactions extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      policy: {
        accountName: '',
        accountNumber: '',
        companyNumber: '',
        policyNumber: '',
        producerEntity: '',
        producerEntityCode: '',
        productName: '',
      },
      transactionList: [],
      isLoading: true,
      toast:  {
        displayType: '',
        message: '',
        title: '',
      },
      userIsProducer: '',
      hasViewPolicyDocuments: false
    };
  }

  componentDidMount() {
    this.getAccountAndPolicyInfo();
    this.getTransactionList();
    this.getUserInfo();
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

  getAccountNumber() {
    let urlSearchParameters = new URLSearchParams(window.location.search);

    return urlSearchParameters.get('accountNumber') ? urlSearchParameters.get('accountNumber') : '';
  }

  getCompanyNumber() {
    let urlSearchParameters = new URLSearchParams(window.location.search);

    return urlSearchParameters.get('companyNumber') ? urlSearchParameters.get('companyNumber') : '';
  }

  getHasViewPolicyDocuments(accountNumber, companyNumber) {
    let callback = (hasViewPolicyDocuments) => this.setState({hasViewPolicyDocuments: hasViewPolicyDocuments});

    hasAccountPermission(accountNumber, companyNumber, 'VIEW_POLICY_DOCUMENTS', callback);
  }

  getAccountAndPolicyInfo() {
    let accountCallback = account => {
      this.getHasViewPolicyDocuments(account.accountNumber, account.companyNumber);

      Liferay.Service(
        '/cmic.cmicqspolicy/get-policy-by-policy-number',
        {
          policyNumber: this.getPolicyNumber()
        },
        (policy) => {
          this.setState({
            policy: {
              accountName: account.accountName,
              accountNumber: account.accountNumber,
              companyNumber: account.companyNumber,
              policyNumber: policy.policyNumber,
              producerEntity: account.producerName,
              producerEntityCode: account.producerCodeFormatted,
              productName: policy.productName
            }
          });
        },
        () => this.displayErrorMessage('your-request-failed-to-complete')
      );
    }

    Liferay.Service(
      '/cmic.cmicaccountentry/get-cmic-account-entry-display',
      {
        cmicBusinessKey: JSON.stringify({
          producerId: '0',
          accountNumber: this.getAccountNumber(),
          companyNumber: this.getCompanyNumber(),
          producer: false
        })
      },
      accountCallback,
      () => this.displayErrorMessage('your-request-failed-to-complete')
    );
  }

  getPolicyNumber() {
    let urlSearchParameters = new URLSearchParams(window.location.search);

    return urlSearchParameters.get('policyNumber');
  }

  getTransactionList() {
    let transactionListCallback = data => this.setState({transactionList: data, isLoading: false});

    Liferay.Service(
      '/cmic.cmictransaction/get-transaction-displays',
      {
        policyNumber: this.getPolicyNumber()
      },
      transactionListCallback,
      () => this.displayErrorMessage('error.unable-to-retrieve-list-of-transactions')
    )
  }

  getUserInfo() {
    Liferay.Service(
      '/cmic.cmicuser/get-user-details',
      {
        userId: Liferay.ThemeDisplay.getUserId()
      },
      (user) => {
        this.setState({
          userIsProducer: user.producer
        });
      },
      () => this.displayErrorMessage('your-request-failed-to-complete')
    );
  }

  render() {
    if (this.state.isLoading) {
      return (<ClayLoadingIndicator />);
    }
    else if (this.state.transactionList.length == 0) {
      return (
        <div className="transaction-list-portlet">
          <AccountInfo
            policy={this.state.policy}
            userIsProducer={this.state.userIsProducer}
          />
          <ClayCard>
            <ClayCard.Body className="mobile-table-padding">
              <div>{Liferay.Language.get('no-transaction-information-is-available')}</div>
            </ClayCard.Body>
          </ClayCard>
        </div>);
    }
    else {
      return (
        <div className="transaction-list-portlet">
          <AccountInfo
            policy={this.state.policy}
            userIsProducer={this.state.userIsProducer}
          />
          <ClayCard>
            <TransactionList
              productName={this.state.policy.productName}
              transactionList={this.state.transactionList}
              policy={this.state.policy}
              displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
              displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
              hasViewPolicyDocuments={this.state.hasViewPolicyDocuments}
            />
          </ClayCard>

          <Toast
            displayType={this.state.toast.displayType}
            message={this.state.toast.message}
            onClose={() => this.onToastClosed()}
            title={this.state.toast.title} />
        </div>
      );
    }
  }
}

export default Transactions;
