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
	Password					VARCHAR(100)				NOT NULL,
	Question					VARCHAR(100)				NOT NULL,
	Answer						VARCHAR(100)				NOT NULL,
	Name						VARCHAR(100),
	Surname						VARCHAR(100),
	Picture						VARCHAR(100)				NOT NULL			DEFAULT 'img/contactImg/Default.png',
	
	PRIMARY KEY(ID_user),
	UNIQUE(E_Mail)
);

CREATE TABLE Calls
(
	ID_call						VARCHAR(100)				NOT NULL,
	Start_date					TIMESTAMP					NOT NULL,
	End_date					TIMESTAMP,
	
	PRIMARY KEY(ID_call)
);

CREATE TABLE Groups
(
	ID_group					BIGINT UNSIGNED				NOT NULL			AUTO_INCREMENT,
	Name						VARCHAR(100)				NOT NULL,
	ID_Owner					BIGINT UNSIGNED,
	
	PRIMARY KEY(ID_group),
	FOREIGN KEY(ID_Owner) REFERENCES UserData(ID_user)
);

CREATE TABLE Messages
(
	ID_message					BIGINT UNSIGNED				NOT NULL,
	Sender						BIGINT UNSIGNED				NOT NULL,
	Receiver					BIGINT UNSIGNED				NOT NULL,
	Newer						TINYINT						NOT NULL			DEFAULT 1,
	Video						TINYINT						NOT NULL			DEFAULT 0,
	Start_date					TIMESTAMP					NOT NULL,
	
	PRIMARY KEY(ID_message),
	FOREIGN KEY(Sender) REFERENCES UserData(ID_user),
	FOREIGN KEY(Receiver) REFERENCES UserData(ID_user)
);

CREATE TABLE CallLists
(
	ID_callList					BIGINT UNSIGNED				NOT NULL			AUTO_INCREMENT,
	ID_call						VARCHAR(100)				NOT NULL,
	ID_user						BIGINT UNSIGNED				NOT NULL,
	Caller						TINYINT						NOT NULL			DEFAULT 0,
	
	PRIMARY KEY(ID_callList),
	FOREIGN KEY(ID_call) REFERENCES Calls(ID_call) ON DELETE CASCADE,
	FOREIGN KEY(ID_user) REFERENCES UserData(ID_user)
);

CREATE TABLE AddressBookEntries
(
	ID_addressBookEntry			BIGINT UNSIGNED				NOT NULL			AUTO_INCREMENT,
	ID_user						BIGINT UNSIGNED				NOT NULL,
	ID_group					BIGINT UNSIGNED				NOT NULL,
	Owner						BIGINT UNSIGNED				NOT NULL,
	Blocked						TINYINT						NOT NULL			DEFAULT 0,
	
	PRIMARY KEY(ID_addressBookEntry),
	FOREIGN KEY(ID_user) REFERENCES UserData(ID_user),
	FOREIGN KEY(ID_group) REFERENCES Groups(ID_group) ON DELETE CASCADE,
	FOREIGN KEY(Owner) REFERENCES UserData(ID_user)
);

/*
	Creazione di trigger necessari alla gestione del database
*/
DELIMITER $
CREATE TRIGGER ControlloProprietario
BEFORE INSERT ON AddressBookEntries
FOR EACH ROW
BEGIN
	DECLARE owner BIGINT UNSIGNED;
	SELECT G.ID_Owner INTO owner FROM Groups AS G WHERE G.ID_group = NEW.ID_group;
	
	IF owner <> NEW.Owner
	THEN
		INSERT INTO Groups (ID_group, Name)
		VALUES	(NULL, NULL);
	END IF;
END; $
DELIMITER ;