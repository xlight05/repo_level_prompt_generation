package se.openprocesslogger.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import se.opendataexchange.common.IAddressValueChanged;
import se.opendataexchange.common.InvalidAddressException;
import se.openprocesslogger.Data;
import se.openprocesslogger.OplController;
import se.openprocesslogger.db.DataBuffer;
import se.openprocesslogger.db.EventSaver;
import se.openprocesslogger.db.OPLDatabase;
import se.openprocesslogger.proxy.SubscriptionProxy;
import se.openprocesslogger.proxy.TriggerProxy;
import se.openprocesslogger.templates.OplTemplateCreator;
public class Subscription implements IAddressValueChanged,ITimerTrigg{
	private static Logger log = Logger.getLogger(Subscription.class);
	
	private String valueAddress;
	// triggerOption en del av trigger
	//private TriggerOptions triggerOption = TriggerOptions.onChange; // Farlig; spelar ingen roll end. get&set.
	private ITriggerEval trigger = null;
	protected TriggerOptions[] trigOptions = null;
	//private boolean useTrigger=false;
	
	private HashMap<String, ITriggerEval> triggers = new HashMap<String, ITriggerEval>();
	
	// If true, data is stored when no trigger is present
	private boolean onChange=false;
	
	private boolean onTimer=false;
	private long timerInterval=100;
	
	private RingBuffer buffer = null;
	private boolean useRingBuffer=false;
	
	private boolean useTrigger;
	
	private TimerTask timerTask;
	
	private long logTaskId=0;
	private long equipmentId=0;
	private DataBuffer db=null;
	private Data lastData=null;
	//private Data lastTimerData=null;
	
	protected boolean started = false;

	private Date lastTime = new Date(0);

	private TrigEvent trigevent = null;

	private boolean useExternalTrigger;
	//private ReentrantLock lock = new ReentrantLock();
	
	public Subscription(Object data, String valueAddress){
		if(data instanceof Byte || data instanceof Character || data instanceof Short || 
				data instanceof Integer || data instanceof Long || data instanceof  Float || 
				data instanceof Double) {
			trigOptions = AnalogTriggerEval.getTriggerOptions();
			this.trigger = new AnalogTriggerEval(TriggerOptions.onChange);
		}else if(data instanceof Boolean){
			trigOptions= DigitalTriggerEval.getTriggerOptions();
			this.trigger = new DigitalTriggerEval(TriggerOptions.onChange);
		}else{
			trigOptions=TriggerEval.getPossibleOptions();
			this.trigger = new TriggerEval(TriggerOptions.onChange);
		}
		this.valueAddress = valueAddress;
		db = OPLDatabase.getDataBuffer();
		//Log.debug("¤¤¤ Creating subscription ¤¤¤ "+valueAddress +" id "+random);
		
	}
	
