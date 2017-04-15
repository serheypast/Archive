package by.bsuir.client.models.request.implementations;

import by.bsuir.client.models.request.interfaces.Request;

/**
 * Created by Сергей on 15.04.2017.
 */
public class ClientRequest implements Request {
    private String nameOfRequest;

    private String xmlParseDoc;

    @Override
    public String getNameOfRequest() {
        return nameOfRequest;
    }

    @Override
    public void setNameOfRequest(String nameOfRequest) {
        this.nameOfRequest = nameOfRequest;
    }

    @Override
    public String getRequest() {
        return xmlParseDoc;
    }

    @Override
    public void setRequest(String xmlParseDoc) {
        this.xmlParseDoc = xmlParseDoc;
    }
}
