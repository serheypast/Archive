package by.bsuir.models.response.implementations;

import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.response.interfaces.Response;

import java.io.Serializable;

/**
 * Created by Сергей on 15.04.2017.
 */
public class ServerResponse implements Response,Serializable {
    private EResponse nameOfResponse;

    private Object data;

    @Override
    public Object getResponseInfo() {
        return data;
    }

    @Override
    public void setResponseInfo(Object xmlParseDoc) {
        this.data = xmlParseDoc;
    }

    @Override
    public EResponse getNameOfResponse() {
        return nameOfResponse;
    }

    @Override
    public void setNameOfResponse(EResponse nameOfResponse) {
        this.nameOfResponse = nameOfResponse;
    }
}
