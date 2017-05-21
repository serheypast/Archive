package by.bsuir.XMLParsers.StAXParser;

import by.bsuir.XMLParsers.DOMParser.DOMParser;
import by.bsuir.XMLParsers.Parser.interfaces.Parser;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.person.implementations.WorkPersonDao;
import by.bsuir.models.person.interfaces.InfoAboutWorkDao;
import by.bsuir.models.parseData.interfaces.ParseData;
import by.bsuir.models.user.implementations.UserDao;

import java.io.*;
import java.util.ArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by Сергей on 01.05.2017.
 */
public class StAxParser implements Parser {
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
            ArrayList<UserDao> users = new ArrayList<UserDao>();
            boolean bLogin = false;
            boolean bPassword = false;
            boolean bRole = false;
            String login = null;
            String password= null;
            String role= null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(
                            new FileReader(path));
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("user")) {
                        } else if (qName.equalsIgnoreCase("login")) {
                            bLogin = true;
                        } else if (qName.equalsIgnoreCase("password")) {
                            bPassword = true;
                        } else if (qName.equalsIgnoreCase("role")) {
                            bRole = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bLogin) {
                            login = characters.getData();
                            bLogin = false;
                        } else if (bPassword) {
                            password = characters.getData();
                            bPassword = false;
                        }  else if (bRole) {
                            role = characters.getData();
                            bRole = false;
                        }
                        break;
                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("user")){
                            UserDao user = new UserDao();
                            user.setPassword(password);
                            user.setName(login);
                            user.setRole((role.equals(Role.ADMIN.getRole())) ? Role.ADMIN : (role.equals(Role.GUEST.getRole()) ? Role.GUEST : Role.USER));
                            users.add(user);
                        }
                        break;
                }
            }
            return users;
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
            ArrayList<PersonDao> persons = new ArrayList<PersonDao>();
            boolean bFirstName = false;
            boolean bLastName = false;
            boolean bphoneNumber = false;
            boolean bEmail = false;
            boolean bcompany = false;
            boolean bPosition = false;
            boolean bExperience = false;
            boolean bSalary = false;
            String firstName = null;
            String lastName = null;
            String phoneNumber = null;
            String email = null;
            String company = null;
            String position = null;
            String experience = null;
            String salary = null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(
                            new FileReader(path));
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("person")) {
                        } else if (qName.equalsIgnoreCase("firstname")) {
                            bFirstName = true;
                        } else if (qName.equalsIgnoreCase("lastname")) {
                            bLastName = true;
                        } else if (qName.equalsIgnoreCase("phoneNumber")) {
                            bphoneNumber = true;
                        } else if (qName.equalsIgnoreCase("email")) {
                            bEmail = true;
                        } else if (qName.equalsIgnoreCase("company")) {
                            bcompany = true;
                        } else if (qName.equalsIgnoreCase("position")) {
                            bPosition = true;
                        } else if (qName.equalsIgnoreCase("experience")) {
                            bExperience = true;
                        }else if (qName.equalsIgnoreCase("salary")) {
                            bSalary = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bFirstName) {
                            firstName = characters.getData();
                            bFirstName = false;
                        } else if (bLastName) {
                            lastName = characters.getData();
                            bLastName = false;
                        }  else if (bphoneNumber) {
                           phoneNumber = characters.getData();
                            bphoneNumber = false;
                        } else if (bEmail) {
                            email = characters.getData();
                            bEmail = false;
                        } else if (bcompany) {
                            company = characters.getData();
                            bcompany = false;
                        } else if (bExperience) {
                            experience = characters.getData();
                            bExperience = false;
                        } else if (bSalary) {
                            bSalary = false;
                            salary = characters.getData();
                        } else if (bPosition) {
                            bPosition = false;
                            position = characters.getData();
                        }
                        break;
                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("person")){
                            PersonDao person = new PersonDao();
                            InfoAboutWorkDao infoAboutWorkDao = new WorkPersonDao();
                            person.setEmail(email);
                            person.setNumberPhone(phoneNumber);
                            person.setLastName(lastName);
                            person.setFirstName(firstName);
                            infoAboutWorkDao.setExperience(Integer.parseInt(experience));
                            infoAboutWorkDao.setNameOfPosition(position);
                            infoAboutWorkDao.setSalary(Integer.parseInt(salary));
                            infoAboutWorkDao.setNameOfCompany(company);
                            person.setInfoAboutWork(infoAboutWorkDao);
                            persons.add(person);
                        }
                        break;
                }
            }
            return persons;
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }
    }



    /**
     * Parse a string as a xml document
     * @param xmlDoc string which will be parsed
     * @param type Xml kind of data for parse
     * @return kind of data which will be parsed
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
    private PersonDao parseXmlStringPerson(String xmlDoc){
        try {
            boolean bFirstName = false;
            boolean bLastName = false;
            boolean bphoneNumber = false;
            boolean bEmail = false;
            boolean bcompany = false;
            boolean bPosition = false;
            boolean bExperience = false;
            boolean bSalary = false;
            String firstName = null;
            String lastName = null;
            String phoneNumber = null;
            String email = null;
            String company = null;
            String position = null;
            String experience = null;
            String salary = null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(
                            new StringReader(xmlDoc));
            PersonDao person = null;
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("person")) {
                        } else if (qName.equalsIgnoreCase("firstname")) {
                            bFirstName = true;
                        } else if (qName.equalsIgnoreCase("lastname")) {
                            bLastName = true;
                        } else if (qName.equalsIgnoreCase("phoneNumber")) {
                            bphoneNumber = true;
                        } else if (qName.equalsIgnoreCase("email")) {
                            bEmail = true;
                        } else if (qName.equalsIgnoreCase("company")) {
                            bcompany = true;
                        } else if (qName.equalsIgnoreCase("position")) {
                            bPosition = true;
                        } else if (qName.equalsIgnoreCase("experience")) {
                            bExperience = true;
                        }else if (qName.equalsIgnoreCase("salary")) {
                            bSalary = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bFirstName) {
                            firstName = characters.getData();
                            bFirstName =false;
                        } else if (bLastName) {
                            lastName = characters.getData();
                            bLastName = false;
                        }  else if (bphoneNumber) {
                            phoneNumber = characters.getData();
                            bphoneNumber = false;
                        } else if (bEmail) {
                            email = characters.getData();
                            bEmail = false;
                        } else if (bcompany) {
                            company = characters.getData();
                            bcompany = false;
                        } else if (bExperience) {
                            experience = characters.getData();
                            bExperience = false;
                        } else if (bSalary) {
                            bSalary = false;
                            salary = characters.getData();
                        } else if (bPosition) {
                            bPosition = false;
                            position = characters.getData();
                        }
                        break;
                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("person")){
                            person = new PersonDao();
                            InfoAboutWorkDao infoAboutWorkDao = new WorkPersonDao();
                            person.setEmail(email);
                            person.setNumberPhone(phoneNumber);
                            person.setLastName(lastName);
                            person.setFirstName(firstName);
                            infoAboutWorkDao.setExperience(Integer.parseInt(experience));
                            infoAboutWorkDao.setNameOfPosition(position);
                            infoAboutWorkDao.setSalary(Integer.parseInt(salary));
                            infoAboutWorkDao.setNameOfCompany(company);
                            person.setInfoAboutWork(infoAboutWorkDao);
                            return person;
                        }
                        break;
                }
            }
            return person;
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }
    }

    /**
     * Parse a string as a xml document by Persons
     * @param xmlDoc string which will be parsed
     * @return kinf of data which will be parsed
     */
    private UserDao parseXmlStringUser(String xmlDoc){
        try {
            boolean bLogin = false;
            boolean bPassword = false;
            boolean bRole = false;
            String login = null;
            String password= null;
            String role= null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new StringReader(xmlDoc));
            UserDao user = null;
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("user")) {
                        } else if (qName.equalsIgnoreCase("login")) {
                            bLogin = true;
                        } else if (qName.equalsIgnoreCase("password")) {
                            bPassword = true;
                        } else if (qName.equalsIgnoreCase("role")) {
                            bRole = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if (bLogin) {
                            login = characters.getData();
                            bLogin = false;
                        } else if (bPassword) {
                            password = characters.getData();
                            bPassword = false;
                        }  else if (bRole) {
                            role = characters.getData();
                            bRole = false;
                        }
                        break;
                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("user")){
                            user = new UserDao();
                            user.setPassword(password);
                            user.setName(login);
                            user.setRole((role.equals(Role.ADMIN.getRole())) ? Role.ADMIN : (role.equals(Role.GUEST.getRole()) ? Role.GUEST : Role.USER));
                            return user;
                        }
                        break;

                }
            }
            return user;
        } catch (Exception e) {
            AppLogger.getLogger().error(e.getMessage());
            return null;
        }
    }
}
