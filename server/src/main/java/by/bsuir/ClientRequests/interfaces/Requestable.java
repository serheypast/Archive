package by.bsuir.ClientRequests.interfaces;

import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;

/**
 * Created by Сергей on 30.04.2017.
 */
public interface Requestable {
    /**
     * Executes the request from the client
     * @param request form the client
     * @return Response by client
     */
    public ServerResponse doRequest(ClientRequest request);
}
