package by.bsuir.XMLParsers.SAXParser;

import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.user.implementations.UserDao;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Сергей on 01.05.2017.
 */
public class UserHandler extends DefaultHandler{
    private ArrayList<UserDao> users = new ArrayList<UserDao>();

    boolean bLogin = false;
    boolean bPassword = false;
    boolean bRole = false;


    String login;
    String password;
    String role;


    int count = 0;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("user")) {
        } else if (qName.equalsIgnoreCase("login")) {
            bLogin = true;
            count++;
        } else if (qName.equalsIgnoreCase("password")) {
            bPassword = true;
            count++;
        } else if (qName.equalsIgnoreCase("role")) {
            bRole = true;
            count++;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("user")) {

        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {


        if (bLogin) {
            login = new String(ch, start, length);
            bLogin = false;
        } else if (bPassword) {
            password = new String(ch, start, length);
            bPassword = false;
        }  else if (bRole) {
            role = new String(ch, start, length);
            bRole = false;
        }
        if (count == 3) {
            UserDao user = new UserDao();
            user.setName(login);
            user.setPassword(password);
            user.setRole((role.equals(Role.ADMIN.getRole())) ? Role.ADMIN : (role.equals(Role.GUEST.getRole()) ? Role.GUEST : Role.USER));
            users.add(user);

            boolean bLogin = false;
            boolean bPassword = false;
            boolean bRole = false;


            login = null;
            password = null;
            role = null;

            count = 0;

        }
    }


    protected Object getObjects(){
        return users;
    }
}
