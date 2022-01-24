package Client;

import DataBase.DataBase;
import File.MyFile;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс, описывающий визуальную составляющую пользователя
 * @author Koziakov Nikolay
 */
public class ViewGuiClient {
    private final Client client;
    private final JFrame frame = new JFrame("Чат");
    private final JTextArea messages = new JTextArea(30, 20);
    private final JTextArea users = new JTextArea(30, 20);
    private final JPanel panel = new JPanel();
    private final JTextField textField = new JTextField(40);
    private final JButton buttonDisable = new JButton("Отключиться");
    private final JButton buttonConnect = new JButton("Подключиться");
    private final ImageIcon img = new ImageIcon("C:\\Users\\userv\\Desktop\\KP\\src\\Materials\\Project.png");
    private final JButton jbChooseFile = new JButton("Обзор");

    private final JMenu emoji = new JMenu("\uD83D\uDE04");
    private final JMenuItem empji_group1 = new JMenuItem("\uD83D\uDE0A");
    JMenuBar emojimb = new JMenuBar();

    JMenu menu;
    JMenuItem message_history;
    JMenuBar mb = new JMenuBar();

    UtilDateModel utilDateModel = new UtilDateModel();

    Properties p = new Properties();

    final File[] fileToSend = new File[1];

    static ArrayList<MyFile> myFiles = new ArrayList<>();


    /**
     * Инициализирует поле клиента
     * @param client Объект класса Client
     */
    public ViewGuiClient(Client client) {
        this.client = client;

    }

    /**
     * Инициализация основного окна пользователя
     */
    protected void initFrameClient() {
        buttonConnect.setBackground((new Color(224, 255, 255)));//Цвет кнопка Отправить;
        buttonDisable.setBackground((new Color(224, 255, 255)));//Цвет кнопка Выйти;
        messages.setEditable(false);
        messages.setBackground(new Color(245, 245, 220));//Цвет сообщений
        users.setEditable(false);
        users.setBackground(new Color(254, 226, 234));//Цвет Пользователей
        frame.add(new JScrollPane(messages), BorderLayout.CENTER);
        frame.add(new JScrollPane(users), BorderLayout.EAST);

        emoji.add(empji_group1);
        emojimb.add(emoji);
        panel.add(emojimb);

        panel.add(textField);
        panel.add(buttonConnect);
        jbChooseFile.setVisible(false);
        panel.add(jbChooseFile);
        panel.add(buttonDisable);

        panel.setBackground(Color.lightGray);

        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null); // при запуске отображает окно по центру экрана
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //класс обработки события при закрытии окна приложения Сервера
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isConnect()) {
                    client.disableClient();
                }
                System.exit(0);
            }
        });

        menu = new JMenu("Меню");
        message_history = new JMenuItem("История сообщений", new ImageIcon("C:\\Users\\userv\\Desktop\\KP\\src\\Materials\\Message.gif"));
        message_history.setBackground(Color.lightGray);
        menu.add(message_history);
        menu.add(new JSeparator());

        JRadioButtonMenuItem start = new JRadioButtonMenuItem("Включить музыку", new ImageIcon("C:\\Users\\userv\\Desktop\\KP\\src\\Materials\\Music.png"));
        JRadioButtonMenuItem stop = new JRadioButtonMenuItem("Остановить музыку", new ImageIcon("C:\\Users\\userv\\Desktop\\KP\\src\\Materials\\Stop.png"));

        JMenu sound = new JMenu("Музыка");

        sound.add(start);
        sound.addSeparator();
        sound.add(stop);

        menu.add(sound);
