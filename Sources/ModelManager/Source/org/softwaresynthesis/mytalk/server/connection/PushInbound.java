package myServlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import com.google.gson.*;

import org.apache.catalina.websocket.MessageInbound;

public class PushInbound extends MessageInbound {
	
	private String id;
	
	public void setId(String s){id=s;}
	public String getId(){return id;}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
		String text=message.toString();
		
		
		Gson gson= new Gson();
		JsonParser parser = new JsonParser();
	    JsonArray array = parser.parse(text).getAsJsonArray();
	    String type = gson.fromJson(array.get(0), String.class);
	    
	    //identificazione canale
		if(type.equals("1")){
			String value = gson.fromJson(array.get(1), String.class);
			setId(value);
			String msg= "1|"+gson.fromJson(array.get(1), String.class);
			getWsOutbound().writeTextMessage(CharBuffer.wrap(msg));
		}
		//scambio dati per chiamata
		else if (type.equals("2")){
			String client = gson.fromJson(array.get(1), String.class);
			PushInbound sendTo= ChannelServlet.findClient(client);
			String msg = "2|" + gson.fromJson(array.get(2), String.class);
			sendTo.getWsOutbound().writeTextMessage(CharBuffer.wrap(msg));
		}
		//manda mio id al chiamato
		else if (type.equals("3")){
			String client = gson.fromJson(array.get(1), String.class);
			PushInbound sendTo= ChannelServlet.findClient(client);
			String msg = "3|" + id;
			sendTo.getWsOutbound().writeTextMessage(CharBuffer.wrap(msg));
		}
		//disconnessione ed eliminazione canale
		else if (type.equals("4")){
			String client = gson.fromJson(array.get(1), String.class);
			PushInbound remove= ChannelServlet.findClient(client);
			ChannelServlet.removeClient(remove);
		}
		
	}
}



