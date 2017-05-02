package by.bsuir.clientThread.interfaces;

import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.request.interfaces.Request;
import by.bsuir.models.response.implementations.ServerResponse;

/**
 * Created by Сергей on 16.04.2017.
 */
public interface ClientThread{

    /**
     * Get request from the client to server
     * @return Object class by ClientRequest
     */
    public ClientRequest getRequest();

    /**
     * Send response to the client by server to his request
     * @param response Object class by ServerResponse
     */
    public void sendResponse(ServerResponse response);
}
