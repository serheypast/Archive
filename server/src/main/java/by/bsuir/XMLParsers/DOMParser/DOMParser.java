package by.bsuir.XMLParsers.DOMParser;

import by.bsuir.XMLParsers.Parser.interfaces.Parser;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;

import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.person.implementations.WorkPersonDao;
import by.bsuir.models.person.interfaces.InfoAboutWorkDao;
import by.bsuir.models.parseData.interfaces.ParseData;

import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import by.bsuir.models.user.implementations.UserDao;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Created by Сергей on 16.04.2017.
 */
public class DOMParser implements Parser{

    /**
     * Parse the data in the xml document along the path
     * @param parseData data which be parsed
     * @param path path to file for parsing
     */
    @Override
    public void parseToXml(ParseData parseData, String path) {
        switch (parseData.getType()){
            case PERSON:
                parseToXmlPerson(path,parseData);
                break;
            case USER:
                parseToXmlUser(path,parseData);
                break;
            default:
                break;
        }
    }
    /**
     * Parse the data in the xml document along the path
     * @param parseData data which be parsed
     * @param path path to file for parsing
     */
    private void parseToXmlUser(String path, ParseData parseData){
        try {
            UserDao userDao = (UserDao) parseData.getData();
            File file = new File(path);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            // root element
            Node root = doc.getDocumentElement();
            Element user = doc.createElement("user");
            root.appendChild(user);


            // carname element
            Element name = doc.createElement("login");
            name.appendChild(
                    doc.createTextNode(userDao.getName()));
            user.appendChild(name);

            Element password = doc.createElement("password");
            password.appendChild(
                    doc.createTextNode(userDao.getPassword()));
            user.appendChild(password);

            Element role = doc.createElement("role");
            role.appendChild(doc.createTextNode(userDao.getRole().getRole()));
            user.appendChild(role);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            FileOutputStream fos = new FileOutputStream(path);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    /**
     * Parse the data in the xml document along the path
     * @param parseData data which be parsed
     * @param path path to file for parsing
     */
    private void parseToXmlPerson(String path, ParseData parseData)  {
        try {
            PersonDao person = (PersonDao) parseData.getData();
            File file = new File(path);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            // root element
            Node root = doc.getDocumentElement();

            //  supercars element
            Element personEl = doc.createElement("person");
            root.appendChild(personEl);


            // carname element
            Element name = doc.createElement("firstName");
            name.appendChild(
                    doc.createTextNode(person.getFirstName()));
            personEl.appendChild(name);

            Element password = doc.createElement("lastName");
            password.appendChild(
                    doc.createTextNode(person.getLastName()));
            personEl.appendChild(password);

            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(person.getEmail()));
            personEl.appendChild(email);
            Element phoneNumber = doc.createElement("phoneNumber");
            phoneNumber.appendChild(doc.createTextNode(person.getNumberPhone()));
            personEl.appendChild(phoneNumber);
            Element infoWork = doc.createElement("infoAboutWork");
            personEl.appendChild(infoWork);

            Element nameOfCompany = doc.createElement("company");
            nameOfCompany.appendChild(doc.createTextNode(person.getInfoAboutWork().getNameOfCompany()));
            infoWork.appendChild(nameOfCompany);

            Element positionInCompany = doc.createElement("position");
            positionInCompany.appendChild(doc.createTextNode(person.getInfoAboutWork().getNameOfPosition()));
            infoWork.appendChild(positionInCompany);

            Element experience = doc.createElement("experience");
            experience.appendChild(doc.createTextNode(String.valueOf(person.getInfoAboutWork().getExperience())));
            infoWork.appendChild(experience);

            Element salary = doc.createElement("salary");
            salary.appendChild(doc.createTextNode(String.valueOf(person.getInfoAboutWork().getSalary())));
            infoWork.appendChild(salary);
            // write the content into xml file
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            FileOutputStream fos = new FileOutputStream(path);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
        }
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
    private ArrayList<UserDao> parseFormXmlUser(String path) throws Exception{
        File inputFile = new File(path);
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("user");
        ArrayList<UserDao> users = new ArrayList<UserDao>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            UserDao user = new UserDao();
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                user.setName(eElement.getElementsByTagName("login").item(0).getTextContent());
                user.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
                String role = eElement.getElementsByTagName("role").item(0).getTextContent();
                Role rl = (role.equals(Role.ADMIN.getRole())) ? Role.ADMIN : (role.equals(Role.GUEST.getRole()) ? Role.GUEST : Role.USER);
                user.setRole(rl);
            }
            users.add(user);
        }
        return users;
    }


