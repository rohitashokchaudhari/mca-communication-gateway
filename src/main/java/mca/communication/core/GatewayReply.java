package mca.communication.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.mobicents.smsc.library.MessageUtil;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.pdu.BaseSm;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;

public class GatewayReply implements Callable {

	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		
		Object obj = eventContext.getMessage();
		if (obj != null) {
			System.out.println("MessageReader - Message is : " + obj.getClass());
			DefaultMuleMessage msg = (DefaultMuleMessage)obj;
			System.out.println("MessageReader - Message received is : " + msg.getPayload());
			Object obj1 = msg.getPayload();
			System.out.println("-------------------------------------------------------");
			
			InputStream input = (InputStream) obj1;
			System.out.println("111111111111111111111111" + obj1.getClass());
			String outputStr = getStringFromInputStream(input);
			System.out.println("23232323232222222222222222222222222222222" + outputStr);
		}
		
		String message = "Short Message has been send successfully";
		
		try {
			SmppSession session = (SmppSession)eventContext.getMuleContext().getRegistry().get("session");
			BaseSm pdu = new SubmitSm();
	         pdu.setSourceAddress(new Address((byte)1, (byte)1, "6666"));
	         pdu.setDestAddress(new Address((byte)1, (byte)1, "5555"));
	         pdu.setEsmClass((byte)3);
	         pdu.setValidityPeriod(MessageUtil.printSmppRelativeDate(0, 0, 0, 0, 5, 0));
	         pdu.setDataCoding((byte)0);
	         Charset utf8Charset = Charset.forName("UTF-8");
	         pdu.setShortMessage("Your Account has been activated.".getBytes(utf8Charset));
	         pdu.setRegisteredDelivery((byte)0);
	         //WindowFuture<Integer, PduRequest, PduResponse> future0 = session.sendRequestPdu(pdu, 2000, false);
		} catch (Exception e) {
			System.out.println("Exception has been accoured while sending message : ");
			message = "Error has been accourred while sending Short Message";
			e.printStackTrace();
		}
		
		return message;
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
