package by.bsuir.xsdSchemas;



import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eType.TypeXml;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Сергей on 02.05.2017.
 */
public class XmlSchemasValidator {
    private static final String pathXsdSchemaPerson =
            "D:\\project java\\Archive\\server\\src\\main\\java\\by\\bsuir\\xsdSchemas\\xsdSchemaPerson.xsd";
    private static final String pathXsdSchemaUser =
            "D:\\project java\\Archive\\server\\src\\main\\java\\by\\bsuir\\xsdSchemas\\xsdSchemaUser.xsd";


    /**
     * Validate xml file by xsd schema
     * @param xmlDoc xml file which will be validated
     * @param typeXml kind of xml file
     * @return true - xml file is correctly ; false - xml file is not correctly
     */
    public static boolean validateXMLSchema( String xmlDoc, TypeXml typeXml){
        boolean retunrValue;
       switch (typeXml){
           case USER:
               retunrValue = validateXmlSchemaUser(xmlDoc);
               break;
           case PERSON:
               retunrValue = validateXmlSchemaPerson(xmlDoc);
               break;
           default:
               retunrValue = false;
               break;
       }
       return retunrValue;
    }

    /**
     * Validate a xml document that contains information about the Person
     * @param xmlDoc document for validation
     * @return true - xml file is correctly ; false - xml file is not correctly
     */
    private static boolean validateXmlSchemaPerson(String xmlDoc){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(pathXsdSchemaPerson));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlDoc)));
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Validate a xml document that contains information about the User
     * @param xmlDoc document for validation
     * @return true - xml file is correctly ; false - xml file is not correctly
     */
    private static boolean validateXmlSchemaUser( String xmlDoc){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(pathXsdSchemaUser));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlDoc)));
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
            return false;
        }
        return true;
    }
}
