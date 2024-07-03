import React from 'react';
import {useStateValue} from './ChangesTrackerProvider';

export const ChangesFeedback = () => {
  let {unsavedChanges} = useStateValue();
  return (
    <div className={`changes-feedback ${unsavedChanges ? "unsaved-changes" : ""}`}>
      <span className="indicator-feedback"></span>
      <span className="text-feedback">
        {unsavedChanges ? Liferay.Language.get('unsaved-changes') : Liferay.Language.get('no-changes-made')}
      </span>
    </div>
  );
};