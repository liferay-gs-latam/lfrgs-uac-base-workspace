import React from 'react';
import ClayForm, {ClaySelect} from "@clayui/form";
import AsteriskIcon from './AsteriskIcon';

const Select = (props) => {

  const onChange = (e) => {
    let fieldValue = e.target.value;
    props.handleFieldChange(props.fieldName, fieldValue);
  }

  let displayError = props.showErrors && !props.defaultValue;

  let options = props.options;

  let {required} = props;

  let spritemap = Liferay.ThemeDisplay.getPathThemeImages() + "/lexicon/icons.svg";

  let containsSensitiveInformation = props.containsSensitiveInformation;

  return (
    <ClayForm.Group className={displayError ? "has-error" : ""} filled={props.defaultValue ? "true" : null}>
      <label htmlFor={props.fieldName}>
          {props.label}
          {required ? <AsteriskIcon /> : ''}
          {props.labelHint ? <small><i> {props.labelHint.toLowerCase()}</i></small> : ''}
      </label>
      <ClaySelect id={props.fieldName} name={props.fieldName} onChange={(e) => onChange(e)} value={props.defaultValue} className={`${containsSensitiveInformation ? "data-hj-suppress" : ""}`}>
        {options.length > 0 ? options.map(item => (
          <ClaySelect.Option
            key={item.value}
            label={item.label}
            value={item.value}
          />
        )) : ''}
      </ClaySelect>
      {displayError &&
      <ClayForm.FeedbackGroup>
        <ClayForm.FeedbackItem>
          {props.errorMsg}
        </ClayForm.FeedbackItem>
      </ClayForm.FeedbackGroup>
      }
    </ClayForm.Group>);
}

export default Select;