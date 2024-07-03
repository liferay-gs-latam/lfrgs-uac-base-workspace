import React from 'react';
import ClayIcon from '@clayui/icon';
import ClayForm, {ClayInput, ClayCheckbox, ClayRadio, ClayRadioGroup, ClaySelect, ClaySelectWithOption, ClayToggle} from '@clayui/form';
import ClayLayout from '@clayui/layout';

const spritemap = themeDisplay.getPathThemeImages() + '/lexicon/icons.svg';
const options = [
    { label: "Option 1", value: "1" },
    { label: "Option 2", value: "2" }
];
const symbol = { off: "plus", on: "hr" };

const Form = () => {
	return (
		<ClayLayout.Sheet className="mb-5">
			<ClayLayout.SheetHeader>
				<h3 className="sheet-title">Form</h3>
			</ClayLayout.SheetHeader>
			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Inputs</h4>
				<div className="sheet-text">
					<div className="heading-text mb-4">Default</div>
					<ClayLayout.Row>
						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="defaultInputText">Name</label>
								<ClayInput id="defaultInputText" placeholder="Default State" type="text" />
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="defaultSelect">Select</label>
								<ClaySelectWithOption id="defaultSelect" options={options} />
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="defaultTextarea">Textarea</label>
								<ClayInput component="textarea" id="defaultTextarea" placeholder="Talk to me..." type="text" />
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
							

					<hr/>

					<div className="heading-text mb-4">Focus</div>
					<ClayLayout.Row>
						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="focusInputText">Name</label>
								<ClayInput id="focusInputText" placeholder="Active State" type="text" className="focus" />
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="focusSelect">Select</label>
								<ClaySelectWithOption id="focusSelect" options={options} className="focus" />
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="focusTextarea">Textarea</label>
								<ClayInput component="textarea" id="focusTextarea" placeholder="Talk to me..." type="text" className="focus" />
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
							

					<hr/>

					<div className="heading-text mb-4">Disabled</div>
					<ClayLayout.Row>
						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="disableInputText">Name</label>
								<ClayInput id="disableInputText" placeholder="Disabled State" type="text" disabled />
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="disableSelect">Select</label>
								<ClaySelectWithOption id="disableSelect" options={options} disabled />
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group>
								<label htmlFor="disableTextarea">Textarea</label>
								<ClayInput component="textarea" id="disableTextarea" placeholder="Talk to me..." type="text" disabled />
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
							
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">States</h4>
				<div className="sheet-text">
					<ClayLayout.Row>
						<ClayLayout.Col>
							<ClayForm.Group className="has-error">
								<label htmlFor="errorInput">
									Name
									<span className="reference-mark">
										<ClayIcon symbol="asterisk" spritemap={spritemap} />
									</span>
								</label>
								<ClayInput id="errorInput" placeholder="Error State" type="text" />
								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										{"This is an error message"}
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group className="has-warning">
								<label htmlFor="warningInput">
									Name
									<span className="reference-mark">
										<ClayIcon symbol="asterisk" spritemap={spritemap} />
									</span>
								</label>
								<ClayInput id="warningInput" placeholder="Warning State" type="text" />
								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										{"This is a warning message"}
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							</ClayForm.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<ClayForm.Group className="has-success">
								<label htmlFor="successInput">
									Name
									<span className="reference-mark">
										<ClayIcon symbol="asterisk" spritemap={spritemap} />
									</span>
								</label>
								<ClayInput id="successInput" placeholder="Success State" type="text" />
								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										{"This is a success message"}
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<ClayLayout.Row>
					<ClayLayout.Col>
						<h4 className="sheet-subtitle">Checkbox</h4>
						<div className="sheet-text">
							<div className="heading-text">Default</div>
							<ClayCheckbox label="Checkbox Off" checked={false} onChange={() => {}} />

							<hr/>

							<div className="heading-text">Checked</div>
							<ClayCheckbox label="Checkbox On" checked={true} onChange={() => {}} />

							<hr/>

							<div className="heading-text">Disabled</div>
							<ClayCheckbox label="Checkbox Off Disabled" onChange={() => {}} disabled inline />
							<ClayCheckbox label="Checkbox On Disabled" checked={true} onChange={() => {}} disabled inline />
							
						</div>
					</ClayLayout.Col>

					<ClayLayout.Col>
						<h4 className="sheet-subtitle">Radio</h4>
						<div className="sheet-text">
							<div className="heading-text">Default</div>
							<ClayRadio label="Radio Off" value="1" />

							<hr/>

							<div className="heading-text">Checked</div>
							<ClayRadio label="Radio On" value="1" checked={true} onChange={() => {}} />

							<hr/>

							<div className="heading-text">Disabled</div>
							<ClayRadio label="Radio Off Disabled" value="1" disabled inline />
							<ClayRadio label="Radio On Disabled" value="1" checked={true} onChange={() => {}} disabled inline />
							
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Toggle</h4>
				<div className="sheet-text">
					<ClayLayout.Row>
						<ClayLayout.Col>
							<ClayToggle
								label="Toggle Off"
								onToggle={() => {}}
								spritemap={spritemap}
								symbol={symbol}
								toggled={false}
							/>
						</ClayLayout.Col>
						<ClayLayout.Col>
							<ClayToggle
								label="Toggle On"
								onToggle={() => {}}
								spritemap={spritemap}
								symbol={symbol}
								toggled={true}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</div>
			</ClayLayout.SheetSection>
		</ClayLayout.Sheet>
	);
};

export default Form;