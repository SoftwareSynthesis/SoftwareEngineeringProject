package org.softwaresynthesis.mytalk.server.connection;

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
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		PushInbound channelClient = new PushInbound();
		clients.add(channelClient);
	    return channelClient;
	}
	
	public static PushInbound findClient(String c){
		for(int i=0; i<clients.size(); i++){
			if(((clients.get(i)).getId()).equals(c)){
				return clients.get(i);
			}
		}
		return null;
	}
	
	public static void removeClient(PushInbound c){
		for(int i=0; i<clients.size(); i++){
			if((clients.get(i)==c)){
				clients.remove(i);
			}
		}
	}

}
