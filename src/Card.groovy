class Card {

    private int cardScore

    private Color cardColor

    private int cardCode

    Card(int cardScore, Color cardColor, int cardCode) {
        this.cardScore = cardScore
        this.cardColor = cardColor
        this.cardCode = cardCode
    }

    int getCardScore() {
        return cardScore
    }

    Color getCardColor() {
        return cardColor
    }

    int getCardCode() {
        return cardCode
    }

    @Override
    boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false
        }

        Card card = (Card) obj
        return cardCode == card.cardCode
    }

    String toString(int lineNumber) {
        switch (lineNumber) {
            case 1:
            case 7:
                return Color.getColorCodeString(Color.WHITE) + "•~~~~~~~•" +
                        Color.getColorCodeString(Color.RESET)

            case 2:
                return Color.getColorCodeString(Color.WHITE) + "|   " +
                        Color.getColorCodeString(Color.GREEN) + "♢  " +
                        Color.getColorCodeString(Color.WHITE) + " |" +
                        Color.getColorCodeString(Color.RESET)

            case 3:
            case 5:
                return Color.getColorCodeString(Color.WHITE) + "|  " +
                        Color.getColorCodeString(Color.GREEN) + "♢♢♢  " +
                        Color.getColorCodeString(Color.WHITE) + "|" +
                        Color.getColorCodeString(Color.RESET)

            case 4:
                return Color.getColorCodeString(Color.WHITE) + "| " +
                        Color.getColorCodeString(Color.RED) + "U " +
                        Color.getColorCodeString(Color.YELLOW) + "N " +
                        Color.getColorCodeString(Color.BLUE) + "O " +
                        Color.getColorCodeString(Color.WHITE) + "|" +
                        Color.getColorCodeString(Color.RESET)

            case 6:
                return Color.getColorCodeString(Color.WHITE) + "|   " +
                        Color.getColorCodeString(Color.GREEN) + "♢  " +
                        Color.getColorCodeString(Color.WHITE) + " |" +
                        Color.getColorCodeString(Color.RESET)
        }

        return null
    }
}
