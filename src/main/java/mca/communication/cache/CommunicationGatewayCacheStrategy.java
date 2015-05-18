package mca.communication.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.processor.MessageProcessor;

public class CommunicationGatewayCacheStrategy implements Callable {
	
	private MuleEventContext eventContext;
	
	private String action;

	private MessageProcessor next;  
    private Ehcache cache;  
    public void setListener(MessageProcessor listener)  
    {  
      next = listener;  
    }
    
    public void setCache(final Ehcache cache)  
    {  
      this.cache = cache;  
    }

	@Override
	public Object onCall(MuleEventContext event) throws Exception {

		Object chachedObj = null;
		if ("Write".equalsIgnoreCase(action)) {
			final MuleMessage currentMessage = event.getMessage();
			final Object value = currentMessage.getPayload();
			final String key = event.getSession().getProperty("sms");
			chachedObj = value;
			Element element = new Element(key, value);
			cache.put(element);
		} else {
			
			final MuleMessage currentMessage = event.getMessage();
			final Object key = currentMessage.getPayload(); 
			final Element cachedElement = cache.get(key);
			if (cachedElement != null) {
				chachedObj = cachedElement.getObjectValue();
			}
		}
		return chachedObj;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	

}
