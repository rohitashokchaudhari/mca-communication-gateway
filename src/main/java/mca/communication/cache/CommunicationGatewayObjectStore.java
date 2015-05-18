package mca.communication.cache;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
 




import org.mule.DefaultMuleEvent;
import org.mule.api.MuleEvent;
import org.mule.api.MuleEventContext;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;

import com.mulesoft.datamapper.mule.MuleContextAccessorFactory;

public class CommunicationGatewayObjectStore<T extends Serializable> implements ObjectStore<T> {

	 private Ehcache cache;
	 
	 private MuleEventContext eventContext;
	 
		@Override
		public synchronized boolean contains(Serializable key) throws ObjectStoreException {
			return cache.isKeyInCache(key);
		}
	 
		@Override
		public synchronized void store(Serializable key, T value) throws ObjectStoreException {
			System.out.println("11111111111 CommunicationGatewayObjectStore 111111111111111 Key : " + key + " Value :" + value);
			DefaultMuleEvent event = (DefaultMuleEvent)value;
			
			Object obj = event.getMessage().getPayload();
			Element element = new Element(key, value);
			cache.put(element);
		}
	 
		@SuppressWarnings("unchecked")
		@Override
		public synchronized T retrieve(Serializable key) throws ObjectStoreException {
	 
			System.out.println("0000000000000000000 CommunicationGatewayObjectStore 0000000000000000 Key : ");
			Element element = cache.get(key);
			if (element == null)
			{
				return null;
			}
			return (T)cache.get(key).getObjectValue();
		}
	 
		@Override
		public synchronized T remove(Serializable key) throws ObjectStoreException {
			T value = retrieve(key);
			cache.remove(key);
			return value;
		}
	 
		@Override
		public boolean isPersistent() {
			return false;
		}
	 
		public Ehcache getCache() {
			return cache;
		}
	 
		public void setCache(Ehcache cache) {
			this.cache = cache;
		}

		@Override
		public void clear() throws ObjectStoreException {
			// TODO Auto-generated method stub
			
		}

}
