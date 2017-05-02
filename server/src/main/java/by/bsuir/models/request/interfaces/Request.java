package by.bsuir.models.request.interfaces;

import by.bsuir.models.enumKinds.eRequest.ERequest;

/**
 * Created by Сергей on 15.04.2017.
 */
public interface Request {
    ERequest getNameOfRequest();

    void setNameOfRequest(ERequest nameOfRequest);

    String getRequest();

    void setRequest(String xmlParseDoc);

}
