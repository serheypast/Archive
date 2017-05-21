package by.bsuir.models.parseData.interfaces;

import by.bsuir.models.enumKinds.eType.TypeXml;

/**
 * Created by Сергей on 17.04.2017.
 */
public interface ParseData {
    public TypeXml getType();

    public void setType(TypeXml type);
    public Object getData();
    public void setData(Object data);

}
