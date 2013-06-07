package org.softwaresynthesis.mytalk.server.abook.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link AccountSettingsController}
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountSettingsControllerTest {
	private final String username = "indirizzo5@dominio.it";
	private final String newName = "paperone";
	private final String newSurname = "dePaperoni";
	private Writer writer;
	private AccountSettingsController tester;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private Part part;
	@Mock
	private InputStream istream;
	@Mock
	private FileOutputStream ostream;
	@Mock
	private IUserData user;

	/**
	 * Configura il comportamento dei mock e reinizializza l'oggetto da testare
	 * prima di tutte le verifiche contenute all'interno di questo caso di test.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// configura il gestore della pesistenza
		when(dao.getUserData(username)).thenReturn(user);
		// comportamento della multipart
		when(part.getInputStream()).thenReturn(istream);
		// comportamento della richiesta
		when(request.getParameter("name")).thenReturn(newName);
		when(request.getParameter("surname")).thenReturn(newSurname);
		when(request.getPart("picturePath")).thenReturn(part);
		// comportamento della risposta
		writer = new StringWriter();
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// inizializza l'oggetto da testare
		tester = spy(new AccountSettingsController() {
			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			protected String getUserMail() {
				return username;
			}

			@Override
			FileOutputStream createFileOutputStream(String path)
					throws IOException {
				return ostream;
			}

			@Override
			byte[] readFully(InputStream istream) throws Exception {
				return null;
			}
		});
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui viene
	 * invocato con una richiesta contenente tutti i parametri necessari per
	 * portare a termine con successo la richiesta. Il test verifica che il
	 * testo stampato sulla pagina di risposta corrisponda alla stringa 'true',
	 * che siano recuperate le informazioni dalla richiesta in maniera corretta,
	 * che sia ottenuto un riferimento all'utente richiedente tramite il sistema
	 * di persistenza, che siano impostate le proprietà di quest'ultimo secondo
	 * i parametri della richiesta e che infine sia effettuato l'aggiornamento
	 * del record corrispondente nel database. Il test verifica inoltre che sia
	 * effettivamente richiamato il metodo createFileOutputStream per scrivere
	 * sul disco l'immagine del contatto.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUpdateCorrectly() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("name");
		verify(request).getParameter("surname");
		verify(request).getPart("picturePath");
		verify(dao).getUserData(username);
		verify(user).setName(newName);
		verify(user).setSurname(newSurname);
		verify(user).setPath("img/contactImg/" + username + ".png");
		verify(dao).update(user);
		verify(tester).createFileOutputStream(anyString());
	}

	/**
	 * Verifica il comportamento del metodo doAction nel momento in cui viene
	 * invocato con una richiesta contenente tutti i parametri necessari per
	 * portare a termine con successo la richiesta ma non prevede
	 * l'aggiornamento dell'immagine del profilo. Il test verifica che il testo
	 * stampato sulla pagina di risposta corrisponda alla stringa 'true', che
	 * siano recuperate le informazioni dalla richiesta in maniera corretta, che
	 * sia ottenuto un riferimento all'utente richiedente tramite il sistema di
	 * persistenza, che siano impostate le proprietà di quest'ultimo secondo i
	 * parametri della richiesta e che infine sia effettuato l'aggiornamento del
	 * record corrispondente nel database. Il test verifica inoltre che non sia
	 * salvato sullo spazio del server alcun file immagine.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testUpdateWithoutPart() throws Exception {
		// nessuna immagine associata alla richiesta
		when(request.getPart("picturePath")).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);

		// verifica il corretto utilizzo dei mock
		verify(response).getWriter();
		verify(request).getParameter("name");
		verify(request).getParameter("surname");
		verify(request).getPart("picturePath");
		verify(dao).getUserData(username);
		verify(user).setName(newName);
		verify(user).setSurname(newSurname);
		verify(user, never()).setPath(anyString());
		verify(dao).update(user);
		verify(tester, never()).createFileOutputStream(anyString());
	}
}
