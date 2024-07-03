import React, {useRef} from 'react';
import ClayButton from '@clayui/button';
import {Dialog, UserAvatar} from 'com.churchmutual.commons.web';

function UpdatePortraitModal(props) {
  const inputRef = useRef(null);

  return <Dialog
    title={Liferay.Language.get('update-profile-picture')}
    confirmButtonText={Liferay.Language.get('done')}
    onClickCancel={() => props.onClickCancel()}
    onClickConfirm={() => props.onClickDone()}
    setVisible={props.setVisible}
    visible={props.visible}
  >
    <div className="text-center">
      <p className="mb-4">{Liferay.Language.get('upload-images-no-larger-than-100kb')}</p>

      <div className="mb-4">
        <UserAvatar shape="rounded" size="xxxl" image={props.portraitURL} />
        <input id="inputPortraitFile" type="file" ref={inputRef} className="hide"
          onChange={(e) => props.handlePictureFileChanged(e)} />
      </div>

      <ClayButton.Group spaced>
        <ClayButton
          displayType="primary"
          outline="true"
          onClick={() => inputRef.current.click()}>
          {Liferay.Language.get('select')}
        </ClayButton>
        <ClayButton
          displayType="secondary"
          borderless="true"
          outline="true"
          onClick={() => props.handleDeleteClick(true)}>
          {Liferay.Language.get('delete')}
        </ClayButton>
      </ClayButton.Group>
    </div>
  </Dialog>;
}

export {UpdatePortraitModal};