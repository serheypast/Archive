package by.bsuir.XMLParsers.SAXParser;

import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.person.implementations.WorkPersonDao;
import by.bsuir.models.person.interfaces.Person;
import by.bsuir.models.user.implementations.UserDao;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Сергей on 01.05.2017.
 */
public class PersonHandler extends DefaultHandler {
    private ArrayList<PersonDao> persons = new ArrayList<PersonDao>();

    boolean bFirstName = false;
    boolean bLastName = false;
    boolean bphoneNumber = false;
    boolean bEmail = false;
    boolean bcompany = false;
    boolean bPosition = false;
    boolean bExperience = false;
    boolean bSalary = false;

    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    String company ;
    String position;
    String experience;
    String salary;

    int count = 0;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("person")) {
        } else if (qName.equalsIgnoreCase("firstname")) {
            bFirstName = true;
            count++;
        } else if (qName.equalsIgnoreCase("lastname")) {
            bLastName = true;
            count++;
        } else if (qName.equalsIgnoreCase("phoneNumber")) {
            bphoneNumber = true;
            count++;
        } else if (qName.equalsIgnoreCase("email")) {
            bEmail = true;
            count++;
        } else if (qName.equalsIgnoreCase("company")) {
            bcompany = true;
            count++;
        } else if (qName.equalsIgnoreCase("position")) {
            bPosition = true;
            count++;
        } else if (qName.equalsIgnoreCase("experience")) {
            bExperience = true;
            count++;
        }else if (qName.equalsIgnoreCase("salary")) {
            bSalary = true;
            count++;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("person")) {

        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {


        if (bFirstName) {
            firstName = new String(ch, start, length);
            bFirstName = false;
        } else if (bLastName) {
            lastName = new String(ch, start, length);
            bLastName = false;
        }  else if (bphoneNumber) {
            phoneNumber = new String(ch, start, length);
            bphoneNumber = false;
        } else if (bEmail) {
            email = new String(ch, start, length);
            bEmail = false;
        } else if (bcompany) {
            company = new String(ch, start, length);
            bcompany = false;
        } else if (bExperience) {
            experience = new String(ch, start, length);
            bExperience = false;
        } else if (bSalary) {
            bSalary = false;
            salary = new String(ch, start, length);
        } else if (bPosition) {
            bPosition = false;
            position = new String(ch, start, length);
        }
        if (count == 8) {
            PersonDao person = new PersonDao();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setEmail(email);
            person.setNumberPhone(phoneNumber);
            WorkPersonDao workPersonDao = new WorkPersonDao();
            workPersonDao.setNameOfPosition(position);
            workPersonDao.setNameOfCompany(company);
            workPersonDao.setSalary(Integer.parseInt(salary));
            workPersonDao.setExperience(Integer.parseInt(experience));
            person.setInfoAboutWork(workPersonDao);
            persons.add(person);

            bFirstName = false;
            bLastName = false;
            bphoneNumber = false;
            bEmail = false;
            bcompany = false;
            bExperience = false;
            bSalary = false;
            bPosition = false;


            firstName = null;
            lastName = null;
            phoneNumber = null;
            email = null;
            company  = null;
            position = null;
            experience = null;
            salary = null;

            count = 0;

        }
    }


    protected Object getObjects(){
        return persons;
    }

}
