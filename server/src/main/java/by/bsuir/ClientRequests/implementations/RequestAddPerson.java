package by.bsuir.ClientRequests.implementations;

import by.bsuir.ClientRequests.interfaces.Requestable;
import by.bsuir.Compressor.Compressor;
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
public class RequestAddPerson implements Requestable {
    /**
     * Executes the request from the client
     * Parse Clientrequest, and add Person data in xmlFile
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
        Compressor compressor = new Compressor();//seriy - lox
        compressor.decompress(TypeXml.PERSON);
        PersonDao person = (PersonDao)ParserDaoSingleton.getParserSingleton().getCurrentParser().parseXmlString(xmlDoc,TypeXml.PERSON);
        ParseDataDao parseDataDao = new ParseDataDao();
        parseDataDao.setData(person);
        parseDataDao.setType(TypeXml.PERSON);
        ParserDaoSingleton.getParserSingleton().getCurrentParser().parseToXml(parseDataDao, ClientThreadDao.PATH_PERSONS);
        serverResponse.setNameOfResponse(EResponse.REQUEST_COMPLETE);

        compressor.compress(ClientThreadDao.PATH_PERSONS,TypeXml.PERSON);
        return serverResponse;
    }
}
