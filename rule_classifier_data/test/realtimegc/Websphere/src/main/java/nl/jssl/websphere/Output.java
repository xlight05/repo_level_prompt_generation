package nl.jssl.websphere;


import com.googlecode.aluminumproject.Aluminum;
import com.googlecode.aluminumproject.configuration.DefaultConfiguration;
import com.googlecode.aluminumproject.context.Context;
import com.googlecode.aluminumproject.context.DefaultContext;
import com.googlecode.aluminumproject.writers.StringWriter;

public class Output {
	static Aluminum engine = new Aluminum(new DefaultConfiguration());
	
	private String templateClasspathResource;
	private Context context;
	
	public static Output template(String templateClasspathResource) {
		Output output = new Output();
		output.templateClasspathResource=templateClasspathResource;
		return output;
	}

	/**
	 * @param valueSet the "closure" that sets the values
	 * @return
	 */
	public Output addValues(ValueSet valueSet) {
		Context context = new DefaultContext();
		/*call the callback*/
		valueSet.set(context);
		
		this.context=context;
		return this;
	}

	public String getText() {
		StringWriter stringWriter = new StringWriter();
		engine.processTemplate(templateClasspathResource, "xml", context, stringWriter);
		return stringWriter.getString();
	}
	
	
}
