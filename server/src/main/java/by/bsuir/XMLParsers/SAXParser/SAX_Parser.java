package by.bsuir.XMLParsers.SAXParser;

import by.bsuir.XMLParsers.DOMParser.DOMParser;
import by.bsuir.XMLParsers.Parser.interfaces.Parser;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.parseData.interfaces.ParseData;
import by.bsuir.models.user.implementations.UserDao;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;


/**
 * Created by Сергей on 01.05.2017.
 */
public class SAX_Parser implements Parser{

    /**
     * Parse the data in the xml document along the path
     * @param parseData data which be parsed
     * @param path path to file for parsing
     */
    @Override
    public void parseToXml(ParseData parseData, String path) {
        DOMParser domParser = new DOMParser();
        domParser.parseToXml(parseData,path);
    }


    /**
     * Parse data from the xml document along Type of data
     * @param path path to file for parsing
     * @param type kind of data
     * @return data whick will be parsed
     */
    @Override
    public Object parseFromXml(String path, TypeXml type) {
        try {
            Object object = new Object();
            switch (type) {
                case PERSON:
                    object = parseFromXmlPerson(path);
                    break;
                case USER:
                    object = parseFormXmlUser(path);
                    break;
                default:
                    break;
            }
            return object;
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }
    }


    /**
     * Parse data from the xml document along Users
     * @param path path to file for parsing
     * @return data whick will be parsed
     */
    private ArrayList<UserDao> parseFormXmlUser(String path){
        try {
            File inputFile = new File(path);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userHandler = new UserHandler();
            saxParser.parse(inputFile, userHandler);
            return (ArrayList<UserDao>)userHandler.getObjects();
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }
    }

    /**
     * Parse data from the xml document along persons
     * @param path path to file for parsing
     * @return data whick will be parsed
     */
    private ArrayList<PersonDao> parseFromXmlPerson(String path){
        try {
        File inputFile = new File(path);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        PersonHandler personHandler = new PersonHandler();
        saxParser.parse(inputFile, personHandler);
        return (ArrayList<PersonDao>)personHandler.getObjects();
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }
    }

    /**
     * Parse a string as a xml document
     * @param xmlDoc string which will be parsed
     * @param type Xml kind of data for parse
     * @return kinf of data which will be parsed
     */
    @Override
    public Object parseXmlString(String xmlDoc,TypeXml type) {
        Object object;
        switch (type){
            case USER:
                object = parseXmlStringUser(xmlDoc);
                break;
            case PERSON:
                object = parseXmlStringPerson(xmlDoc);
                break;
            default:
                object = null;
                break;
        }
        return  object;
    }

    /**
     * Parse a string as a xml document by User
     * @param xmlDoc string which will be parsed
     * @return kinf of data which will be parsed
     */
    private PersonDao parseXmlStringPerson(String xmlDoc){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            PersonHandler personHandle = new PersonHandler();
            saxParser.parse(new InputSource(new StringReader(xmlDoc)), personHandle);
            return ((ArrayList<PersonDao>)personHandle.getObjects()).get(0);
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
        }
        return null;
    }

    /**
     * Parse a string as a xml document by Persons
     * @param xmlDoc string which will be parsed
     * @return kinf of data which will be parsed
     */
    private UserDao parseXmlStringUser(String xmlDoc){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userHandler = new UserHandler();
            saxParser.parse(new InputSource(new StringReader(xmlDoc)), userHandler);
            return ((ArrayList<UserDao>)userHandler.getObjects()).get(0);
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
        }
        return null;
    }
}
