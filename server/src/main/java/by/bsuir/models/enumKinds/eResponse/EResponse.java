package by.bsuir.models.enumKinds.eResponse;

/**
 * Created by Сергей on 16.04.2017.
 */
public enum EResponse {
    REQUEST_COMPLETE("REQUEST_COMPLETE"),
    REQUEST_NOT_COMPLETE("REQUEST_NOT_COMPLETE"),
    USER_EXIST("USER_EXIST"),
    USER_NOT_EXIST("USER_NOT_EXIST");


    EResponse(String response) {
        this.response = response;
    }

    private String response;

    public String getResponse() {
        return response;
    }
}
