package by.bsuir.XMLParsers.Parser.interfaces;

import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.interfaces.ParseData;

/**
 * Created by Сергей on 17.04.2017.
 */
public interface Parser {
    /**
     * Parse the data in the xml document along the path
     * @param parseData data which be parsed
     * @param path path to file for parsing
     */
    public void parseToXml(ParseData parseData, String path);

    /**
     * Parse data from the xml document along Type of data
     * @param path path to file for parsing
     * @param type kind of data
     * @return data whick will be parsed
     */
    public Object parseFromXml(String path, TypeXml type);

    /**
     * Parse a string as a xml document
     * @param xmlDoc string which will be parsed
     * @param typeXml kind of data for parse
     * @return kinf of data which will be parsed
     */
    public Object parseXmlString(String xmlDoc,TypeXml typeXml);
}
