package Client;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс описывающий всех пользователей
 * @author Koziakov Nikolay
 */
public class ModelGuiClient {
    private Set<String> users = new HashSet<>();

    /**
     * Геттер, позволяющий получить список всех пользователей
     * @return Список пользователей
     */
    protected Set<String> getUsers() {
        return users;
    }

    /**
     * Добавление пользователя в список всех пользователей
     * @param nameUser Имя пользователя
     */
    protected void addUser(String nameUser) {
        users.add(nameUser);
    }

    /**
     * Удаление пользователя из списка всех пользователей
     * @param nameUser Имя пользователя
     */
    protected void removeUser(String nameUser) {
        users.remove(nameUser);
    }

    /**
     * Сеттер, переписывающий список пользователей
     * @param users Список пользователей
     */
    protected void setUsers(Set<String> users) {
        this.users = users;
    }
}
