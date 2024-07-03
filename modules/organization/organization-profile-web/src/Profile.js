import React from 'react';
import ClayCard from '@clayui/card';
import {Toast} from 'com.churchmutual.commons.web';
import ProducerOrgList from './ProducerOrgList';

class Profile extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      producerOrgList: [],
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
    this.getProducerOrgList();
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

  getProducerOrgList() {
    let callback = (data) => this.setState({producerOrgList: data, isLoading: false});

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-producer-organizations');

    Liferay.Service(
      '/cmic.cmicorganization/get-cmic-organization-displays',
      callback,
      errCallback
    );
  }

  render() {
    return (
      <div className="organization-profile-portlet">
        <ClayCard>
          <div className="card-header">
            <ClayCard.Description displayType="title">{Liferay.Language.get('producer-organizations')}</ClayCard.Description>
          </div>
          <ClayCard.Body>
            <ProducerOrgList
              producerOrgList={this.state.producerOrgList}
              isLoading={this.state.isLoading}
            />
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


export default Profile;
