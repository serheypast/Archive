package by.bsuir.socket.interfaces;

/**
 * Created by Сергей on 15.04.2017.
 */
public interface SocketDao {

    public void createConn();

    public Object getMessage();

    public void sendMessage(Object message);

    public void closeConn();
}
