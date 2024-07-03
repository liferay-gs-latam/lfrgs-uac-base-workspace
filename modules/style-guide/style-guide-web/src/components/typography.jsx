import React from 'react';
import ClayLayout from '@clayui/layout';

const Typography = () => {
	return (
		<ClayLayout.Sheet className="mb-5">
			<ClayLayout.SheetHeader>
				<h3 className="sheet-title">Typography</h3>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Font Family</h4>
				<div className="sheet-text">
					<ClayLayout.Row>
						<ClayLayout.Col>
							<div className="display-3 font-weight-normal">Aa</div>
							<div className="h3 font-weight-normal">Avenir Roman</div>
							<div>abcdefghijklmnopqrstuvwxyz</div>
							<div>ABCDEFGHIJKLMNOPQRSTUVWXYZ</div>
							<div>1234567890?!"@#$%&()[]=</div>
						</ClayLayout.Col>
						<ClayLayout.Col className="font-weight-bold">
							<div className="display-3 font-weight-bold">Aa</div>
							<div className="h3 font-weight-bold">Avenir Heavy</div>
							<div>abcdefghijklmnopqrstuvwxyz</div>
							<div>ABCDEFGHIJKLMNOPQRSTUVWXYZ</div>
							<div>1234567890?!"@#$%&()[]=</div>
						</ClayLayout.Col>
						<ClayLayout.Col className="font-weight-bolder">
							<div className="display-3 font-weight-bolder">Aa</div>
							<div className="h3">Avenir Black</div>
							<div>abcdefghijklmnopqrstuvwxyz</div>
							<div>ABCDEFGHIJKLMNOPQRSTUVWXYZ</div>
							<div>1234567890?!"@#$%&()[]=</div>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Headings</h4>
				<div className="sheet-text">
					<ClayLayout.Row>
						<ClayLayout.Col>
							<h1 className="font-weight-normal">H1 Avenir Roman</h1>
							<h2 className="font-weight-normal">H2 Avenir Roman</h2>
							<h3 className="font-weight-normal">H3 Avenir Roman</h3>
							<h4 className="font-weight-normal">H4 Avenir Roman</h4>
							<h5 className="font-weight-normal">H5 Avenir Roman</h5>
							<h6 className="font-weight-normal">H6 Avenir Roman</h6>
						</ClayLayout.Col>
						<ClayLayout.Col>
							<h1 className="font-weight-bold">H1 Avenir Heavy</h1>
							<h2 className="font-weight-bold">H2 Avenir Heavy</h2>
							<h3 className="font-weight-bold">H3 Avenir Heavy</h3>
							<h4 className="font-weight-bold">H4 Avenir Heavy</h4>
							<h5 className="font-weight-bold">H5 Avenir Heavy</h5>
							<h6 className="font-weight-bold">H6 Avenir Heavy</h6>
						</ClayLayout.Col>
						<ClayLayout.Col>
							<h1>H1 Avenir Black</h1>
							<h2>H2 Avenir Black</h2>
							<h3>H3 Avenir Black</h3>
							<h4>H4 Avenir Black</h4>
							<h5>H5 Avenir Black</h5>
							<h6>H6 Avenir Black</h6>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</div>
			</ClayLayout.SheetSection>
		</ClayLayout.Sheet>
	);
};

export default Typography;
