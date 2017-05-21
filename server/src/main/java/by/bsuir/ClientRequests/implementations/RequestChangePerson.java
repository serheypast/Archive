package by.bsuir.ClientRequests.implementations;

import by.bsuir.ClientRequests.interfaces.Requestable;
import by.bsuir.Compressor.Compressor;
import by.bsuir.XMLParsers.DOMParser.DOMParser;
import by.bsuir.XMLParsers.Parser.implementations.ParserDaoSingleton;
import by.bsuir.clientThread.implementations.ClientThreadDao;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.parseData.implementations.ParseDataDao;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.xsdSchemas.XmlSchemasValidator;

/**
 * Created by Сергей on 30.04.2017.
 */
public class RequestChangePerson implements Requestable {
    /**
     * Executes the request from the client
     * Parse Clientrequest, change info about person
     * @param request form the client
     * @return Response by client
     */
    @Override
    public ServerResponse doRequest(ClientRequest request) {
        ServerResponse serverResponse = new ServerResponse();
        String xmlDoc = request.getRequest();
        if(!XmlSchemasValidator.validateXMLSchema(xmlDoc,TypeXml.PERSON)){
            serverResponse.setNameOfResponse(EResponse.REQUEST_NOT_COMPLETE);
            return serverResponse;
        }
        Compressor compressor = new Compressor();
        compressor.decompress(TypeXml.PERSON);
        PersonDao personDao = (PersonDao) ParserDaoSingleton.getParserSingleton().getCurrentParser().parseXmlString(xmlDoc, TypeXml.PERSON);
        DOMParser domParser = new DOMParser();
        ParseDataDao parseDataDao = new ParseDataDao();
        parseDataDao.setType(TypeXml.PERSON);
        parseDataDao.setData(personDao);
        serverResponse.setNameOfResponse(
                (domParser.changeInfo(parseDataDao, ClientThreadDao.PATH_PERSONS)) ?
                        EResponse.REQUEST_COMPLETE :EResponse.REQUEST_NOT_COMPLETE);
        compressor.compress(ClientThreadDao.PATH_PERSONS,TypeXml.PERSON);
        return serverResponse;
    }
}
