package mca.communication;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import org.mobicents.smsc.library.MessageUtil;

import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSession;
import com.cloudhopper.smpp.pdu.BaseSm;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.EnquireLinkResp;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.simulator.SmppSimulatorBindProcessor;
import com.cloudhopper.smpp.simulator.SmppSimulatorServer;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.util.DaemonExecutors;
import com.cloudhopper.smpp.util.SmppSessionUtil;


public class GatewayPushMessage {
	
    public static final String SYSTEMID = "test";
    public static final String PASSWORD = "test";
    public static final int PORT = 9785;

    static SmppSimulatorServer server;
    static DefaultSmppClient bootstrap;
    
    private Map<Integer, String> messageMap;
    
    
    private GatewayPushMessage() {
    	
    }
    
    public GatewayPushMessage(Map<Integer, String> map) {
    	this.messageMap = map;
    }
    
    
    public static void startSimulator() {
        server = new SmppSimulatorServer(DaemonExecutors.newCachedDaemonThreadPool());
        server.start(PORT);
        bootstrap = new DefaultSmppClient();
    }

    public static void stopSimulator() {
        server.stop();
        bootstrap.destroy();
    }
    
    public void clearAllServerSessions() {
        server.getHandler().getSessionQueue().clear();
    }

    public void registerServerBindProcessor() {
       // server.getHandler().setDefaultPduProcessor(new SmppSimulatorBindProcessor(SYSTEMID, PASSWORD));
    }

    public void unregisterServerBindProcessor() {
       // server.getHandler().setDefaultPduProcessor(null);
    }
	
    public SmppSessionConfiguration createDefaultConfiguration() {
        SmppSessionConfiguration configuration = new SmppSessionConfiguration();
        configuration.setWindowSize(1);
        configuration.setName("Tester.Session.0");
        configuration.setType(SmppBindType.TRANSCEIVER);
        configuration.setHost("127.0.0.1");
        configuration.setPort(2776);
        configuration.setConnectTimeout(10000);
        configuration.setSystemId(SYSTEMID);
	    configuration.setPassword(PASSWORD);
	    configuration.setAddressRange(new Address((byte)1, (byte)1, "6666"));
	    configuration.getLoggingOptions().setLogBytes(true);
	    
	    configuration.setRequestExpiryTimeout(30000);
	    configuration.setWindowMonitorInterval(15000);
	    configuration.setCountersEnabled(true);
        return configuration;
    }
	
	public String pushMessagesAsynchronousSend() throws Exception {
		
		Set<Integer> keySet = messageMap.keySet();
		if (keySet != null) {
			for (Integer key : keySet) {
				System.out.println("1111111111 - pushMessagesAsynchronousSend - 111111111111111");
				System.out.println(" Key : " + key);
				System.out.println(" Message : " + messageMap.get(key));
				if (key != null) {
					
				startSimulator();
				try {
			        SmppSessionConfiguration configuration = createDefaultConfiguration();
			        registerServerBindProcessor();
			        clearAllServerSessions();
			        // bind and get the simulator session
			        DefaultSmppSession session = (DefaultSmppSession)bootstrap.bind(configuration);
			        try {
			            EnquireLink el0 = new EnquireLink();
			            el0.setSequenceNumber(0x1000);
			            EnquireLinkResp el0Resp = el0.createResponse();
			            // send asynchronously (no response will be received yet)
			            BaseSm pdu = new SubmitSm();
			            pdu.setSourceAddress(new Address((byte)1, (byte)1, "6666"));
			            pdu.setDestAddress(new Address((byte)1, (byte)1, String.valueOf(key)));
			            pdu.setEsmClass((byte)3);
			            pdu.setValidityPeriod(MessageUtil.printSmppRelativeDate(0, 0, 0, 0, 5, 0));
			            pdu.setDataCoding((byte)0);
			            Charset utf8Charset = Charset.forName("UTF-8");
			            pdu.setShortMessage(messageMap.get(key).getBytes(utf8Charset));
			            pdu.setRegisteredDelivery((byte)0);
			            session.sendRequestPdu(pdu, 2000, false);
			        } finally {
			            SmppSessionUtil.close(session);
			        }
				} catch(Exception ex) {
					ex.printStackTrace();
				} finally {
					stopSimulator();
				}
				}
			}
			
		}
		
		System.out.println("Message has been send successfully");
		return "Message";
    }
}
