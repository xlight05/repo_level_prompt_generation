package br.com.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/*@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "jms/tst	"),
		@ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "Queue-0") })*/

@MessageDriven
(mappedName = "jms.tst",name = "Queue-0",activationConfig = {
		
@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
@ActivationConfigProperty(propertyName = "destinationName", propertyValue = "jms.tst")
}
)
public class ProcessMDB implements MessageListener {

	public void onMessage(Message message) {
		  System.out.println("Received Message: " +message); 
	}

}
