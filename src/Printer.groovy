class Printer {
    private static final String INDENT = "\t\t\t      "

    static void calibrate(Scanner finish) {
        clear()

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
        System.out.println(Color.getColorCodeString(Color.RESET) + INDENT + "\b\b\b\b\b\b\b" +
                "Use (ctrl, +) e (ctrl, -) para ajustar a linha na sua tela")

        System.out.println("<~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~>")
        finishEnter(finish)
    }

    static void printMenu() {
        clear()
        System.out.println(Color.getColorCodeString(Color.RESET))

        System.out.println(INDENT + "\t       " + "    <••••••. UNO Game .••••••> ️")
        System.out.print("\n")
        System.out.println(INDENT + "\t      " + "            1. Novo jogo")
        System.out.print("\n")
        System.out.println(INDENT + "\t      " + "              2. Fim")
        System.out.print("\n")
    }

    static void getNumberOfThePlayers() {
        clear()

        System.out.println(INDENT + "\b\b\b" +
                "Insira a quantidade de jogadores (1 < n < 8):  ")
    }

    static void getPlayerName(int playerNum) {
        clear()

        System.out.print(INDENT + "\b\b\b\b\b\b\b\b\b\b\b" +
                "Insira o nome do jogador" + playerNum +" (se quiser que seja pc, insira bot):  ")
    }

    static void getPlayerChosenColor() {
        System.out.print("Você usou a carta Wild. Escolha uma cor ( " + Color.getColorCodeString(Color.WHITE) +
                Color.getColorCodeString(Color.RED_B) + " 1 " + Color.getColorCodeString(Color.RESET) +
                ", " + Color.getColorCodeString(Color.WHITE) +
                Color.getColorCodeString(Color.YELLOW_B) + " 2 " + Color.getColorCodeString(Color.RESET) +
                ", " + Color.getColorCodeString(Color.WHITE) +
                Color.getColorCodeString(Color.GREEN_B) + " 3 " + Color.getColorCodeString(Color.RESET) +
                ", " + Color.getColorCodeString(Color.WHITE) +
                Color.getColorCodeString(Color.BLUE_B) + " 4 " + Color.getColorCodeString(Color.RESET) +
                " ) :   ")
    }

    static void printGameBoard(Card theCardOnTheBoard, Color colorOnTheBoard) {
        clear()

        for (def j = 1; j <= 7; j++) {
            System.out.print(INDENT + "\t\t\b" + theCardOnTheBoard.toString(-j) + "    " + theCardOnTheBoard.toString(j))
            if (j == 2) {
                System.out.print("  Cor Mesa")
            } else if (j > 2 && j < 6) {
                System.out.print("    " + Color.getColorCodeString(colorOnTheBoard) + "      " +
                        Color.getColorCodeString(Color.RESET))
            }

            System.out.print("\n")
        }

        System.out.print("\n\n\n")
    }

    static void printNumberOfPlayersCards(ArrayList<Player> players, int currentPlayerIndex) {
        System.out.println("\nNúmero de cartas jogadas:\n")

        def cntr = 0
        for (Player player: players) {
            System.out.print("\t " + player.getName() + ":  " + player.getNumberOfPlayerCards())
            if (cntr == 0 && (currentPlayerIndex == (players.size()-1))) {
                System.out.print("\t---> (proximo jogador)")
            }

            if (cntr == currentPlayerIndex) {
                System.out.print("\t---> (jogador atual)")
            } else if ((cntr-1) == currentPlayerIndex) {
                System.out.print("\t---> (proximo jogador)")
            }

            System.out.print("\n")
            cntr++
        }

        System.out.print("\n\n")
    }

    static void printPlayerCards(Player player) {
        for (def j = 0; j < player.getPlayerCards().size(); j += 9) {
            for (def i = 1; i <= 8; i++) {
                System.out.print("\t\b")
                for (def k = j; (k < j+9) && (k < player.getPlayerCards().size()); k++) {
                    System.out.print(player.getPlayerCards().get(k).toString(i) + "  ")
                    if (i == 8) {
                        for(int space =  player.getPlayerCards().get(k).toString(i-1).length() - player.getPlayerCards().get(k).toString(i).length(); space > 0; space--) {
                            System.out.print(" ")
                        }
                    }
                }
                System.out.print("\n")
            }
            System.out.print("\n")
        }
    }

    static void getPlayerChoice(Player player) {
        System.out.print("\nJogador " + Color.getColorCodeString(Color.BLACK_BRIGHT_B) +
                player.getName() + Color.getColorCodeString(Color.RESET) +
                " escolha uma carta (insira o codigo da carta):  ")
    }


    static void printScores(ArrayList<Player> players, Scanner finish) {
        clear()
        System.out.print(Color.getColorCodeString(Color.WHITE)  + "\n\n\n\n")
        System.out.println(INDENT + "\b\b\b\b\b\bNome dos Jogadores |  Score dos jogadores       Número de cartas dos jogadores")
        System.out.println(INDENT + "\b\b\b\b\b\b–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––")

        Player currPlayer
        for (def index = 0; index < players.size(); index++) {
            currPlayer = players.get(index)
            System.out.printf("%s\b\b\b\b\b\b%13s :  %7d                      %8d\n", INDENT,
                    currPlayer.getName(),
                    currPlayer.getScore(),
                    currPlayer.getNumberOfPlayerCards())
        }

        System.out.print("\n\n\n" + Color.getColorCodeString(Color.RESET))
        finishEnter(finish)
    }

    static void inValidInputError(Scanner finish) {
        System.out.println(INDENT + "\t         " +
                Color.getColorCodeString(Color.YELLOW)+ Color.getColorCodeString(Color.RED) +
                "Sua entrada não é válida!" +
                Color.getColorCodeString(Color.RESET))
        finishEnter(finish)
    }

    static void noChoiceError(Scanner finish) {
        System.out.println("\t\t\t" +
                Color.getColorCodeString(Color.YELLOW)+ Color.getColorCodeString(Color.RED) +
                "Você não têm cartas que possam ser jogadas. Você sacou uma carta!" +
                Color.getColorCodeString(Color.RESET))

        finishEnter(finish)
    }

    private static void finishEnter(Scanner inputsSource) {
        System.out.println(INDENT + "\t\t    " + "(Enter para continuar)")
        inputsSource.nextLine()
    }

    private static void clear() {
        System.out.print("\033[H\033[2J")
        System.out.flush()
    }
}
