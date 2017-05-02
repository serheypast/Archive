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
import by.bsuir.models.user.interfaces.User;
import by.bsuir.xsdSchemas.XmlSchemasValidator;

import java.util.ArrayList;

/**
 * Created by Сергей on 30.04.2017.
 */
public class RequestCheckUser implements Requestable {
    /**
     * Executes the request from the client
     * Parse Clientrequest, checks the user for existence
     * @param request form the client
     * @return Response by client
     */
    @Override
    public ServerResponse doRequest(ClientRequest request) {
        ServerResponse serverResponse = new ServerResponse();
        Compressor compressor = new Compressor();
        compressor.decompress(TypeXml.USER);
        ArrayList<UserDao> users = (ArrayList<UserDao>) ParserDaoSingleton.getParserSingleton().getCurrentParser().parseFromXml(
                ClientThreadDao.PATH_USERS, TypeXml.USER);
        String xmlDoc = request.getRequest();
        if(!XmlSchemasValidator.validateXMLSchema(xmlDoc,TypeXml.USER)){
            serverResponse.setNameOfResponse(EResponse.REQUEST_NOT_COMPLETE);
            compressor.compress(ClientThreadDao.PATH_USERS,TypeXml.USER);
            return serverResponse;
        }
        UserDao user = (UserDao)ParserDaoSingleton.getParserSingleton().getCurrentParser().parseXmlString(xmlDoc,TypeXml.USER);
        User newUser = null;
        for(int i = 0 ; i < users.size();i++){
            if(user.getName().equals(users.get(i).getName()) && user.getPassword().equals(users.get(i).getPassword())){
                newUser = users.get(i);
            }
        }
        serverResponse.setNameOfResponse(EResponse.USER_EXIST);
        serverResponse.setResponseInfo(newUser);
        compressor.compress(ClientThreadDao.PATH_USERS,TypeXml.USER);
        return serverResponse;
    }
}
