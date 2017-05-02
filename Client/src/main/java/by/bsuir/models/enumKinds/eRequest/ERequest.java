package by.bsuir.models.enumKinds.eRequest;

/**
 * Created by Сергей on 16.04.2017.
 */
public enum ERequest {

    ADD_PERSON("ADD_PERSON"),
    ADD_USER("ADD_USER"),
    DELETE_PERSON("DELETE_PERSON"),
    CHANGE_PERSON("CHANGE_PERSON"),
    GET_PERSONS("GET_PERSONS"),
    CHECK_USER("CHECK_USER"),
    GET_USERS("GET_USERS"),
    CHANGE_ROLE("CHANGE_ROLE"),
    CHANGE_PARSER("CHANGE_PARSER");

    ERequest(String request) {
        this.request = request;
    }

    private String request;

    public String getRequest() {
        return request;
    }
}
