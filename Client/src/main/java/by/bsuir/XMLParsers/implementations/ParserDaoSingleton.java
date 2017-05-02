package by.bsuir.XMLParsers.implementations;

import by.bsuir.XMLParsers.DOMParser.DOMParser;
import by.bsuir.XMLParsers.interfaces.Parser;

/**
 * Created by Сергей on 17.04.2017.
 */
public class ParserDaoSingleton {
    /**
     * Singleton parser
     */
    private static ParserDaoSingleton parser;
    /**
     * default parser for parsing xml files
     */
    Parser currentParser = new DOMParser();
    public ParserDaoSingleton() {
    }

    /**
     * getParser for parse xml files
     * @return SingletonParser
     */
    public static ParserDaoSingleton getParser() {
        return (parser == null) ? new ParserDaoSingleton(): parser;
    }

    public Parser getCurrentParser() {
        return currentParser;
    }

    public void setCurrentParser(Parser currentParser) {
        this.currentParser = currentParser;
    }
}
