package org.softwaresynthesis.mytalk.server.connection;

import java.nio.CharBuffer;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

/**
 * Servlet implementation class ChannelServlet
 */ 
@WebServlet(description = "Signaling channel", urlPatterns = { "/ChannelServlet" })
public class ChannelServlet extends WebSocketServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static Vector<PushInbound> clients= new Vector<PushInbound>();

    public ChannelServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {}

	public void destroy() {}
	
	/**
	 * Crea una websocket, la salva nel vettore di connessioni 
	 * attive e la ritorna al client
	 * 
	 * @author 	Marco Schivo
	 * @param 	subProtocol		{@link String} protocollo usato per la connessione
	 * @param 	request		{@link HttpServletRequest} oggetto per la richiesta
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		PushInbound channelClient = new PushInbound();
		clients.add(channelClient);
	    return channelClient;
	}
	
	/**
	 * Ricerca una connessione client dato l'identificativo
	 * 
	 * @author 	Marco Schivo
	 * @param 	n		{@link Long} identificativo del canale
	 */
	public static PushInbound findClient(Long n){
		for(int i=0; i<clients.size(); i++){
			if(((clients.get(i)).getId())==n){
				return clients.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Rimuove una connessione client
	 * 
	 * @author 	Marco Schivo
	 * @param 	n		{@link PushInbound} canale da rimuovere
	 */
	public static void removeClient(PushInbound c){
		for(int i=0; i<clients.size(); i++){
			if((clients.get(i)==c)){
				clients.remove(i);
			}
		}
	}

}
