package Server;

import Connection.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс производит запись и удаление пользователей, находящихся онлайн
 * @author Koziakov Nikolay
 */
public class ModelGuiServer {

    /**    Поле хранящее всех пользователей и информацию о них      */
    private Map<String, Connection> allUsersMultiChat = new HashMap<>();

    /**
     * Геттер, возвращающий список всех пользователей со статусом онлайн
     * @return Общий список пользователей
     */
    protected Map<String, Connection> getAllUsersMultiChat() {
        return allUsersMultiChat;
    }

    /**
     * Добавление пользователя в общий список
     * @param nameUser Имя пользователя
     * @param connection объект класса Connection для данного пользователя
     */
    protected void addUser(String nameUser, Connection connection) {
        allUsersMultiChat.put(nameUser, connection);
    }

    /**
     * Удаление пользователя из общего списка пользователей
     * @param nameUser Имя пользователя
     */
    protected void removeUser(String nameUser) {
        allUsersMultiChat.remove(nameUser);
    }

}