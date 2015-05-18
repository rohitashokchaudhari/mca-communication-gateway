import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.module.json.JsonData;


public class InboundMessageReader implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		Object obj = eventContext.getMessage();
		Object obj1 = null;
		JsonData json = null;
		if (obj != null) {
			System.out.println("Message is : " + obj.getClass());
			MuleMessage msg = (MuleMessage)obj;
			System.out.println("Message received is : " + msg.getPayload());
			obj1 = msg.getPayload();
/*			InputStream input = (InputStream) obj1;
			System.out.println("111111111111111111111111" + obj1.getClass());
			String outputStr = getStringFromInputStream(input);
			System.out.println("23232323232222222222222222222222222222222" + outputStr);*/
			json = (JsonData)obj1;
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
