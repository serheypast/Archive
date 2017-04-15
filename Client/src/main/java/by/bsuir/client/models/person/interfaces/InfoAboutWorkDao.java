package by.bsuir.client.models.person.interfaces;

/**
 * Created by Сергей on 15.04.2017.
 * - company
 - dolshnost
 - stage
 - coast
 */
public interface InfoAboutWorkDao {
    public String getNameOfCompany();

    public String getNameOfPosition();

    public int getExperience();

    public int getSalary();

    public void setNameOfCompany(String nameOfCompany);

    public void setNameOfPosition(String nameOfPosition);

    public void setExperience(int experience);

    public void setSalary(int salary);

}
