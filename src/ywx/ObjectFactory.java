
package ywx;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ywx package. 
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

    private final static QName _SayhiResponse_QNAME = new QName("http://ywx/", "sayhiResponse");
    private final static QName _Sayhi_QNAME = new QName("http://ywx/", "sayhi");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ywx
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayhiResponse }
     * 
     */
    public SayhiResponse createSayhiResponse() {
        return new SayhiResponse();
    }

    /**
     * Create an instance of {@link Sayhi }
     * 
     */
    public Sayhi createSayhi() {
        return new Sayhi();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayhiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ywx/", name = "sayhiResponse")
    public JAXBElement<SayhiResponse> createSayhiResponse(SayhiResponse value) {
        return new JAXBElement<SayhiResponse>(_SayhiResponse_QNAME, SayhiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Sayhi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ywx/", name = "sayhi")
    public JAXBElement<Sayhi> createSayhi(Sayhi value) {
        return new JAXBElement<Sayhi>(_Sayhi_QNAME, Sayhi.class, null, value);
    }

}
