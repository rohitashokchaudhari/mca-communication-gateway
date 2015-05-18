package mca.communication.audit;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.interceptor.AbstractEnvelopeInterceptor;
import org.mule.management.stats.ProcessingTime;

public class LoggingInterceptor extends AbstractEnvelopeInterceptor {

	@Override
	public MuleEvent before(MuleEvent event) throws MuleException {
		System.out.println("Before Events : " + event);
		// TODO Auto-generated method stub
		return event;
	}

	@Override
	public MuleEvent after(MuleEvent event) throws MuleException {
		// TODO Auto-generated method stub
		System.out.println("After Events : " + event);
		return event;
	}

	@Override
	public MuleEvent last(MuleEvent event, ProcessingTime time, long startTime,
			boolean exceptionWasThrown) throws MuleException {
		// TODO Auto-generated method stub
		return null;
	}

}
