package by.bsuir.models.person.interfaces;

/**
 * Created by Сергей on 15.04.2017.
 * - Integer id
 - String firstName
 - String LastName
 - String email
 - String numberPhone
 - InfoAboutWord work
 */
public interface Person {

    public String getFirstName();
    public String getLastName();
    public String getEmail();
    public String getNumberPhone();
    public InfoAboutWorkDao getInfoAboutWork();

    public void setFirstName(String firstName);
    public void setLastName(String lastName);
    public void setEmail(String email);
    public void setNumberPhone(String numberPhone);
    public void setInfoAboutWork(InfoAboutWorkDao infoAboutWork);
}
