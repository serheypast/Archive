package by.bsuir.XMLParsers.JDOMParser;

import by.bsuir.XMLParsers.DOMParser.DOMParser;
import by.bsuir.XMLParsers.Parser.interfaces.Parser;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.person.implementations.WorkPersonDao;
import by.bsuir.models.parseData.interfaces.ParseData;
import by.bsuir.models.user.implementations.UserDao;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Сергей on 01.05.2017.
 */
public class JDOMParser implements Parser {
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
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();
            List<Element> personList = classElement.getChildren();
            ArrayList<UserDao> users = new ArrayList<UserDao>();
            for (int temp = 0; temp < personList.size(); temp++) {
                UserDao userDao = new UserDao();
                Element user = personList.get(temp);
                userDao.setName(user.getChild("login").getText());
                userDao.setPassword(user.getChild("password").getText());
                String role = user.getChild("role").getText();
                userDao.setRole((role.equals(Role.ADMIN.getRole())) ? Role.ADMIN : (role.equals(Role.GUEST.getRole()) ? Role.GUEST : Role.USER));
                users.add(userDao);
            }
            return users;
        }catch (Exception e){
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
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();
            List<Element> personList = classElement.getChildren();
            ArrayList<PersonDao> persons = new ArrayList<PersonDao>();
            for (int temp = 0; temp < personList.size(); temp++) {
                PersonDao personDao = new PersonDao();
                Element person = personList.get(temp);
                personDao.setFirstName(person.getChild("firstName").getText());
                personDao.setLastName(person.getChild("lastName").getText());
                personDao.setEmail(person.getChild("email").getText());
                personDao.setNumberPhone(person.getChild("phoneNumber").getText());
                WorkPersonDao workPersonDao = new WorkPersonDao();
                List workPersonD = person.getChildren("infoAboutWork");
                person = (Element)workPersonD.get(0);
                workPersonDao.setNameOfCompany(person.getChild("company").getText());
                workPersonDao.setNameOfPosition(person.getChild("position").getText());
                workPersonDao.setSalary(Integer.parseInt(person.getChild("salary").getText()));
                workPersonDao.setExperience(Integer.parseInt(person.getChild("experience").getText()));
                personDao.setInfoAboutWork(workPersonDao);
                persons.add(personDao);
            }
            return persons;
        }catch (Exception e){
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
    public Object parseXmlString(String xmlDoc, TypeXml type) {
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
    private UserDao parseXmlStringUser(String xmlDoc){
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build( new InputSource(new StringReader(xmlDoc)));
            Element classElement = document.getRootElement();
            List<Element> personList = classElement.getChildren();
            ArrayList<UserDao> users = new ArrayList<UserDao>();

            UserDao userDao = new UserDao();
            Element user = personList.get(0);
            userDao.setName(user.getChild("login").getText());
            userDao.setPassword(user.getChild("password").getText());
            String role = user.getChild("role").getText();
            userDao.setRole((role.equals(Role.ADMIN.getRole())) ? Role.ADMIN : (role.equals(Role.GUEST.getRole()) ? Role.GUEST : Role.USER));
            users.add(userDao);
            return userDao;
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return  null;
        }
    }

    /**
     * Parse a string as a xml document by Persons
     * @param xmlDoc string which will be parsed
     * @return kinf of data which will be parsed
     */
    private PersonDao parseXmlStringPerson(String xmlDoc){
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build( new InputSource(new StringReader(xmlDoc)));
            Element classElement = document.getRootElement();
            List<Element> personList = classElement.getChildren();
            PersonDao personDao = new PersonDao();
            Element person = personList.get(0);
            personDao.setFirstName(person.getChild("firstName").getText());
            personDao.setLastName(person.getChild("lastName").getText());
            personDao.setEmail(person.getChild("email").getText());
            personDao.setNumberPhone(person.getChild("phoneNumber").getText());
            WorkPersonDao workPersonDao = new WorkPersonDao();
            person = person.getChild("infoAboutWork");
            workPersonDao.setNameOfCompany(person.getChild("company").getText());
            workPersonDao.setNameOfPosition(person.getChild("position").getText());
            workPersonDao.setSalary(Integer.parseInt(person.getChild("salary").getText()));
            workPersonDao.setExperience(Integer.parseInt(person.getChild("experience").getText()));
            personDao.setInfoAboutWork(workPersonDao);
            return personDao;
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return  null;
        }
    }


}
