USE MyTalk;

INSERT INTO UserData(ID_user, E_Mail, Password, Question, Answer, Name, Surname, Picture)
VALUES 	(1, 'indirizzo1@dominio.it', 'password', 'Come mi chiamo', 'Pippo', 'pippo', 'rossi', ' '),
		(2, 'indirizzo2@dominio.it', 'password', 'Come mi chiamo', 'Marco', 'marco', 'verdi', ' '),
		(3, 'indirizzo3@dominio.it', 'password', 'Come mi chiamo', 'Luigi', 'luigi', 'gialli', ' '),
		(4, 'indirizzo4@dominio.it', 'password', 'Come mi chiamo', 'Piero', 'piero', 'pelu', ' '),
		(5, 'indirizzo5@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'Luigi', 'luigi', 'mannoio', ' ');
		
INSERT INTO Groups (ID_group, Name)
VALUES	(1, 'Gruppo 1'),
		(2, 'Groupo 2'),
		(3, 'Gruppo 3'),
		(4, 'Gruppo 4'),
		(5, 'Gruppo 5');
		
INSERT INTO AddressBookEntries (ID_addressBookEntry, ID_user, ID_group, Owner, Blocked)
VALUES	(1, 2, NULL, 1, 0),
		(2, 4, 4, 1, 0),
		(3, 1, NULL, 2, 0),
		(4, 3, NULL, 2, 0),
		(5, 4, 2, 2, 0),
		(6, 1, 3, 3, 0),
		(7, 2, 3, 3, 0),
		(8, 2, 2, 3, 0);