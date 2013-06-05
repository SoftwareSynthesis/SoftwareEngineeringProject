USE MyTalk;

INSERT INTO `UserData` (`ID_user`, `E_Mail`, `Password`, `Question`, `Answer`, `Name`, `Surname`, `Picture`) VALUES
(1, 'marcoskivo@gmail.com', '32qCKdqxOcv8XtLXTQhSjg==', 'ciao', '32qCKdqxOcv8XtLXTQhSjg==', 'Marco', 'Schivo', 'img/contactImg/marcoskivo@gmail.com.png'),
(2, 'tres@gmail.com', '32qCKdqxOcv8XtLXTQhSjg==', 'ciao', '32qCKdqxOcv8XtLXTQhSjg==', 'Riccardo', 'Tresoldi', 'img/contactImg/tres@gmail.com.png'),
(3, 'stefano@gmail.com', '32qCKdqxOcv8XtLXTQhSjg==', 'ciao', '32qCKdqxOcv8XtLXTQhSjg==', 'Stefano', NULL, 'img/contactImg/stefano@gmail.com.png');

INSERT INTO `Groups` (`ID_group`, `Name`, `ID_Owner`) VALUES
(1, 'addrBookEntry', 1),
(2, 'addrBookEntry', 2),
(3, 'addrBookEntry', 3),
(4, 'Unipd', 1),
(5, 'Mona', 1);

INSERT INTO `AddressBookEntries` (`ID_addressBookEntry`, `ID_user`, `ID_group`, `Owner`, `Blocked`) VALUES
(1, 2, 1, 1, 0),
(2, 1, 2, 2, 0),
(5, 2, 4, 1, 0),
(8, 3, 1, 1, 0),
(9, 1, 3, 3, 0),
(10, 2, 5, 1, 0);

INSERT INTO `Calls` (`ID_call`, `Start_date`, `End_date`) VALUES
(1, '2013-06-05 13:46:23', '2013-06-05 13:46:08');

INSERT INTO `CallLists` (`ID_callList`, `ID_call`, `ID_user`, `Caller`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 0);