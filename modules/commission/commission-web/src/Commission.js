import React from 'react';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import {Toast} from 'com.churchmutual.commons.web';
import Download from './Download';

class Commission extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      hasViewCommissions: false,
      statementList: [],
      isLoading: true,
      receivedError: false,
      toast:  {
        displayType: '',
        message: ''
      }
    };
  }

  componentDidMount() {
    this.getStatementList();
  }

  displayStatementList() {
    if (this.state.receivedError) {
      return (
        <div className="danger">
          <ClayIcon symbol={"info-circle"} spritemap={this.getSpriteMap()} className='mr-2' />{Liferay.Language.get('error.unable-to-retrieve-commission-statements')}
        </div>
      );
    }

    if (this.state.statementList.length == 0) {
      return (
        <div>{Liferay.Language.get('no-recent-commission-statements-are-available')}</div>
      );
    }

    return (
      <Download
        displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
        isLoading={this.state.isLoading}
        statementList={this.state.statementList}
      />
    );
  }

  displaySuccessMessage(msg) {
    this.setState({
      toast: {
        displayType: 'success',
        message: msg,
      }
    });
  }

  getSpriteMap() {
    return Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';
  }

  onToastClosed() {
    this.setState({
      toast: {
        displayType: '',
        message: ''
      }
    });
  }

  getStatementList() {
    let permissionCallback = (hasViewCommissions) => {
      if (hasViewCommissions) {
        this.setState({hasViewCommissions: true});

        let callback = (data) => this.setState({statementList: data, isLoading: false});

        let errCallback = () => this.setState({receivedError: true});

        Liferay.Service(
          '/cmic.cmiccommissiondocument/get-commission-documents',
          callback,
          errCallback
        );
      }
    }

    Liferay.Service(
      '/cmic.cmicpermission/has-permission',
      {
        actionId: 'VIEW_COMMISSION_STATEMENTS'
      },
      permissionCallback
    );
  }

  render() {
    if (this.state.hasViewCommissions) {
      return (
        <div className="commission-portlet">
          <ClayCard>
            <div className="card-header">
              <ClayCard.Description displayType="title">{Liferay.Language.get('commissions')}</ClayCard.Description>
            </div>
            <ClayCard.Body>
              {this.displayStatementList()}
            </ClayCard.Body>
          </ClayCard>

          <Toast
            displayType={this.state.toast.displayType}
            message={this.state.toast.message}
            onClose={() => this.onToastClosed()}/>
        </div>
      );
    }
    else {
      return (
        <div className="commission-portlet" />
      );
    }
  }
};


export default Commission;
