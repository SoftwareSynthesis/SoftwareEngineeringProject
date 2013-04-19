package org.softwaresynthesis.mytalk.server.abook.servlet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

/**
 * Verifica la possibilità di scaricare la rubrica di un utente autenticato al
 * sistema tramite la servlet AddressBookGetContacts
 * 
 * @author Diego Beraldin
 */
public class AddressBookGetContactsServletTest {
	// oggetto da testare
	private static AddressBookGetContactsServlet tester;
	// stub di richiesta HTTP
	private HttpServletRequest request;
	// stub di risposta HTTP
	private HttpServletResponse response;
	// writer in cui è contenuto lo StringBuffer su cui sarà memorizzato il
	// testo della risposta ottenuta dalla servlet
	private StringWriter writer;

	/**
	 * Inizializza l'oggetto da verificare prima di tutti i test
	 * 
	 * @author Diego Beraldin
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		tester = new AddressBookGetContactsServlet();
	}

	/**
	 * Crea prima di ogni test gli stub necessari per la sua esecuzione e azzera
	 * il buffer in cui sarà memorizzato il testo della risposta
	 * 
	 * @author Diego Beraldin
	 */
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		writer = new StringWriter();
	}

	private static class JsContact {
		private String name;
		private String surname;
		private String email;
		private Long id;
		private String picturePath;
		private String state;
		private boolean blocked;

		public boolean equals(Object other) {
			boolean equal = false;
			if (other instanceof JsContact) {
				JsContact another = (JsContact) other;
				equal = name.equals(another.name)
						&& surname.equals(another.surname)
						&& email.equals(another.email) && id == another.id
						&& picturePath.equals(another.picturePath)
						&& state.equals(another.state)
						&& blocked == another.blocked;
			}
			return equal;
		}

//		public JsContact() {
//		}

		public String toString() {
			String result = "{";
			result += "name: " + name + ", ";
			result += "surname: " + surname + ", ";
			result += "email: " + email + ", ";
			result += "id: " + id + ", ";
			result += "picturePath: " + picturePath + ", ";
			result += "state: " + state + ", ";
			result += "blocked: " + blocked;
			result += "}";
			return result;
		}
	}

	/**
	 * Verifica che sia correttamente scaricata la rubrica di un utente
	 * registrato nel sistema
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * @author Diego Beraldin
	 */
	@Test
	public void testGetCorrectContacts() throws ServletException, IOException {
		// crea una sessione che identifica l'utente 'indirizzo5@dominio.it'
		HttpSession mySession = mock(HttpSession.class);
		when(mySession.getAttribute("username")).thenReturn(
				"indirizzo5@dominio.it");

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(mySession);

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		Gson gson = new Gson();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);
		Map<String, JsContact> addressBook = gson.fromJson(responseText,
				new TypeToken<Map<String, JsContact>>() {
					// classe anonima che non fa nulla ;)
				}.getType());

		JsContact marco = gson.fromJson("{\"name\":\"marco\", "
				+ "\"surname\":\"verdi\", "
				+ "\"email\":\"indirizzo2@dominio.it\", " + "\"id\":\"2\", "
				+ "\"picturePath\":\"img/contactImg/Default.png\", "
				+ "\"state\":\"offline\", " + "\"blocked\":false}",
				JsContact.class);

		assertTrue(addressBook.containsValue(marco));
	}

	/**
	 * Verifica la corretta gestione del caso in cui la richiesta provenga da un
	 * utente non registrato al sistema (sessione di autenticazione non valida)
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * @author Diego Beraldin
	 */
	@Test
	public void testWrongData() throws ServletException, IOException {
		// crea una sessione non valida
		HttpSession failSession = mock(HttpSession.class);

		// configura il comportamento della richiesta
		when(request.getSession(false)).thenReturn(failSession);

		// configura il comportamento della risposta
		when(response.getWriter()).thenReturn(new PrintWriter(writer));

		// invoca il metodo da testare
		tester.doPost(request, response);

		// verifica l'output
		writer.flush();
		String responseText = writer.toString();
		assertNotNull(responseText);
		assertFalse(responseText.length() == 0);

		String toCompare = "null";
		assertEquals(toCompare, responseText);
	}
}
