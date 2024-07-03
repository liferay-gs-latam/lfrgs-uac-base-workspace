create table cmic_CMICPermission (
	userId LONG not null,
	cmicBusinessKey VARCHAR(75) not null,
	actionId VARCHAR(75) not null,
	primary key (userId, cmicBusinessKey, actionId)
);

create table cmic_CMICUser (
	cmicUserId LONG not null primary key,
	userId LONG
);