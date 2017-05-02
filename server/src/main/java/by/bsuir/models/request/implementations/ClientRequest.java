package by.bsuir.models.request.implementations;

import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.request.interfaces.Request;

import java.io.Serializable;

/**
 * Created by Сергей on 15.04.2017.
 */
public class ClientRequest implements Request,Serializable {
    private ERequest nameOfRequest;

    private String xmlParseDoc;

    @Override
    public ERequest getNameOfRequest() {
        return nameOfRequest;
    }

    @Override
    public void setNameOfRequest(ERequest nameOfRequest) {
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
