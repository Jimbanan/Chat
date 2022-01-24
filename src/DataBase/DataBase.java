package DataBase;

import java.sql.*;

/**
 * Класс, описывающий взаимодействие с базой данных
 * @author Koziakov Nikolay
 */
public class DataBase {
    /**  Поле для сброса цвета текста в консоли   */
    public static final String ANSI_RESET = "\u001B[0m";
    /**  Поле для изменения цвета текста в консоли (Голубой)  */
    public static final String ANSI_CYAN = "\u001B[36m";
    /**  Поле для изменения цвета текста в консоли (Желтый)  */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**  URL - адрес для подключения к базе данных  */
    private static final String URL = "jdbc:mysql://localhost:3306/KP?serverTimezone=Europe/Moscow&useSSL=false";
    /**  Логин для подключения к базе данных  */
    private static final String LOGIN = "root";
    /**  Пароль для подключения к базе данных  */
    private static final String PASSWORD = "root";

    private static Connection connection = null;
    private static Statement statement = null;

    /**  ID пользователя  */
    private static String ID;

    /**
     * Конструктор, реализующий подключение Драйвера JDBC
     */
    public DataBase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(ANSI_CYAN + "Driver Connected" + ANSI_RESET);
        } catch (ClassNotFoundException e) {
            System.out.println(ANSI_YELLOW + "Driver Not Connected\nError: " + e.getMessage() + ANSI_RESET);
        }

    }

    /**
     * Проверка количества пользователей с введенным логином и паролем для авторизации
     * @param login Логин
     * @param password Пароль
     * @return Количество найденных пользователей
     */
    public boolean find(String login, String password) {

        int count = 0;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connected BD");

                statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*), Users.Login FROM Users WHERE Users.login like '" + login + "' and Users.password like '" + password + "'");
                while (resultSet.next()) {
                    count = resultSet.getInt("COUNT(*)");
                    if (count != 0) {
                        ID = resultSet.getString("Login");
                    }
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
            }
        }

        System.out.println("Count: " + count);
        System.out.println("ID: " + ID);
        return (count == 1);
    }

    /**
     * Данный метод осуществляет проверку количества пользователей с введенным логином, для исключения возможности регистрации 2 пользователей с одинаковым логином
     * @param login Логин
     * @return Количество пользователей с данным логином
     */
    public boolean findLogin(String login) {

        int count = 0;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connected BD");

                statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*), Users.Login FROM Users WHERE Users.login like '" + login + "'");
                while (resultSet.next()) {
                    count = resultSet.getInt("COUNT(*)");
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
            }
        }

        System.out.println("Count: " + count);
        return (count == 0);
    }

    /**
     * Данный метод осуществляет добавление информации о зарегистрированном пользователе в базу данных
     * @param login Логин
     * @param password Пароль
     * @param name Имя
     * @param surname Фамилия
     */
    public void addUser(String login, String password, String name, String surname) {
        String request = "insert users (Login, Password, Name, Surname, IP) values (?, ?, ?, ?, ?)";

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            if (!connection.isClosed()) {
                PreparedStatement preparedStatement = connection.prepareStatement(request);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, surname);
                preparedStatement.setInt(5, 0);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.getMessage();
            }
        }
    }

    /**
     * Данный метод изменяет информацию ip-адреса для определенного пользователя
     * @param IP IP-адрес пользователя
     */
    public void ip(String IP) {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            if (!connection.isClosed()) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE users SET IP = ? WHERE Login = ? ");

                preparedStatement.setString(1, IP);
                preparedStatement.setString(2, ID);

                // call executeUpdate to execute our sql update statement
                preparedStatement.executeUpdate();
                preparedStatement.close();


            }
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.getMessage();
            }


        }
    }

    /**
     * Данный метод осуществляет добавление сообщений, отправленных пользователями в базу данных
     * @param message Текст сообщения
     * @param date Время отправки сообщения
     */
    public void addMessage(String message, String date) {

        String request = "insert message_history (Message, Data, user) values (?, ?, ?)";

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            if (!connection.isClosed()) {
                PreparedStatement preparedStatement = connection.prepareStatement(request);
                preparedStatement.setString(1, message);
                preparedStatement.setString(2, date);
                preparedStatement.setString(3, ID);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.getMessage();
            }
        }
    }

    /**
     * Данный метод считывает все сообщения за выбранный период, для отображения сообщений в окне «История сообщений»
     * @param date Время для определения рамок выборки сообщений
     * @param num Вариация SQL запроса
     * @return Массив сообщений за выбранный период
     */
    public String[][] message_History(String date, int num) {

        System.out.println(date);
        String[] d_new = date.split("-");
        String SQLQuery = "";
        switch (num) {
            case 1 -> SQLQuery = "    Select Message_History.user AS 'Пользователь',\n" +
                    "    Message_History.Message AS 'Сообщение',\n" +
                    "    Message_History.Data AS 'Время отправки'\n" +
                    "    From Message_History\n" +
                    "    WHERE Message_History.Data < '" + date + "'";
            case 2 -> {
                String d = d_new[0] + "-" + d_new[1] + "-" + (Integer.parseInt(d_new[2]) + 1);
                System.out.println(d);

                SQLQuery = "    Select Message_History.user AS 'Пользователь',\n" +
                        "    Message_History.Message AS 'Сообщение',\n" +
                        "    Message_History.Data AS 'Время отправки'\n" +
                        "    From Message_History\n" +
                        "    WHERE Message_History.Data < '" + d + "'" + " AND Message_History.Data > '" + date + "'";
            }
            case 3 -> {
                date = d_new[0] + "-" + d_new[1] + "-" + (Integer.parseInt(d_new[2]) + 1);
                SQLQuery = "    Select Message_History.user AS 'Пользователь',\n" +
                        "    Message_History.Message AS 'Сообщение',\n" +
                        "    Message_History.Data AS 'Время отправки'\n" +
                        "    From Message_History\n" +
                        "    WHERE Message_History.Data > '" + date + "'";
            }
        }
        String[][] message = new String[count(num, date)][3];
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connected BD");

                statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(SQLQuery);
                int i = 0;
                while (resultSet.next()) {

                    String user = resultSet.getString("Пользователь");
                    String mes = resultSet.getString("Сообщение");
                    String time_of_dispatch = resultSet.getString("Время отправки");
                    message[i][0] = user;
                    message[i][1] = mes;
                    message[i][2] = time_of_dispatch;
                    i++;
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
            }
        }

        return message;
    }

    /**
     * Данный метод возвращает количество сообщений за выбранный период
     * @param num Вариация SQL запроса
     * @param date Время выборки сообщений
     * @return Количество сообщений за выбранный период
     */
    public int count(int num, String date) {
        String[] d_new = date.split("-");

        String SQLQuery = "";
        switch (num) {
            case 1 -> SQLQuery = "    Select COUNT(*) " +
                    "    From Message_History\n" +
                    "    WHERE Message_History.Data < '" + date + "'";
            case 2 -> {
                String d = d_new[0] + "-" + d_new[1] + "-" + (Integer.parseInt(d_new[2]) + 1);
                SQLQuery = "    Select COUNT(*) " +
                        "    From Message_History\n" +
                        "    WHERE Message_History.Data < '" + d + "'" + " AND Message_History.Data > '" + date + "'";
            }
            case 3 -> SQLQuery = "    Select COUNT(*) " +
                    "    From Message_History\n" +
                    "    WHERE Message_History.Data > '" + date + "'";
        }
        int count = 0;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connected BD");

                statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(SQLQuery);
                while (resultSet.next()) {
                    count = resultSet.getInt("COUNT(*)");
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(ANSI_YELLOW + "Error: " + e.getMessage() + ANSI_RESET);
            }
        }
        System.out.println(count);
        return count;
    }

}
