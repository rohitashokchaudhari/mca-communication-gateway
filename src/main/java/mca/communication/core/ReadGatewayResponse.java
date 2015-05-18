package mca.communication.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.PollableSmppSessionHandler;
import com.cloudhopper.smpp.simulator.SmppSimulatorServer;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.util.DaemonExecutors;

public class ReadGatewayResponse {
	
    public static final int PORT = 9785;
    public static final String SYSTEMID = "test";
    public static final String PASSWORD = "test";

    static SmppSimulatorServer server;
    static DefaultSmppClient bootstrap;
	
	
	public Map<String, Object> getSMPPSessionHandler() throws Exception {
		
		startSMPPServer();
		
		Map smppObjectMap = new HashMap<String, Object>();

        SmppSessionConfiguration configuration = createDefaultConfiguration();
        registerServerBindProcessor();
        clearAllServerSessions();

        // bind and get the simulator session
        PollableSmppSessionHandler sessionHandler = new PollableSmppSessionHandler();
        //DefaultSmppSession session = (DefaultSmppSession)bootstrap.bind(configuration, sessionHandler);
        ScheduledThreadPoolExecutor monitorExecutor = ((ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1, new ThreadFactory()
        {
            private AtomicInteger sequence = new AtomicInteger(0);
            public Thread newThread(Runnable r)
            {
              Thread t = new Thread(r);
              t.setName("SmppClientSessionWindowMonitorPool-" + this.sequence.getAndIncrement());
              return t;
            }
          }));
        this.bootstrap = new DefaultSmppClient(Executors.newCachedThreadPool(), 1, monitorExecutor);
        SmppSession session = null;
        try
        {
        	session = this.bootstrap.bind(configuration, sessionHandler);
        }
        catch (Exception e)
        {
          System.out.println("Failure to start a new session" + e.toString());
          throw new Exception("Failure to start a new session" + e.toString());
        }
        
        System.out.println(" Session has been created  : " + session.isOpen());
        
        smppObjectMap.put("session", session);
        smppObjectMap.put("handler", sessionHandler);
        
        //SmppSimulatorSessionHandler simulator0 = server.pollNextSession(1000);
        //simulator0.setPduProcessor(null);

        System.out.println("------------------receiveExpectedPduResponseViaAnAsynchronousSend----------------------");
        return smppObjectMap;
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
    
    public static void startSMPPServer() {
        server = new SmppSimulatorServer(DaemonExecutors.newCachedDaemonThreadPool());
        server.start(PORT);
        bootstrap = new DefaultSmppClient();
    }


    public static void stopSMPPServer() {
        System.out.println("Stopping the server inside test class");
        server.stop();
        System.out.println("Stopping the boostrap inside test class");
        bootstrap.destroy();
    }
    
    public void clearAllServerSessions() {
        server.getHandler().getSessionQueue().clear();
    }

    public void registerServerBindProcessor() {
       // server.getHandler().setDefaultPduProcessor(new SmppSimulatorBindProcessor(SYSTEMID, PASSWORD));
    }

    public void unregisterServerBindProcessor() {
      //  server.getHandler().setDefaultPduProcessor(null);
    }
}
