package mca.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.module.json.JsonData;
/**
 * @author s.zende
 *
 */
public class InputBufferMessageReader implements Callable {

	/*
	 * (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		Object messageObject = eventContext.getMessage();
		JsonData json = null;
		if (messageObject != null) {
			DefaultMuleMessage msg = (DefaultMuleMessage)messageObject;
			Object payloadObject = msg.getPayload();
			if (payloadObject instanceof InputStream) {
				InputStream inputStream = (InputStream) payloadObject;
				String inputString = getStringFromInputStream(inputStream);
				json = new JsonData(inputString);
			}
		}
		return json;
	}
	
	/*
	 * Convert input string into string.
	 */
	private static String getStringFromInputStream(InputStream inputStream) {
		 
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
 
		String line;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuilder.toString();
	}
}