    /**
     * Parse data from the xml document along persons
     * @param path path to file for parsing
     * @return data whick will be parsed
     */
    private ArrayList<PersonDao> parseFromXmlPerson(String path) throws Exception{
        File inputFile = new File(path);
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("person");
        ArrayList<PersonDao> persons = new ArrayList<PersonDao>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            PersonDao person = new PersonDao();
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                person.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
                person.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
                person.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
                person.setNumberPhone(eElement.getElementsByTagName("phoneNumber").item(0).getTextContent());
                InfoAboutWorkDao info = new WorkPersonDao();
                NodeList list = eElement.getElementsByTagName("infoAboutWork");
                Element element = (Element)list.item(0);
                info.setNameOfCompany(element.getElementsByTagName("company").item(0).getTextContent());
                info.setNameOfPosition(element.getElementsByTagName("position").item(0).getTextContent());
                info.setSalary(Integer.parseInt(element.getElementsByTagName("salary").item(0).getTextContent()));
                info.setExperience(Integer.parseInt(element.getElementsByTagName("experience").item(0).getTextContent()));
                person.setInfoAboutWork(info);
            }
            persons.add(person);
        }
        return persons;
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
    private UserDao parseXmlStringUser(String xmlDoc){
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlDoc));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("user");

            UserDao user = new UserDao();
            Node nNode = nodes.item(0);

            Element eElement = (Element) nNode;
            user.setName(eElement.getElementsByTagName("login").item(0).getTextContent());
            user.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
            String role = eElement.getElementsByTagName("role").item(0).getTextContent();
            Role rl = (role.equals(Role.ADMIN.getRole())) ? Role.ADMIN : (role.equals(Role.GUEST.getRole()) ? Role.GUEST : Role.USER);
            user.setRole(rl);

            return  user;

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
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlDoc));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("person");

            PersonDao person = new PersonDao();


            Node nNode = nodes.item(0);

            Element eElement = (Element) nNode;
            person.setFirstName(eElement.getElementsByTagName("firstName").item(0).getTextContent());
            person.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
            person.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
            person.setNumberPhone(eElement.getElementsByTagName("phoneNumber").item(0).getTextContent());
            InfoAboutWorkDao info = new WorkPersonDao();
            NodeList list = eElement.getElementsByTagName("infoAboutWork");
            Element element = (Element)list.item(0);
            info.setNameOfCompany(element.getElementsByTagName("company").item(0).getTextContent());
            info.setNameOfPosition(element.getElementsByTagName("position").item(0).getTextContent());
            info.setSalary(Integer.parseInt(element.getElementsByTagName("salary").item(0).getTextContent()));
            info.setExperience(Integer.parseInt(element.getElementsByTagName("experience").item(0).getTextContent()));
            person.setInfoAboutWork(info);

            return  person;

        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return  null;
        }
    }

    /**
     * Change information in xml files
     * @param parseData new data which will be save in xml file
     * @param path Path to file
     * @return true - xml file is correctly changed; false - xml file is not correctly changed
     */
    public boolean changeInfo(ParseData parseData,String path){
        boolean returnValue;
        switch (parseData.getType()){
            case PERSON:
                returnValue = changePerson(parseData,path);
                break;
            case USER:
                returnValue = changeRole(parseData,path);
                break;
            default:
                returnValue =false;
                break;
        }
        return returnValue;
    }

    /**
     * Change information in xml files
     * @param data new User which will be save in xml file
     * @param path Path to file
     * @return true - xml file is correctly changed; false - xml file is not correctly changed
     */
    private boolean changeRole(ParseData data,String path){
        try {
            UserDao user = (UserDao) data.getData();
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String login = eElement.getElementsByTagName("login").item(0).getTextContent();
                    if(login.equals(user.getName())){
                        eElement.getElementsByTagName("role").item(0).setTextContent(user.getRole().getRole());

                        Transformer transformer = TransformerFactory.newInstance()
                                .newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(path));
                        transformer.transform(source, result);
                        return  true;
                    }
                }
            }
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return false;
        }
        return false;
    }
    /**
     * Change information in xml files
     * @param data new Person which will be save in xml file
     * @param path Path to file
     * @return true - xml file is correctly changed; false - xml file is not correctly changed
     */
    private boolean changePerson(ParseData data,String path){
        try {
            PersonDao personDao = (PersonDao) data.getData();
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("person");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String firstName = eElement.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = eElement.getElementsByTagName("lastName").item(0).getTextContent();
                    if(firstName.equals(personDao.getFirstName()) && lastName.equals(personDao.getLastName())) {
                        eElement.getElementsByTagName("email").item(0).setTextContent(personDao.getEmail());
                        eElement.getElementsByTagName("phoneNumber").item(0).setTextContent(personDao.getNumberPhone());
                        NodeList list = eElement.getElementsByTagName("infoAboutWork");
                        Element element = (Element)list.item(0);
                        element.getElementsByTagName("company").item(0).
                                setTextContent(personDao.getInfoAboutWork().getNameOfCompany());
                        element.getElementsByTagName("position").item(0).
                                setTextContent(personDao.getInfoAboutWork().getNameOfPosition());
                        element.getElementsByTagName("salary").item(0).
                                setTextContent(String.valueOf(personDao.getInfoAboutWork().getSalary()));
                        element.getElementsByTagName("experience").item(0).
                                setTextContent(String.valueOf(personDao.getInfoAboutWork().getExperience()));

                        Transformer transformer = TransformerFactory.newInstance()
                                .newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(path));
                        transformer.transform(source, result);
                        return true;
                    }

                }

            }
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * Delete some data from xmlDoc
     * @param personDao data which will be deleted
     * @param path Path to file
     * @return true - data from xml file is correctly deleted; false - data from xml file is not correctly deleted
     */
    public boolean deletePerson(PersonDao personDao,String path){
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("person");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                String firstName = eElement.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = eElement.getElementsByTagName("lastName").item(0).getTextContent();
                if (firstName.equals(personDao.getFirstName()) && lastName.equals(personDao.getLastName())) {
                    eElement.getParentNode().removeChild(eElement);

                    Transformer transformer = TransformerFactory.newInstance()
                            .newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(path));
                    transformer.transform(source, result);
                    return true;
                }
            }

        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            return false;
        }
        return true;
    }




}
