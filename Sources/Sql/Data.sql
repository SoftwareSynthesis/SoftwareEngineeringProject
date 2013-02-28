USE mytalk;

INSERT INTO UserData(ID_user, E_Mail, Password, Question, Answer, Name, Surname, Picture)
VALUES 	(1, 'indirizzo1@dominio.it', 'password', 'Come mi chiamo', 'Pippo', 'Pippo', 'Rossi', ' '),
		(2, 'indirizzo2@dominio.it', 'password', 'Come mi chiamo', 'Marco', 'Marco', 'Verdi', ' '),
		(3, 'indirizzo3@dominio.it', 'password', 'Come mi chiamo', 'Luigi', 'Luigi', 'Gialli', ' ');
		(4, 'indirizzo4@dominio.it'
		
INSERT INTO Groups (ID_group, Name)
VALUES	(1, 'Gruppo 1'),
		(2, 'Groupo 2'),
		(3, 'Gruppo 3'),
		(4, 'Gruppo 4'),
		(5, 'Gruppo 5');
		
INSERT INTO AddressBookEntries (ID_addressBookEntry, ID_user, ID_group, Owner, Blocked)
VALUES	(1, 2, NULL, 1, 0),
		(2, 4, 4, 1),
		(3, 1, NULL, 2, 0),
		(4, 3, NULL, 2, 0),
		(5, 4, 2, 2, 0),
		(6, 1, 3, 3, 0),
		(7, 2, 3, 3, 0);