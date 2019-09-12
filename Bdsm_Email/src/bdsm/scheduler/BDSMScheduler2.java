package bdsm.scheduler;

import org.apache.log4j.Logger;


/**
*
* @author v00019372
*/
public abstract class BDSMScheduler2 extends BdsmScheduler {
	private static Logger LOGGER = Logger.getLogger(BDSMScheduler2.class);

	public BDSMScheduler2(long timeout, long sleep, long btSleep, int threadCount) {
		super(timeout, sleep, btSleep, threadCount);
	}
	
	
	public final void terminate(boolean force) {
		if (force)
			this.forceTerminate();
		else
			this.terminate();
	}
	
	protected final void forceTerminate() {
		this.forceTerminateBackThreads();
		
		this.terminate();
		this.interrupt();
	}
	
	@SuppressWarnings({"unchecked"})
	protected final void forceTerminateBackThreads() {
		try {
			java.util.List<BdsmBackThread> backThreads = (java.util.List<BdsmBackThread>) getFieldValue(this, "backThreads");
			
			for (BdsmBackThread bt : backThreads) {
				bt.terminate();
                LOGGER.debug("BACKTHREAD :" + bt.getName());
				bt.interrupt();
			}
		}
		catch (Exception ex) {
			this.getLogger().fatal(ex, ex);
		}
	}
	
	protected static Object getFieldValue(Object object, String fieldName) {
		return getFieldValue(object, object.getClass(), fieldName);
	}
	
	@SuppressWarnings("rawtypes")
	private static final Object getFieldValue(Object object, Class clazz, String fieldName) {
		try {
			java.lang.reflect.Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			Object returnObject = f.get(object);
			
			return returnObject;
		}
		catch (NoSuchFieldException nsfe) {
			if (clazz.getSuperclass() != null)
				return getFieldValue(object, clazz.getSuperclass(), fieldName);
			else
				return null;
		}
		catch (Exception e) {
			LOGGER.fatal(e, e);
			return null;
		}
	}

}
