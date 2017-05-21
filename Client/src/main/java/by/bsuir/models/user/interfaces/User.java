package by.bsuir.models.user.interfaces;

import by.bsuir.models.enumKinds.eRole.Role;

/**
 * Created by Сергей on 16.04.2017.
 */
public interface User {

    public Role getRole();

    public String getName();

    public void setRole(Role role);

    public void setName(String name);

    public String getPassword();

    public void setPassword(String password);
}
