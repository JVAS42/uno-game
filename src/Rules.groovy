class Rules {
    private static ArrayList<Player> players = new ArrayList<>()
    private static ArrayList<Card> gameCards = new ArrayList<>()
    private static Card boardCard
    private static Color boardColor
    private static ArrayList<Card> penaltyCards = new ArrayList<>()

    static void preparationGameCards() {
        makeGameCards()
        shuffleCards()
    }

    static boolean addPlayer(Player playerToAdd) {
        return players.add(playerToAdd)
    }

    static Player getPlayer(int playerIndex) {
        return players.get(playerIndex)
    }

    static ArrayList<Player> getPlayers() {
        return players
    }

    static void distributeCards() {
        for (def n = 0; n < 7; n++) {
            for (Player p : players) {
                p.addCard(gameCards.get(0))
                gameCards.remove(0)
            }
        }

        while (!(gameCards.get(0) instanceof NumberCard)) {
            boardCard = gameCards.get(0)
            gameCards.remove(0)
            gameCards.add(boardCard)
        }

        boardCard = gameCards.remove(0)
        boardColor = Color.getBackgroundColor(boardCard.getCardColor())
        gameCards.remove(0)
    }

    static boolean checkChoose(Card playerchosenCard, Player player) {
        if (playerchosenCard instanceof WildCard) {
            return true
        }

        if (playerchosenCard instanceof WildDrawCard) {
            for (Card card : player.getPlayerCards()) {
                if (card instanceof WildDrawCard) {
                    continue
                }

                if (checkChoose(card, player)) {
                    return false
                }
            }

            return true
        }

        if (boardCard instanceof Draw2Card && penaltyCards.size() != 0) {
            return playerchosenCard instanceof Draw2Card
        }

        if (boardCard instanceof ReverseCard && playerchosenCard instanceof ReverseCard) {
            return true
        }

        if (Color.getBackgroundColor(playerchosenCard.getCardColor()) == boardColor) {
            return true
        }

        if (playerchosenCard instanceof NumberCard && boardCard instanceof NumberCard) {
            if (playerchosenCard.getCardScore() == boardCard.getCardScore()) {
                return true
            }
        }

        if (playerchosenCard instanceof SkipCard && boardCard instanceof SkipCard) {
            return true
        }

        return false
    }

    static void applyChoose(Card playerchosenCard, Color chosenColor) {
        changeBoardCard(playerchosenCard)
        boardColor = Color.getBackgroundColor(chosenColor)

        if (playerchosenCard instanceof WildDrawCard) {
            for (int n = 0; n < 4; n++) {
                penaltyCards.add(gameCards.get(0))
                gameCards.remove(0)
            }
        } else if (playerchosenCard instanceof Draw2Card) {
            for (int n = 0; n < 2; n++) {
                penaltyCards.add(gameCards.get(0))
                gameCards.remove(0)
            }
        }
    }

    static void runGame(Scanner inputs) {
        Player currentPlayer
        int currentPlayerindex = firstPlayer()
        Card playerchosenCard
        String holdInput
        Bot bot

        while (!endGame()) {
            currentPlayer = players.get(currentPlayerindex)

            if (penaltyCards.size() != 0) {
                boolean check = false
                for (Card card : currentPlayer.getPlayerCards()) {
                    if (card instanceof Draw2Card) {
                        check = true
                        break
                    }
                }

                if (!check) {
                    int n = penaltyCards.size()
                    for (; n > 0; n--) {
                        currentPlayer.addCard(penaltyCards.get(0))
                        penaltyCards.remove(0)
                    }
                }

                currentPlayerindex = ((currentPlayerindex + 1) % players.size())
                continue
            }

            if (!checkPlayerCards(currentPlayer)) {
                giveCardToPlayer(currentPlayer)

                if (!checkPlayerCards(currentPlayer)) {
                    Printer.printGameBoard(boardCard, boardColor)
                    Printer.printNumberOfPlayersCards(players, currentPlayerindex)
                    Printer.printPlayerCards(currentPlayer)

                    if (!(currentPlayer instanceof Bot)) {
                        Printer.noChoiceError(inputs)
                    }

                    currentPlayerindex = ((currentPlayerindex + 1) % players.size())
                    continue
                }
            }

            if (currentPlayer instanceof Bot) {
                bot = (Bot) currentPlayer

                playerchosenCard = bot.playTurn(players, penaltyCards, currentPlayerindex)

                currentPlayerindex = setIndex(playerchosenCard, currentPlayerindex)
                continue
            }

            while (true) {
                while (true) {
                    Printer.printGameBoard(boardCard, boardColor)
                    Printer.printNumberOfPlayersCards(players, currentPlayerindex)
                    Printer.printPlayerCards(currentPlayer)

                    Printer.getPlayerChoice(currentPlayer)
                    holdInput = inputs.nextLine()

                    if (holdInput.length() > 0 && holdInput.length() < 4 && isInt(holdInput)) {
                        if (Integer.valueOf(holdInput) <= 108 && Integer.valueOf(holdInput) > 0) {
                            if (currentPlayer.haveCard(Integer.valueOf(holdInput))) {
                                break
                            }
                        }
                    }

                    Printer.inValidInputError(inputs)
                }

                playerchosenCard = currentPlayer.removeCard(Integer.valueOf(holdInput))

                if (checkChoose(playerchosenCard, currentPlayer)) {
                    if (playerchosenCard instanceof WildCard || playerchosenCard instanceof WildDrawCard) {
                        while (true) {
                            Printer.getPlayerChosenColor()
                            holdInput = inputs.nextLine()

                            if (holdInput.length() == 1 && holdInput.charAt(0) > '0' && holdInput.charAt(0) < '5') {
                                break
                            }

                            Printer.inValidInputError(inputs)

                            Printer.printGameBoard(boardCard, boardColor)
                            Printer.printNumberOfPlayersCards(players, currentPlayerindex)
                            Printer.printPlayerCards(currentPlayer)
                        }

                        switch (holdInput) {
                            case "1":
                                applyChoose(playerchosenCard, Color.RED)
                                break

                            case "2":
                                applyChoose(playerchosenCard, Color.YELLOW)
                                break

                            case "3":
                                applyChoose(playerchosenCard, Color.GREEN)
                                break

                            case "4":
                                applyChoose(playerchosenCard, Color.BLUE)
                                break
                        }
                    } else {
                        applyChoose(playerchosenCard, playerchosenCard.getCardColor())
                    }

                    break
                }

                currentPlayer.addCard(playerchosenCard)

                Printer.inValidInputError(inputs)
            }

            if (playerchosenCard instanceof WildDrawCard) {
                int index = (currentPlayerindex + 1) % players.size()
                int n = penaltyCards.size()

                for (; n > 0; n--) {
                    players.get(index).addCard(penaltyCards.get(0))
                    penaltyCards.remove(0)
                }
            }

            currentPlayerindex = setIndex(playerchosenCard, currentPlayerindex)
        }

        sortPlayers()
        Printer.printScores(players, inputs)
    }

    static void reset() {
        players = new ArrayList<>()
        gameCards = new ArrayList<>()
        penaltyCards = new ArrayList<>()
    }

    private static int firstPlayer() {
        Random rand = new Random()
        return rand.nextInt(players.size())
    }

    private static void revesePlayers() {
        Player holdPlayer

        for (def first = 0, end = players.size() - 1; first < players.size() / 2; first++, end--) {
            holdPlayer = players.get(first)
            players.set(first, players.get(end))
            players.set(end, holdPlayer)
        }
    }

    private static void sortPlayers() {
        Player holdPlayer

        for (int i = 0; i < players.size(); i++) {
            for (int j = i; j < players.size(); j++) {
                if (players.get(i).getScore() > players.get(j).getScore()) {
                    holdPlayer = players.get(i)
                    players.set(i, players.get(j))
                    players.set(j, holdPlayer)
                } else if (players.get(i).getScore() == players.get(j).getScore() &&
                        (players.get(i).getNumberOfPlayerCards() > players.get(j).getNumberOfPlayerCards())) {
                    holdPlayer = players.get(i)
                    players.set(i, players.get(j))
                    players.set(j, holdPlayer)
                }
            }
        }
    }

    private static boolean checkPlayerCards(Player player) {
        for (Card card : player.getPlayerCards()) {
            if (checkChoose(card, player)) {
                return true
            }
        }

        return false
    }

    private static void giveCardToPlayer(Player currentPlayer) {
        currentPlayer.addCard(gameCards.get(0))
        gameCards.remove(0)
    }

    private static void changeBoardCard(Card newCard) {
        gameCards.add(boardCard)
        boardCard = newCard
    }

    private static int setIndex(Card playerchosenCard, int currentPlayerindex) {
        if (playerchosenCard instanceof SkipCard || playerchosenCard instanceof WildDrawCard) {
            currentPlayerindex = currentPlayerindex + 2
        } else if (playerchosenCard instanceof ReverseCard) {
            revesePlayers()
            currentPlayerindex = (players.size() - currentPlayerindex)
        } else if (currentPlayerindex + 1 == players.size()) {
            currentPlayerindex = 0
        } else {
            currentPlayerindex++
        }

        return (currentPlayerindex % players.size())
    }

    private static boolean endGame() {
        for (Player player : players) {
            if (player.getNumberOfPlayerCards() == 0) {
                return true
            }
        }
        return false
    }

    private static void makeGameCards() {
        int cardCode = 0

        makeCards(Color.RED, cardCode)
        cardCode += 25

        makeCards(Color.YELLOW, cardCode)
        cardCode += 25

        makeCards(Color.GREEN, cardCode)
        cardCode += 25

        makeCards(Color.BLUE, cardCode)
        cardCode += 25

        for (int n = 0; n < 4; n++) {
            gameCards.add(new WildCard(++cardCode))
        }

        for (int n = 0; n < 4; n++) {
            gameCards.add(new WildDrawCard(++cardCode))
        }
    }

    private static void makeCards(Color cardColor, int cardCode) {
        gameCards.add(new NumberCard(0, cardColor, ++cardCode))
        gameCards.add(new NumberCard(1, cardColor, ++cardCode))
        gameCards.add(new NumberCard(2, cardColor, ++cardCode))
        gameCards.add(new NumberCard(3, cardColor, ++cardCode))
        gameCards.add(new NumberCard(4, cardColor, ++cardCode))
        gameCards.add(new NumberCard(5, cardColor, ++cardCode))
        gameCards.add(new NumberCard(6, cardColor, ++cardCode))
        gameCards.add(new NumberCard(7, cardColor, ++cardCode))
        gameCards.add(new NumberCard(8, cardColor, ++cardCode))
        gameCards.add(new NumberCard(9, cardColor, ++cardCode))

        gameCards.add(new NumberCard(1, cardColor, ++cardCode))
        gameCards.add(new NumberCard(2, cardColor, ++cardCode))
        gameCards.add(new NumberCard(3, cardColor, ++cardCode))
        gameCards.add(new NumberCard(4, cardColor, ++cardCode))
        gameCards.add(new NumberCard(5, cardColor, ++cardCode))
        gameCards.add(new NumberCard(6, cardColor, ++cardCode))
        gameCards.add(new NumberCard(7, cardColor, ++cardCode))
        gameCards.add(new NumberCard(8, cardColor, ++cardCode))
        gameCards.add(new NumberCard(9, cardColor, ++cardCode))

        gameCards.add(new SkipCard(cardColor, ++cardCode))
        gameCards.add(new SkipCard(cardColor, ++cardCode))

        gameCards.add(new ReverseCard(cardColor, ++cardCode))
        gameCards.add(new ReverseCard(cardColor, ++cardCode))

        gameCards.add(new Draw2Card(cardColor, ++cardCode))
        gameCards.add(new Draw2Card(cardColor, ++cardCode))
    }

    private static void shuffleCards() {
        ArrayList<Integer> shuffledCards = new ArrayList<>()

        Card holdCard

        Random rand = new Random()

        int randNum1 = 0, randNum2 = 0

        for (def n = 0; n < 40; n++) {
            while (true) {
                randNum1 = rand.nextInt(108)

                if (!shuffledCards.contains(randNum1)) {
                    break
                }
            }
            shuffledCards.add(randNum1)

            while (true) {
                randNum2 = rand.nextInt(108)

                if (!shuffledCards.contains(randNum2) && randNum2 != randNum1) {
                    break
                }
            }
            shuffledCards.add(randNum2)

            holdCard = gameCards.get(randNum1)
            gameCards.set(randNum1, gameCards.get(randNum2))
            gameCards.set(randNum2, holdCard)
        }
    }

    private static boolean isInt(String stringToCheck) {
        for (def n = 0; n < stringToCheck.length(); n++) {
            if (!('0' <= stringToCheck.charAt(n) && stringToCheck.charAt(0) <= '9')) {
                return false
            }
        }

        return true
    }
}
