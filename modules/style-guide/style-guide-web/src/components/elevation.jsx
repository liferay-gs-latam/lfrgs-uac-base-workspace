import React from 'react';
import ClayLayout from '@clayui/layout';

const Elevation = () => {
	return (
		<ClayLayout.Sheet className="mb-5">
			<ClayLayout.SheetHeader>
				<h3 className="sheet-title">Elevation</h3>
			</ClayLayout.SheetHeader>
			<ClayLayout.SheetSection>
				<ClayLayout.Row>
					{
						[...Array(9)].map((e, i) =>
							<ClayLayout.Col key={i}>
								<div className={"p-4 mb-3 text-center elevation-" + (i + 1)}>Elevation {i + 1}</div>
							</ClayLayout.Col>
						)
					}
				</ClayLayout.Row>
			</ClayLayout.SheetSection>
		</ClayLayout.Sheet>
	);
};

export default Elevation;