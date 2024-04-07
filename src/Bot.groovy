class Bot extends Player {

    Bot(int num) {
        super("Bot$num")
    }

    Card playTurn(List<Player> players, List<Card> penaltyCards, int botIndex) {
        Card botChosenCard = null
        for (int n = 0; n < getPlayerCards().size(); n++) {
            botChosenCard = getPlayerCards()[n]
            if (Rules.checkChoose(botChosenCard, this)) {
                getPlayerCards().remove(botChosenCard)
                score -= botChosenCard.getCardScore()
                break
            }
        }

        if (botChosenCard instanceof WildCard || botChosenCard instanceof WildDrawCard) {
            Random rand = new Random()
            switch (rand.nextInt(4)+1) {
                case 1:
                    Rules.applyChoose(botChosenCard, Color.RED)
                    break
                case 2:
                    Rules.applyChoose(botChosenCard, Color.YELLOW)
                    break
                case 3:
                    Rules.applyChoose(botChosenCard, Color.GREEN)
                    break
                case 4:
                    Rules.applyChoose(botChosenCard, Color.BLUE)
                    break
            }
        } else {
            Rules.applyChoose(botChosenCard, botChosenCard.getCardColor())
        }

        if (botChosenCard instanceof WildDrawCard) {
            int index = (botIndex+1)%players.size()
            for (def n = penaltyCards.size(); n > 0; n--) {
                players[index].addCard(penaltyCards[0])
                penaltyCards.remove(0)
            }
        }

        return botChosenCard
    }
}
