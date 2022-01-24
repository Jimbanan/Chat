package Connection;

/**
 * Класс перечисление, для сортировки вида полученного или отправленного сообщения
 * @author Koziakov Nikolay
 */
public enum MessageType {
    REQUEST_NAME_USER,
    TEXT_MESSAGE,
    NAME_ACCEPTED,
    USER_NAME,
    NAME_USED,
    USER_ADDED,
    DISABLE_USER,
    REMOVED_USER,
    FILE_MESSAGE;
}