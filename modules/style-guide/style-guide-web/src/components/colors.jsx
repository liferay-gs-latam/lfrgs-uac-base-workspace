import React from 'react';
import ClayLayout from '@clayui/layout';
import ClayCard from '@clayui/card';

const Colors = () => {
	return (
		<ClayLayout.Sheet className="mb-5">
			<ClayLayout.SheetHeader>
				<h3 className="sheet-title">Colors</h3>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Brand</h4>
				<div className="sheet-text">
					<div className="card-deck">
						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--red)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									CMIC / Red
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--red')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--white)' }}>
								<ClayCard.Description displayType="title">
									CMIC / White
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--white')}</small>
							</div>
						</ClayCard>
						
						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--dark)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									CMIC / Black
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--dark')}</small>
							</div>
						</ClayCard>
					</div>
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Grays</h4>
				<div className="sheet-text">
					<div className="card-deck">
						{
							[...Array(9)].map((e, i) =>
								<ClayCard key={i}>
									<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: `var(--gray-${(i + 1)}00)` }}>
										<ClayCard.Description displayType="title" className={`${(i > 4) ? "text-white" : ""}`}>
											Gray {i + 1}00
										</ClayCard.Description>
									</ClayCard.Body>
									<div className="card-footer text-center">
										<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue(`--gray-${(i + 1)}00`)}</small>
									</div>
								</ClayCard>
							)
						}
					</div>
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Action</h4>
				<div className="sheet-text">
					<div className="card-deck">
						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--primary)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Primary / Default
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--primary')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: '#D41929' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Primary / Hover
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">#D41929</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: '#BA1624' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Primary / Active
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">#BA1624</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--secondary)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Secondary / Default
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--secondary')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: '#575D66' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Secondary / Hover
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">#575D66</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: '#41464D' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Secondary / Active
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">#41464D</small>
							</div>
						</ClayCard>
					</div>
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">States</h4>
				<div className="sheet-text">
					<div className="card-deck">
						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--danger)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Danger
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--danger')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--warning)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Warning
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--warning')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--success)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Success
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--success')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--info)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Info
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--info')}</small>
							</div>
						</ClayCard>
					</div>
				</div>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection>
				<h4 className="sheet-subtitle">Accent</h4>
				<div className="sheet-text">
					<div className="card-deck">
						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--blue)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Blue
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--blue')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--green)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Green
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--green')}</small>
							</div>
						</ClayCard>

						<ClayCard>
							<ClayCard.Body className="card-item-first d-flex align-items-center justify-content-center" style={{ height: 150, backgroundColor: 'var(--orange)' }}>
								<ClayCard.Description displayType="title" className="text-white">
									Orange
								</ClayCard.Description>
							</ClayCard.Body>
							<div className="card-footer text-center">
								<small className="text-muted">{getComputedStyle(document.documentElement).getPropertyValue('--orange')}</small>
							</div>
						</ClayCard>
					</div>
				</div>
			</ClayLayout.SheetSection>
		</ClayLayout.Sheet>
	);
};

export default Colors;
