import React from 'react';
import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {Dialog, parseAsHTML, Toast, UserAvatar} from 'com.churchmutual.commons.web';
import {UpdatePortraitModal} from './UpdatePortraitModal';
import {EditProfileModal} from './EditProfileModal';

class Profile extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      deletePortraitModal: {
        visible: false
      },
      editProfileModal: {
        visible: false
      },
      email: '',
      fullName: '',
      portraitURL: '',
      updatePortraitModal: {
        file: null,
        previewURL: null,
        visible: false,
      },
      errorMessage: ''
    }

    this.spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
  }

  componentDidMount() {
    this.getPortraitImageURL();
    this.getUser();
  }

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  getPortraitImageURL() {
    let callback = (portraitURL) => this.setState({portraitURL: portraitURL});

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-user-portrait-picture');

    Liferay.Service(
      '/cmic.cmicuser/get-portrait-image-url',
      callback,
      errCallback
    );
  }

  getUser() {
    let callback = (user) => this.setState({email: user.emailAddress, fullname: user.firstName + ' ' + user.lastName});

    let errCallback = () => this.displayErrorMessage('error.unable-to-retrieve-user-information');

    Liferay.Service(
      '/user/get-user-by-id',
      {
        userId: this.getUserId()
      },
      callback,
      errCallback
    );
  }

  setDeletePortraitModalVisible(value) {
    this.setState({
      deletePortraitModal: {
        ...this.state.deletePortraitModal,
        visible: value
      }
    });

    if (value) {
      this.setState({
        updatePortraitModal: {
          ...this.state.updatePortraitModal,
          visible: false
        }
      });
    }
  }

  setEditProfileModalVisible(value) {
    this.setState({
      editProfileModal: {
        ...this.state.editProfileModal,
        visible: value
      }
    });
  }

  setUpdatePortraitModalVisible(value) {
    this.setState({
      updatePortraitModal: {
        ...this.state.updatePortraitModal,
        visible: value
      }
    });

    if (value) {
      this.setState({
        deletePortraitModal: {
          ...this.state.deletePortraitModal,
          visible: false
        }
      });
    }
  }

  setPortraitPreview(event) {
    let file = event.target.files[0];

    if (!file) {
      return;
    }

    let reader = new FileReader();

    reader.onload = (e) =>  this.setState({
      updatePortraitModal: {
        file: file,
        previewURL: e.target.result,
        visible: true
      }
    });

    reader.readAsDataURL(file);
  }

  cancelDeletePortrait() {
    this.setUpdatePortraitModalVisible(true);
  }

  cancelUpdatePortrait() {
    this.setState({
      portrait: {
        file: null,
        previewURL: null,
        visible: false,
      },
      updatePortraitModal: {
        file: null,
        previewURL: null,
        visible: false,
      }
    });
  }

  deletePortrait() {
    let callback = () => {
      this.getPortraitImageURL();

      this.setState({
        deletePortraitModal: {
          visible: false
        },
        updatePortraitModal: {
          file: null,
          previewURL: null,
          visible: false,
        }
      });
    }

    let errCallback = () => this.displayErrorMessage('your-request-failed-to-complete');

    Liferay.Service(
      '/cmic.cmicuser/delete-portrait-image',
      callback,
      errCallback
    );
  }

  updatePortrait() {
    let file = this.state.updatePortraitModal.file;
    let previewImageURL = this.state.updatePortraitModal.previewURL;

    if (!file) {
      this.setUpdatePortraitModalVisible(false);
      return;
    }

    if (file.size > 100000) {
      this.displayErrorMessage('upload-images-no-larger-than-100kb');
      return;
    }

    let callback = (portraitURL) => {
      this.setState({portraitURL: portraitURL});
      this.setUpdatePortraitModalVisible(false);
    }

    let errCallback = () => this.displayErrorMessage('your-request-failed-to-complete');

    Liferay.Service(
      '/cmic.cmicuser/update-portrait-image',
      {
        userId: this.getUserId(),
        imageFileString: previewImageURL
      },
      callback,
      errCallback
    );
  }

  displayErrorMessage(message) {
    this.setState({
      errorMessage: message
    });
  }

  logout() {
    Liferay.Util.navigate('/c/portal/logout');
  }

  render() {
    return (
      <div className="profile-portlet">
        <ClayCard className="bg-dark card-insured text-white">
          <ClayCard.Body>
            <ClayLayout.Row gutters={false}>
              <ClayLayout.ContentCol expand>
                <h2 className="mb-4">{Liferay.Language.get('my-profile')}</h2>
              </ClayLayout.ContentCol>

              <ClayLayout.Col size={12} className="order-md-last">
                <ClayLayout.Row className="align-items-md-center">
                  <ClayLayout.Col lg={12} className="col-auto">
                    <ClayButton
                      displayType="unstyled"
                      className="btn-user-avatar mb-lg-4"
                      onClick={() => this.setUpdatePortraitModalVisible(true)}>
                      <UserAvatar image={this.state.portraitURL} size="xxxl"/>
                      <ClaySticker shape="circle" displayType="gray-dark" size="sm" position="bottom-right">
                        <ClayIcon spritemap={this.spritemap} symbol="pencil" />
                      </ClaySticker>
                    </ClayButton>
                  </ClayLayout.Col>

                  <ClayLayout.Col lg={12} className="col">
                    <ClayLayout.Row>
                      <ClayLayout.Col size={12} md="auto" lg={12}>
                        <h4>{Liferay.Language.get('name')}</h4>
                        <p className="mb-md-0 mb-lg-3 data-hj-suppress">{this.state.fullname}</p>
                      </ClayLayout.Col>

                      <ClayLayout.Col size={12} md="auto" lg={12}>
                        <h4>{Liferay.Language.get('email')}</h4>
                        <p className="text-break mb-md-0 mb-lg-3 data-hj-suppress">{this.state.email}</p>
                      </ClayLayout.Col>
                    </ClayLayout.Row>
                  </ClayLayout.Col>
                </ClayLayout.Row>
              </ClayLayout.Col>

              <ClayLayout.Col lg={12} className="col-md-auto order-lg-last mt-3 mt-md-0 mt-lg-3">
                <div className="d-flex d-md-block">
                  <a href="b2c-edit" rel="noopener noreferrer">
                    <ClayButton
                      borderless="true"
                      className="link-action btn-lg-block">
                      {Liferay.Language.get('edit-profile')}
                    </ClayButton>
                  </a>

                  <ClayButton
                    displayType="light"
                    outline="true"
                    className="btn-lg-block order-first"
                    onClick={() => this.logout()}>
                    {Liferay.Language.get('sign-out')}
                  </ClayButton>
                </div>
              </ClayLayout.Col>
            </ClayLayout.Row>
          </ClayCard.Body>
        </ClayCard>

        <UpdatePortraitModal
          portraitURL={this.state.updatePortraitModal.previewURL ? this.state.updatePortraitModal.previewURL : this.state.portraitURL}
          setVisible={(visible) => this.setUpdatePortraitModalVisible(visible)}
          visible={this.state.updatePortraitModal.visible}
          handleDeleteClick={(event) => this.setDeletePortraitModalVisible(event)}
          handlePictureFileChanged={(event) => this.setPortraitPreview(event)}
          onClickDone={() => this.updatePortrait()}
          onClickCancel={() => this.cancelUpdatePortrait()}
        />

        <EditProfileModal
          setVisible={(visible) => this.setEditProfileModalVisible(visible)}
          visible={this.state.editProfileModal.visible}
          onClickDone={() => this.setEditProfileModalVisible(false)}
        />

        <Dialog
          title={Liferay.Language.get('delete-profile-picture')}
          onClickCancel={() => this.cancelDeletePortrait()}
          onClickConfirm={() => this.deletePortrait()}
          setVisible={(visible) => this.setDeletePortraitModalVisible(visible)}
          visible={this.state.deletePortraitModal.visible}
          status='warning'
        >
          <div className="text-center">
            <p>{parseAsHTML(Liferay.Language.get('are-you-sure-you-want-to-delete-your-profile-picture'))}</p>
            <p>{parseAsHTML(Liferay.Language.get('deleting-your-profile-picture-is-permanent'))}</p>
          </div>
        </Dialog>

        <Toast
          message={this.state.errorMessage}
          displayType="danger"
          title={Liferay.Language.get('error-colon')}
          onClose={() => this.displayErrorMessage('')} />
      </div>
    );
  }

}

export default Profile;