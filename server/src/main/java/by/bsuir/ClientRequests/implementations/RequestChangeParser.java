package by.bsuir.ClientRequests.implementations;

import by.bsuir.ClientRequests.interfaces.Requestable;
import by.bsuir.XMLParsers.DOMParser.DOMParser;
import by.bsuir.XMLParsers.JDOMParser.JDOMParser;
import by.bsuir.XMLParsers.Parser.implementations.ParserDaoSingleton;
import by.bsuir.XMLParsers.SAXParser.SAX_Parser;
import by.bsuir.XMLParsers.StAXParser.StAxParser;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;

/**
 * Created by Сергей on 01.05.2017.
 */
public class RequestChangeParser implements Requestable {
    /**
     * Executes the request from the client
     * Parse Clientrequest, change Parser in server
     * @param request form the client
     * @return Response by client
     */
    @Override
    public ServerResponse doRequest(ClientRequest request) {
        ServerResponse serverResponse = new ServerResponse();
        String xmlDoc = request.getRequest();
        ParserDaoSingleton parser = null;
        switch (xmlDoc){
            case "JDOM":
                parser = ParserDaoSingleton.getParserSingleton();
                parser.setCurrentParser(new JDOMParser());
                break;
            case "DOM":
                parser = ParserDaoSingleton.getParserSingleton();
                parser.setCurrentParser(new DOMParser());
                break;
            case "SAX":
                parser = ParserDaoSingleton.getParserSingleton();
                parser.setCurrentParser(new SAX_Parser());
                break;
            case "STAX":
                parser = ParserDaoSingleton.getParserSingleton();
                parser.setCurrentParser(new StAxParser());
                break;
            default:
                serverResponse.setNameOfResponse(EResponse.REQUEST_NOT_COMPLETE);
                break;
        }
        serverResponse.setNameOfResponse(EResponse.REQUEST_COMPLETE);
        return serverResponse;
    }
}
