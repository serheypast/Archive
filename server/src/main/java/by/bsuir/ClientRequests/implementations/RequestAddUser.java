package by.bsuir.ClientRequests.implementations;

import by.bsuir.ClientRequests.interfaces.Requestable;
import by.bsuir.Compressor.Compressor;
import by.bsuir.XMLParsers.Parser.implementations.ParserDaoSingleton;
import by.bsuir.clientThread.implementations.ClientThreadDao;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.implementations.ParseDataDao;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.models.user.implementations.UserDao;
import by.bsuir.xsdSchemas.XmlSchemasValidator;

/**
 * Created by Сергей on 30.04.2017.
 */
public class RequestAddUser implements Requestable {
    /**
     * Executes the request from the client
     * Parse Clientrequest, and
     * @param request form the client
     * @return Response by client
     */
    @Override
    public ServerResponse doRequest(ClientRequest request) {
        ServerResponse serverResponse = new ServerResponse();
        String xmlDoc = request.getRequest();

        if(!XmlSchemasValidator.validateXMLSchema(xmlDoc,TypeXml.USER)){
            serverResponse.setNameOfResponse(EResponse.REQUEST_NOT_COMPLETE);
            return serverResponse;
        }
        Compressor compressor = new Compressor();
        compressor.decompress(TypeXml.USER);
        UserDao userDao = (UserDao)ParserDaoSingleton.getParserSingleton().getCurrentParser().parseXmlString(xmlDoc,TypeXml.USER);
        ParseDataDao parseDataDao = new ParseDataDao();
        parseDataDao.setData(userDao);
        parseDataDao.setType(TypeXml.USER);
        ParserDaoSingleton.getParserSingleton().getCurrentParser().parseToXml(parseDataDao,ClientThreadDao.PATH_USERS);
        serverResponse.setNameOfResponse(EResponse.REQUEST_COMPLETE);

        compressor.compress(ClientThreadDao.PATH_USERS,TypeXml.USER);
        return serverResponse;
    }
}
