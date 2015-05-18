package mca.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.module.json.JsonData;

import com.thoughtworks.xstream.XStream;

public class MessageReader implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		
		Object obj = eventContext.getMessage();
		JsonData json = null;
		if (obj != null) {
			//System.out.println("MessageReader - Message is : " + obj.getClass());
			DefaultMuleMessage msg = (DefaultMuleMessage)obj;
			//System.out.println("MessageReader - Message received is : " + msg.getPayload());
			Object obj1 = msg.getPayload();

			/*InputStream input = (InputStream) obj1;
			System.out.println("MessageReader - 111111111111111111111111" + obj1.getClass());
			String outputStr = getStringFromInputStream(input);
			System.out.println("MessageReader - 23232323232222222222222222222222222222222" + outputStr);*/
			
			XStream str = new XStream();
			json = (JsonData)str.fromXML(obj1.toString());
			
			String requestMsg = json.get("message").asText();
			
			Object sessionObj = eventContext.getSession().getProperty("userRequest");
			System.out.println(" Test Message is : ================================================> " + requestMsg);
			//System.out.println(" ========================Exception -========================== " + sessionObj);
			
			if (requestMsg.equalsIgnoreCase("Exception")) {
				throw new Exception("This is custom message for testing exception strategy !!! ");
			}
			
		}
		
		return json;
	}
	
	private static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
 

}