	/***
	 * constructor from template.
	 */
	public Subscription(String valueAddress){
		trigOptions=TriggerEval.getPossibleOptions();
		this.trigger = new DigitalTriggerEval(TriggerOptions.onChange);
		this.valueAddress = valueAddress;
		db = OPLDatabase.getDataBuffer();
		//Log.debug("¤¤¤ Creating subscription ¤¤¤ "+valueAddress +" id "+random);
	}
	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#getTriggerOptions()
	 */
	public  TriggerOptions[] getTriggerOptions(){
		return trigOptions;
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#isOnChange()
	 */
	public boolean isOnChange() {
		return onChange;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setOnChange(boolean)
	 */
	public void setOnChange(boolean onChange) {
		this.onChange = onChange;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#isOnTimer()
	 */
	public boolean isOnTimer() {
		return onTimer;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setOnTimer(boolean)
	 */
	public void setOnTimer(boolean onTimer) {
		this.onTimer = onTimer;
		
		if(onTimer && this.timerTask != null && started){ // Cancel any running timer task
			this.timerTask.cancel();
			OplController.getController().getExecutionTimer().purge();
		}
		
		final Subscription sub = this;
		timerTask = new TimerTask(){
			public void run(){
				sub.timerTriggered();
			}
		};
		
		if(onTimer && started){ // Start immediately
			log.debug("Start immediately with interval "+timerInterval);
			OplController.getController().getExecutionTimer().schedule(this.timerTask,new Date(),this.timerInterval);
		}
	}
	
	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#getTimerTask()
	 */
	public TimerTask getTimerTask(){
		return this.timerTask;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#getTimerIntervall()
	 */
	public long getTimerInterval() {
		return timerInterval;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setTimerIntervall(long)
	 */
	public void setTimerInterval(long timerIntervall) {
		this.timerInterval = timerIntervall;		
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#getLogtaskId()
	 */
	public long getLogTaskId() {
		return logTaskId;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setLogtaskId(long)
	 */
	public void setLogTaskId(long logtaskId) {
		this.logTaskId = logtaskId;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#getEquipmentId()
	 */
	public long getEquipmentId() {
		return equipmentId;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setEquipmentId(long)
	 */
	public void setEquipmentId(long equipmentId) {
		this.equipmentId = equipmentId;
	}


	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setTrigger(se.openprocesslogger.log.ITriggerEval)
	 */
	public void setTrigger(ITriggerEval trigger) {
		if(trigger == null) return;
		if(trigger instanceof AnalogTriggerEval){
			//System.out.println("Setting analog trigger on " + this.valueAddress);
			trigOptions = AnalogTriggerEval.getTriggerOptions();
		}else if(trigger instanceof DigitalTriggerEval){
			trigOptions = DigitalTriggerEval.getTriggerOptions();
		}else{
			trigOptions=TriggerEval.getPossibleOptions();
		}	
		this.trigger = trigger;
	}
	
	public void setTriggers(HashMap<String, ITriggerEval> map){
		triggers = map;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setUseRingBuffer(boolean)
	 */
	public void setUseRingBuffer(boolean useRingBuffer) {
		this.useRingBuffer = useRingBuffer;
	}

	/* (non-Javadoc)
	 * @see se.openprocesslogger.log.ISubscription#setBuffer(se.openprocesslogger.log.RingBuffer)
	 */
	public void setBuffer(RingBuffer buffer) {
		this.buffer = buffer;
	}

	public void startSubscription(){
		this.started= true;
		String space;
		triggers.put(this.valueAddress, this.trigger);
		for(String value : triggers.keySet()){
			space = value.split(OplTemplateCreator.nameSeparator)[0];
			try {
				//OplController.getController().getOdeController().subscribeForAddressValue(value, this);
				OplTemplateCreator.getAddressSpace(space).subscribeForAddressValue(value, this);
			} catch (InvalidAddressException e) {
				log.error("SERIOUS ERROR IN Subscription: Address not found: ." +value+".");
			}
		}
		triggers.remove(this.valueAddress);
		if(onTimer){
			OplController.getController().getExecutionTimer().schedule(this.timerTask,new Date(),this.timerInterval);
		}
	}
	
	public void stopSubscription(){
		this.started=false;
		String space;
		triggers.put(this.valueAddress, this.trigger);
		for(String value : triggers.keySet()){
			space = value.split(OplTemplateCreator.nameSeparator)[0];
			try {
				//OplController.getController().getOdeController().unsubscribeForAddressValue(value, this);
				OplTemplateCreator.getAddressSpace(space).unsubscribeForAddressValue(value, this);
			} catch (InvalidAddressException e) {
				log.error("SERIOUS ERROR IN Subscription: Address not found: ." +value+".");
			}
		}
		triggers.remove(this.valueAddress);
		
		if(onTimer){
			this.timerTask.cancel();
			OplController.getController().getExecutionTimer().purge();
		}


//		lock.lock();
//		try {
		synchronized (lastTime) {
			if(useRingBuffer && buffer.hasTriggered()){
				Data[] d = buffer.getData();
				if(trigevent != null){
					log.debug("Adding triggevent in processData(), hash: " + trigevent.hash());
					trigevent.setStartTime(d[0].getTimestamp());
					trigevent.setStopTime(d[d.length-1].getTimestamp());
					EventSaver.instance().storeEvent(trigevent);
					trigevent = null;
				}
				db.addData(d);
			}
			if(lastData != null && lastData.getTimestamp().compareTo(lastTime) > 0)
				processData(lastData, true); // Always save the last value with actual timestamp
		}
//		} finally {
//			lock.unlock();
//		}
	}

	public String getValueAddress() {
		return valueAddress;
	}

	/* (non-Javadoc)
	 * @see org.openia.opendataexchange.common.IAddressValueChanged#valueHasChanged(org.openia.opendataexchange.common.AddressValue)
	 */
	@Override
	public void valueHasChanged(String name, Object value, Date timestamp) {
		if(!started)return;
		synchronized (lastTime) {
			if(timestamp.getTime() < lastTime.getTime()-250){
				log.fatal("########### Lost value #############");
				return;
			}
			boolean saveVal = false;
			Object lastValue = lastData == null ? null : lastData.getValue();
			Data triggData = new Data(name,this.equipmentId,this.logTaskId,value,timestamp);
			if(name.equals(valueAddress)){
				if(timestamp.getTime() > lastTime.getTime()){
					lastData = new Data(this.valueAddress,this.equipmentId,this.logTaskId,value,timestamp);
					if(lastValue != null && lastValue.equals(value)){
						return; // Don't save same value again, but the timestamp is updated
					}
					lastTime = lastData.getTimestamp();
				}
				saveVal = true;
			}
			processData(triggData, saveVal);
		}
	}
	
	private void processData(Data newData, boolean save){
		if(useRingBuffer && save){
			if(buffer.addData(newData)){
				Data[] d = buffer.getData();
				if(trigevent != null){
					log.debug("Adding triggevent in processData(), hash: " + trigevent.hash());
					trigevent.setStartTime(d[0].getTimestamp());
					trigevent.setStopTime(d[d.length-1].getTimestamp());
					EventSaver.instance().storeEvent(trigevent);
					trigevent = null;
				}
				db.addData(d);
				/*for(Data data : d){
					log.debug("Adding "+data.getValue());
				}*/
				log.debug(this.valueAddress+" : Adding "+d.length+" data points.");
			}
		}
		if(onChange && save){
			// report on changed value
			db.addData(newData);
		} else if(useTrigger || !triggers.isEmpty()){
			if((useTrigger && newData.getName().equals(this.valueAddress)) || triggers.containsKey(newData.getName())){
				if(evalTrigger(newData)){
					log.debug("# "+newData.getName()+" triggered, logging "+ this.valueAddress+" #");
					if(triggers.get(newData.getName()) != null)
						log.debug("# Reason: "+triggers.get(newData.getName()).getDescription()+" #");
					if(useRingBuffer){
						if(buffer.trigger()){
							Data[] d = buffer.getData();
							if(trigevent != null){
								if(d.length > 0){
									log.debug("Adding triggevent in processData(), hash: " + trigevent.hash());
									trigevent.setStartTime(d[0].getTimestamp());
									trigevent.setStopTime(d[d.length-1].getTimestamp());
									EventSaver.instance().storeEvent(trigevent);
								} else{
									log.debug("No data available when trigger came");
								}
								trigevent = null;
							}
							db.addData(d);
							log.debug(this.valueAddress+" : Adding "+d.length+" data points.");
						}
					}else{
						if(trigevent != null && (save || lastData != null)){
							if(save)
								db.addData(newData);
							else
								db.addData(lastData);
							
							trigevent.setStartTime(newData.getTimestamp());
							trigevent.setStopTime(newData.getTimestamp());
							EventSaver.instance().storeEvent(trigevent);
						}
						trigevent = null;
					}
				}
			}
		}
	}
	
	public void timerTriggered(){
		if(!started) return;
		//final Subscription sub = this;
		Thread updater = new Thread(new Runnable(){
			public void run(){
//				lock.lock();
//				try{
				synchronized (lastTime) {
					if(lastData != null){
						db.addData(lastData);
						//db.addData(new Data(lastData.getName(), lastData.getEquipment(), lastData.getLoggingTask(), lastData.getValue(), new Date()));
						//log.debug("data add");
					}
				}
//				} finally {
//					lock.unlock();
//				}
			}});
		updater.start();
	}
	
	protected boolean evalTrigger(Data data){
		String name = data.getName();
		Object val = data.getValue();
		boolean trigged = false;
		ITriggerEval trigg = triggers.get(name);
		if(name.equals(valueAddress))
			trigg = trigger;
		if(trigg!=null){
			try{
				// Onödigt? Smäll in Object bara. Kräver liten ändring i DigitalTriggerEval.
				// Vill vi ha denna logik på många ställen?
				if(val instanceof Byte) {
					trigged = trigg.eval((Byte)val);
				
				}else if (val instanceof Character) {
					trigged = trigg.eval((Character) val);
				
				}else if (val instanceof Short) {
					trigged = trigg.eval((Short) val);
				
				}else if (val instanceof Integer) {
					trigged = trigg.eval((Integer) val);
				
				}else if (val instanceof Long) {
					trigged = trigg.eval((Long) val);
				
				}else if (val instanceof Float) {
					trigged = trigg.eval((Float) val);
				
				}else if (val instanceof Double) {
					trigged = trigg.eval((Double) val);
				}else if(val instanceof Boolean) {
					trigged = trigg.eval((Boolean)val);
				}else {
					trigged = trigg.eval(val);
				}
				if(trigged){
					if(trigevent == null){
						trigevent = new TrigEvent(data.getTimestamp().getTime(), name, trigg.getDescription(), this.logTaskId);
					}
				}
			}catch(TriggerEvalException e){
				e.printStackTrace();
				trigged = false;
			}
		}
		return trigged;
	}

	public boolean isUseTrigger() {
		return useTrigger;
	}

	public void setUseTrigger(boolean useTrigger) {
		this.useTrigger = useTrigger;
	}

	public SubscriptionProxy proxify() {
		SubscriptionProxy proxy = new SubscriptionProxy();
		fill(proxy);
		return proxy;
	}

	public void fill(SubscriptionProxy proxy) {
		proxy.setEquipmentId(equipmentId);
		proxy.setValueAddress(valueAddress);
		proxy.setLogTaskId(logTaskId);
		proxy.setOnChange(onChange);
		proxy.setOnTimer(onTimer);
		proxy.setTimerInterval(timerInterval);
		proxy.setUseTrigger(useTrigger);
		proxy.setUseRingBuffer(useRingBuffer);
		if(this.buffer!=null){
			proxy.setCountAfter(buffer.getCountAfter());
			proxy.setCountBefore(buffer.getCountBefore());
			proxy.setUsingSeconds(buffer.isUsingSeconds());
		}
		proxy.setTriggerOptions(getTriggerOptionsStr());
		if(trigger != null)
			proxy.setTrigger(this.trigger.proxify());
		proxy.setDescription(this.getDescription());
		HashMap<String, TriggerProxy> t = new HashMap<String, TriggerProxy>();
		for(String val : triggers.keySet()){
			t.put(val, triggers.get(val).proxify());
		}
		proxy.setTriggers(t);
		proxy.setUseExternalTrigger(useExternalTrigger);
	}
	
	private String[] getTriggerOptionsStr(){
		//System.out.println(this.valueAddress +" has " +trigOptions.length);
		String[] optStr = new String[trigOptions.length];
		for(int i=0; i<trigOptions.length; i++){
			optStr[i] = trigOptions[i].toString();
		}
		return optStr;
	}
	
	public void update(SubscriptionProxy proxy){
		//Log.debug("Updating subscription "+proxy.getValueAddress()+" with proxy. OnChange: "+proxy.isOnChange());
		setEquipmentId(proxy.getEquipmentId());
		valueAddress = proxy.getValueAddress();
		setLogTaskId(proxy.getLogTaskId());
		setOnChange(proxy.isOnChange());
		setTimerInterval(proxy.getTimerInterval());			
		setOnTimer(proxy.isOnTimer()); // Important to set timer interval before onTimer
		setUseTrigger(proxy.isUseTrigger());
		setUseExternalTrigger(proxy.isUseExternalTrigger());
		setUseRingBuffer(proxy.isUseRingBuffer());
		RingBuffer buf = new RingBuffer();
		buf.setCountAfter(proxy.getCountAfter());
		buf.setCountBefore(proxy.getCountBefore());
		buf.setUsingSeconds(proxy.isUsingSeconds());
		setBuffer(buf);
		setTrigger(TriggerBuilder.getTrigger(proxy.getTrigger()));
		HashMap<String, TriggerProxy> trigs = proxy.getTriggers();
		triggers.clear();
		for(String s : trigs.keySet()){
			addTrigger(s, TriggerBuilder.getTrigger(trigs.get(s)));
		}
	}
	
	public void setUseExternalTrigger(boolean useExternalTrigger2) {
		useExternalTrigger = useExternalTrigger2;
	}
	
	public void isUseExternalTrigger(boolean useExternalTrigger2) {
		useExternalTrigger = useExternalTrigger2;
	}
	
	public boolean getUseExternalTrigger(){
		return useExternalTrigger;
	}

	public static Subscription getFromProxy(SubscriptionProxy proxy){
		Subscription sub = null;
		sub = new Subscription(proxy.getValueAddress());
		sub.update(proxy);
		return sub;
	}
	
	public String getDescription(){
		StringBuilder sb = new StringBuilder();
		if(onChange){
			sb.append("Report on changed value");
		}else {
			if(useTrigger && trigger != null){
				sb.append("Report on trigger: value ").append(trigger.getDescription());
			} 
			if(useExternalTrigger){
				if(sb.length()>0)
					sb.append("<br/>");
				
				if (triggers.size() > 0){
					sb.append("Report on external triggers:<br><ul>");
					for (Map.Entry<String, ITriggerEval> trig : triggers.entrySet()){
						sb.append("<li>").append(trig.getKey()).append(": <br>");
						sb.append(trig.getValue().getDescription());
						sb.append("</li>");						
					}
					sb.append("</ul>");
				}
				
			}
			if(useRingBuffer && (useTrigger || useExternalTrigger)){
				String val = buffer.isUsingSeconds() ? " seconds" : " values";
				sb.append("<br/> Using ring buffer: saving ").append(buffer.getCountBefore()).append(val).append(" before and ")
				.append(buffer.getCountAfter()).append(val).append(" after trigger.");
			}
		}
		if(!onChange && onTimer){
			if(sb.length()>0) sb.append("<br/>");
			sb.append("Report on timer interval "+this.timerInterval +" ms");
		}
		if(sb.length()==0){
			sb.append("Never report");
		}
		return sb.toString();
	}

	public void addTrigger(String addressValue, ITriggerEval trigger) {
		if(triggers.containsKey(addressValue))
			return;
		triggers.put(addressValue, trigger);
	}

	public void removeTrigger(String addressValue) {
		ITriggerEval trigg = triggers.remove(addressValue);
		if(trigg != null && started){
			String space = this.valueAddress.split(OplTemplateCreator.nameSeparator)[0];
			try {
				OplTemplateCreator.getAddressSpace(space).unsubscribeForAddressValue(this.valueAddress, this);
			} catch (InvalidAddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
