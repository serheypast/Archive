package by.bsuir.ClientRequests.implementations;

import by.bsuir.ClientRequests.interfaces.Requestable;
import by.bsuir.Compressor.Compressor;
import by.bsuir.XMLParsers.Parser.implementations.ParserDaoSingleton;
import by.bsuir.clientThread.implementations.ClientThreadDao;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;

import java.util.ArrayList;

/**
 * Created by Сергей on 30.04.2017.
 */
public class RequestGetAllPersons implements Requestable {
    /**
     * Executes the request from the client
     * Parse Clientrequest, getAllPerson from xml doc
     * @param request form the client
     * @return Response by client
     */
    @Override
    public ServerResponse doRequest(ClientRequest request) {
        ServerResponse serverResponse = new ServerResponse();
        Compressor compressor = new Compressor();
        compressor.decompress(TypeXml.PERSON);
        ArrayList<PersonDao> persons = (ArrayList<PersonDao>) ParserDaoSingleton.getParserSingleton().getCurrentParser().parseFromXml(
                ClientThreadDao.PATH_PERSONS, TypeXml.PERSON);
        serverResponse.setNameOfResponse(EResponse.REQUEST_COMPLETE);
        serverResponse.setResponseInfo(persons);
        compressor.compress(ClientThreadDao.PATH_PERSONS,TypeXml.PERSON);
        return serverResponse;
    }
}
