package by.bsuir.models.response.implementations;

import by.bsuir.models.response.interfaces.Response;

import java.io.Serializable;

/**
 * Created by Сергей on 15.04.2017.
 */
public class ServerResponse implements Response,Serializable {
    private String nameOfResponse;

    private String xmlParseDoc;

    @Override
    public String getResponse() {
        return xmlParseDoc;
    }

    @Override
    public void setResponse(String xmlParseDoc) {
        this.xmlParseDoc = xmlParseDoc;
    }

    @Override
    public String getNameOfResponse() {
        return nameOfResponse;
    }

    @Override
    public void setNameOfResponse(String nameOfResponse) {
        this.nameOfResponse = nameOfResponse;
    }
}
