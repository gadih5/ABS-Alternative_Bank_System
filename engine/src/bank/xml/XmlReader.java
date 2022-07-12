package bank.xml;
import bank.exception.NotXmlException;
import bank.xml.generated_old.AbsDescriptor;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

public class XmlReader {
    private final static String JAXB_XML_PACKAGE_NAME = "bank.xml.generated";
    private boolean successfullyLoad;
    private Path xmlPath;
    AbsDescriptor descriptor;

    public XmlReader(Path xmlPath) throws FileNotFoundException, NotXmlException, JAXBException {
        this.xmlPath = xmlPath;
        if(!(xmlPath.toString().endsWith(".xml"))){
            throw (new NotXmlException("Not Xml format!"));
        }else{
            File xmlFile = xmlPath.toFile();
            InputStream inputStream = new FileInputStream(xmlFile);
            JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
            Unmarshaller u = jc.createUnmarshaller();
            this.descriptor =(AbsDescriptor) u.unmarshal(inputStream);
        }
    }

    public AbsDescriptor getDescriptor() {
        return descriptor;
    }

    public Path getXmlPath() {

        return this.xmlPath;
    }

    public void setXmlPath(Path xmlPath) throws NotXmlException {
        if(!xmlPath.endsWith(".xml")){
            throw (new NotXmlException("Not xml Format"));
        }
    }
}
