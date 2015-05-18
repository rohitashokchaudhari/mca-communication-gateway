package mca.communication.core;

import java.util.concurrent.TimeUnit;

import mca.communication.MessageDetails;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleSession;
import org.mule.api.lifecycle.Callable;
import org.mule.api.lifecycle.Disposable;
import org.mule.module.json.JsonData;

import com.cloudhopper.smpp.impl.PollableSmppSessionHandler;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.type.Address;
import com.google.gson.Gson;

public class PollSMPPSsession implements Callable, Disposable {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Polling start ");
		
		Object obj = eventContext.getMuleContext().getRegistry().get("handler");
		System.out.println(" Mule Context ================================================> " + eventContext.getMuleContext());
		System.out.println(" Mule Event   ================================================> " + eventContext);
		System.out.println(" Mule Session ================================================> " + eventContext.getSession());
		JsonData data = null;
		MessageDetails msgObj = null;
		if (obj != null) {
			PollableSmppSessionHandler handler = (PollableSmppSessionHandler) obj;
	        try {
	            PduRequest asyncpdu0 = handler.getReceivedPduRequests().poll(1000, TimeUnit.MILLISECONDS);
	            if (asyncpdu0 != null) {
	            	if (asyncpdu0 instanceof DeliverSm) {
	            		MuleSession session = eventContext.getSession();
	            		msgObj = new MessageDetails();
	            		DeliverSm sm = (DeliverSm)asyncpdu0;
	            		System.out.println("Short Message : " + sm.getShortMessage());
	            		String message = new String(sm.getShortMessage(), "UTF-16");
	            		
	            		System.out.println("Polling Short Message is : " + message);
	            		Address address = sm.getSourceAddress();
	            		//System.out.println("555555555555555555555 Source Address : " + address.getAddress());
	            		String number = address.getAddress();            		
	            		
	            		msgObj.setMessage(message);
	            		msgObj.setNumber(number);
	            		msgObj.setProvider("MTN");
	            		String strMsg = converInJason(msgObj);
	            		data= new JsonData(strMsg);
	            		session.setProperty("userRequest", "11111111111100000000");
	            	}
	            }
	            //}
	  
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	throw new Exception("Exception has been occurred while starting SMPP session" + e.toString());
	        	
	        }
			
		}
		//String gson = converInJason(msg);
		//JsonData data= new JsonData(gson);
		System.out.println("Polling end " + data);
		return data;
	}
	
	public static String converInJason(MessageDetails obj) {
		
		if (obj == null) {
			obj = new MessageDetails();
			obj.setMessage("Activate");
			obj.setNumber("5555");
			obj.setProvider("MTN");
		} 
	      Gson gson = new Gson();
	      //convert java object to JSON format
	      String json = gson.toJson(obj);
	      System.out.println(json);
	      return json;
	}

}
