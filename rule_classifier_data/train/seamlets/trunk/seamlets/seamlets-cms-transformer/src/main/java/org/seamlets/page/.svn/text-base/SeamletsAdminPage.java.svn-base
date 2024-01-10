package org.seamlets.page;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.seamlets.cms.deploy.JAXBClassRepository;
import org.seamlets.cms.entities.PageDefinition;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class SeamletsAdminPage extends SeamletsPage {

	private final PageDefinition		pageDefinition;

	public SeamletsAdminPage(PageDefinition pageDefinition, String templateKey) {
		super(templateKey);
		this.pageDefinition = pageDefinition;
	}

	@Override
	public void getSAXContent(ContentHandler ch) throws SAXException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(JAXBClassRepository.instance().getRegisteredClasses());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(pageDefinition, ch);
		} catch (JAXBException e) {
			throw new SAXException(e);
		}
	}

}
