import React from 'react';
import ClayForm, {ClayInput} from "@clayui/form";
import AsteriskIcon from "./AsteriskIcon";
import './css/styles.css';

const Input = (props) => {

  let { fieldName, showErrors, defaultValue, handleFieldChange } = props;

  const onChange = (e) => {
    let fieldValue = e.target.value;

    if (handleFieldChange) {
      handleFieldChange(fieldName, fieldValue);
    }
  };

  let displayError = showErrors && !defaultValue;

  let showRequired = props.showRequired != null ? props.showRequired : true;

  let { label, labelHint, floating, disabled, inputRef, errorMsg, errorMsgPosition, maxLength, type, required, placeholder, value } = props;

  return (
    <ClayForm.Group className={displayError ? "has-error" : ""} filled={defaultValue ? "true" : null}>
      {errorMsgPosition == 'top' && displayError && errorMsg != null &&
        <ClayForm.FeedbackGroup>
          <ClayForm.FeedbackItem className="bg-white pb-4">
            {errorMsg}
          </ClayForm.FeedbackItem>
        </ClayForm.FeedbackGroup>
      }

      <label htmlFor={fieldName} className={floating ? '' : 'floating-label'}>
          <ClayInput
              autoComplete={props.autocomplete ? props.autocomplete : null}
              className={value != '' ? 'filled' : ''}
              disabled={disabled}
              id={fieldName}
              maxLength={maxLength ? maxLength : "524288"}
              name={fieldName}
              placeholder={placeholder}
              type={type ? type : "text"}
              onChange={(e) => onChange(e)}
              ref={inputRef}
              value={value}
          />
          <span className="label-text">{label}{required && showRequired ? <AsteriskIcon /> : ''}</span>
      </label>

      {errorMsgPosition != 'top' && displayError && errorMsg != null &&
        <ClayForm.FeedbackGroup>
          <ClayForm.FeedbackItem>
            {errorMsg}
          </ClayForm.FeedbackItem>
        </ClayForm.FeedbackGroup>
      }

      {labelHint != null &&
        <div className="help-block">{labelHint}</div>
      }
    </ClayForm.Group>
  );
};

export default Input;