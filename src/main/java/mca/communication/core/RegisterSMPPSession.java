package mca.communication.core;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;


public class RegisterSMPPSession implements MuleContextAware, Disposable {

	@Override
	public void setMuleContext(MuleContext context) {
		
		try {
			/*ReadGatewayResponse reader = new ReadGatewayResponse();
			//PollableSmppSessionHandler handler 
			
			Map<String, Object> smppMap = reader.getSMPPSessionHandler();
			SmppSession sessionObje = (SmppSession)smppMap.get("session");
			PollableSmppSessionHandler handler = (PollableSmppSessionHandler)smppMap.get("handler");
			System.out.println("Handler has been created successfully : " + handler);

			context.getRegistry().registerObject("handler", handler);
			context.getRegistry().registerObject("session", sessionObje);*/
			
			Map<String, String> keywordMap = new HashMap<String, String>();
			keywordMap.put("Activate", "Provider,Message,CCNumber");
			context.getRegistry().registerObject("keywordMap", keywordMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		//System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		
	}



}
