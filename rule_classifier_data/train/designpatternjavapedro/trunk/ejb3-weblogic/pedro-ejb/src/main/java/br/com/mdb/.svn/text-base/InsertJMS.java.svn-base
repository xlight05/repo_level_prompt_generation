package br.com.mdb;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class InsertJMS {

	public static void main(String[] args) throws Exception {
		Context context = getInitialContext();

		ConnectionFactory connectionFactory = (ConnectionFactory) context
				.lookup("jms.com");
		Queue queue = (Queue) context.lookup("jms.tst");
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(queue);
		ObjectMessage ob = session.createObjectMessage();
		ob.setObject(new String());
		TextMessage message = session.createTextMessage();
		message.setText("Hello World");
		producer.send(ob);

		connection.close();
	}

	private static Context getInitialContext() throws Exception {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		// TODO: Change the server and port name to suit your environment before
		// running the class
		env.put(Context.PROVIDER_URL, "t3://localhost:7001");
		return new InitialContext(env);
	}
}
