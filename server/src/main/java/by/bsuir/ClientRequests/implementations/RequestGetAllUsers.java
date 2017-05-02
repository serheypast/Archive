package by.bsuir.ClientRequests.implementations;

import by.bsuir.ClientRequests.interfaces.Requestable;
import by.bsuir.Compressor.Compressor;
import by.bsuir.XMLParsers.Parser.implementations.ParserDaoSingleton;
import by.bsuir.clientThread.implementations.ClientThreadDao;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.models.user.implementations.UserDao;

import java.util.ArrayList;

/**
 * Created by Сергей on 30.04.2017.
 */
public class RequestGetAllUsers implements Requestable {
    /**
     * Executes the request from the client
     * Parse Clientrequest, getAllUsers from xml doc
     * @param request form the client
     * @return Response by client
     */
    @Override
    public ServerResponse doRequest(ClientRequest request) {
        ServerResponse serverResponse = new ServerResponse();
        Compressor compressor = new Compressor();
        compressor.decompress(TypeXml.USER);
        ArrayList<UserDao> persons = (ArrayList<UserDao>) ParserDaoSingleton.getParserSingleton().getCurrentParser().parseFromXml(
                ClientThreadDao.PATH_USERS, TypeXml.USER);
        serverResponse.setNameOfResponse(EResponse.REQUEST_COMPLETE);
        serverResponse.setResponseInfo(persons);
        compressor.compress(ClientThreadDao.PATH_USERS,TypeXml.USER);
        return serverResponse;
    }
}
