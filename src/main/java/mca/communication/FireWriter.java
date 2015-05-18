package mca.communication;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class FireWriter implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("FireWriter received : " + eventContext.getMessage());
		return eventContext.getMessage();
	}

}
