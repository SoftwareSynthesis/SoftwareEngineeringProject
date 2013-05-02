package org.softwaresynthesis.mytalk.server;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

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
		// TODO Auto-generated method stub
		return null;
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
	}
}
