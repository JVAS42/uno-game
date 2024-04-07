class Player {
    private String name

    protected int score

    private List<Card> playerCards = []

    Player(String name) {

        this.name = name
        this.score = 0
    }

    String getName() {
        return name
    }

    int getScore() {
        return score
    }

    List<Card> getPlayerCards() {
        return playerCards
    }

    int getNumberOfPlayerCards() {
        return playerCards.size()
    }

    void addCard(Card cardToAdd) {
        score += cardToAdd.getCardScore()
        playerCards.add(cardToAdd)
    }

    Card removeCard(int cardCodeToRemove) {
        Card cardToRemove = playerCards.find { it.getCardCode() == cardCodeToRemove }

        if (cardToRemove) {
            score -= cardToRemove.getCardScore()
            playerCards.remove(cardToRemove)
        }

        return cardToRemove
    }

    boolean haveCard(int cardCode) {
        return playerCards.any { it.getCardCode() == cardCode }
    }
}
