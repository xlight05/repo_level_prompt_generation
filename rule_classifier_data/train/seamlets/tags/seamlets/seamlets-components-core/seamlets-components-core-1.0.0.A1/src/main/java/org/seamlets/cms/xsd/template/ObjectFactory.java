//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.01.24 at 05:16:09 PM CET 
//


package org.seamlets.cms.xsd.template;

import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.seamlets.cms.admin.xsd.template package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Key_QNAME = new QName("http://jboss.com/products/seam/components", "key");
    private final static QName _Template_QNAME = new QName("http://seamlets.org/cms/template", "template");
    private final static QName _Import_QNAME = new QName("http://jboss.com/products/seam/components", "import");
    private final static QName _Value_QNAME = new QName("http://jboss.com/products/seam/components", "value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.seamlets.cms.admin.xsd.template
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ViewParts }
     * 
     */
    public ViewParts createViewParts() {
        return new ViewParts();
    }

    /**
     * Create an instance of {@link Factory }
     * 
     */
    public Factory createFactory() {
        return new Factory();
    }

    /**
     * Create an instance of {@link Components }
     * 
     */
    public Components createComponents() {
        return new Components();
    }

    /**
     * Create an instance of {@link Action }
     * 
     */
    public Action createAction() {
        return new Action();
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link ViewPart }
     * 
     */
    public ViewPart createViewPart() {
        return new ViewPart();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link MultiValuedProperty }
     * 
     */
    public MultiValuedProperty createMultiValuedProperty() {
        return new MultiValuedProperty();
    }

    /**
     * Create an instance of {@link Component }
     * 
     */
    public Component createComponent() {
        return new Component();
    }

    /**
     * Create an instance of {@link MapProperty }
     * 
     */
    public MapProperty createMapProperty() {
        return new MapProperty();
    }

    /**
     * Create an instance of {@link Template }
     * 
     */
    public Template createTemplate() {
        return new Template();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jboss.com/products/seam/components", name = "key")
    public JAXBElement<List<String>> createKey(List<String> value) {
        return new JAXBElement<List<String>>(_Key_QNAME, ((Class) List.class), null, ((List<String> ) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Template }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://seamlets.org/cms/template", name = "template")
    public JAXBElement<Template> createTemplate(Template value) {
        return new JAXBElement<Template>(_Template_QNAME, Template.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jboss.com/products/seam/components", name = "import")
    public JAXBElement<String> createImport(String value) {
        return new JAXBElement<String>(_Import_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jboss.com/products/seam/components", name = "value")
    public JAXBElement<List<String>> createValue(List<String> value) {
        return new JAXBElement<List<String>>(_Value_QNAME, ((Class) List.class), null, ((List<String> ) value));
    }

}