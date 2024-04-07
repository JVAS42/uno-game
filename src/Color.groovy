enum Color {
    BLACK, RED, YELLOW, GREEN, BLUE, WHITE,

    BLACK_B, BLACK_BRIGHT_B, RED_B, YELLOW_B, GREEN_B, BLUE_B, WHITE_B,

    RESET

    private String colorCodeString

    static {
        BLACK.colorCodeString = "\033[0;30m"
        RED.colorCodeString = "\033[0;91m"
        YELLOW.colorCodeString = "\033[0;93m"
        GREEN.colorCodeString = "\033[0;92m"
        BLUE.colorCodeString = "\033[0;96m"
        WHITE.colorCodeString = "\033[0;97m"

        BLACK_B.colorCodeString = "\033[40m"
        BLACK_BRIGHT_B.colorCodeString = "\033[100m"
        RED_B.colorCodeString = "\033[101m"
        YELLOW_B.colorCodeString = "\033[103m"
        GREEN_B.colorCodeString = "\033[102m"
        BLUE_B.colorCodeString = "\033[106m"
        WHITE_B.colorCodeString = "\033[107m"

        RESET.colorCodeString = "\033[92;40m"
    }

    static String getColorCodeString(Color color) {
        return color.colorCodeString
    }

    static Color getBackgroundColor(Color textColor) {
        switch (textColor) {
            case RED:
                return RED_B
            case YELLOW:
                return YELLOW_B
            case GREEN:
                return GREEN_B
            case BLUE:
                return BLUE_B
            case WHITE:
                return WHITE_B
            case BLACK:
                return BLACK_BRIGHT_B
            default:
                return BLACK_B
        }
    }
}
