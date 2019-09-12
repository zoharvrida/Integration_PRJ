/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.FixQXtractDao;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.FixTemplateMasterDao;
import bdsm.scheduler.model.FixQXtract;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.FixTemplateMaster;
import bdsm.scheduler.model.FixTemplateMasterPK;
import bdsm.util.PatternUtil;


/**
 * 
 * @author bdsm
 */
public abstract class XtractProcessor extends BaseReflection {
	protected static final String filePath = PropertyPersister.dirFixOut;
	protected static final PatternUtil param = new PatternUtil("[$][{].{1,}[}]");
	protected Map context;
	protected org.hibernate.Transaction transaction;
	protected FixSchedulerXtract fixSchedulerXtract;
	protected FixTemplateMasterDao fixTemplateMasterDao;
	protected FixTemplateMasterPK fixTemplateMasterPK;
	protected FixTemplateMaster fixTemplateMaster;
	protected FixSchedulerXtractDao fixSchedulerXtractDao;
	protected FixQXtract fixQXtract;
	protected FixQXtractDao fixQXtractDao;
	

	protected void executeClass() throws Exception {
		Class[] paramString = new Class[1];
		paramString[0] = Map.class;
		String className = this.context.get(MapKey.javaClass).toString();
		Class cls;
		cls = Class.forName(className);
		Constructor cons = cls.getConstructor(paramString);
		this.getLogger().info("Execute class : " + className);
		/*
		 * add param to context for execute class
		 */
		this.context.put(MapKey.param1, this.fixQXtract.getParam1());
		this.context.put(MapKey.param2, this.fixQXtract.getParam2());
		this.context.put(MapKey.param3, this.fixQXtract.getParam3());
		this.context.put(MapKey.param4, this.fixQXtract.getParam4());
		if (this.fixQXtract.getParam5() != null)
			this.context.put(MapKey.param5, filePath + this.fixQXtract.getParam5());
		this.context.put(MapKey.param6, this.fixQXtract.getParam6());
		
		BaseProcessor baseProcessor = (BaseProcessor) cons.newInstance(this.context);
		boolean result = baseProcessor.execute();
		this.getLogger().info("Finish class '" + className + "' execution. Result : " + result);

	}

	protected String getValue(String value) {
		if (value != null) {
			if (this.param.match(value)) {
				// get method name
				Object[] a = null;
				String newValue = null;
				value = "get" + value.substring(2, value.length() - 1);
				try {
					Method method = this.fixQXtract.getClass().getDeclaredMethod(value, null);
					newValue = (String) method.invoke(this.fixQXtract, a);
				}
				catch (Exception ex) {
					this.getLogger().error(ex);
				}
				return newValue;
			}
			else {
				return value;
			}
		}
		else {
			return value;
		}
	}

}
