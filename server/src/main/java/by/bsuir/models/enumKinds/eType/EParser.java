package by.bsuir.models.enumKinds.eType;

/**
 * Created by Сергей on 29.04.2017.
 */
public enum EParser {
    SAX("SAX"),DOM("DOM"),JDOM("JDOM"),STAX("STAX");

    private String parserName;

    EParser(String parserName) {
        this.parserName = parserName;
    }

    public String getParserName() {
        return parserName;
    }
}