//        menu.add(sound);
        JMenuItem allFiles = new JMenuItem("Файлы");
        menu.add(allFiles);

        allFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFile(myFiles);
            }
        });

        mb.add(menu);
        mb.setBackground(Color.lightGray);

        frame.setIconImage(img.getImage());
        frame.setJMenuBar(mb);
        frame.setSize(700, 700);
        frame.setVisible(true);
        Sound music = new Sound(new File("C:\\Users\\userv\\Desktop\\KP\\src\\Materials\\nico-vinz-am-i-wrong.aif"));

        start.addActionListener(e -> {
            start.setSelected(true);
            stop.setSelected(false);
//                Sound.playSound("C:\\Users\\userv\\Desktop\\MultiUsersChat-master\\src\\Materials\\nico-vinz-am-i-wrong.aif").play();
            music.play();

//                Sound.playSound("C:\\Users\\userv\\Desktop\\MultiUsersChat-master\\src\\Materials\\nico-vinz-am-i-wrong.aif").stop();
        });

        stop.addActionListener(e -> {
            start.setSelected(false);
            stop.setSelected(true);
            music.stop();
        });

        jbChooseFile.addActionListener(e -> client.sendFile(fileToSend[0]));
        buttonDisable.addActionListener(e -> client.disableClient());
        buttonConnect.addActionListener(e -> client.connectToServer());
        textField.addActionListener(e -> {
            client.sendMessageOnServer(textField.getText());
            textField.setText("");
        });
        message_history.addActionListener(arg0 -> {

            JFrame frame2 = new JFrame("История сообщений");
            JPanel panel2 = new JPanel();
            JButton buttonSend = new JButton("Вывод");
            panel2.setBackground(Color.lightGray);
            buttonSend.setBackground((new Color(224, 255, 255)));

            JComboBox<String> jComboBox = new JComboBox<>(new String[]{"До", "За", "После"});

            JPanel panel3 = new JPanel();

            //https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
            DefaultTableModel model = new DefaultTableModel();
            JTable table1 = new JTable(model);

            JTextArea message_h = new JTextArea(30, 20);


            //Добавление календаря
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(utilDateModel, p);
            // Don't know about the formatter, but there it is...
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

            panel2.add(datePicker);

            panel2.add(jComboBox);
            panel2.add(buttonSend, BorderLayout.EAST);

            table1.setDefaultEditor(Object.class, null);
            table1.setFocusable(false);

            table1.setBackground(new Color(245, 245, 220));

            panel3.add(new JScrollPane(table1));

            panel3.setLayout(new GridLayout(1, 2));

            frame2.setIconImage(img.getImage());
            frame2.add(panel2, BorderLayout.SOUTH);
            frame2.add(panel3);
            frame2.pack();
            frame2.setSize(500, 400);
            frame2.setVisible(true);//делаю форму 2 видимой

            buttonSend.addActionListener(e -> {

                Date selectedDate = (Date) datePicker.getModel().getValue();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");//Выбранная дата

                System.out.println(formatForDateNow.format(selectedDate));

                message_h.setText(null);
                Object cmboitem = jComboBox.getSelectedItem();

                System.out.println(cmboitem);
                DataBase dataBase = new DataBase();
                String[][] m = null;
                if (cmboitem.equals("До")) {
                    m = dataBase.message_History(formatForDateNow.format(selectedDate), 1);
                } else if (cmboitem.equals("За")) {
                    m = dataBase.message_History(formatForDateNow.format(selectedDate), 2);
                } else if (cmboitem.equals("После")) {
                    m = dataBase.message_History(formatForDateNow.format(selectedDate), 3);
                }
                model.setRowCount(0);
                model.setColumnCount(0);
                model.addColumn("Пользователь");
                model.addColumn("Сообщение");
                model.addColumn("Время отправки");
                table1.setForeground(Color.darkGray);
                table1.setSelectionForeground(Color.yellow);
                table1.setSelectionBackground(Color.lightGray);
                table1.setFont(new Font("Monospace", Font.PLAIN, 14));

                if (m.length == 0) {
                    not_found("Сообщения за выбранный период не найдены");
                } else {
                    for (int i = 0; i < m.length; i++) {
                        model.addRow(new Object[]{m[i][0], m[i][1], m[i][2]});
                    }
                }

            });


        });
    }


    {
        empji_group1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFrame frame_emoji = new JFrame("Эмодзи");
                JTabbedPane jp = new JTabbedPane(JTabbedPane.LEFT); // Устанавливаем вкладку в координатах

                frame_emoji.setBackground(new Color(245, 245, 220));
                frame_emoji.setSize(460, 225);
                frame_emoji.setLayout(new BoxLayout(frame_emoji.getContentPane(), BoxLayout.Y_AXIS));
                frame_emoji.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel p1 = new JPanel();

                //https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
                DefaultTableModel model = new DefaultTableModel();
                JTable table1 = new JTable(model);
                table1.setSize(700, 20);
                // Don't know about the formatter, but there it is...
                table1.setDefaultEditor(Object.class, null);
                table1.setFocusable(false);

                table1.setBackground(new Color(245, 245, 220));

                p1.add(new JScrollPane(table1), BorderLayout.NORTH);


                p1.setSize(20, 700);

                frame_emoji.add(p1, BorderLayout.NORTH);


                String[][] m = null;

                model.setRowCount(0);
                model.setColumnCount(0);
                model.addColumn(0);
                model.addColumn(1);
                model.addColumn(2);
                model.addColumn(3);
                model.addColumn(4);
                model.addColumn(5);
                model.addColumn(6);
                model.addColumn(7);
                model.addColumn(8);
                model.addColumn(9);
                table1.setForeground(Color.darkGray);
                table1.setSelectionForeground(Color.yellow);
                table1.setSelectionBackground(Color.lightGray);
                table1.setFont(new Font("Monospace", Font.PLAIN, 20));
                table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                for (int i = 0; i < model.getColumnCount(); i++) {
                    table1.getColumnModel().getColumn(i).setPreferredWidth(45);
                }
                System.out.println(new Emoji().getEmojiSize());
                for (int i = 0; i < new Emoji().getEmojiSize(); i += 9) {
                    if (i + 9 < new Emoji().getEmojiSize())
                        model.addRow(new Object[]{new Emoji().getEmoji(i), new Emoji().getEmoji(i + 1), new Emoji().getEmoji(i + 2),
                                new Emoji().getEmoji(i + 3), new Emoji().getEmoji(i + 4), new Emoji().getEmoji(i + 5),
                                new Emoji().getEmoji(i + 6), new Emoji().getEmoji(i + 7), new Emoji().getEmoji(i + 8),
                                new Emoji().getEmoji(i + 9)});
                }

                frame_emoji.setVisible(true);


                table1.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.out.println(table1.getSelectedRow());
                        System.out.println(table1.getSelectedColumn());
                        String selectedCellValue = (String) table1.getValueAt(table1.getSelectedRow() , table1.getSelectedColumn());
                        System.out.println(selectedCellValue);
                        client.sendMessageOnServer(selectedCellValue);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });

            }
        });
    }

    /**
     * Добавление файла
     * @param fileList Список файлов
     */
    protected void addFile(ArrayList<MyFile> fileList) { // Сделать проверку на открытие

        int fileID = 0;


        JFrame jFrame = new JFrame("Файлы");
        jFrame.setBackground(new Color(245, 245, 220));
        jFrame.setSize(400, 450);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(245, 245, 220));
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setBackground(new Color(245, 245, 220));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel jlTitle = new JLabel("Все файлы");
        jlTitle.setBackground(new Color(245, 245, 220));
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);


        jFrame.add(jlTitle);
        jScrollPane.setBackground(new Color(245, 245, 220));
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);


        for (MyFile file : fileList) {
            JPanel jpFileRow = new JPanel();
            jpFileRow.setBackground(new Color(245, 245, 220));
            jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.Y_AXIS));

            String fileName = file.getName();
            JLabel jlFileName = new JLabel(fileName);
            jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
            jlFileName.setBorder(new EmptyBorder(10, 0, 10, 0));


            jpFileRow.setName(String.valueOf(file.getName()));
            jpFileRow.addMouseListener(getMyMouseListener());

            jpFileRow.add(jlFileName);
            jPanel.add(jpFileRow);

            jFrame.validate();
            myFiles.add(file);

        }
    }

    /**
     * Метод, описывающий загрузку файла
     * @param fileName Имя файла
     * @param fileData Байтовое представление файла
     * @return Окно загрузки файла
     */
    public static JFrame createFrame(String fileName, byte[] fileData) {

        JFrame jFrame = new JFrame("Загрузчик файла");
        jFrame.setBackground(new Color(245, 245, 220));
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel jlTitle = new JLabel("Загрузка файла");
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel jlPromt = new JLabel("Скачать файл: " + fileName + " ?");
        jlPromt.setFont(new Font("Arial", Font.BOLD, 20));
        jlPromt.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlPromt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton jbYes = new JButton("Да");
        jbYes.setBackground((new Color(224, 255, 255)));
        jbYes.setPreferredSize(new Dimension(150, 75));
        jbYes.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jbNo = new JButton("Нет");
        jbNo.setBackground((new Color(224, 255, 255)));
        jbNo.setPreferredSize(new Dimension(150, 75));
        jbNo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButtons = new JPanel();
        jpButtons.setBorder(new EmptyBorder(20, 0, 10, 0));
        jpButtons.add(jbYes);
        jpButtons.add(jbNo);


        jlFileContent.setIcon(new ImageIcon(fileData));


        jbYes.addActionListener(e -> {
            File fileToDownload = new File(fileName);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                fileOutputStream.write(fileData);
                fileOutputStream.close();

                jFrame.dispose();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        jbNo.addActionListener(e -> jFrame.dispose());

        jPanel.add(jlTitle);
        jPanel.add(jlPromt);
        jPanel.add(jlFileContent);
        jpButtons.setBackground(new Color(245, 245, 220));
        jPanel.add(jpButtons);
        jPanel.setBackground(new Color(245, 245, 220));
        jFrame.add(jPanel);

        return jFrame;

    }

    /**
     * Обработка нажатий по полученным файлам
     * @return Название выбранного файла
     */
    public static MouseListener getMyMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel jPanel = (JPanel) e.getSource();

                String fileNAME = jPanel.getName();
                int counter = 0;
                for (MyFile myFile : myFiles) {
                    if (fileNAME.equals(myFile.getName())) {
                        if (counter == 0) {
                            JFrame jfPreview = createFrame(myFile.getName(), myFile.getData());
                            jfPreview.setVisible(true);
                            counter++;
                        }

                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }


    /**
     * Добавления сообщения в поле сообщений
     * @param text Текст сообщения
     */
    protected void addMessage(String text) {
        messages.setFont(new Font("Dialog", Font.PLAIN, 14));
        messages.append(text);
    }

    /**
     * Обновление списка пользователей онлайн
     * @param listUsers Общий список пользователей
     */
    protected void refreshListUsers(Set<String> listUsers) {
        users.setFont(new Font("Dialog", Font.ITALIC, 15));
        users.setText("");
        if (client.isConnect()) {
            StringBuilder text = new StringBuilder("Список пользователей:\n");
            for (String user : listUsers) {
                text.append(user + "\n");
            }
            users.append(text.toString());
        }
    }

    /**
     * Геттер, возвращающий логин, вписанный пользователем
     * @return Логин
     */
    protected String getLogin() {
        return JOptionPane.showInputDialog(
                frame, "Введите логин:",
                "Ввод логина пользователя",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    /**
     * Геттер, возвращающий пароль, вписанный пользователем
     * @return Пароль
     */
    protected String getPasswordUser() {
        return JOptionPane.showInputDialog(
                frame, "Введите пароль:",
                "Ввод пароля пользователя",
                JOptionPane.QUESTION_MESSAGE
        );
    }


    /**
     * Геттер, возвращающий имя, вписанное пользователем
     * @return Имя
     */
    protected String getNameUser() {
        return JOptionPane.showInputDialog(
                frame, "Введите имя:",
                "Ввод имени пользователя",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    /**
     * Геттер, возвращающий фамилию, вписанную пользователем
     * @return Фамилия
     */
    protected String getSurnameUser() {
        return JOptionPane.showInputDialog(
                frame, "Введите фамилию:",
                "Ввод фамилии пользователя",
                JOptionPane.QUESTION_MESSAGE
        );
    }


    /**
     * Окно ошибки
     * @param text Текст ошибки
     */
    protected void errorDialogWindow(String text) {
        JOptionPane.showMessageDialog(
                frame, text,
                "Ошибка", JOptionPane.ERROR_MESSAGE
        );
    }


    /**
     * Окно ошибки при регистрации
     * @param text Текст ошибки
     */
    protected void errorReg(String text) {
        JOptionPane.showMessageDialog(
                frame, text,
                "Ошибка", JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Окно ошибки при работе с "История сообщений"
     * @param text Текст ошибки
     */
    protected void not_found(String text) {
        JOptionPane.showMessageDialog(
                frame, text,
                "Сообщения не найдены", JOptionPane.ERROR_MESSAGE
        );
    }


    Object[] options = {"Войти",
            "Зарегистрироваться",
            "Выйти"};
    public int n = JOptionPane.showOptionDialog(frame,
            "Вы хотите войти или зарегистрироваться?",
            "Авторизация",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);


    /**
     * Метод, описывающий дальнейшее взаимодействие пользователя, при удачном подключении
     */
    public void connectVisible() {
        buttonConnect.setVisible(false);
        jbChooseFile.setVisible(true);
        jbChooseFile.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Choose a file to send");

            if (jFileChooser.showOpenDialog(null) == jFileChooser.APPROVE_OPTION) {
                fileToSend[0] = jFileChooser.getSelectedFile();
//                    fileName = fileToSend[0].getName();
//                    System.out.println("The file you want to send is: " + fileToSend[0].getName());
//                    jlFileName.setText("The file you want to send is: " + fileToSend[0].getName());

            }
        });
    }

    public void music() {
        try {
            FileInputStream f = new FileInputStream("C:\\Users\\userv\\Desktop\\MultiUsersChat-master\\src\\Materials\\nico-vinz-am-i-wrong.mp3");
            Player player = new Player(f);
            player.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }


}


/**
 * Класс для работы с датой
 * @author Koziakov Nikolay
 */
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);


    /**
     * Парсинг полученный даты
     * @param text Дата
     * @return Пропарсенная дата
     * @throws ParseException
     */
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Перевод даты из календаря в тип String
     * @param value Обработка календаря
     * @return Пропарсенная дата
     */
    public String valueToString(Object value) {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}