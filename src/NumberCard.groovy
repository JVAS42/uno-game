class NumberCard extends Card {
    private int cardNumber

    NumberCard(int cardNumber, Color cardColor, int cardCode) {
        super(cardNumber, cardColor, cardCode)

        this.cardNumber = cardNumber
    }

    int getCardNumber() {
        return cardNumber
    }

    @Override
    String toString(int lineNumber) {
        if (lineNumber < 0) {
            return super.toString(-lineNumber)
        }

        String cardColorCode = Color.getColorCodeString(super.getCardColor())

        switch (lineNumber) {
            case 1:
            case 7:
                return cardColorCode + "•~~~~~~~•" + Color.getColorCodeString(Color.RESET)

            case 2:
                return cardColorCode + "|$cardNumber      |" + Color.getColorCodeString(Color.RESET)

            case 3:
            case 5:
                return cardColorCode + "|       |" + Color.getColorCodeString(Color.RESET)

            case 4:
                return cardColorCode + "|   $cardNumber   |" + Color.getColorCodeString(Color.RESET)

            case 6:
                return cardColorCode + "|      $cardNumber|" + Color.getColorCodeString(Color.RESET)

            case 8:
                return Color.getColorCodeString(Color.WHITE) + "code: " + super.getCardCode() + Color.getColorCodeString(Color.RESET)
        }

        return null
    }
}
