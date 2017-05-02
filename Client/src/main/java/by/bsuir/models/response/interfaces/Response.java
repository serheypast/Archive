package by.bsuir.models.response.interfaces;

import by.bsuir.models.enumKinds.eResponse.EResponse;

/**
 * Created by Сергей on 15.04.2017.
 */
public interface Response {
    /**
     * Get Kind of Response from server Response
     * @return Kind of Response
     */
    EResponse getNameOfResponse();

    void setNameOfResponse(EResponse nameOfResponse);

    Object getResponseInfo();

    void setResponseInfo(Object xmlParseDoc);
}
