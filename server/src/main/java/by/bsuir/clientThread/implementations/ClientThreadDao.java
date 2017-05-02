package by.bsuir.clientThread.implementations;

import by.bsuir.ClientRequests.implementations.*;
import by.bsuir.ClientRequests.interfaces.Requestable;
import by.bsuir.clientThread.interfaces.ClientThread;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.implementations.SocketDaoClient;

/**
 * Created by Сергей on 16.04.2017.
 */
public class ClientThreadDao implements Runnable,ClientThread {
    public final static String PATH_USERS =
            "D:\\project java\\Archive\\server\\src\\main\\java\\by\\bsuir\\XMLDoc\\users\\users.xml";
    public final static String PATH_PERSONS =
            "D:\\project java\\Archive\\server\\src\\main\\java\\by\\bsuir\\XMLDoc\\persons\\persons.xml";
    private SocketDaoClient socket;

    public ClientThreadDao(SocketDaoClient socket) {
        this.socket = socket;
    }

    public ClientThreadDao() {}


    /**
     * Get request from the client to server
     * @return Object class by ClientRequest
     */
    @Override
    public ClientRequest getRequest() {
        return (ClientRequest)socket.getMessage();
    }

    /**
     * Send response to the client by server to his request
     * @param response Object class by ServerResponse
     */
    @Override
    public void sendResponse(ServerResponse response) {
        socket.sendMessage(response);
    }

    /**
     * Thread in which server communicate with a client
     */
    @Override
    public void run() {
        while (true) {
            ClientRequest clientRequest = getRequest();
            ServerResponse serverResponse = doRequest(clientRequest);
            sendResponse(serverResponse);
        }
    }

    /**
     * Get request and parse request and processes it and selects the desired implementation
     * @param clientRequest client request
     * @return Server response to the client
     */
    private ServerResponse doRequest(ClientRequest clientRequest){
        try {
            ERequest request = clientRequest.getNameOfRequest();
            Requestable requestable;
            switch (request) {
                case ADD_PERSON:
                    requestable = new RequestAddPerson();
                    break;
                case GET_PERSONS:
                    requestable = new RequestGetAllPersons();
                    break;
                case GET_USERS:
                    requestable = new RequestGetAllUsers();
                    break;
                case CHECK_USER:
                    requestable = new RequestCheckUser();
                    break;
                case ADD_USER:
                    requestable = new RequestAddUser();
                    break;
                case CHANGE_PERSON:
                    requestable = new RequestChangePerson();
                    break;
                case CHANGE_ROLE:
                    requestable = new RequestChangeRole();
                    break;
                case DELETE_PERSON:
                    requestable = new RequestDeletePerson();
                    break;
                case CHANGE_PARSER:
                    requestable = new RequestChangeParser();
                    break;
                default:
                    requestable = null;
            }
            ServerResponse response = requestable.doRequest(clientRequest);
            return response;
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
            ServerResponse serverResponse = new ServerResponse();
            serverResponse.setNameOfResponse(EResponse.REQUEST_NOT_COMPLETE);
            serverResponse.setResponseInfo(null);
            return serverResponse;
        }
    }
}
