package bank.xml;
import bank.exception.NotXmlException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

public class XmlReader {
    private boolean successfullyLoad;
    private Path xmlPath;
    public XmlReader(Path xmlPath) throws FileNotFoundException, NotXmlException, JAXBException {
        this.xmlPath = xmlPath;
        if (!(xmlPath.endsWith(".xml"))) {
            throw (new NotXmlException("Not xml Format"));
            }else{
                InputStream inputStream = new FileInputStream(new File(String.valueOf(xmlPath)));

            JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
            Unmarshaller u = jc.createUnmarshaller();
            u.unmarshal(xmlPath.toFile());
            }

        }

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "bank.xml.generated";

    public Path getXmlPath() {

        return this.xmlPath;
    }

    public void setXmlPath(Path xmlPath) throws NotXmlException {
        if(!xmlPath.endsWith(".xml")){
            throw (new NotXmlException("Not xml Format"));
        }
    }
}
