package by.bsuir.models.enumKinds.eRole;

/**
 * Created by Сергей on 16.04.2017.
 */
public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    GUEST("GUEST");

    private String role;


    public String getRole() {
        return role;
    }

    Role(String role) {
        this.role = role;
    }
}
