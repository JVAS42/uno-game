class Run {
    private static Scanner inputs = new Scanner(System.in)

    static void main(String[] args) {
        Printer.calibrate(inputs)

        String holdInput
        int numberOfPlayers
        String newPlayerName

        while (true) {
            while (true) {
                Printer.printMenu()
                holdInput = inputs.nextLine()

                if (holdInput.length() == 1 && (holdInput.charAt(0) == '1' || holdInput.charAt(0) == '2'))
                    break
                else
                    Printer.inValidInputError(inputs)
            }

            switch (holdInput) {
                case "1":
                    while (true) {
                        Printer.getNumberOfThePlayers()
                        holdInput = inputs.nextLine()

                        if (holdInput.length() == 1 && holdInput.charAt(0) > '0' && holdInput.charAt(0) < '8')
                            break
                        else
                            Printer.inValidInputError(inputs)
                    }

                    numberOfPlayers = (int)holdInput.charAt(0) - (int)'0'


                    (0..<numberOfPlayers).each { n ->
                        Printer.getPlayerName(n + 1)
                        newPlayerName = inputs.nextLine()

                        if (newPlayerName.toLowerCase() == "bot") {
                            Rules.addPlayer(new Bot(n))
                        } else {
                            Rules.addPlayer(new Player(newPlayerName))
                        }
                    }

                    Rules.preparationGameCards()
                    Rules.distributeCards()

                    Rules.runGame(inputs)

                    Rules.reset()

                    break

                case "2":
                    return
            }
        }
    }
}
