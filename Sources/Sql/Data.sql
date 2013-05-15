USE MyTalk;

/*
	La password per tutti gli utenti qui sotto è 'password'
	La risposta alla domanda segreta per gli utenti qui sotto è il nome con l'iniziale maiuscola (i.e. Pippo)
 */
INSERT INTO UserData(ID_user, E_Mail, Password, Question, Answer, Name, Surname, Picture)
VALUES 	(1, 'indirizzo1@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'dM7ASu/mUmWGAO4Djc+3ow==', 'pippo', 'rossi', 'img/contactImg/Default.png'),
		(2, 'indirizzo2@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', '9UXLXrThEp2TWberQdOlcw==', 'marco', 'verdi', 'img/contactImg/Default.png'),
		(3, 'indirizzo3@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'hJWDdDLfWZN1OYhmVIVzew==', 'luigi', 'gialli', 'img/contactImg/Default.png'),
		(4, 'indirizzo4@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'NAeYtJJhGJyArczXGxGt1A==', 'piero', 'pelu', 'img/contactImg/Default.png'),
		(5, 'indirizzo5@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'hJWDdDLfWZN1OYhmVIVzew==', 'luigi', 'mannoio', 'img/contactImg/Default.png'),
		(6, 'indirizzo6@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'OZs8eT+KGFEuwZ7tr7BX7w==', 'Paolo', 'Bitta', 'img/contactImg/Default.png'),
		(7, 'indirizzo7@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'ShtP9+f4yI+gsFM4Se5xfQ==', 'Luca', 'Nervi', 'img/contactImg/Default.png'),
		(8, 'indirizzo8@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'ShtP9+f4yI+gsFM4Se5xfQ==', 'Direttore', 'De Marinis', 'img/contactImg/Default.png'),
		(9, 'indirizzo9@dominio.it', 'tQJu8lEBkXWy+YuqNKZsqA==', 'Come mi chiamo', 'gU9yAwvuoJZrvHQIjc2QLA==', 'Silvano', 'Rogi', 'img/contactImg/Default.png');

/*
	Inseriti per tutti gli utenti il gruppo di default
*/		
INSERT INTO Groups (ID_group, Name, ID_Owner)
VALUES	(1, 'Gruppo 1', 5),
		(2, 'Gruppo 2', 5),
		(3, 'Gruppo 3', 5),
		(4, 'Gruppo 4', 3),
		(5, 'Gruppo 5', 3),
		(6, 'addrBookEntry', 1),
		(7, 'addrBookEntry', 2),
		(8, 'addrBookEntry', 3),
		(9, 'addrBookEntry', 4),
		(10, 'addrBookEntry', 5),
		(11, 'addrBookEntry', 6),
		(12, 'addrBookEntry', 7),
		(13, 'addrBookEntry', 8),
		(14, 'addrBookEntry', 9);
		
INSERT INTO AddressBookEntries (ID_addressBookEntry, ID_user, ID_group, Owner, Blocked)
VALUES	(1, 2, 1, 5, 0),
		(2, 4, 2, 5, 0),
		(3, 3, 3, 5, 0),
		(4, 4, 5, 3, 0),
		(5, 1, 5, 3, 0),
		(6, 2, 5, 3, 0);