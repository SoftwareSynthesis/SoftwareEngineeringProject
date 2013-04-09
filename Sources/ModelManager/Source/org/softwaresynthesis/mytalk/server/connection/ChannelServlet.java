package org.softwaresynthesis.mytalk.server.connection;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import org.softwaresynthesis.mytalk.server.connection.PushInbound.State;

/**
 * Servlet implementation class ChannelServlet
 */ 
@WebServlet(description = "Signaling channel", urlPatterns = { "/ChannelServlet" })
public class ChannelServlet extends WebSocketServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static Map<Long, PushInbound> clients= new HashMap<Long, PushInbound>();

    public ChannelServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {}

	public void destroy() {}
	
	/**
	 * Crea una websocket, la salva nel vettore di connessioni 
	 * attive e la ritorna al client
	 * 
	 * @param 	subProtocol		{@link String} protocollo usato per la connessione
	 * @param 	request		{@link HttpServletRequest} oggetto per la richiesta
	 */
	@Override
	
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		PushInbound channelClient = new PushInbound();
		channelClient.setState(State.AVAILABLE);
	    return channelClient;
	}
	
	
	/**
	 * Inserisce un client dato l'identificativo nella HashMap
	 * 
	 * @param 	n		{@link Long} identificativo del canale
	 * @param	c		{@link PushInbound} oggetto PushInbound
	 */
	public static void putClient(Long n, PushInbound c){
		clients.put(n, c);
	}
	
	
	/**
	 * Ricerca una connessione client dato l'identificativo
	 * 
	 * @param 	n		{@link Long} identificativo del canale
	 */
	public static PushInbound findClient(Long n){
		PushInbound client= clients.get(n);
		if(client != null)
			return client;
		else
			return null;
	}
	
	/**
	 * Rimuove una connessione client
	 * 
	 * @param 	c		{@link PushInbound} canale da rimuovere
	 */
	public static void removeClient(PushInbound c){
		for (Long key : clients.keySet()) {
			if (clients.get(key)==c) {
				clients.remove(key) ;
			}
		}
	}

	/**
	 * Restituisce lo stato di un utente
	 * 
	 * @param 	identifier	{@link Long} identificatore
	 * 						dello {@link org.softwaresynthesis.mytalk.server.abook.IUserData}
	 * @return	{@link String} con lo stato
	 */
	public static String getState(Long identifier)
	{
		PushInbound ps = null;
		State state = null;
		String result = null;
		ps = clients.get(identifier);
		if (ps != null)
		{
			state = ps.getState();
			result = state.toString().toLowerCase();
		}
		else
		{
			result = "offline";
		}
		return result;
	}
	
	/**
	 * Consente l'accesso alla mappa dei client
	 * 
	 * @return {@link Map} contenente gli identificativi degli utenti
	 * 			e le connessioni ad essi associate
	 * @author Diego Beraldin
	 */
	static Map<Long, PushInbound> getClients() {
		return clients;
	}
	
	/**
	 * Consente di sovrascrivere la mappa dei clients
	 * 
	 * @param map	{@link Map} che contiene le associazioni fra gli id
	 * 				degli utenti e i relativi canali di connessione
	 * @author Diego Beraldin
	 */
	static void setClients(Map<Long, PushInbound> map) {
		clients = map;
	}
}
