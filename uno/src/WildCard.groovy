class WildCard extends Card {

    WildCard(int cardCode) {
        super(20, Color.BLACK, cardCode)
    }

    @Override
    String toString(int lineNumber) {
        if (lineNumber < 0) {
            return super.toString((-1) * lineNumber)
        }

        switch (lineNumber) {
            case 1:
            case 7:
                return "${Color.getColorCodeString(Color.WHITE)}•~~~~~~~•${Color.getColorCodeString(Color.RESET)}"

            case 2:
                return "${Color.getColorCodeString(Color.WHITE)}|       |${Color.getColorCodeString(Color.RESET)}"

            case 3:
            case 5:
                return "${Color.getColorCodeString(Color.WHITE)}|       |${Color.getColorCodeString(Color.RESET)}"

            case 4:
                return "${Color.getColorCodeString(Color.WHITE)}|${Color.getColorCodeString(Color.RED)}W ${Color.getColorCodeString(Color.YELLOW)}i ${Color.getColorCodeString(Color.GREEN)}l ${Color.getColorCodeString(Color.BLUE)}d${Color.getColorCodeString(Color.WHITE)}|${Color.getColorCodeString(Color.RESET)}"

            case 6:
                return "${Color.getColorCodeString(Color.WHITE)}|       |${Color.getColorCodeString(Color.RESET)}"

            case 8:
                return "${Color.getColorCodeString(Color.WHITE)}code: ${super.getCardCode()}${Color.getColorCodeString(Color.RESET)}"
        }

        return null
    }
}
