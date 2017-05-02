package by.bsuir.XMLParsers.DOMParser;

import by.bsuir.XMLParsers.interfaces.Parser;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.person.implementations.WorkPersonDao;
import by.bsuir.models.person.interfaces.InfoAboutWorkDao;
import by.bsuir.models.parseData.interfaces.ParseData;
import by.bsuir.models.person.interfaces.Person;
import by.bsuir.models.user.implementations.UserDao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Сергей on 16.04.2017.
 */
public class DOMParser implements Parser {

    /**
     * Parse object in xml file(xml file by string)
     * @param parseData default data by parse
     * @return xmlDoc how string
     */
    @Override
    public String parseToXml(ParseData parseData) {
        try {
            String xmlDoc= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            switch (parseData.getType()) {
                case PERSON:
                    PersonDao person = (PersonDao)parseData.getData();
                    xmlDoc+="<class>";
                    xmlDoc+="<person>";
                    xmlDoc+="<firstName>" + person.getFirstName() +"</firstName>";
                    xmlDoc+="<lastName>" + person.getLastName() + "</lastName>";
                    xmlDoc+="<email>" + person.getEmail() +"</email>";
                    xmlDoc+="<phoneNumber>" + person.getNumberPhone() + "</phoneNumber>";
                    xmlDoc+="<infoAboutWork>";
                    xmlDoc+="<company>" + person.getInfoAboutWork().getNameOfCompany() +"</company>";
                    xmlDoc+="<position>" + person.getInfoAboutWork().getNameOfPosition() + "</position>";
                    xmlDoc+="<experience>" + String.valueOf(person.getInfoAboutWork().getExperience()) +"</experience>";
                    xmlDoc+="<salary>" + String.valueOf(person.getInfoAboutWork().getSalary()) + "</salary>";
                    xmlDoc+="</infoAboutWork>";
                    xmlDoc+="</person>";
                    xmlDoc+="</class>";
                    break;
                case USER:
                    UserDao user = (UserDao)parseData.getData();
                    xmlDoc+="<class>";
                    xmlDoc+="<user>";
                    xmlDoc+="<login>" + user.getName() +"</login>";
                    xmlDoc+="<password>" + user.getPassword() + "</password>";
                    xmlDoc+="<role>" + user.getRole().getRole() + "</role>";
                    xmlDoc+="</user>";
                    xmlDoc+="</class>";
                    break;
                default:
                    break;
            }
            return xmlDoc;
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }
    }
}
