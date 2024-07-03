import React from 'react';
import ClayCard from '@clayui/card';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Toast} from 'com.churchmutual.commons.web';
import {ContactCards} from 'com.churchmutual.commons.web';

class Contacts extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      contactsList: [],
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
    this.getContactsList();
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

  getContactsList() {
    let callback = (data) => {
      let contacts = data;

      contacts.forEach(contact => contact.title = 'Producer Code: ' + contact.producerCodes);

      this.setState({
        contactsList: contacts,
        isLoading: false
      })
    };

    let errCallback = () => {this.displayErrorMessage('error.unable-to-retrieve-list-of-territory-managers')};

    Liferay.Service(
        '/cmic.cmiccontact/get-territory-managers', {},
        callback,
        errCallback
    );
  }

  render() {
    return (
      <div className="contact-producer-list-portlet">
        <div className="container-view">
          <h1>Contact Us</h1>
        </div>

        {this.state.contactsList.length > 0 && (
            <ClayCard>
              <div className="card-header">
                <ClayCard.Description displayType="title">{Liferay.Language.get('territory-managers')}</ClayCard.Description>
              </div>
              <ClayCard.Body>
                {this.state.isLoading ? (
                  <ClayLoadingIndicator />
                  ) : (
                  <ContactCards
                    list={this.state.contactsList}
                    isLoading={this.state.isLoading}
                    md={6}
                    lg={4}
                  />
                )}
              </ClayCard.Body>
            </ClayCard>
        )}

        <Toast
          displayType={this.state.toast.displayType}
          message={this.state.toast.message}
          onClose={() => this.onToastClosed()}
          title={this.state.toast.title} />
      </div>
    );
  }
};


export default Contacts;
