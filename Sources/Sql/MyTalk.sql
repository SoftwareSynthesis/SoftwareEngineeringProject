/*
	Cancello e reinstallo il file di database
*/
DROP DATABASE IF EXISTS MyTalk;
CREATE DATABASE MyTalk;

USE MyTalk;

/*
	Imposto come default il motore INNODB
*/
SET storage_engine = INNODB;

/*
	Creo le entità della base di dati
*/
CREATE TABLE UserData
(
	ID_user						BIGINT UNSIGNED				NOT NULL			AUTO_INCREMENT,
	E_Mail						VARCHAR(100)				NOT NULL,
	Password					CHAR(41)					NOT NULL,
	Question					VARCHAR(100)				NOT NULL,
	Answer						VARCHAR(100)				NOT NULL,
	Name						VARCHAR(100),
	Surname						VARCHAR(100),
	Picture						VARCHAR(100),
	
	PRIMARY KEY(ID_user),
	UNIQUE(E_Mail)
);

CREATE TABLE Calls
(
	ID_call						BIGINT UNSIGNED				NOT NULL			AUTO_INCREMENT,
	Start_date					TIMESTAMP					NOT NULL			DEFAULT CURRENT_TIMESTAMP,
	End_date					TIMESTAMP,
	
	PRIMARY KEY(ID_call)
);

CREATE TABLE Groups
(
	ID_group					BIGINT UNSIGNED				NOT NULL			AUTO_INCREMENT,
	Name						VARCHAR(100)				NOT NULL,
	
	PRIMARY KEY(ID_group)
);

CREATE TABLE Messages
(
	ID_message					BIGINT UNSIGNED				NOT NULL			AUTO_INCREMENT,
	Sender						BIGINT UNSIGNED				NOT NULL,
	Receiver					BIGINT UNSIGNED				NOT NULL,
	Newer						TINYINT						NOT NULL			DEFAULT 1,
	Audio						TINYINT						NOT NULL			DEFAULT 1,
	Start_date					TIMESTAMP					NOT NULL			DEFAULT CURRENT_TIMESTAMP,
	
	PRIMARY KEY(ID_message),
	FOREIGN KEY(Sender) REFERENCES UserData(ID_user),
	FOREIGN KEY(Receiver) REFERENCES UserData(ID_user)
);

CREATE TABLE CallLists
(
	ID_call						BIGINT UNSIGNED				NOT NULL,
	ID_user						BIGINT UNSIGNED				NOT NULL,
	Caller						TINYINT						NOT NULL			DEFAULT 0,
	
	PRIMARY KEY(ID_call, ID_user),
	FOREIGN KEY(ID_call) REFERENCES Calls(ID_call),
	FOREIGN KEY(ID_user) REFERENCES UserData(ID_user)
);

CREATE TABLE AddressBooks
(
	ID_addressBooks				BIGINT UNSIGNED				NOT NULL,
	ID_user						BIGINT UNSIGNED				NOT NULL,
	ID_group					BIGINT UNSIGNED				NOT NULL,
	Owner						BIGINT UNSIGNED				NOT NULL,
	Blocked						TINYINT						NOT NULL			DEFAULT 1,
	
	PRIMARY KEY(ID_addressBooks),
	FOREIGN KEY(ID_user) REFERENCES UserData(ID_user),
	FOREIGN KEY(ID_group) REFERENCES Groups(ID_group),
	FOREIGN KEY(Owner) REFERENCES UserData(ID_user)
);