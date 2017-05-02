package by.bsuir.XMLParsers.Parser.implementations;

import by.bsuir.XMLParsers.DOMParser.DOMParser;
import by.bsuir.XMLParsers.Parser.interfaces.Parser;
import by.bsuir.models.enumKinds.eType.EParser;

/**
 * Created by Сергей on 17.04.2017.
 */
public class ParserDaoSingleton {
    private static ParserDaoSingleton parserSingleton;
    private static Parser currentParser = new DOMParser();


    ParserDaoSingleton(){}

    /**
     * get singleton parser
     * @return currentParser
     */
    public static ParserDaoSingleton getParserSingleton() {
        return (parserSingleton == null) ? parserSingleton = new ParserDaoSingleton() : parserSingleton;
    }

    public Parser getCurrentParser() {
        return currentParser;
    }

    public static void setCurrentParser(Parser _currentParser) {
        currentParser = _currentParser;
    }


}
