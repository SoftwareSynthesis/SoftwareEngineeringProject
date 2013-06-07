package org.softwaresynthesis.mytalk.server.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.softwaresynthesis.mytalk.server.ControllerManagerTest.getClients;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.WsOutbound;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.softwaresynthesis.mytalk.server.abook.IAddressBookEntry;
import org.softwaresynthesis.mytalk.server.abook.IUserData;
import org.softwaresynthesis.mytalk.server.connection.PushInbound;
import org.softwaresynthesis.mytalk.server.dao.DataPersistanceManager;

/**
 * Verifica della classe {@link LogoutController} che in questa nuova versione
 * può finalmente definirsi un test di unità degno di questo nome!
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class LogoutControllerTest {
	private final Long userId = 1L;
	private final Long contactId = 2L;
	private final String username = "indirizzo5@dominio.it";
	private Writer writer;
	private Writer writerOutbound;
	private LogoutController tester;
	private Set<IAddressBookEntry> addressbook;
	private Map<Long, PushInbound> clients;
	@Mock
	private LoginContext context;
	@Mock
	private HttpSession session;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private DataPersistanceManager dao;
	@Mock
	private IAddressBookEntry entry;
	@Mock
	private IUserData user;
	@Mock
	private IUserData contact;
	@Mock
	private PushInbound inbound;
	@Mock
	private WsOutbound outbound;

	/**
	 * Reinizializza il comportamento di tutti i mock prima dell'esecuzione di
	 * ciascuna delle verifiche contenute in questo caso di test e azzera il
	 * contenuto del buffer in cui sarà salvato il testo della risposta.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Before
	public void setUp() throws Exception {
		// comportamento della voce di rubrica
		when(entry.getContact()).thenReturn(contact);
		// rubrica del contatto
		addressbook = new HashSet<IAddressBookEntry>();
		addressbook.add(entry);
		// comportamento del contatto
		when(contact.getId()).thenReturn(contactId);
		// comportamento dell'utente
		when(user.getId()).thenReturn(userId);
		when(user.getAddressBook()).thenReturn(addressbook);
		// comportamento del gestore di persistenza
		when(dao.getUserData(username)).thenReturn(user);
		// azzera il contenuto del writer
		writer = new StringWriter();
		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));
		// configura il comportamento della richiesta
		when(request.getSession(anyBoolean())).thenReturn(session);
		// configura il comportamento della sessione
		when(session.getAttribute("context")).thenReturn(context);
		// configura i clients
		clients = getClients();
		clients.put(contactId, inbound);
		// configura il comportamento del WsOutbound
		writerOutbound = new StringWriter();
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				try {
					CharBuffer buf = (CharBuffer) invocation.getArguments()[0];
					writerOutbound.write(buf.toString());
				} catch (Exception e) {
				}
				return null;
			}
		}).when(outbound).writeTextMessage(any(CharBuffer.class));
		// inizializza l'oggetto da testare (qui non occorrono sottoclassi)
		tester = new LogoutController() {
			@Override
			protected String getUserMail() {
				return username;
			}

			@Override
			protected DataPersistanceManager getDAOFactory() {
				return dao;
			}

			@Override
			WsOutbound getWsOutbound(PushInbound pi) {
				return outbound;
			}
		};
	}

	/**
	 * Verifica la possibilità di effettuare logout da parte di un utente che ha
	 * superato la fase di autenticazione. Il test verifica che la richiesta sia
	 * utilizzata correttamente (con il parametro <code>false</code>) per
	 * ottenere la sessione e che dalla sessione sia recuperato il contesto di
	 * autenticazione. È inoltre verificato che sia invocato il logout() sul
	 * contesto, che in seguito la sessione invalidata e che la stringa stampata
	 * sulla risposta sia, come richiesto, 'true'.
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLogoutCorrectUser() throws Exception {
		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertEquals("true", responseText);
		writerOutbound.flush();
		String message = writerOutbound.toString();
		assertEquals(message, "5|" + userId + "|offline");

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(request, never()).getSession(true);
		verify(session).getAttribute("context");
		verify(session).invalidate();
		verify(context).logout();
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(user).getAddressBook();
		verify(entry).getContact();
		verify(contact).getId();
	}

	/**
	 * Verifica il comportamento della classe nel caso in cui la richiesta di
	 * logout provenga da un utente che non ha effettuato previamente
	 * l'autenticazione al sistema, situazione che è simulata impedendo di
	 * recuperare la sessione associata alla richiesta HTTP. Il test verifica
	 * che in questo caso sia stampata sulla risposta la stringa 'null' e che
	 * non sia mai manipolata la sessione per recuperare il contesto (su cui non
	 * può quindi essere invocato il metodo logout()).
	 * 
	 * @author Diego Beraldin
	 * @version 2.0
	 */
	@Test
	public void testLogoutWrongUser() throws Exception {
		// impedisce di recuperare la sessione
		when(request.getSession(anyBoolean())).thenReturn(null);

		// invoca il metodo da testare
		tester.doAction(request, response);

		// verifica l'output ottenuto
		writer.flush();
		String responseText = writer.toString();
		assertEquals("null", responseText);

		// verifica il corretto utilizzo dei mock
		verify(request).getSession(false);
		verify(session, never()).getAttribute(anyString());
		verify(session, never()).invalidate();
		verify(context, never()).logout();
		verify(response).getWriter();
		verify(dao).getUserData(username);
		verify(user).getAddressBook();
		verify(entry).getContact();
		verify(contact).getId();
	}
}
