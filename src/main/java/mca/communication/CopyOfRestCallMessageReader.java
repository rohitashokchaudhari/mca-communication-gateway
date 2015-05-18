package mca.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class CopyOfRestCallMessageReader implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
				Object obj = eventContext.getMessage();
				
				if (obj != null) {
					System.out.println("CopyOfRestCallMessageReader- Message is : " + obj.getClass());
					MuleMessage msg = (MuleMessage)obj;
					System.out.println("CopyOfRestCallMessageReader - Message received is : " + msg.getPayload());
					Object obj1 = msg.getPayload();
					InputStream input = (InputStream) obj1;
					System.out.println("CopyOfRestCallMessageReader - 111111111111111111111111" + obj1.getClass());
					String outputStr = getStringFromInputStream(input);
					System.out.println("CopyOfRestCallMessageReader - 23232323232222222222222222222222222222222" + outputStr);
				}
				
				return eventContext.getMessage();
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
