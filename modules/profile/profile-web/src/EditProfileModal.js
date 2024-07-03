import React from 'react';
import ClayIcon from '@clayui/icon';
import {Dialog} from 'com.churchmutual.commons.web';

function EditProfileModal(props) {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
  
  return <Dialog
    size="md"
    className="edit-profile-modal"
    title={Liferay.Language.get('edit-profile')}
    confirmButtonText={Liferay.Language.get('done')}
    onClickConfirm={() => props.onClickDone()}
    setVisible={props.setVisible}
    visible={props.visible}
    hideCancel={true}
  >
    <ul className="list-unstyled m-0">
      <li className="mb-4">
        <a href="b2c-edit-email" rel="noopener noreferrer" target="_blank">{Liferay.Language.get('update-email')}</a>
        <ClayIcon spritemap={spritemap} symbol="shortcut" className="ml-2" />
      </li>
      <li className="mb-4">
        <a href="b2c-edit-profile" rel="noopener noreferrer" target="_blank">{Liferay.Language.get('update-profile')}</a>
        <ClayIcon spritemap={spritemap} symbol="shortcut" className="ml-2" />
      </li>
      <li className="mb-4">
        <a href="b2c-edit-mfa" rel="noopener noreferrer" target="_blank">{Liferay.Language.get('configure-secondary-authentication-method')}</a>
        <ClayIcon spritemap={spritemap} symbol="shortcut" className="ml-2" />
      </li>
      <li>
        <span className="see-updates-text text-secondary">{Liferay.Language.get('to-see-updates-to-your-email-or-profile-log-out-and-back-in-to-the-portal')}</span>
      </li>
    </ul>
  </Dialog>;
}

export {EditProfileModal};