import React from 'react';
import ClayLayout from '@clayui/layout';
import ClayButton from '@clayui/button';

const Buttons = () => {
	return (
		<ClayLayout.Sheet className="mb-5">
			<ClayLayout.SheetHeader>
				<h3 className="sheet-title">Buttons</h3>
			</ClayLayout.SheetHeader>
			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Default</h4>
				<div className="sheet-text">
					<ClayLayout.Row>
						<ClayLayout.Col>
							<div className="heading-text">Normal</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary">Primary</ClayButton>
								<ClayButton displayType="secondary">Secondary</ClayButton>
								<ClayButton displayType="link">Link</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<div className="heading-text">Focus</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary" className="focus">Primary</ClayButton>
								<ClayButton displayType="secondary" className="focus">Secondary</ClayButton>
								<ClayButton displayType="link" className="focus">Link</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr/>

					<ClayLayout.Row>
						<ClayLayout.Col>
							<div className="heading-text">Active</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary" className="active">Primary</ClayButton>
								<ClayButton displayType="secondary" className="active">Secondary</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<div className="heading-text">Disabled</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary" disabled>Primary</ClayButton>
								<ClayButton displayType="secondary" disabled>Secondary</ClayButton>
								<ClayButton displayType="link" disabled>Link</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Outline</h4>
				<div className="sheet-text">
					<ClayLayout.Row>
						<ClayLayout.Col>
							<div className="heading-text">Normal</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary" outline="true">Primary</ClayButton>
								<ClayButton displayType="secondary" outline="true">Secondary</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<div className="heading-text">Focus</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary" outline="true" className="focus">Primary</ClayButton>
								<ClayButton displayType="secondary" outline="true" className="focus">Secondary</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr/>

					<ClayLayout.Row>
						<ClayLayout.Col>
							<div className="heading-text">Active</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary" outline="true" className="active">Primary</ClayButton>
								<ClayButton displayType="secondary" outline="true" className="active">Secondary</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>

						<ClayLayout.Col>
							<div className="heading-text">Disabled</div>
							<ClayButton.Group spaced>
								<ClayButton displayType="primary" outline="true" disabled>Primary</ClayButton>
								<ClayButton displayType="secondary" outline="true" disabled>Secondary</ClayButton>
							</ClayButton.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

							
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Sizes</h4>
				<div className="sheet-text">
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" outline="true" small="true">Small</ClayButton>
						<ClayButton displayType="secondary" outline="true">Default</ClayButton>
						<ClayButton displayType="secondary" outline="true" className="btn-lg">Large</ClayButton>
					</ClayButton.Group>
				</div>
			</ClayLayout.SheetSection>
		</ClayLayout.Sheet>
	);
};

export default Buttons;