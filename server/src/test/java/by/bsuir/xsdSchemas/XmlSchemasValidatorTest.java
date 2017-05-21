package by.bsuir.xsdSchemas;

import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.person.implementations.WorkPersonDao;
import by.bsuir.models.person.interfaces.InfoAboutWorkDao;
import by.bsuir.models.person.interfaces.Person;
import by.bsuir.models.user.implementations.UserDao;
import by.bsuir.models.user.interfaces.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 02.05.2017.
 */
public class XmlSchemasValidatorTest {
    @Test
    public void validateXMLSchema() throws Exception {
        Person person = new PersonDao();
        person.setEmail("asdf");
        person.setNumberPhone("+4352342432");
        person.setFirstName("fdsadf");
        person.setLastName("fdsadf");
        InfoAboutWorkDao infoAboutWorkDao = new WorkPersonDao();
        infoAboutWorkDao.setExperience(123);
        infoAboutWorkDao.setSalary(123);
        infoAboutWorkDao.setNameOfCompany("bsuir");
        infoAboutWorkDao.setNameOfPosition("student");
        person.setInfoAboutWork(infoAboutWorkDao);
        String xmlDoc = "";
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
        Assert.assertTrue(XmlSchemasValidator.validateXMLSchema(xmlDoc, TypeXml.PERSON));
        Assert.assertFalse(XmlSchemasValidator.validateXMLSchema(xmlDoc, TypeXml.USER));
    }

    @Test
    public void validateXMLSchema_2() throws Exception {
        Person person = new PersonDao();
        person.setEmail("asdf");
        person.setNumberPhone("asdf");
        person.setFirstName("fdsadf");
        person.setLastName("fdsadf");
        InfoAboutWorkDao infoAboutWorkDao = new WorkPersonDao();
        infoAboutWorkDao.setExperience(123);
        infoAboutWorkDao.setSalary(123);
        infoAboutWorkDao.setNameOfCompany("bsuir");
        infoAboutWorkDao.setNameOfPosition("student");
        person.setInfoAboutWork(infoAboutWorkDao);
        String xmlDoc = "";
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
        Assert.assertFalse(XmlSchemasValidator.validateXMLSchema(xmlDoc, TypeXml.PERSON));
        Assert.assertFalse(XmlSchemasValidator.validateXMLSchema(xmlDoc, TypeXml.USER));
    }

    @Test
    public void validateXMLSchema_3() throws Exception {
        User user = new UserDao();
        user.setName("sadfadf");
        user.setPassword("asdsaf");
        user.setRole(Role.ADMIN);
        String xmlDoc = "";
        xmlDoc+="<class>";
        xmlDoc+="<user>";
        xmlDoc+="<login>" + user.getName() +"</login>";
        xmlDoc+="<password>" + user.getPassword() + "</password>";
        xmlDoc+="<role>" + user.getRole().getRole() + "</role>";
        xmlDoc+="</user>";
        xmlDoc+="</class>";
        Assert.assertFalse(XmlSchemasValidator.validateXMLSchema(xmlDoc, TypeXml.PERSON));
        Assert.assertTrue(XmlSchemasValidator.validateXMLSchema(xmlDoc, TypeXml.USER));
    }




}