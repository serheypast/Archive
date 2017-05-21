package by.bsuir.models.parseData.implementations;

import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.interfaces.ParseData;

/**
 * Created by Сергей on 17.04.2017.
 */
public class ParseDataDao implements ParseData {
    /**kinf of Type of Xml doc*/
    private TypeXml type;
    /**Object whick will be parse in xml document*/
    private Object data;
    @Override
    public TypeXml getType() {
        return type;
    }

    @Override
    public void setType(TypeXml type) {
        this.type = type;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }
}
