package org.softwaresynthesis.mytalk.server;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.softwaresynthesis.mytalk.server.connection.PushInbound;
import org.softwaresynthesis.mytalk.server.connection.PushInbound.State;

/**
 * Rappresenta il punto di accesso alle operazioni
 * eseguibili dal server del sistema MyTalk
 * 
 * @author 	Andrea Meneghinello
 * @version	3.0
 */
public class ControllerManager extends WebSocketServlet implements Servlet 
{
	private static final long serialVersionUID = 10001L;
	static Map<Long, PushInbound> clients= new HashMap<Long, PushInbound>();
	private Hashtable<String, String> controllers;
	
	/**
	 * Inizializza una nuova istanza dell'oggetto
	 */
	public ControllerManager()
	{
		this.controllers = new Hashtable<String, String>();
	}
	
	/**
	 * Resituisce una collezione dei controller disponibili
	 * 
	 * @return	{@link Hashtable} tabella hash con i controller
	 */
	Hashtable<String, String> getControllers()
	{
		return this.controllers;
	}
	
	/**
	 * Crea una nuova istanza di uno specifico controller a partire
	 * dal nome qualificato di esso
	 * 
	 * @param 	classPath	{@link String} nome qualificato del controller
	 * @return	{@link IController} controller inizializzato e pronto all'uso
	 * @throws 	ClassNotFoundException	se la classe non viene trovata dal class loader
	 * @throws 	IllegalAccessException	se non si hanno i privilegi di accesso a tale classe
	 * @throws 	InstantiationException	se non si riesce ad instanziare un oggetto di tale classe
	 */
	@SuppressWarnings("rawtypes")
	IController createController(String classPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		Class classDefinition = Class.forName(classPath);
		IController controller = (IController)classDefinition.newInstance();
		return controller;
	}
	
	/**
	 * Inizializza la servlet caricando in memoria
	 * la lista dei controller
	 * 
	 * @throws 	{@link ServletException} se l'operazione dovesse
	 * 			fallire. Se l'operazione è fallita la servlet non
	 * 			è utilizzabile
	 */
	@Override
	public void init(ServletConfig configuration) throws ServletException
	{
		super.init();
		ResourceBundle bundle = ResourceBundle.getBundle("org.softwaresynthesis.mytalk.server.controllerList");
		Enumeration<String> keys = bundle.getKeys();
		String key = null;
		String value = null;
		while (keys.hasMoreElements())
		{
			key = keys.nextElement();
			value = bundle.getString(key);
			this.controllers.put(key, value);
		}
	}
	
	/**
	 * Distrugge la servlet eliminando i dati in suo possesso
	 * impedendo sovrascritture la prossima volta che viene
	 * istanziata
	 */
	@Override
	public void destroy()
	{
		this.controllers.clear();
		this.controllers = null;
	}
	
	@Override
	protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest arg1) 
	{
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
	 * Risponde alla richiesta giunta dal client in
	 * modalità GET
	 * 
	 * @param	resquest	{@link HttpServletRequest} contiene le informazioni inviate dal client
	 * 						per completare l'operazione richiesta
	 * @param	response	{@link HttpServletResponse} contine le informazione inviate dal server
	 * 						per informare il client dell'esito della richiesta
	 * @throws 	IOException se si verificano errori durante operazioni di IO
	 * @throws	ServletException se si verificato errori interni alla servlet
	 * 			durante l'esecuzione
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		this.doPost(request, response);
	}
	
	/**
	 * Risponde alla richiesta giunta dal client in
	 * modalità POST
	 * 
	 * @param	request		{@link HttpServletRequest} contiene le informazioni inviate dal client
	 * 						per completare l'operazione richiesta
	 * @param	response	{@link HttpServletResponse} contine le informazione inviate dal server
	 * 						per informare il client dell'esito della richiesta
	 * @throws 	IOException se si verificano errori durante operazioni di IO
	 * @throws	ServletException se si verificato errori interni alla servlet
	 * 			durante l'esecuzione
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		IController controller = null;
		String operation = null;
		String controllerName = null;
		try
		{
			operation = request.getParameter("operation");
			if (operation != null)
			{
				if (this.controllers.containsKey(operation) == true)
				{
					controllerName = this.controllers.get(operation);
					controller = this.createController(controllerName);
					controller.execute(request, response);
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
		}
		catch (IllegalAccessException ex)
		{
		}
		catch (InstantiationException ex)
		{
		}
		finally
		{
			controllerName = null;
			controller = null;
		}
	}
}
