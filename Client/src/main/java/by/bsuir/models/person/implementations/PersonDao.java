package by.bsuir.models.person.implementations;

import by.bsuir.models.person.interfaces.InfoAboutWorkDao;
import by.bsuir.models.person.interfaces.Person;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Сергей on 15.04.2017.
 */
@XmlType(propOrder = {  "firstName",
                        "lastName",
                        "email",
                        "phone",
                        "infoAboutWork"}, name = "Person")
@XmlRootElement
public class PersonDao implements Person,Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private InfoAboutWorkDao infoAboutWorkDao;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getNumberPhone() {
        return phone;
    }

    @Override
    public InfoAboutWorkDao getInfoAboutWork() {
        return infoAboutWorkDao;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setNumberPhone(String numberPhone) {
        this.phone = numberPhone;
    }

    @Override
    public void setInfoAboutWork(InfoAboutWorkDao infoAboutWork) {
        this.infoAboutWorkDao = infoAboutWork;
    }
}
