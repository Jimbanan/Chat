package Client;

/**
 * Класс, описывающий эмодзи в Unicode представлении
 * @author Koziakov Nikolay
 */
public class Emoji {

    /**  Поле, хранящее массив String Unicode представления эмодзи  */
    String[] emoji = new String[]{
            "\uD83D\uDE04",
            "\uD83D\uDE03",
            "\uD83D\uDE00",
            "\uD83D\uDE0A",
            "\u263A",
            "\uD83D\uDE09",
            "\uD83D\uDE0D",
            "\uD83D\uDE18",
            "\uD83D\uDE1A",
            "\uD83D\uDE17",
            "\uD83D\uDE19",
            "\uD83D\uDE1C",
            "\uD83D\uDE1D",
            "\uD83D\uDE1B",
            "\uD83D\uDE33",
            "\uD83D\uDE01",
            "\uD83D\uDE14",
            "\uD83D\uDE0C",
            "\uD83D\uDE12",
            "\uD83D\uDE1E",
            "\uD83D\uDE23",
            "\uD83D\uDE22",
            "\uD83D\uDE02",
            "\uD83D\uDE2D",
            "\uD83D\uDE2A",
            "\uD83D\uDE25",
            "\uD83D\uDE30",
            "\uD83D\uDE05",
            "\uD83D\uDE13",
            "\uD83D\uDE29",
            "\uD83D\uDE2B",
            "\uD83D\uDE28",
            "\uD83D\uDE31",
            "\uD83D\uDE20",
            "\uD83D\uDE21",
            "\uD83D\uDE24",
            "\uD83D\uDE16",
            "\uD83D\uDE06",
            "\uD83D\uDE0B",
            "\uD83D\uDE37",
            "\uD83D\uDE0E",
            "\uD83D\uDE34",
            "\uD83D\uDE35",
            "\uD83D\uDE32",
            "\uD83D\uDE1F",
            "\uD83D\uDE26",
            "\uD83D\uDE27",
            "\uD83D\uDE08",
            "\uD83D\uDC7F",
            "\uD83D\uDE2E",
            "\uD83D\uDE2C",
            "\uD83D\uDE10",
            "\uD83D\uDE15",
            "\uD83D\uDE2F",
            "\uD83D\uDE36",
            "\uD83D\uDE07",
            "\uD83D\uDE0F",
            "\uD83D\uDE11",
            "\uD83D\uDC72",
            "\uD83D\uDC73",
            "\uD83D\uDC6E",
            "\uD83D\uDC77",
            "\uD83D\uDC82",
            "\uD83D\uDC76",
            "\uD83D\uDC66",
            "\uD83D\uDC67",
            "\uD83D\uDC68",
            "\uD83D\uDC69",
            "\uD83D\uDC74",
            "\uD83D\uDC75",
            "\uD83D\uDC71",
            "\uD83D\uDC7C",
            "\uD83D\uDC78",
            "\uD83D\uDE3A",
            "\uD83D\uDE38",
            "\uD83D\uDE3B",
            "\uD83D\uDE3D",
            "\uD83D\uDE3C",
            "\uD83D\uDE40",
            "\uD83D\uDE3F",
            "\uD83D\uDE39",
            "\uD83D\uDE3E",
            "\uD83D\uDC79",
            "\uD83D\uDC7A",
            "\uD83D\uDE48",
            "\uD83D\uDE49",
            "\uD83D\uDE4A",
            "\uD83D\uDC80",
            "\uD83D\uDC7D",
            "\uD83D\uDCA9",
            "\uD83D\uDD25",
            "\u2728",
            "\uD83C\uDF1F",
            "\uD83D\uDCAB",
            "\uD83D\uDCA5",
    };

    /**
     * Геттер, возвращающий определенный эмодзи
     * @param i Номер выбранного эмодзи
     * @return Выбранный эмодзи
     */
    public String getEmoji(int i){
        return emoji[i];
    }

    /**
     * Геттер, возвращающий длину массива
     * @return Длина массива
     */
    public int getEmojiSize(){
        return emoji.length;
    }

}
