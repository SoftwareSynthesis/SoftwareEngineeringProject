USE MyTalk;

INSERT INTO UserData(ID_user, E_Mail, Password, Question, Answer, Name, Surname, Picture)
VALUES 	(1, 'indirizzo1@dominio.it', 'password', 'Come mi chiamo', 'Pippo', 'pippo', 'rossi', ' '),
		(2, 'indirizzo2@dominio.it', 'password', 'Come mi chiamo', 'Marco', 'marco', 'verdi', ' '),
		(3, 'indirizzo3@dominio.it', 'password', 'Come mi chiamo', 'Luigi', 'luigi', 'gialli', ' '),
		(4, 'indirizzo4@dominio.it', 'password', 'Come mi chiamo', 'Piero', 'piero', 'pelu', ' '),
		(5, 'indirizzo5@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'Luigi', 'luigi', 'mannoio', ' '),
		(6, 'indirizzo6@dominio.it', 'password', 'Come mi chiamo', 'Paolo', 'Paolo', 'Bitta', ' '),
		(7, 'indirizzo7@dominio.it', 'password', 'Come mi chiamo', 'Luca', 'Luca', 'Nervi', ' '),
		(8, 'indirizzo8@dominio.it', 'password', 'Come mi chiamo', 'Direttore', 'Direttore', 'De Marinis', ' '),
		(9, 'indirizzo9@dominio.it', 'password', 'Come mi chiamo', 'Silvano', 'Silvano', 'Rogi', ' ');
		
INSERT INTO Groups (ID_group, Name, ID_user)
VALUES	(1, 'Gruppo 1', 5),
		(2, 'Groupo 2', 5),
		(3, 'Gruppo 3', 5),
		(4, 'Gruppo 4', 3),
		(5, 'Gruppo 5', 3),
		(6, 'addrBookEntry', 5);
		
INSERT INTO AddressBookEntries (ID_addressBookEntry, ID_user, ID_group, Owner, Blocked)
VALUES	(1, 2, 6, 5, 0),
		(2, 4, 2, 5, 0),
		(3, 1, 6, 5, 0),
		(4, 3, 3, 5, 0),
		(5, 4, 5, 2, 0),
		(6, 1, 5, 3, 0),
		(7, 2, 5, 3, 0);