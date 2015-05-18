package mca.communication;

import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;


public class ExecuteGatewayMessage implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		
		MuleMessage message = eventContext.getMessage();
		Map<Integer, String> map = (Map<Integer, String>) message.getPayload();
		
		GatewayPushMessage pushServer = new GatewayPushMessage(map);
		
		return pushServer.pushMessagesAsynchronousSend();
	}

}
