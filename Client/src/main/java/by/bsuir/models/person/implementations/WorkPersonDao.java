package by.bsuir.models.person.implementations;

import by.bsuir.models.person.interfaces.InfoAboutWorkDao;

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Сергей on 15.04.2017.
 */
@XmlType(propOrder = { "company", "position", "experience", "salary" }, name = "infoAboutWork")
public class WorkPersonDao implements InfoAboutWorkDao,Serializable {
    private String company;
    private String position;
    private int experience;
    private int salary;

    @Override
    public String getNameOfCompany() {
        return company;
    }

    @Override
    public String getNameOfPosition() {
        return position;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public void setNameOfCompany(String nameOfCompany) {
        company = nameOfCompany;
    }

    @Override
    public void setNameOfPosition(String nameOfPosition) {
        position = nameOfPosition;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = (experience < 0 && experience >100) ? 0 : experience;
    }

    @Override
    public void setSalary(int salary) {
        this.salary = (salary < 0 ) ? 100 : salary;
    }
}
