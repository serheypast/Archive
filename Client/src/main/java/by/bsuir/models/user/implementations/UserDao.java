package by.bsuir.models.user.implementations;

import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.user.interfaces.User;

import java.io.Serializable;

/**
 * Created by Сергей on 16.04.2017.
 */
public class UserDao implements User,Serializable {
    /**Roots access*/
    private Role role;

    /**login*/
    private String name;
    /**password*/
    private String password;

    /**getters and setter*/

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nRole:" + role;
    }

}
