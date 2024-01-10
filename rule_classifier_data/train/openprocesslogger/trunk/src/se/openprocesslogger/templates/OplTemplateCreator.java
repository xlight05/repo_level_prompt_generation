package se.openprocesslogger.templates;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import se.opendataexchange.common.AddressSpace;
import se.opendataexchange.common.AddressUnit;
import se.opendataexchange.common.AddressValue;
import se.openprocesslogger.OplController;
import se.openprocesslogger.OplProperties;
import se.openprocesslogger.log.LoggingTask;
import se.openprocesslogger.proxy.LogTaskProxy;
import se.openprocesslogger.proxy.SubscriptionProxy;

public class OplTemplateCreator {
	public static final String nameSeparator = "/";
	private static Logger log = Logger.getLogger(OplTemplateCreator.class);
	
	private static HashMap<String, AddressSpace> spaces = new HashMap<String, AddressSpace>();
	
	private static LoggingTask[] taskTemplates = null;
	
	public static AddressSpace getAddressSpace(String name){
		if(spaces.size() == 0){
			for(AddressSpace space : OplController.getController().getOdeController().getAddressSpaces()){
				spaces.put(space.getName(), space);
				log.debug("Loaded address space: "+space.getName());
			}
		}
		
		if(name != "" && name != null && spaces.get(name) != null)
			return spaces.get(name);
		
		if(spaces.size() == 0){
			log.error("No address spaces found");
			return null;
		}		
		
		return spaces.values().toArray(new AddressSpace[0])[0];		
	}

	private static void loadTemplates(){
		String path = OplProperties.getTemplateFileName();
		AbstractResource resource = null;
		if(OplProperties.getTemplatePath().equals("") || OplProperties.getTemplatePath().equals(null)){
			log.debug("No template path given. Loading from classpath: "+path);
			resource = new ClassPathResource(path);
		}else{
			resource = new FileSystemResource(new File(OplProperties.getTemplatePath(),path));
		}
		long tStart = System.currentTimeMillis();
		log.debug("Loading templates..");
		XmlBeanFactory bf = new XmlBeanFactory(resource);
		String[] beans = bf.getBeanNamesForType(se.openprocesslogger.templates.LoggingTaskCreator.class);
		taskTemplates = new LoggingTask[beans.length];
		log.debug("Templates loaded in "+(System.currentTimeMillis()-tStart));
		for(int i=0; i<taskTemplates.length; i++){
			taskTemplates[i] = ((LoggingTaskCreator)bf.getBean(beans[i])).getLoggingTask();
			log.debug("Task template: "+taskTemplates[i]);
		}
		log.debug("All templates created in "+(System.currentTimeMillis()-tStart));
	}
	
	public static void init(){
		loadTemplates();
	}
	/*
	public static LoggingTask getLoggingTaskTemplate(String templateName){
		if(taskTemplates == null){
			loadTemplates();
		}
		for(LoggingTask task : taskTemplates){
			if(task.getTemplateName().equals(templateName)) return task;
		}
		return null;
	}
	*/
	public static LogTaskProxy[] getLoggingTaskTemplates(){
		if(taskTemplates == null){
			loadTemplates();
		}
		LogTaskProxy[] result = new LogTaskProxy[taskTemplates.length];
		for(int i=0; i<taskTemplates.length; i++){
			result[i] = taskTemplates[i].proxify();
		}
		return result;
	}
	
	public static void main(String[] args){
		log.debug("Get address spaces");
		AddressSpace space = getAddressSpace("");
		if(space != null){
			log.debug("Got space");
			printSpace(space);
		}else{
			log.debug("Found no spaces");
		}
		log.error("#########################");
		LogTaskProxy[] tasks = getLoggingTaskTemplates();
		for(LogTaskProxy task : tasks){
			log.debug(task.getName());
			for(SubscriptionProxy sub : task.getSubscriptions().values()){
				log.debug(sub.getValueAddress() +": " +sub.getDescription());
			}
		}
		log.debug("#########################");
	}
	
	private static void printSpace(AddressSpace space){
		for(AddressValue val : space.getAddressValues()){
			printVal(val);
		}
		for(AddressUnit unit : space.getAddressUnits()){
			printUnit(unit);
		}
	}
	
	private static void printUnit(AddressUnit unit1){
		log.debug(unit1.getName());
		for(AddressValue val : unit1.getMapValues().values()){
			printVal(val);
		}
		for(AddressUnit unit : unit1.getMapUnits().values()){
			printUnit(unit);
		}
	}
	
	private static void printVal(AddressValue value){
		log.debug(value.getName());
	}
}
